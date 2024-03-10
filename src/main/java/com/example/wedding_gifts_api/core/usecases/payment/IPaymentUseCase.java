package com.example.wedding_gifts_api.core.usecases.payment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts_api.core.domain.model.Payment;
import com.example.wedding_gifts_api.infra.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts_api.infra.dtos.payment.GetPaymentByPaidDTO;

public interface IPaymentUseCase {

    public Payment createPayment(UUID giftId, CreatePaymentDTO payment) throws Exception;

    public boolean isPaid(UUID paymentId) throws Exception;

    public Payment paid(UUID paymentid) throws Exception;

    public boolean isExpired(UUID paymentId) throws Exception;

    public Page<Payment> getAllPayments(UUID accountId, Pageable paging);

    public Page<Payment> getByIsPaid(UUID accountId, GetPaymentByPaidDTO paidFilter, Pageable paging);

}