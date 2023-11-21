package com.example.wedding_gifts.infra.pix;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.common.LimitTimeForPix;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CalendarDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatePixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatedPixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.PayerDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.ResponsePixError;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.ValueDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentGatewayException;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PixServices implements PaymentAdapter {

    private IGiftUseCase giftService;

    public PixServices(IGiftUseCase giftService){
        this.giftService = giftService;
    }

    private final long TIMEOUT_SECONDS = 8L;

    @Value("${api.payment.pix.url}")
    private static String BASE_PIX_URL;
    @Value("${api.payment.pix.client-id}")
    private static String CLIEND_ID;
    @Value("${api.payment.pix.client-secret}")
    private static String CLIENT_SECRET;

    private final String PIX_CONTENT_TYPE = "application/json";
    private final String CREATE_PIX_METHOD = "POST";
    private final String MESSAGE_PIX = "Cobrança para %s, do presente %s para %s com o valor .2%f";

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
                    gift.getPrice().toString()  
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

            OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

            MediaType mediaType = MediaType.parse(PIX_CONTENT_TYPE);
            RequestBody body = RequestBody.create(createPix.toString(), mediaType);

            Request request = new Request.Builder()
                .url(BASE_PIX_URL)
                .method(CREATE_PIX_METHOD, body)
                .addHeader("Content-Type", PIX_CONTENT_TYPE)
                .build();

            Response response = client.newCall(request).execute();

            if(response.code() != 201) {
                String MESSAGE_ERROR = "\n\t\"code\": %d,\n\t\"title\": %s,\n\t\"detail\": %s";
                ResponsePixError error = generatedClass(response.body(), ResponsePixError.class);

                throw new PaymentGatewayException(String.format(MESSAGE_ERROR, error.status(), error.title(), error.detail()));
            }

            CreatedPixDTO createdPix = generatedClass(response.body(), CreatedPixDTO.class);

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
            OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

            Request request = new Request.Builder()
                .url(BASE_PIX_URL+"/"+payment.getTransactionId())
                .get()
                .addHeader("Content-Type", PIX_CONTENT_TYPE)
                .build();

            Response response = client.newCall(request).execute();

            if(response.code() != 200) {
                String MESSAGE_ERROR = "\n\t\"code\": %d,\n\t\"title\": %s,\n\t\"detail\": %s";
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

            Request request = new Request.Builder()
                .url("https://"+payment.getPaymentCode())
                .get()
                .addHeader("Content-Type", PIX_CONTENT_TYPE)
                .build();

            Response response = client.newCall(request).execute();

            if(response.code() != 200) {
                String MESSAGE_ERROR = "\n\t\"code\": %d,\n\t\"title\": %s,\n\t\"detail\": %s";
                ResponsePixError error = generatedClass(response.body(), ResponsePixError.class);

                throw new PaymentGatewayException(String.format(MESSAGE_ERROR, error.status(), error.title(), error.detail()));
            }

            return new String(response.body().bytes());
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new PaymentGatewayException("Some error in request Gateway", e);
        }
    }

    private <T> T generatedClass(ResponseBody json, Class<T> typeClass) throws JsonMappingException, JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new String(json.bytes()), typeClass);
    }
    
}
