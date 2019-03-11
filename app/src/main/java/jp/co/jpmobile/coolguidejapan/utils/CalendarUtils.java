package jp.co.jpmobile.coolguidejapan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by monkeyismeme on 16/03/22.
 */
public class CalendarUtils {
	public  static String getStringFromDateWithFormatter(Date date,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
		return sdf.format(date);
	}

	public static boolean isValidDate(String input) {
		String formatString = "yyyy/MM/dd";
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			format.setLenient(false);
			format.parse(input);
		} catch (ParseException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	public static Calendar stringToCalendar(String strDate,String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		Date date = sdf.parse(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static long getNextYearOfDate(String yyyyMMdd,String split,String formatter){
		try {
			String[] dates = yyyyMMdd.split(split);
			dates[0] = String.valueOf(Integer.parseInt(dates[0]) + 1);
			String dateStr = dates[0] + split + dates[1]+split+dates[2];
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
			return  sdf.parse(dateStr).getTime();
		}catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public static Calendar getNextNDate(Calendar calendar,int days){
		calendar.add(Calendar.DATE, days);
		return calendar;
	}
}
