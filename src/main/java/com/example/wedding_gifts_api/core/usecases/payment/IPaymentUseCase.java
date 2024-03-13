package com.example.wedding_gifts_api.core.usecases.payment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts_api.core.domain.model.Payment;
import com.example.wedding_gifts_api.infra.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts_api.infra.dtos.payment.GetPaymentByPaidDTO;

public interface IPaymentUseCase {

    public Payment createPayment(UUID giftId, CreatePaymentDTO payment) throws Exception;

    public boolean isPaid(UUID id) throws Exception;

    public Payment paid(UUID id) throws Exception;

    public boolean isExpired(UUID id) throws Exception;

    public Page<Payment> getAllPayments(UUID account, Pageable paging) throws Exception;

    public Page<Payment> getByIsPaid(UUID account, GetPaymentByPaidDTO paidFilter, Pageable paging) throws Exception;

}