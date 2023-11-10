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
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.example.wedding_gifts.core.usecases.payment.IPaymentRepository;
import com.example.wedding_gifts.core.usecases.payment.IPaymentUseCase;

@Service
public class PaymentServices implements IPaymentUseCase {

    @Autowired
    private IPaymentRepository repository;
    @Autowired
    private IGiftUseCase giftService;
    @Autowired
    private PaymentAdapter paymentAdapter;

    private final String DECRIPTION_PAYMENT = "Gift payment \"%s\", for bride and groom \"%s\", with value R$%d";

    @Override
    public Payment createPayment(CreatePaymentDTO payment) throws Exception {
        Payment newPayment = paymentAdapter.createPayment(payment);

        Gift gift = giftService.getById(payment.giftId());
        newPayment.setPaymentDescription(String.format(DECRIPTION_PAYMENT, gift.getTitle(), gift.getAccount().getBrideGroom(), gift.getPrice().doubleValue()));

        return repository.createPayment(newPayment);
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
        try{
            Payment payment = repository.getById(paymentId);
            paymentAdapter.checkPayment(payment);

            if(payment.getExpiration().isBefore(LocalDateTime.now())){
                repository.deletePayment(paymentId);
                return true;
            }

            return false;
        } catch (Exception e){
            return true;
        }
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
