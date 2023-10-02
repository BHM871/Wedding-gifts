package com.example.wedding_gifts.core.domain.model;

public enum CategoriesEnum {
    
    KITCHEN,
    ROOM,
    BEDROOM,
    TOILET;

    public String toString(){
        return this.name();
    }

}
