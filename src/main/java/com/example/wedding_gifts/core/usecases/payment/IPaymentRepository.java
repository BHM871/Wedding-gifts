package com.example.wedding_gifts.core.usecases.payment;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.infra.dtos.payment.GetPaymentByPaidDTO;

public interface IPaymentRepository {
    
    public Payment savePayment(Payment payment) throws Exception;

    public Payment createPayment(Payment payment) throws Exception;

    public Payment paid(UUID paymentId) throws Exception;

    public void deletePayment(UUID paymentId) throws Exception;

    public void deleteAll(List<Payment> payments) throws Exception;

    public Payment getById(UUID paymentId) throws Exception;

    public Page<Payment> getAllPayments(UUID accountId, Pageable paging);

    public Page<Payment> getByIsPaid(GetPaymentByPaidDTO paidFilter, Pageable paging);

}