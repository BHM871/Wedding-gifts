package com.example.wedding_gifts.infra.pix;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adobe.internal.xmp.impl.Base64;
import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.common.LimitTimeForPix;
import com.example.wedding_gifts.core.domain.dtos.oauthpsb.CreateOAuthPsb;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CalendarDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatePixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatedPixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.PayerDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.ResponseBBOauthError;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.ResponsePixError;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.ValueDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentGatewayException;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.OAuthPsb;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.example.wedding_gifts.core.usecases.oauthpsb.IOAuthPsbUsecase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class PixServices implements PaymentAdapter {

    @Autowired
    private IGiftUseCase giftService;
    @Autowired
    private IOAuthPsbUsecase oauthService;
    @Autowired
    private PixHttpConfig httpConfig;

    @Value("${api.payment.pix.url}")
    private String basePixUrl;

    @Value("${api.payment.oauth.url}") 
    private String oauthTokenUrl;

    @Value("${api.payment.pix.client-id}")
    private String clientId;

    @Value("${api.payment.pix.client-secret}")
    private String clientSecret;

    @Value("${api.payment.pix.scope}")
    private String scope;

    private final String MESSAGE_PIX = "Cobrança para %s, do presente %s para %s com o valor %.2f";

    private final String MESSAGE_ERROR = "\n\t\"code\": %d,\n\t\"title\": %s,\n\t\"detail\": %s";

    @Override
    public Payment createPayment(CreatePaymentDTO payment) throws Exception {
        try{
            Gift gift = giftService.getById(payment.giftId());

            CreatePixDTO createPix = new CreatePixDTO(
                new CalendarDTO(
                    null,
                    LimitTimeForPix.getLimit()
                ), 
                new PayerDTO(
                    payment.name(), 
                    payment.cpf() != null ? payment.cpf() : null,
                    payment.cnpj() != null ? payment.cnpj() : null
                ), 
                new ValueDTO(
                    gift.getPrice().toString(),
                    gift.getPrice(),
                    0
                ),
                gift.getAccount().getPixKey(), 
                String.format(
                    MESSAGE_PIX, 
                    payment.name(), 
                    gift.getTitle(), 
                    gift.getAccount().getFirstName()+" "+gift.getAccount().getLastName(),
                    gift.getPrice()
                )
            );

            String token = getOauthToken();

            HttpClient client = httpConfig.getClientWithCert();

            HttpRequest request = httpConfig.getRequest(token, basePixUrl+"/cob", "POST", createPix.toString());

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 201) {
                throw new PaymentGatewayException(response.body());
            }

            String body = response.body().replaceFirst("(\\-|\\+)\\d{2}\\:\\d{2}", "");
            CreatedPixDTO createdPix = generatedClass(body, CreatedPixDTO.class);

            Payment newPayment = new Payment(createdPix);
            newPayment.setPaymentCode(getPaymentCode(newPayment));
            newPayment.setGift(gift);
            newPayment.setAccount(gift.getAccount());

            return newPayment;
        } catch (MyException e){
            throw e;
        } catch (JsonProcessingException e){
            throw new PaymentGatewayException("Error in map Gateway JSON response", e);
        } catch (Exception e){
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    @Override
    public Payment checkPayment(Payment payment) throws Exception {
        try {

            String token = getOauthToken();

            HttpClient client = httpConfig.getClientWithCert();

            HttpRequest request = httpConfig.getRequest(token, basePixUrl+"/"+payment.getTransactionId(), "GET", "");

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                ResponsePixError error = generatedClass(response.body(), ResponsePixError.class);

                throw new PaymentGatewayException(String.format(MESSAGE_ERROR, error.status(), error.title(), error.detail()));
            }

            CreatedPixDTO createdPix = generatedClass(response.body(), CreatedPixDTO.class);

            payment.update(createdPix);

            return payment;
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    @Override
    public String getPaymentCode(Payment payment) throws Exception {
        try {
            String token = getOauthToken();

            HttpClient client = httpConfig.getClientWithCert();

            HttpRequest request = httpConfig.getRequest(token, "https://"+payment.getPaymentCode(), "GET", "");

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                ResponsePixError error = generatedClass(response.body(), ResponsePixError.class);

                throw new PaymentGatewayException(String.format(MESSAGE_ERROR, error.status(), error.title(), error.detail()));
            }

            return response.body();
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    private String getOauthToken() throws Exception {

        OAuthPsb oauth = oauthService.getOAuth();

        if(oauth != null) return oauth.getAuthToken();

        try {

            HttpClient client = httpConfig.getClient();

            String body = "grant_type=client_credentials&scope="+scope;

            HttpRequest request = httpConfig.getRequestForOauth(Base64.encode(clientId+":"+clientSecret), oauthTokenUrl, body);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                String messageError = "\n\t\"error\": %s,\n\t\"error_description\": %s\n";
                ResponseBBOauthError error = generatedClass(response.body(), ResponseBBOauthError.class);

                throw new PaymentGatewayException(String.format(messageError, error.error(), error.error_description()));
            }

            CreateOAuthPsb oauthPsb = generatedClass(response.body(), CreateOAuthPsb.class);
            oauthService.createOAuth(oauthPsb);

            return oauthPsb.access_token();
        } catch (MyException e){
            throw e;
        } catch (Exception e) {
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    private <T> T generatedClass(String json, Class<T> typeClass) throws JsonMappingException, JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(json.getBytes(), typeClass);
    }
    
}
