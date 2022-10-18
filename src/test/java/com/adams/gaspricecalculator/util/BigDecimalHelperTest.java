package com.adams.gaspricecalculator.util;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigDecimalHelperTest {

    @Test
    public void scaleTest() {
        assertEquals(BigDecimalHelper.scale(BigDecimal.valueOf(0.995)).compareTo(BigDecimal.ONE), 0);
        assertEquals(BigDecimalHelper.scale(BigDecimal.valueOf(0.004)).compareTo(BigDecimal.ZERO), 0);
        assertEquals(BigDecimalHelper.scale(BigDecimal.valueOf(0.996)).compareTo(BigDecimal.ONE), 0);
    }
}
