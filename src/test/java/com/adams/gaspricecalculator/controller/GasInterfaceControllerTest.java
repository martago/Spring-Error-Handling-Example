package com.adams.gaspricecalculator.controller;

import com.adams.gaspricecalculator.model.incoming.GasBillDTO;
import com.adams.gaspricecalculator.service.GasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GasInterfaceController.class)
public class GasInterfaceControllerTest {

    private static ZonedDateTime zonedDateTime = ZonedDateTime.of(2016, 3, 4, 11, 30, 0, 0, ZoneId.of("GMT"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GasService gasService;

    @MockBean
    private Clock clock;

    @Test
    public void addBillSuccess() throws Exception {
        when(clock.getZone()).thenReturn(zonedDateTime.getZone());
        when(clock.instant()).thenReturn(zonedDateTime.toInstant());

        GasBillDTO gasBillDTO = new GasBillDTO();
        gasBillDTO.setDate(LocalDate.now(clock));
        gasBillDTO.setFees(BigDecimal.ZERO);
        gasBillDTO.setLiterAmount(BigDecimal.valueOf(1203));
        gasBillDTO.setLiterPrice(BigDecimal.valueOf(0.685));
        gasBillDTO.setTaxPercent(BigDecimal.valueOf(19));
        gasBillDTO.setTaxes(BigDecimal.valueOf(156.57));
        gasBillDTO.setCompleteAmount(BigDecimal.valueOf(980.63));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(gasBillDTO);

        this.mockMvc.perform(post("/addBill").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("All good!")));
    }

    @Test
    public void addBillsSuccess() throws Exception {
        when(clock.getZone()).thenReturn(zonedDateTime.getZone());
        when(clock.instant()).thenReturn(zonedDateTime.toInstant());

        GasBillDTO gasBillDTO = new GasBillDTO();
        gasBillDTO.setDate(LocalDate.now(clock));
        gasBillDTO.setFees(BigDecimal.ZERO);
        gasBillDTO.setLiterAmount(BigDecimal.valueOf(1203));
        gasBillDTO.setLiterPrice(BigDecimal.valueOf(0.685));
        gasBillDTO.setTaxPercent(BigDecimal.valueOf(19));
        gasBillDTO.setTaxes(BigDecimal.valueOf(156.57));
        gasBillDTO.setCompleteAmount(BigDecimal.valueOf(980.63));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        List<GasBillDTO> gasBills = List.of(gasBillDTO);
        String requestJson=ow.writeValueAsString(gasBills);

        this.mockMvc.perform(post("/addBills").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("All good!")));
    }

    @Test // tax attribut is null
    public void addBillTestNoFieldSet() throws Exception {
        when(clock.getZone()).thenReturn(zonedDateTime.getZone());
        when(clock.instant()).thenReturn(zonedDateTime.toInstant());

        GasBillDTO gasBillDTO = new GasBillDTO();
        gasBillDTO.setDate(LocalDate.now(clock));
        gasBillDTO.setFees(BigDecimal.ZERO);
        gasBillDTO.setLiterAmount(BigDecimal.valueOf(1203));
        gasBillDTO.setLiterPrice(BigDecimal.valueOf(0.685));
        gasBillDTO.setTaxPercent(BigDecimal.valueOf(19));
        gasBillDTO.setTaxes(null);
        gasBillDTO.setCompleteAmount(BigDecimal.valueOf(980.63));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(gasBillDTO);

        this.mockMvc.perform(post("/addBill").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("{\"field\":\"taxes\",\"description\":\"This field is not allowed to be null\"}")))
        ;
    }

    @Test // tax & literPrice attributes are null
    public void addBillTestMultiNoFieldSet() throws Exception {
        when(clock.getZone()).thenReturn(zonedDateTime.getZone());
        when(clock.instant()).thenReturn(zonedDateTime.toInstant());

        GasBillDTO gasBillDTO = new GasBillDTO();
        gasBillDTO.setDate(LocalDate.now(clock));
        gasBillDTO.setFees(BigDecimal.ZERO);
        gasBillDTO.setLiterAmount(BigDecimal.valueOf(1203));
        gasBillDTO.setLiterPrice(null);
        gasBillDTO.setTaxPercent(BigDecimal.valueOf(19));
        gasBillDTO.setTaxes(null);
        gasBillDTO.setCompleteAmount(BigDecimal.valueOf(980.63));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(gasBillDTO);

        this.mockMvc.perform(post("/addBill").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("{\"field\":\"taxes\",\"description\":\"This field is not allowed to be null\"}")))
                .andExpect(content().string(containsString("{\"field\":\"literPrice\",\"description\":\"This field is not allowed to be null\"}")))
        ;
    }

}
