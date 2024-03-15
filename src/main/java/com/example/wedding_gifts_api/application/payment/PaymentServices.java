package com.example.wedding_gifts_api.application.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts_api.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts_api.common.MyZone;
import com.example.wedding_gifts_api.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts_api.core.domain.exceptions.payment.PaymentNotFoundException;
import com.example.wedding_gifts_api.core.domain.model.Gift;
import com.example.wedding_gifts_api.core.domain.model.Payment;
import com.example.wedding_gifts_api.core.domain.model.util.PaymentStatus;
import com.example.wedding_gifts_api.core.usecases.payment.IPaymentRepository;
import com.example.wedding_gifts_api.core.usecases.payment.IPaymentUseCase;
import com.example.wedding_gifts_api.infra.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts_api.infra.dtos.payment.GetPaymentByPaidDTO;

@Service
@SuppressWarnings("null")
public class PaymentServices implements IPaymentUseCase {

    @Autowired
    private IPaymentRepository repository;
    @Autowired
    private PaymentAdapter paymentAdapter;

    private final String DECRIPTION_PAYMENT = "Gift payment \"%s\", for bride and groom \"%s\", with value R$%d";

    @Override
    public Payment createPayment(UUID giftId, CreatePaymentDTO payment) throws Exception {
        Payment newPayment = paymentAdapter.createPayment(giftId, payment);

        Gift gift = newPayment.getGift();
        newPayment.setPaymentDescription(
            String.format(
                DECRIPTION_PAYMENT, 
                gift.getTitle(), 
                gift.getAccount().getBrideGroom(), 
                gift.getPrice().doubleValue()
            )
        );

        return repository.createPayment(newPayment);
    }

    @Override
    public boolean isPaid(UUID id) throws Exception {
        Payment payment = repository.getById(id);

        if(
            payment.getPaid() != null && 
            payment.getPaymentStatus() == PaymentStatus.COMPLETE
        ) return true;

        payment = paymentAdapter.checkPayment(payment);

        repository.savePayment(payment);

        if(payment.getPaid() != null && payment.getPaymentStatus() == PaymentStatus.COMPLETE){
            paid(id);
            return true;
        }
            
        return false;
    }

    @Override
    public Payment paid(UUID id) throws Exception {
        return repository.paid(id);
    }

    @Override
    public boolean isExpired(UUID id) throws Exception {
        try{
            Payment payment = repository.getById(id);
            payment = paymentAdapter.checkPayment(payment);

            repository.savePayment(payment);

            if(payment.getExpiration().isBefore(LocalDateTime.now())){
                return true;
            }

            return false;
        } catch (PaymentNotFoundException e){
            return true;
        } catch (MyException e){
            throw e;
        }
    }

    @Override
    public Page<Payment> getAllPayments(UUID account, Pageable paging) throws Exception {
        Page<Payment> payments = repository.getAllPayments(account, paging);
        List<Payment> paymentsList = payments.getContent();

        List<Payment> paymentsForDelete = new ArrayList<>();
        for(Payment p : paymentsList){
            if(p.getExpiration().plusMonths(3).isBefore(LocalDateTime.now(MyZone.zoneId()))){
                paymentsForDelete.add(p);
            }
        }
        repository.deleteAll(paymentsForDelete);

        return new PageImpl<Payment>(paymentsList, paging, payments.getTotalElements());
    }

    @Override
    public Page<Payment> getByIsPaid(UUID account, GetPaymentByPaidDTO paidFilter, Pageable paging) throws Exception {
        Page<Payment> payments = repository.getByIsPaid(account, paidFilter, paging);
        List<Payment> paymentsList = payments.getContent();

        List<Payment> paymentsForDelete = new ArrayList<>();
        for(Payment p : paymentsList){
            if(p.getExpiration().plusMonths(3).isBefore(LocalDateTime.now(MyZone.zoneId()))){
                paymentsForDelete.add(p);
            }
        }
        repository.deleteAll(paymentsForDelete);

        return new PageImpl<Payment>(paymentsList, paging, payments.getTotalElements());
    }
    
}
