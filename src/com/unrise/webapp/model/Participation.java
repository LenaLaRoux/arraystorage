package com.unrise.webapp.model;

import java.time.LocalDate;

public class Participation {
    final private LocalDate dateFrom;
    final private LocalDate dateTo;

    final private String companyName;
    private String role;
    private String description;

    public Participation(LocalDate dateFrom, String companyName) {
        this(dateFrom, null, companyName);
    }

    public Participation(LocalDate dateFrom, LocalDate dateTo, String companyName) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.companyName = companyName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
