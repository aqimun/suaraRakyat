package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public class ProjectRequest {

    @NotBlank
    @Size(min = 5, max = 255)
    private String title;

    @NotBlank
    @Size(min = 20)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal budget;

    private Set<String> expenseItems; // Optional

    private Set<String> evidenceRefs; // Optional, references to uploaded evidence

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Set<String> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(Set<String> expenseItems) {
        this.expenseItems = expenseItems;
    }

    public Set<String> getEvidenceRefs() {
        return evidenceRefs;
    }

    public void setEvidenceRefs(Set<String> evidenceRefs) {
        this.evidenceRefs = evidenceRefs;
    }
}
