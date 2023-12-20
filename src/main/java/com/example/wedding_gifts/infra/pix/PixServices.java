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

            OAuthPsb token = getOauthToken();

            HttpClient client = httpConfig.getClient();

            HttpRequest request = httpConfig.getRequest(token, basePixUrl+"/cob", "POST", createPix.toString());

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200 && response.statusCode() != 201) {
                throw new PaymentGatewayException(response.body());
            }

            String body = response.body().replaceFirst("(\\-|\\+)\\d{2}\\:\\d{2}", "");
            CreatedPixDTO createdPix = generatedClass(body, CreatedPixDTO.class);

            Payment newPayment = new Payment(createdPix);
            newPayment.setPaymentCode(createdPix.pixCopiaECola());
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

            OAuthPsb token = getOauthToken();

            HttpClient client = httpConfig.getClient();

            HttpRequest request = httpConfig.getRequest(token, basePixUrl+"/"+payment.getTransactionId(), "GET", "");

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200 && response.statusCode() != 201) {
                throw new PaymentGatewayException(response.body());
            }

            CreatedPixDTO createdPix = generatedClass(response.body(), CreatedPixDTO.class);

            return payment.update(createdPix);
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    @Override
    public String getPaymentCode(Payment payment) throws Exception {
        try {
            OAuthPsb token = getOauthToken();

            HttpClient client = httpConfig.getClient();

            HttpRequest request = httpConfig.getRequest(token, "https://"+payment.getPaymentCode(), "GET", "");

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200 && response.statusCode() != 201) {
                throw new PaymentGatewayException(response.body());
            }

            return response.body();
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    private OAuthPsb getOauthToken() throws Exception {

        OAuthPsb oauth = oauthService.getOAuth();

        if(oauth != null) return oauth;

        try {

            HttpClient client = httpConfig.getClient();

            String body = "grant_type=client_credentials&scope="+scope;

            HttpRequest request = httpConfig.getRequestForOauth(Base64.encode(clientId+":"+clientSecret), oauthTokenUrl, body);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200 && response.statusCode() != 201) {
                throw new PaymentGatewayException(response.body());
            }

            CreateOAuthPsb oauthPsb = generatedClass(response.body(), CreateOAuthPsb.class);

            return oauthService.createOAuth(oauthPsb);
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
