package com.tax.services;

import com.tax.controllers.dtos.TaxCalculation;
import com.tax.controllers.dtos.TaxCalculationRequest;
import com.tax.controllers.dtos.TaxType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class TaxService {

    private List<TaxType> taxTypes = new ArrayList<>();

    public TaxService() {
        taxTypes.add(new TaxType(1, "ICMS", "Tax on Circulation of Goods and Services", 18.0));
        taxTypes.add(new TaxType(2, "ISS", "Tax on Services", 5.0));
    }

    public List<TaxType> getAllTaxTypes() {
        return taxTypes;
    }

    public TaxType createTaxType(TaxType newTaxType) {
        if (newTaxType.getName() == null || newTaxType.getDescription() == null) {
            return null;
        }
        newTaxType.setId(taxTypes.size() + 1);
        taxTypes.add(newTaxType);
        return newTaxType;
    }

    public TaxCalculation calculateTax(TaxCalculationRequest request) {
        Optional<TaxType> taxTypeOpt = taxTypes.stream()
                .filter(tax -> tax.getId() == request.getTaxTypeId())
                .findFirst();

        if (!taxTypeOpt.isPresent()) {
            return null;
        }

        TaxType taxType = taxTypeOpt.get();
        double taxAmount = request.getBaseValue() * taxType.getAliquot() / 100;
        return new TaxCalculation(taxType.getName(), request.getBaseValue(), taxType.getAliquot(), taxAmount);
    }

    public TaxType getTaxTypeById(int id) {
        return taxTypes.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteTaxType(int id) {
        return taxTypes.removeIf(t -> t.getId() == id);
    }
}
