package com.example.wedding_gifts.core.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "tb_gift")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gift implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String title;

    private String giftDescription;
    
    @Enumerated(EnumType.STRING)
    private List<CategoriesEnum> categories;

    @NonNull
    private BigDecimal price;

    private Boolean isBought;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    public Gift(CreateGiftDTO gift) {
        this.title = gift.title();
        this.giftDescription = gift.giftDescription();
        this.categories = gift.categories();
        this.price = gift.price();
        this.isBought = false;
    }

    public void update(UpdateGiftDTO gift) throws Exception {
        String message = "Some value is invalid";

        if(gift.title() != null && gift.title().length() > 3) this.title = gift.title();
        else if(gift.title() != null) throw new Exception(message);

        if(gift.giftDescription() != null && gift.giftDescription().length() > 10) this.giftDescription = gift.giftDescription();
        else if(gift.giftDescription() != null) throw new Exception(message);

        if(gift.categories() != null) this.categories = gift.categories();

        if(gift.price() != null) this.price = gift.price();

        if(gift.isBought() != null) this.isBought = gift.isBought();

    }

}
