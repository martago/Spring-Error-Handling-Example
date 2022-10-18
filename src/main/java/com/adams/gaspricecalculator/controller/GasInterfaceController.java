package com.adams.gaspricecalculator.controller;

import com.adams.gaspricecalculator.config.SwaggerConfig;
import com.adams.gaspricecalculator.error.GasValidationException;
import com.adams.gaspricecalculator.model.incoming.GasBillDTO;
import com.adams.gaspricecalculator.model.outcoming.ApiError;
import com.adams.gaspricecalculator.model.outcoming.GasGeneralResponse;
import com.adams.gaspricecalculator.service.GasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@RestController
@Api(tags = {SwaggerConfig.GAS_INTERFACE_TAG})
public class GasInterfaceController {

    private GasService gasService;

    public GasInterfaceController(GasService gasService) {
        this.gasService = gasService;
    }

    @PostMapping("addBill")
    @ApiOperation(value = "With this interface you can add a bill to your profile")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added your bill to your profil", response = GasGeneralResponse.class),
            @ApiResponse(code = 400, message = "Your gas bill has not all required fields filled", response = ApiError.class),
            @ApiResponse(code = 406, message = "Your gas bill is not correct, please fix your input data", response = ApiError.class)
    })
    public ResponseEntity<GasGeneralResponse> addBill(@Valid @RequestBody GasBillDTO gasBill) throws GasValidationException {
        gasService.addBill(gasBill);
        return new ResponseEntity<>(new GasGeneralResponse("All good!", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PostMapping("addBills")
    @ApiOperation(value = "With this interface you can add a bill to your profile")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added your bill to your profil", response = GasGeneralResponse.class),
            @ApiResponse(code = 400, message = "Your gas bill has not all required fields filled", response = ApiError.class),
            @ApiResponse(code = 406, message = "Your gas bill is not correct, please fix your input data", response = ApiError.class)
    })
    public ResponseEntity<GasGeneralResponse> addBills(@RequestBody @NotEmpty List<@Valid GasBillDTO> gasBills) throws GasValidationException {
        gasService.addBills(gasBills);
        return new ResponseEntity<>(new GasGeneralResponse("All good!", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }
}