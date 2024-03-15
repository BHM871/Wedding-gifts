package com.example.wedding_gifts_api.infra.dtos.payment.pix;

import com.example.wedding_gifts_api.core.domain.model.util.PaymentStatus;

public record CreatedPixDTO(
    CalendarDTO calendario,
    String txid,
    String location,
    PaymentStatus status,
    PayerDTO devedor,
    ValueDTO valor,
    String chave,
    int revisao,
    String pixCopiaECola,
    String solicitacaoPagador,
    AdicionalInformationDTO[] infoAdicionais
){}
