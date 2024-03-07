package com.example.wedding_gifts.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.util.MethodOfPayment;
import com.example.wedding_gifts.core.domain.model.util.PixStatus;
import com.example.wedding_gifts.infra.dtos.payment.pix.CreatedPixDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String transactionId;

    private String payer;

    private String payerCpf;

    private BigDecimal paymentValue;

    private String paymentDescription;

    private LocalDateTime creation;

    private LocalDateTime expiration;

    private LocalDateTime paid;

    private Boolean isPaid;

    private String paymentCode;

    @Enumerated(EnumType.STRING)
    private MethodOfPayment method;

    @Enumerated(EnumType.STRING)
    private PixStatus paymentStatus;

    @ManyToOne()
    @JoinColumn(name = "gift_id")
    private Gift gift;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    public Payment(CreatedPixDTO pix) {
        this.transactionId = pix.txid();
        this.payer = pix.devedor().nome();
        this.payerCpf = pix.devedor().cpf() != null ? pix.devedor().cpf() : null;
        this.paymentValue = new BigDecimal(pix.valor().original());
        this.creation = pix.calendario().criacao();
        this.expiration = LocalDateTime.of(pix.calendario().criacao().toLocalDate(), pix.calendario().criacao().toLocalTime())
            .plusSeconds(pix.calendario().expiracao());
        this.paid = pix.status() == PixStatus.CONCLUIDA ? LocalDateTime.now() : null;
        this.isPaid = pix.status() == PixStatus.CONCLUIDA ? true : false;
        this.paymentCode = pix.pixCopiaECola();
        this.method = MethodOfPayment.PIX;
        this.paymentStatus = pix.status();
    }

    public Payment update(CreatedPixDTO pix) {
        this.transactionId = pix.txid();
        this.payer = pix.devedor().nome();
        this.payerCpf = pix.devedor().cpf() != null ? pix.devedor().cpf() : null;
        this.paymentValue = new BigDecimal(pix.valor().original());
        this.creation = pix.calendario().criacao();
        this.expiration = LocalDateTime.of(pix.calendario().criacao().toLocalDate(), pix.calendario().criacao().toLocalTime())
            .plusSeconds(pix.calendario().expiracao());
        this.paid = pix.status() == PixStatus.CONCLUIDA ? LocalDateTime.now() : null;
        this.isPaid = pix.status() == PixStatus.CONCLUIDA ? true : false;
        this.paymentCode = pix.pixCopiaECola();
        this.method = MethodOfPayment.PIX;
        this.paymentStatus = pix.status();

        return this;
    }

}