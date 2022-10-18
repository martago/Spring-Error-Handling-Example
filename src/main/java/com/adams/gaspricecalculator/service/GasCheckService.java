package com.adams.gaspricecalculator.service;

import com.adams.gaspricecalculator.model.incoming.GasBillDTO;
import com.adams.gaspricecalculator.model.outcoming.ApiFieldError;
import com.adams.gaspricecalculator.util.BigDecimalHelper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GasCheckService {

    public List<ApiFieldError> checkGasBill(GasBillDTO gasBill) {
        List<ApiFieldError> errors = new ArrayList<>();
        checkGasprices(gasBill, errors);
        // TODO: checkTaxRatio
        return errors;
    }

    private void checkGasprices(GasBillDTO gasBill, List<ApiFieldError> errors) {
        BigDecimal literFullPrice = BigDecimalHelper.scale(gasBill.getLiterAmount().multiply(gasBill.getLiterPrice()));
        BigDecimal completeAmount = BigDecimalHelper.scale(BigDecimal.ZERO.add(gasBill.getTaxes()).add(gasBill.getFees()).add(literFullPrice));
        BigDecimal completeAmountUser = BigDecimalHelper.scale(gasBill.getCompleteAmount());

        if (completeAmount.compareTo(completeAmountUser) != 0) {
            errors.add(new ApiFieldError("completeAmount",
                    String.format(
                    "Bill-date: %s / Your complete amount does not fit. Your entry: %s calculated entry: %s " +
                            "/ Calculation: (literPrice * literAmount) + taxes + fees == completeAmount",
                    gasBill.getDate(),
                    completeAmountUser,
                    completeAmount)));
        }
    }
}