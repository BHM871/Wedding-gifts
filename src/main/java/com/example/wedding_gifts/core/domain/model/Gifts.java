package com.example.wedding_gifts.core.domain.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gifts {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NonNull
    private String title;

    private String description;
    
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CategoriesEnum.class, fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "categories")
    private List<CategoriesEnum> categories;

    @NonNull
    private BigDecimal value;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "images")
    @JoinColumn(referencedColumnName = "id")
    private List<Image> images;

    private boolean isBought;

}
