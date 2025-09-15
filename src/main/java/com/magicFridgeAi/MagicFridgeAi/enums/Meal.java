package com.magicFridgeAi.MagicFridgeAi.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Meal {
    CAFE_DA_MANHA("cafe-da-manha"),
    ALMOCO("almoco"),
    CAFE_DA_TARDE("cafe-da-tarde"),
    JANTA("janta");

    private final String value;

    Meal(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonValue
    public String toString(){
        return value;
    }


}
