package com.example.wedding_gifts.core.usecases.payment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.infra.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.infra.dtos.payment.GetPaymentByPaidDTO;

public interface IPaymentUseCase {

    public Payment createPayment(CreatePaymentDTO payment) throws Exception;

    public boolean isPaid(UUID paymentId) throws Exception;

    public Payment paid(UUID paymentid) throws Exception;

    public boolean isExpired(UUID paymentId) throws Exception;

    public Page<Payment> getAllPayments(UUID accountId, Pageable paging);

    public Page<Payment> getByIsPaid(GetPaymentByPaidDTO paidFilter, Pageable paging);

}