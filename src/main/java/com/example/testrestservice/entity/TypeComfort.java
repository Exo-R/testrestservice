package com.example.testrestservice.entity;

import com.example.testrestservice.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@AllArgsConstructor
public enum TypeComfort {

    STANDARD("STANDARD", 1),
    EXTRA_COMFORT("EXTRA_COMFORT", 2),
    LUX("LUX", 3);

    private final String value;
    private final int code;

    public static TypeComfort fromValue(String par, String nameEnum) {
        TypeComfort[] values = TypeComfort.values();
        for(TypeComfort value : values) {
            if(value.getValue().equals(par.toUpperCase())) {
                return value;
            }
        }
        String errorMsg = String.format("%s not found!", nameEnum);
        log.error(errorMsg);
        throw new ValidationException(String.format("%s not found!", nameEnum));
    }

}
