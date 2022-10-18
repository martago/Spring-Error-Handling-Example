package com.adams.gaspricecalculator.service;

import com.adams.gaspricecalculator.model.incoming.GasBillDTO;
import com.adams.gaspricecalculator.model.outcoming.ApiFieldError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GasCheckServiceTest {

    private static ZonedDateTime zonedDateTime = ZonedDateTime.of(2016, 3, 4, 11, 30, 0, 0, ZoneId.of("GMT"));

    private GasCheckService gasCheckService;
    private GasBillDTO gasBillDTO;

    @Mock
    private Clock clock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(clock.getZone()).thenReturn(zonedDateTime.getZone());
        when(clock.instant()).thenReturn(zonedDateTime.toInstant());

        gasCheckService = new GasCheckService();


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
    public void checkGasBillTestNoError() {
        List<ApiFieldError> apiFieldErrors = gasCheckService.checkGasBill(gasBillDTO);

        assertNotNull(apiFieldErrors);
        assertEquals(apiFieldErrors.size(), 0);
    }

    @Test
    public void checkGasBillTestWithError() {
        gasBillDTO.setFees(BigDecimal.TEN);

        List<ApiFieldError> apiFieldErrors = gasCheckService.checkGasBill(gasBillDTO);

        assertNotNull(apiFieldErrors);
        assertEquals(apiFieldErrors.size(), 1);
        assertEquals(apiFieldErrors.get(0).getField(), "completeAmount");
    }

}
