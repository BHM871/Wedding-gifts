package com.example.wedding_gifts.core.usecases.payment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.infra.dtos.commun.MessageDTO;
import com.example.wedding_gifts.infra.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.infra.dtos.payment.GetPaymentByPaidDTO;
import com.example.wedding_gifts.infra.dtos.payment.PaymentResponseDTO;

public interface IPaymentController {

    public ResponseEntity<PaymentResponseDTO> createPayment(UUID gift, CreatePaymentDTO payment) throws Exception;

    public ResponseEntity<MessageDTO> isPaid(UUID payment) throws Exception;

    public ResponseEntity<MessageDTO> isExpired(UUID payment) throws Exception;

    public ResponseEntity<Page<Payment>> getAll(String token, UUID account, Pageable paging) throws Exception;

    public ResponseEntity<Page<Payment>> getByIsPaid(String token, UUID account, GetPaymentByPaidDTO paidFilter, Pageable paging) throws Exception;
    
}
