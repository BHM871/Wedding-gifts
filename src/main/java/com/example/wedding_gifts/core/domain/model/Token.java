package com.example.wedding_gifts.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String tokenValue;

    private LocalDateTime limitHour;

    @OneToOne
    private Account account;
    
    public Token(SaveTokenDTO tokenDto) throws Exception {
        String message = "Some value is invalid";

        if(tokenDto.token() == null) throw new Exception(message);
        if(tokenDto.limitHour() == null || tokenDto.limitHour().isBefore(LocalDateTime.now(MyZone.zoneId()))) throw new Exception(message);

        this.tokenValue = tokenDto.token();
        this.limitHour = tokenDto.limitHour();
    }

}