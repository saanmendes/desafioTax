package com.tax.services;

import com.tax.controllers.dtos.TaxCalculation;
import com.tax.controllers.dtos.TaxCalculationRequest;
import com.tax.controllers.dtos.TaxType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaxServiceTest {

    private TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = new TaxService();
    }

    @Test
    void testCalculateTax() {
        TaxCalculationRequest request = new TaxCalculationRequest();
        request.setTaxTypeId(1);
        request.setBaseValue(1000.0);

        TaxCalculation result = taxService.calculateTax(request);

        assertNotNull(result);
        assertEquals("ICMS", result.getTaxType());
        assertEquals(180.0, result.getTaxAmount());
    }

    @Test
    void testCreateTaxType() {
        TaxType newTaxType = new TaxType(0, "IPI", "Tax on Industrialized Products", 12.0);
        TaxType createdTaxType = taxService.createTaxType(newTaxType);

        assertNotNull(createdTaxType);
        assertEquals("IPI", createdTaxType.getName());
    }

    @Test
    void testGetTaxTypeById() {
        TaxType taxType = taxService.getTaxTypeById(1);

        assertNotNull(taxType);
        assertEquals("ICMS", taxType.getName());
    }
}
