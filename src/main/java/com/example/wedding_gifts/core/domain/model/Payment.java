package com.example.wedding_gifts.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.example.wedding_gifts.core.domain.model.util.MethodOfPayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String payer;

    @NonNull
    private String payerCpf;

    @NonNull
    private Double paymentValue;

    @NonNull
    private LocalDateTime creation;

    @NonNull
    private LocalDateTime expiration;

    private LocalDateTime paid;

    @NonNull
    private Boolean isPaid;

    @Column(unique = true)
    private String paymentCode;

    @NonNull
    @Enumerated(EnumType.STRING)
    private MethodOfPayment method;

    @NonNull
    @Column(unique = true)
    private UUID giftId;

    @NonNull
    private UUID accountId;

}