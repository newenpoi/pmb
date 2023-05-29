package com.openclassrooms.newenpoi.pmb.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/*
	 * Calcul l'âge d'une entité en fonction de sa date de naissance.
	 */
	public static int calculateAge(String dob) throws DateTimeException {
    	// Peut déclencher DateTimeException.
    	LocalDate localBirthDate = LocalDate.parse(dob, formatter);
    	
    	// Renvoie le nombre d'années écoulées entre ces deux dates.
    	return Period.between(localBirthDate, LocalDate.now()).getYears();
	}
	
	/**
	 * Converti une date de naissance au format JJ/MM/AAA vers une LocalDate.
	 * @param dob (dd/MM/yyyy)
	 * @return
	 * @throws DateTimeException
	 */
	public static LocalDate dobToLocalDate(String dob) throws DateTimeException {
		return LocalDate.parse(dob, formatter);
	}
}