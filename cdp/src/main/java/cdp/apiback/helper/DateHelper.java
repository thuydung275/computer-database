package cdp.apiback.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 
 * @author thuydung
 *
 */
public class DateHelper {
	
	/**
	 * Convert from String to LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate toLocaleDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formatter = formatter.withLocale(Locale.FRANCE);
		return LocalDate.parse(date, formatter);
	}

}
