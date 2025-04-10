package com.unrise.webapp.model;

import com.unrise.webapp.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateTo;
    private String role;
    private String description;

    private Period() {

    }

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

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
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
