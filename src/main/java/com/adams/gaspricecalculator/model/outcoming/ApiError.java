package com.adams.gaspricecalculator.model.outcoming;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@ApiModel(value = "ApiError", description = "Your Api Error Response")
public class ApiError {

    @ApiModelProperty(value = "The http code from the server", example = "406")
    private int httpCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "The Timestamp from the server", example = "2022-10-25 10:25:30")
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "The message from the server to the client", example = "Error")
    private String message;

    @ApiModelProperty(value = "The sub errors, so you can find the wrong attribute")
    private List<ApiFieldError> apiFieldErrors;

}
