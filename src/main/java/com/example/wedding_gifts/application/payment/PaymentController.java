package com.example.wedding_gifts.application.payment;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wedding_gifts.common.Validation;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.GetPaymentByPaidDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.PaymentResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentExecutionException;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.payment.IPaymentController;
import com.example.wedding_gifts.core.usecases.payment.IPaymentUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payment")
public class PaymentController implements IPaymentController {

    @Autowired
    private IPaymentUseCase service;

    @Override
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> createPayment(
        @RequestBody CreatePaymentDTO payment
    ) throws Exception {
        try {
            validData(payment);
            
            Payment newPayment = service.createPayment(payment);
            PaymentResponseDTO responsePayment = new PaymentResponseDTO(
                newPayment.getPaymentDescription(), 
                newPayment.getPaymentCode(), 
                newPayment.getExpiration()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responsePayment);
        } catch (Exception e) {
            PaymentExecutionException exception = new PaymentExecutionException("Some error", e);
            exception.setPath("/payment/create");
            
            throw exception;
        }
    }

    @Override
    @GetMapping(value = "/isPaid{payment}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> isPaid(
        @PathVariable UUID paymentId
    ) throws Exception {
        try {
            String message = service.isPaid(paymentId) ? "YES" : "NO";

            return ResponseEntity.ok(new MessageDTO(message));
        } catch (Exception e) {
            PaymentExecutionException exception = new PaymentExecutionException("Some error", e);
            exception.setPath("/payment/isPaid");

            throw exception;
        }
    }

    @Override
    @GetMapping(value = "/isExpired{payment}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> isExpired(UUID paymentId) throws Exception {try {
            String message = service.isExpired(paymentId) ? "YES" : "NO";

            return ResponseEntity.ok(new MessageDTO(message));
        } catch (Exception e) {
            PaymentExecutionException exception = new PaymentExecutionException("Some error", e);
            exception.setPath("/payment/isExpired");

            throw exception;
        }
    }

    @Override
    @GetMapping(value = "/{account}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Payment>> getAll(
        @PathVariable UUID accountId, 
        Pageable paging
    ) {
        return ResponseEntity.ok(service.getAllPayments(accountId, paging));
    }

    @Override
    @GetMapping(value = "/paid{account}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Payment>> getByIsPaid(
        @RequestBody GetPaymentByPaidDTO paidFilter, 
        Pageable paging
    ) throws Exception {
        try {
            validData(paidFilter);

            return ResponseEntity.ok(service.getByIsPaid(paidFilter, paging));
        } catch (Exception e) {
            PaymentExecutionException exception = new PaymentExecutionException("Some error", e);
            exception.setPath(null);

            throw exception;
        }
    }

    private void validData(CreatePaymentDTO data) throws Exception {
        String invalid = "%s is invalid";
        String isNull = "%s is null";
        
        if((data.cpf() == null || data.cpf().isEmpty()) && (data.cnpj() == null || data.cnpj().isEmpty())) throw new Exception(isNull);
        if(data.name() == null || data.name().isEmpty()) throw new Exception(isNull);

        if(data.cpf() != null && !Validation.cpf(data.cpf()))  throw new Exception(invalid);
        if(data.cnpj() != null && !Validation.cnpj(data.cnpj()))  throw new Exception(invalid);
        if(!Validation.name(data.name()))  throw new Exception(invalid);

    }

    private void validData(GetPaymentByPaidDTO data) throws Exception {
        String isNull = "%s is null";
        
        if(data.accountId() == null) throw new Exception(isNull);
        if(data.isPaid() == null || data.isPaid()) throw new Exception(isNull);

    }

}