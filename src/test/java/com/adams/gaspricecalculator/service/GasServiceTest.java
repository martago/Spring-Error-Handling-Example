package com.adams.gaspricecalculator.service;

import com.adams.gaspricecalculator.error.GasValidationException;
import com.adams.gaspricecalculator.model.incoming.GasBillDTO;
import com.adams.gaspricecalculator.model.outcoming.ApiFieldError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GasServiceTest {

    private static ZonedDateTime zonedDateTime = ZonedDateTime.of(2016, 3, 4, 11, 30, 0, 0, ZoneId.of("GMT"));

    @InjectMocks
    private GasService gasService;

    @Mock
    private GasCheckService gasCheckService;

    @Mock
    private Clock clock;

    private GasBillDTO gasBillDTO;

    @BeforeEach
    public void setUp() {
        when(clock.getZone()).thenReturn(zonedDateTime.getZone());
        when(clock.instant()).thenReturn(zonedDateTime.toInstant());

        gasBillDTO = new GasBillDTO();
        gasBillDTO.setDate(LocalDate.now(clock));
        gasBillDTO.setFees(BigDecimal.ZERO);
        gasBillDTO.setLiterAmount(BigDecimal.valueOf(1203));
        gasBillDTO.setLiterPrice(BigDecimal.valueOf(0.685));
        gasBillDTO.setTaxPercent(BigDecimal.valueOf(19));
        gasBillDTO.setTaxes(BigDecimal.valueOf(156.57));
        gasBillDTO.setCompleteAmount(BigDecimal.valueOf(980.63));
    }

    @Test
    public void addBillTestNoError() {
        boolean errorStatus = false;
        try {
            gasService.addBill(gasBillDTO);
        } catch (GasValidationException ex) {
            errorStatus = true;
        }

        assertEquals(errorStatus, false);
    }

    @Test
    public void addBillTestWithError() {
        List<ApiFieldError> apiFieldErrorList = new ArrayList<>();
        apiFieldErrorList.add(new ApiFieldError("completeAmount", "Testdescription"));
        when(gasCheckService.checkGasBill(any())).thenReturn(apiFieldErrorList);

        boolean errorStatus = false;
        String errorMessage = null;
        try {
            gasService.addBill(gasBillDTO);
        } catch (GasValidationException ex) {
            errorStatus = true;
            errorMessage = ex.getMessage();
        }

        assertEquals(errorStatus, true);
        assertEquals(errorMessage, "By the validation from the gas bills, there was a error");
    }

}
