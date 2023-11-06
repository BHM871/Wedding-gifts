package com.example.wedding_gifts.infra.pix;

import org.springframework.stereotype.Service;

import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.common.LimitTimeForPix;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CalendarDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatePixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatedPixDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.PayerDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.pix.ValueDTO;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class PixServices implements PaymentAdapter {

    private final String BASE_PIX_URL = "https://pix.example.com/api/cob";
    private final String PIX_CONTENT_TYPE = "application/json";
    private final String CREATE_PIX_METHOD = "POST";
    private final String MESSAGE_PIX = "Cobrança para %s, do presente %s para %s com o valor %d";

    private IGiftUseCase giftService;

    public PixServices(IGiftUseCase giftService){
        this.giftService = giftService;
    }

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

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse(PIX_CONTENT_TYPE);
            RequestBody body = RequestBody.create(createPix.toString(), mediaType);

            Request request = new Request.Builder()
                .url(BASE_PIX_URL)
                .put(body)
                .method(CREATE_PIX_METHOD, body)
                .addHeader("Content-Type", PIX_CONTENT_TYPE)
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
    public Payment checkPayment(Payment payment) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkPayment'");
    }
    
}
