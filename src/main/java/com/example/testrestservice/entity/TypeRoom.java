package com.example.testrestservice.entity;

import com.example.testrestservice.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@AllArgsConstructor
public enum TypeRoom {

    MALE("MALE", 1),
    FEMALE("FEMALE", 2);

    private final String value;
    private final int code;

    public static TypeRoom fromValue(String par, String nameEnum) {
        TypeRoom[] values = TypeRoom.values();
        for(TypeRoom value : values) {
            if(value.getValue().equals(par.toUpperCase())) {
                return value;
            }
        }
        String errorMsg = String.format("%s not found!", nameEnum);
        log.error(errorMsg);
        throw new ValidationException(errorMsg);
    }

}
