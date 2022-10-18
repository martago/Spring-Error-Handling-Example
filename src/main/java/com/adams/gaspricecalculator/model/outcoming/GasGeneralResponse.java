package com.adams.gaspricecalculator.model.outcoming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@ApiModel(value = "GasGeneralResponse", description = "Your Gas General Response")
public class GasGeneralResponse {

    @ApiModelProperty(value = "The message from the server to the client", example = "OK")
    private String message;
    @ApiModelProperty(value = "The http code from the server", example = "201")
    private int httpCode;

}