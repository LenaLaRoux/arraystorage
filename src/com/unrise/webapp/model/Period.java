package com.unrise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    final private LocalDate dateFrom;
    final private LocalDate dateTo;
    private String role;
    private String description;

    public Period(LocalDate dateFrom) {
        this(dateFrom, null);
    }

    public Period(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(dateFrom, period.dateFrom) && Objects.equals(dateTo, period.dateTo) && Objects.equals(role, period.role) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, role, description);
    }

    @Override
    public String toString() {
        return "Period{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
