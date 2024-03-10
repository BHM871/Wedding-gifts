package com.example.wedding_gifts_api.core.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts_api.common.Validation;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftInvalidValueException;
import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;
import com.example.wedding_gifts_api.infra.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.UpdateGiftDTO;

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
@Table(name = "tb_gift")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gift implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String giftDescription;
    
    @Enumerated(EnumType.STRING)
    private List<CategoriesEnum> categories;

    private BigDecimal price;

    private Boolean isBought;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    public Gift(CreateGiftDTO gift) {
        this.title = gift.title().trim();
        this.giftDescription = gift.giftDescription() != null ? gift.giftDescription().trim() : "";
        this.categories = gift.categories();
        this.price = gift.price();
        this.isBought = false;
    }

    public void update(UpdateGiftDTO gift) throws Exception {
        String message = "%s is invalid";

        if(gift.title() != null && !Validation.title(gift.title())) throw new GiftInvalidValueException(String.format(message, "title"));
        if(gift.giftDescription() != null && !Validation.description(gift.giftDescription())) throw new GiftInvalidValueException(String.format(message, "description")); 
        if(gift.price() != null && !Validation.price(gift.price())) throw new GiftInvalidValueException(String.format(message, "price"));

        if(gift.title() != null) this.title = gift.title().trim();
        if(gift.giftDescription() != null) this.giftDescription = gift.giftDescription().trim();
        if(gift.categories() != null) this.categories = gift.categories();
        if(gift.price() != null) this.price = gift.price();
        if(gift.isBought() != null) this.isBought = gift.isBought();

    }

}
