package com.unrise.webapp.storage.utils;

import com.unrise.webapp.model.Company;
import com.unrise.webapp.model.Period;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

public class ResumeTestUtils {

    public static Company createParticipant(Integer yearFrom, Integer yearTo, Month monthFrom, Month monthTo, String companyName, String website, String description, String role) {
        Company company = new Company(companyName);
        company.setWebsite(website);

        Period period;
        if (yearTo == null) {
            period = new Period(LocalDate.of(yearFrom, monthFrom, 1));
        } else {
            period = new Period(LocalDate.of(yearFrom, monthFrom, 1),
                    LocalDate.of(yearTo, Optional.ofNullable(monthTo).orElse(Month.JANUARY), 1));
        }
        period.setDescription(description);
        period.setRole(role);
        company.getPeriods().add(period);
        return company;
    }

    public static Company createParticipant(Integer yearFrom, Month monthFrom, String companyName, String website, String description, String role) {
        return createParticipant(yearFrom, null, monthFrom, null, companyName, website, description, role);
    }
}
