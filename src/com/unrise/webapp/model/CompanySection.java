package com.unrise.webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySection extends ASection implements Iterable<Company> {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Company> companies;

    public CompanySection() {
        companies = new ArrayList<>();
    }

    @Override
    public Iterator<Company> iterator() {
        return companies.iterator();
    }

    public CompanySection add(Company company) {
        companies.add(company);
        return this;
    }

    public List<Company> get() {
        return companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection companies1 = (CompanySection) o;
        return Objects.equals(companies, companies1.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companies);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "companies=" + companies +
                '}';
    }
}
