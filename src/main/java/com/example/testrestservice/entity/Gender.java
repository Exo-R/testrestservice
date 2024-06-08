package com.example.testrestservice.entity;

import com.example.testrestservice.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@AllArgsConstructor
public enum Gender {

    MALE("MALE", 1),
    FEMALE("FEMALE", 2);

    private final String value;
    private final int code;

    public static Gender fromValue(String par, String nameEnum)  {
        Gender[] values = Gender.values();
        for(Gender value : values) {
            if (value.getValue().equals(par.toUpperCase())) {
                return value;
            }
        }
        String errorMsg = String.format("%s not found!", nameEnum);
        log.error(errorMsg);
        throw new ValidationException(String.format("%s not found!", nameEnum));
    }

}
