package com.example.wedding_gifts_api.application.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.wedding_gifts_api.common.MyZone;
import com.example.wedding_gifts_api.core.domain.exceptions.payment.PaymentExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.payment.PaymentNotFoundException;
import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.core.domain.model.Gift;
import com.example.wedding_gifts_api.core.domain.model.Payment;
import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;
import com.example.wedding_gifts_api.core.domain.model.util.MethodOfPayment;
import com.example.wedding_gifts_api.core.domain.model.util.PixStatus;
import com.example.wedding_gifts_api.infra.jpa.JpaPaymentRepository;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
public class PaymentRepositoryTest {
    
    Payment payment;
    @InjectMocks
    PaymentRepository repository;

    @Mock
    JpaPaymentRepository jpaRepository;

    @BeforeEach
    void setUp(){
        payment = new Payment(
            UUID.randomUUID(), 
            "this-is-a-transaction-id", 
            "Adrian Almeida", 
            "07131121231", 
            BigDecimal.valueOf(400), 
            "Pagamento do \"Ventilador\"", 
            LocalDateTime.now(MyZone.zoneId()), 
            LocalDateTime.now(MyZone.zoneId()).plusMinutes(5L), 
            null, 
            false, 
            "5654fdsaf1sd65fa4sd9f56sd51fdsfa9efds651fa1f6a5sd1fae8wfds6a5f1a", 
            MethodOfPayment.PIX, 
            PixStatus.ATIVA, 
            new Gift(
                UUID.randomUUID(),
                "Ventilador",
                "É um vetilador porra",
                List.of(CategoriesEnum.BEDROOM, CategoriesEnum.ROOM),
                BigDecimal.valueOf(400),
                false, 
                new Account(
                    UUID.randomUUID(), 
                    "AdrianAdria", 
                    Date.from(Instant.now().plusSeconds(300000)),
                    "Adrian", 
                    "Almeida", 
                    "adrian@gmail.com", 
                    "Adrian@123", 
                    "07131121231"
                )
            ), 
            new Account(
                UUID.randomUUID(), 
                "AdrianAdria", 
                Date.from(Instant.now().plusSeconds(300000)),
                "Adrian", 
                "Almeida", 
                "adrian@gmail.com", 
                "Adrian@123", 
                "07131121231"
            )
        );
    }

    @Test
    void savedPaymentIsSuccess() throws Exception{
        when(jpaRepository.save(payment)).thenReturn(payment);

        Payment savedPayment = repository.savePayment(payment);

        assertEquals(payment, savedPayment);
        verify(jpaRepository).save(payment);
        verifyNoMoreInteractions(jpaRepository);
    } 

    @Test
    void savedPaymentIsFailure() throws Exception{
        when(jpaRepository.save(payment)).thenThrow(new RuntimeException("Falha"));

        PaymentExecutionException paymentThrow = assertThrows(PaymentExecutionException.class, () -> {
            repository.savePayment(payment);
        });

        assertNotNull(paymentThrow);
        assertEquals("Payment can't be saved", paymentThrow.getMessage());
        
        assertNotNull(paymentThrow.getCause());
        assertEquals("Falha", paymentThrow.getCause().getMessage());
        
        verify(jpaRepository).save(payment);
        verifyNoMoreInteractions(jpaRepository);
    } 

    @Test
    void createPayment() throws Exception{
        when(jpaRepository.save(payment)).thenReturn(payment);

        Payment savedPayment = repository.createPayment(payment);

        assertEquals(payment, savedPayment);
        verify(jpaRepository).save(payment);
        verifyNoMoreInteractions(jpaRepository);
    } 

    @Test
    void createPaymentIsFailure() throws Exception{
        when(jpaRepository.save(payment)).thenThrow(new RuntimeException("Falha"));

        PaymentExecutionException paymentThrow = assertThrows(PaymentExecutionException.class, () -> {
            repository.createPayment(payment);
        });

        assertNotNull(paymentThrow);
        assertEquals("Payment can't be saved", paymentThrow.getMessage());
        
        assertNotNull(paymentThrow.getCause());
        assertEquals("Falha", paymentThrow.getCause().getMessage());
        
        verify(jpaRepository).save(payment);
        verifyNoMoreInteractions(jpaRepository);
    } 

    @Test
    void paidIsSuccess() throws Exception {
        when(jpaRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        payment.setIsPaid(true);
        payment.setPaid(LocalDateTime.now(MyZone.zoneId()));

        when(jpaRepository.save(payment)).thenReturn(payment);

        Payment pym = repository.paid(payment.getId());

        assertEquals(pym, payment);

        verify(jpaRepository).findById(payment.getId());
        verify(jpaRepository).save(payment);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void paisIsFailure1(){
        when(jpaRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        payment.setPaid(LocalDateTime.now(MyZone.zoneId()));
        payment.setIsPaid(true);

        when(jpaRepository.save(payment)).thenThrow(new RuntimeException("Falha"));

        PaymentExecutionException e = assertThrows(PaymentExecutionException.class, () -> {
            repository.paid(payment.getId());
        });

        assertNotNull(e);
        assertEquals("Payment can't be saved", e.getMessage());

        assertNotNull(e.getCause());
        assertEquals("Falha", e.getCause().getMessage());

        verify(jpaRepository).findById(payment.getId());
        verify(jpaRepository).save(payment);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void paisIsFailure2(){
        when(jpaRepository.findById(payment.getId())).thenReturn(Optional.empty());

        PaymentNotFoundException e = assertThrows(PaymentNotFoundException.class, () -> {
            repository.paid(payment.getId());
        });

        assertNotNull(e);
        assertEquals(
            String.format("Payment with id %s not found", payment.getId().toString()), 
            e.getMessage()
        );

        verify(jpaRepository).findById(payment.getId());
        verifyNoMoreInteractions(jpaRepository);
    }
}
