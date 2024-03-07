package com.example.wedding_gifts.infra.dtos.payment.pix;

import com.example.wedding_gifts.core.domain.model.util.PixStatus;

public record CreatedPixDTO(
    CalendarDTO calendario,
    String txid,
    String location,
    PixStatus status,
    PayerDTO devedor,
    ValueDTO valor,
    String chave,
    int revisao,
    String pixCopiaECola,
    String solicitacaoPagador,
    AdicionalInformationDTO[] infoAdicionais
){}
