package com.example.testrestservice.util;

import com.example.testrestservice.exception.ValidationException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Utils {

    public static void checkNotNull(Object object, String nameObject) {
        if (object == null) {
            String errorMsg = String.format("Value %s is null! ", nameObject);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    public static void checkNotEmpty(String line, String nameString) {
        if(line == null || line.isEmpty()){
            String errorMsg = String.format("Value %s is empty/null! ", nameString);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

}
