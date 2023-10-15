package com.example.wedding_gifts.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
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

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String tokenValue;

    @NonNull
    private LocalDateTime limitHour;

    @NonNull
    @OneToOne
    @MapsId
    private Account account;
    
    public Token(SaveTokenDTO tokenDto) throws Exception {
        String message = "Some value is invalid";

        if(tokenDto.token() == null) throw new Exception(message);
        if(tokenDto.limitHour() == null || tokenDto.limitHour().isBefore(LocalDateTime.now())) throw new Exception(message);

        this.tokenValue = tokenDto.token();
        this.limitHour = tokenDto.limitHour();
    }

}