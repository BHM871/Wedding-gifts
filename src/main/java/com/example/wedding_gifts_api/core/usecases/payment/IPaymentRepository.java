package com.example.wedding_gifts_api.core.usecases.payment;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts_api.core.domain.model.Payment;
import com.example.wedding_gifts_api.infra.dtos.payment.GetPaymentByPaidDTO;

public interface IPaymentRepository {
    
    public Payment savePayment(Payment payment) throws Exception;

    public Payment createPayment(Payment payment) throws Exception;

    public Payment paid(UUID id) throws Exception;

    public void deletePayment(UUID id) throws Exception;

    public void deleteAll(List<Payment> payments) throws Exception;

    public Payment getById(UUID id) throws Exception;

    public Page<Payment> getAllPayments(UUID account, Pageable paging);

    public Page<Payment> getByIsPaid(UUID account, GetPaymentByPaidDTO paidFilter, Pageable paging);

}