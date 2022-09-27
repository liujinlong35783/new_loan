package com.tkcx.api.utils;

import groovy.util.logging.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**时间转换
 * BaseMessage
 * @author
 * @version :1.0 </br>
 */
@Slf4j
public class DateUtils
{
	/**
	 *
	 *
	 * @param date 时间
	 * @param days 天数
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days) {

		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	/**
	 *@return String
	 *@param  date date
	 */
	public static String getTimeString(java.util.Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date).trim();
	}

	/**
	 * yyyy-MM-dd转换为Date
	 * @param stringTime
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String stringTime){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(stringTime);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取前一天日期 yyyyMMdd 00:00:00
	 * @return
	 */
	public static Date getDate(){
		Date fileDate = dateIncreaseByDay(new Date(), -1);
		String timeString = getTimeString(fileDate);
		Date date = DateUtils.stringToDate(timeString);
		return date;
	}

	/**
	 * 获取前一天日期 yyyyMMdd 00:00:00
	 * @return
	 */
	public static Date getNewDate(){
		Date fileDate = dateIncreaseByDay(new Date(), 0);
		String timeString = getTimeString(fileDate);
		Date date = DateUtils.stringToDate(timeString);
		return date;
	}
}







