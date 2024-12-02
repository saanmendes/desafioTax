package com.tax.controllers;

import com.tax.controllers.dtos.TaxCalculation;
import com.tax.controllers.dtos.TaxCalculationRequest;
import com.tax.controllers.dtos.TaxType;
import com.tax.services.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaxesController.class)
public class TaxControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = Mockito.mock(TaxService.class);
        TaxesController taxesController = new TaxesController(taxService);
        mockMvc = MockMvcBuilders.standaloneSetup(taxesController).build();
    }

    @Test
    void testListTaxTypes() throws Exception {
        when(taxService.getAllTaxTypes()).thenReturn(List.of(
                new TaxType(1, "ICMS", "Tax on Circulation of Goods and Services", 18.0),
                new TaxType(2, "ISS", "Tax on Services", 5.0)
        ));

        mockMvc.perform(get("/api/taxes/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ICMS"))
                .andExpect(jsonPath("$[1].name").value("ISS"));
    }

    @Test
    void testRegisterTaxType() throws Exception {
        TaxType newTaxType = new TaxType(3, "IPI", "Tax on Industrialized Products", 12.0);
        when(taxService.createTaxType(Mockito.any())).thenReturn(newTaxType);

        mockMvc.perform(post("/api/taxes/types")
                        .contentType("application/json")
                        .content("{\"name\":\"IPI\",\"description\":\"Tax on Industrialized Products\",\"aliquot\":12.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("IPI"));
    }

    @Test
    void testCalculateTax() throws Exception {
        TaxCalculation calculation = new TaxCalculation("ICMS", 1000.0, 18.0, 180.0);
        TaxCalculationRequest request = new TaxCalculationRequest();
        request.setTaxTypeId(1);
        request.setBaseValue(1000.0);

        when(taxService.calculateTax(Mockito.any())).thenReturn(calculation);

        mockMvc.perform(post("/api/taxes/calculation")
                        .contentType("application/json")
                        .content("{\"taxTypeId\":1,\"baseValue\":1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taxType").value("ICMS"))
                .andExpect(jsonPath("$.taxAmount").value(180.0));
    }

    @Test
    void testDeleteTaxType() throws Exception {
        when(taxService.deleteTaxType(1)).thenReturn(true);

        mockMvc.perform(delete("/api/taxes/types/1"))
                .andExpect(status().isNoContent());
    }
}
