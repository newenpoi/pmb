package com.openclassrooms.newenpoi.pmb.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateUtilsTest {
    
	@Test
    public void testCalculateAge() {
        String dob = "01/01/2000";
        int expectedAge = Period.between(LocalDate.of(2000, 1, 1), LocalDate.now()).getYears();

        assertEquals(expectedAge, DateUtils.calculateAge(dob));
    }

    @Test
    public void testCalculateAgeWithInvalidDate() {
        String dob = "invalid-date";

        assertThrows(DateTimeParseException.class, () -> DateUtils.calculateAge(dob));
    }

    @Test
    public void testDobToLocalDate() {
        String dob = "01/01/2000";
        LocalDate expectedDate = LocalDate.of(2000, 1, 1);

        assertEquals(expectedDate, DateUtils.dobToLocalDate(dob));
    }

    @Test
    public void testDobToLocalDateWithInvalidDate() {
        String dob = "invalid-date";

        assertThrows(DateTimeParseException.class, () -> DateUtils.dobToLocalDate(dob));
    }
}
