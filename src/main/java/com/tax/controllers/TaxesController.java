package com.tax.controllers;

import com.tax.controllers.dtos.TaxCalculation;
import com.tax.controllers.dtos.TaxCalculationRequest;
import com.tax.controllers.dtos.TaxType;
import com.tax.services.TaxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taxes")
public class TaxesController {

    private final TaxService taxService;

    public TaxesController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/types")
    public ResponseEntity<List<TaxType>> listTaxTypes() {
        List<TaxType> taxTypes = taxService.getAllTaxTypes();
        return new ResponseEntity<>(taxTypes, HttpStatus.OK);
    }

    @PostMapping("/types")
    public ResponseEntity<TaxType> registerTaxType(@RequestBody TaxType newTaxType) {
        TaxType createdTaxType = taxService.createTaxType(newTaxType);
        if (createdTaxType == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdTaxType, HttpStatus.CREATED);
    }

    @PostMapping("/calculation")
    public ResponseEntity<TaxCalculation> calculateTax(@RequestBody TaxCalculationRequest calculationRequest) {
        TaxCalculation calculation = taxService.calculateTax(calculationRequest);
        if (calculation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(calculation, HttpStatus.OK);
    }

    @GetMapping("/types/{id}")
    public ResponseEntity<TaxType> getTaxTypeDetails(@PathVariable int id) {
        TaxType taxType = taxService.getTaxTypeById(id);
        if (taxType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taxType, HttpStatus.OK);
    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<Void> deleteTaxType(@PathVariable int id) {
        boolean isDeleted = taxService.deleteTaxType(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
