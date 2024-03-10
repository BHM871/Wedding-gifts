package com.example.wedding_gifts_api.core.domain.model.util;

public enum CategoriesEnum {
    
    KITCHEN,
    ROOM,
    BEDROOM,
    TOILET;

    public String toString(){
        return this.name();
    }

}
