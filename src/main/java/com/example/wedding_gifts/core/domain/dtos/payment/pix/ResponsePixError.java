package com.example.wedding_gifts.core.domain.dtos.payment.pix;

import java.util.List;

public record ResponsePixError(
    String type,
    String title,
    int status,
    String detail,
    List<ViolationsDTO> violacoes
){}
