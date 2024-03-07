package com.example.wedding_gifts.config.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.wedding_gifts.adapters.payment.PaymentAdapter;
import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.core.domain.model.util.MethodOfPayment;
import com.example.wedding_gifts.infra.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.infra.pix.PixServices;

@Component
@Primary
public class PaymentServiceInjector implements PaymentAdapter {

    @Autowired
    private PixServices pixServices;

    private PaymentAdapter paymentFactory(MethodOfPayment methodOfPayment){
        switch (methodOfPayment) {
            case PIX:
                return pixServices;
            default:
                return null;
        }
    }

    @Override
    public Payment createPayment(CreatePaymentDTO payment) throws Exception {
        return paymentFactory(payment.method()).createPayment(payment);
    }

    @Override
    public Payment checkPayment(Payment payment) throws Exception {
        return paymentFactory(payment.getMethod()).checkPayment(payment);
    }

    @Override
    public String getPaymentCode(Payment payment) throws Exception {
        return paymentFactory(payment.getMethod()).getPaymentCode(payment);
    }

}
