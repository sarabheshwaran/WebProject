package uub.staticlayer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {


	public static long getTime() {
		return System.currentTimeMillis();
	}
	
	public static String formatDate(long millis) {

		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime time = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		return time.format(formatter);
	}

	public static String formatTime(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime time = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MMM/dd - hh:mm:ss a");

		return time.format(formatter);
	}

	public static LocalDate formatDateString(String dateString) throws CustomBankException {

		HelperUtils.nullCheck(dateString);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate date;

		date = LocalDate.parse(dateString, formatter);

		return date;

	}

	public static long formatDate(LocalDate date) throws CustomBankException {

		HelperUtils.nullCheck(date);

		return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

	}


	public static boolean isAdult(long birthMillis) throws CustomBankException {
		
		int age = calculateAge(birthMillis);
		
		if(age < 18) {
			throw new CustomBankException("Invalid age");
		}
		
		return true;
		
	}
	
    public static int calculateAge(long birthdateMillis) {
        LocalDate birthdate = Instant.ofEpochMilli(birthdateMillis)
                                      .atZone(ZoneId.systemDefault())
                                      .toLocalDate();

        LocalDate currentDate = Instant.ofEpochMilli(System.currentTimeMillis())
                                       .atZone(ZoneId.systemDefault())
                                       .toLocalDate();

        int age = currentDate.getYear() - birthdate.getYear();

        if (currentDate.getDayOfYear() < birthdate.getDayOfYear()) {
            age--;
        }

        return age;
    }

}
