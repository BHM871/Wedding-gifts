package com.example.wedding_gifts.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatedPixDTO;
import com.example.wedding_gifts.core.domain.model.util.MethodOfPayment;
import com.example.wedding_gifts.core.domain.model.util.PixStatus;

import jakarta.persistence.Column;
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

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String transactionId;

    @NonNull
    private String payer;

    @NonNull
    private String payerCpf;

    @NonNull
    private BigDecimal paymentValue;

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
        this.paymentCode = pix.location();
        this.method = MethodOfPayment.PIX;
    }

    public void update(CreatedPixDTO pix) {
        this.transactionId = pix.txid();
        this.payer = pix.devedor().nome();
        this.payerCpf = pix.devedor().cpf() != null ? pix.devedor().cpf() : null;
        this.paymentValue = new BigDecimal(pix.valor().original());
        this.creation = pix.calendario().criacao();
        this.expiration = LocalDateTime.of(pix.calendario().criacao().toLocalDate(), pix.calendario().criacao().toLocalTime())
            .plusSeconds(pix.calendario().expiracao());
        this.paid = pix.status() == PixStatus.CONCLUIDA ? LocalDateTime.now() : null;
        this.isPaid = pix.status() == PixStatus.CONCLUIDA ? true : false;
        this.paymentCode = pix.location();
        this.method = MethodOfPayment.PIX;
    }

}