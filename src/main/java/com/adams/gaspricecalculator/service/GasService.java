package com.adams.gaspricecalculator.service;

import com.adams.gaspricecalculator.error.GasValidationException;
import com.adams.gaspricecalculator.model.incoming.GasBillDTO;
import com.adams.gaspricecalculator.model.outcoming.ApiFieldError;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GasService {

    private final GasCheckService gasCheckService;

    public GasService(GasCheckService gasCheckService) {
        this.gasCheckService = gasCheckService;
    }

    public void addBill(GasBillDTO gasBill) throws GasValidationException {
        addBills(List.of(gasBill));
    }

    public void addBills(List<GasBillDTO> gasBills) throws GasValidationException {
        List<ApiFieldError> errors = new ArrayList<>();
        for (GasBillDTO gasBill : gasBills) {
            errors.addAll(gasCheckService.checkGasBill(gasBill));
        }

        if (CollectionUtils.isNotEmpty(errors)) {
            throw new GasValidationException("By the validation from the gas bills, there was a error", errors);
        }
    }
}
