package com.adams.gaspricecalculator.model.outcoming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@ApiModel(value = "ApiFieldError", description = "Your Api Field Error Response")
public class ApiFieldError {

    @ApiModelProperty(value = "The field with the error", example = "gasPrice")
    private String field;

    @ApiModelProperty(value = "The description to the error", example = "The gasPrice is to high")
    private String description;
}
