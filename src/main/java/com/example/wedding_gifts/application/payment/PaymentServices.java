package com.example.wedding_gifts.application.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.dtos.payment.GetPaymentByPaidDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentNotFoundException;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.example.wedding_gifts.core.usecases.payment.IPaymentRepository;
import com.example.wedding_gifts.core.usecases.payment.IPaymentUseCase;

@Service
@SuppressWarnings("null")
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
    public Page<Payment> getAllPayments(UUID accountI, Pageable paging) {
        Page<Payment> payments = repository.getAllPayments(accountI, paging);
        List<Payment> paymentsList = payments.getContent();

        List<Payment> paymentsForDelete = new ArrayList<>();
        for(Payment p : paymentsList){
            if(p.getExpiration().plusMonths(3).isBefore(LocalDateTime.now(MyZone.zoneId()))){
                paymentsForDelete.add(p);
            }
        }
        for(Payment p : paymentsForDelete){
            paymentsList.remove(p);
        }

        return new PageImpl<Payment>(paymentsList, paging, payments.getTotalElements());
    }

    @Override
    public Page<Payment> getByIsPaid(GetPaymentByPaidDTO paidFilter, Pageable paging) {
        Page<Payment> payments = repository.getByIsPaid(paidFilter, paging);
        List<Payment> paymentsList = payments.getContent();

        List<Payment> paymentsForDelete = new ArrayList<>();
        for(Payment p : paymentsList){
            if(p.getExpiration().plusMonths(3).isBefore(LocalDateTime.now(MyZone.zoneId()))){
                paymentsForDelete.add(p);
            }
        }
        for(Payment p : paymentsForDelete){
            paymentsList.remove(p);
        }

        return new PageImpl<Payment>(paymentsList, paging, payments.getTotalElements());
    }
    
}
