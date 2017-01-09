package org.lf.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 将Date型转换成yyyy-MM-dd HH:mm:ss的格式字符串输出
	 * @param date
	 */
	public static String toString(Date date) {
		return sdf.format(date);
	}
	
	/**
	 * 将Date型转换成yyyy-MM-dd 的格式字符串输出
	 * @param date
	 */
	public static String toStringymd(Date date) {
		return ymd.format(date);
	}
	
	/**
	 * 将数据库中取出的int型，转换成换成yyyy-MM-dd HH:mm:ss的格式字符串输出
	 * @param value
	 */
	public static String toString(long value) {
		return toString(new Timestamp(value));
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss的格式字符串类型转换成Date型
	 * @param str
	 * @throws ParseException
	 */
	public static Date toDate(String str) throws ParseException {
		return sdf.parse(str);
	}
	
	/**
	 * 将yyyy-MM-dd的格式字符串类型转换成Date型
	 * @param str
	 * @throws ParseException
	 */
	public static Date toDateymd(String str) throws ParseException {
		return ymd.parse(str);
	}
	
	/**
	 * 将Date转换成int型存入到数据库中
	 * @param date
	 */
	public static long toLong(Date date) {
		return date.getTime();
	}
}
