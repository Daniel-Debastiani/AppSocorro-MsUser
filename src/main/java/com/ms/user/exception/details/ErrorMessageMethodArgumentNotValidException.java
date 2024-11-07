package com.ms.user.exception.details;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageMethodArgumentNotValidException {
    private String field;
    private String message;
    private String objectName;
    private Object rejectedValue;
}
