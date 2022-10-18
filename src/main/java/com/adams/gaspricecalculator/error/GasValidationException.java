package com.adams.gaspricecalculator.error;

import com.adams.gaspricecalculator.model.outcoming.ApiFieldError;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GasValidationException extends RuntimeException {

    private String message;
    private List<ApiFieldError> apiFieldErrors;

}
