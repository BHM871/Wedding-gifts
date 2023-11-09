package com.example.wedding_gifts.application.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.GetPaymentByPaidDTO;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.payment.IPaymentRepository;
import com.example.wedding_gifts.core.usecases.payment.IPaymentUseCase;

@Service
public class PaymentServices implements IPaymentUseCase {

    @Autowired
    private IPaymentRepository repository;
    @Autowired
    private PaymentAdapter paymentAdapter;

    @Override
    public Payment createPayment(CreatePaymentDTO payment) throws Exception {
        return repository.createPayment(paymentAdapter.createPayment(payment));
    }

    @Override
    public boolean isPaid(UUID paymentId) throws Exception {
        Payment payment = repository.getById(paymentId);

        if(payment.getIsPaid()) return true;

        payment = paymentAdapter.checkPayment(payment);

        if(payment.getIsPaid()){
            paid(paymentId);
            return true;
        }
            
        return false;
    }

    @Override
    public Payment paid(UUID paymentid) throws Exception {
        return repository.paid(paymentid);
    }

    @Override
    public boolean isExpired(UUID paymentId) throws Exception {
        Payment payment = repository.getById(paymentId);
        paymentAdapter.checkPayment(payment);

        if(payment.getExpiration().isBefore(LocalDateTime.now())){
            repository.deletePayment(paymentId);
            return true;
        }

        return false;
    }

    @Override
    public Page<Payment> getAllPayments(UUID accountI, Pageable paging) {
        return repository.getAllPayments(accountI, paging);
    }

    @Override
    public Page<Payment> getByIsPaid(GetPaymentByPaidDTO paidFilter, Pageable paging) {
        return repository.getByIsPaid(paidFilter, paging);
    }
    
}
