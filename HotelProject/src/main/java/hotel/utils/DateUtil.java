package hotel.utils;

import java.time.LocalDate;

public class DateUtil {
	
	public static LocalDate calculateEndDate(LocalDate startDate, int nights) {
		return startDate.plusDays(nights);
	}
	
	public static boolean isInDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
		return ( date.isAfter(startDate) && date.isBefore(endDate) || date.isEqual(startDate) || date.isEqual(endDate) );
	}
	
	public static boolean isInDateRange(LocalDate date, LocalDate startDate, int nights) {
		return isInDateRange(date, startDate, calculateEndDate(startDate, nights));
	}
}
