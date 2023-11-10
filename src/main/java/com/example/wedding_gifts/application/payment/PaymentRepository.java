package com.example.wedding_gifts.application.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.payment.GetPaymentByPaidDTO;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.payment.IPaymentRepository;
import com.example.wedding_gifts.infra.jpa.JpaPaymentRepository;

@Repository
public class PaymentRepository implements IPaymentRepository {

    @Autowired
    private JpaPaymentRepository jpaRepository;

    @Override
    public Payment savePayment(Payment payment) throws Exception {
        try {
            return jpaRepository.save(payment);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Payment createPayment(Payment payment) throws Exception {
        try {
            return savePayment(payment);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Payment paid(UUID paymentId) throws Exception {
        try {
            Payment payment = getById(paymentId);

            payment.setIsPaid(true);
            payment.setPaid(LocalDateTime.now());

            return savePayment(payment);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deletePayment(UUID paymentId) throws Exception {
        try {
            Payment payment = getById(paymentId);

            jpaRepository.delete(payment);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Payment getById(UUID paymentId) throws Exception {
        return jpaRepository.findById(paymentId).orElseThrow(() -> new Exception());
    }

    @Override
    public Page<Payment> getAllPayments(UUID accountId, Pageable paging) {
        return jpaRepository.findByAccountId(accountId, paging);
    }

    @Override
    public Page<Payment> getByIsPaid(GetPaymentByPaidDTO paidFilter, Pageable paging) {
        return jpaRepository.findByIsPaidAndAccountId(paidFilter.isPaid(), paidFilter.accountId(), paging);
    }
    
}
