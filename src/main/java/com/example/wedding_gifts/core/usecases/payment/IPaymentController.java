package com.example.wedding_gifts.core.usecases.payment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.GetPaymentByPaidDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.PaymentResponseDTO;
import com.example.wedding_gifts.core.domain.model.Payment;

public interface IPaymentController {

    public ResponseEntity<PaymentResponseDTO> createPayment(CreatePaymentDTO payment) throws Exception;

    public ResponseEntity<MessageDTO> isPaid(UUID paymentId) throws Exception;

    public ResponseEntity<MessageDTO> isExpired(UUID paymentId) throws Exception;

    public ResponseEntity<Page<Payment>> getAll(UUID accountId, Pageable paging);

    public ResponseEntity<Page<Payment>> getByIsPaid(GetPaymentByPaidDTO paidFilter, Pageable paging) throws Exception;
    
}
