package com.example.wedding_gifts.adapters.payment;

public interface PaymentAdapter<T> {

    public T createPayment() throws Exception;

    public T checkPayment() throws Exception;
    
}
