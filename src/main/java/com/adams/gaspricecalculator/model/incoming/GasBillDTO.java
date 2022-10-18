package com.adams.gaspricecalculator.model.incoming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "GasBillDTO", description = "Your Gas bill entity, it provides for example the information about the liter price.")
public class GasBillDTO {

    @ApiModelProperty(value = "The date of your gas bill", example = "2022-10-25")
    @NotNull
    private LocalDate date;

    @ApiModelProperty(value = "The price of the gas based on liter", example = "0.685")
    @NotNull
    private BigDecimal literPrice;

    @ApiModelProperty(value = "The amount of gas liter", example = "1203")
    @NotNull
    private BigDecimal literAmount;

    @ApiModelProperty(value = "The fees amount you had payed", example = "0")
    @NotNull
    private BigDecimal fees;

    @ApiModelProperty(value = "The tax amount you had payed", example = "156.57")
    @NotNull
    private BigDecimal taxes;

    @ApiModelProperty(value = "The tax percent", example = "19")
    @NotNull
    private BigDecimal taxPercent;

    @ApiModelProperty(value = "The full amount of your bill", example = "980.63")
    @NotNull
    private BigDecimal completeAmount;

}