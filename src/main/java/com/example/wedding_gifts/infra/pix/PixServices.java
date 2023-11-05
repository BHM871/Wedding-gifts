package com.example.wedding_gifts.infra.pix;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatePixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatedPixDTO;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class PixServices implements PaymentAdapter {

    private final String BASE_PIX_URL = "https://pix.example.com/api/cob/";
    
    private final String CREATE_PIX_CONTENT_TYPE = "application/json";

    @Override
    public Payment createPayment(CreatePixDTO payment) throws Exception {
        
        try{
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse(CREATE_PIX_CONTENT_TYPE);
            RequestBody body = RequestBody.create(payment.toString(), mediaType);

            Request request = new Request.Builder()
                .url(BASE_PIX_URL+payment.accountId())
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "insomnia/2023.5.8")
                .build();

            Response response = client.newCall(request).execute();

            if(response.code() != 201) throw new Exception();

            ObjectMapper mapper = new ObjectMapper();
            CreatedPixDTO createdPix = mapper.readValue(response.body().toString(), CreatedPixDTO.class);

            return new Payment(createdPix);
        } catch (Exception e){
            throw e;
        }
        
    }

    @Override
    public Payment checkPayment(UUID paymentId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkPayment'");
    }
    
}
