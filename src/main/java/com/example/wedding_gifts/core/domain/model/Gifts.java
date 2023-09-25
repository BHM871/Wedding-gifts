package com.example.wedding_gifts.core.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Gifts {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String title;

    private String description;
    
    @Enumerated(EnumType.STRING)
    private List<CategoriesEnum> categories;

    @NonNull
    private BigDecimal price;

    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Image> images;

    private boolean isBought;

}
