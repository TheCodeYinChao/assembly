/**
 * Project Name:saas-common
 * File Name:DateUtils.java
 * Package Name:com.ccop.common.util
 * Date:2017年11月3日上午10:11:42
 * Copyright (c) 2017, WangLZ All Rights Reserved.
 *
*/

package cn.bainuo.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ClassName:DateUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月3日 上午10:11:42 <br/>
 * 
 * @author WangLZ
 * @version
 * @since JDK 1.6
 * @see
 */
public class DateUtils {

	/** yyyy-MM-dd */
	public static DateTimeFormatter F1 = DateTimeFormatter.ISO_LOCAL_DATE;
	/** yyyy-MM-dd HH */
	public static DateTimeFormatter F2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
	/** yyyy-MM-dd HH:mm */
	public static DateTimeFormatter F3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	/** yyyy-MM-dd HH:mm:ss */
	public static DateTimeFormatter F4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	/** yyyy-MM-dd HH:mm:ss.SSS */
	public static DateTimeFormatter F5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	/** yyyyMMdd */
	public static DateTimeFormatter F6 = DateTimeFormatter.ofPattern("yyyyMMdd");
	/** yyyyMMddHH */
	public static DateTimeFormatter F7 = DateTimeFormatter.ofPattern("yyyyMMddHH");
	/** yyyyMMddHHmm */
	public static DateTimeFormatter F8 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	/** yyyyMMddHHmmss */
	public static DateTimeFormatter F9 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	/** yyyyMMddHHmmssSSS */
	public static DateTimeFormatter F10 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	public static DateTimeFormatter F11 =DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static DateTimeFormatter F12 = DateTimeFormatter.ofPattern("yyMMddHHmm");
	public static DateTimeFormatter F13 = DateTimeFormatter.ofPattern("yyyyMM");
	
	/**
	 * toDate(LocalDateTime,DateTimeFormatter)
	 */
	public static String toDate(LocalDateTime ldt, DateTimeFormatter dtf) {
		return ldt.format(dtf);
	}

	/**
	 * toDate(Date,DateTimeFormatter)
	 */
	public static String toDate(Date date, DateTimeFormatter dtf) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone).format(dtf);
	}

	/**
	 * toDate(String,DateTimeFormatter)
	 */
	public static Date toDate(String date, DateTimeFormatter dtf) {
		LocalDateTime ldt = LocalDateTime.parse(date, dtf);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = ldt.atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * toLocalTimeDate(String,DateTimeFormatter)
	 */
	public static LocalDateTime toLocalTimeDate(String date, DateTimeFormatter dtf) {
		return LocalDateTime.parse(date, dtf);
	}

	/**
	 * now(DateTimeFormatter)
	 */
	public static String now(DateTimeFormatter dtf) {
		LocalDateTime local = LocalDateTime.now();
		return local.format(dtf);
	}

	/**
	 * timestamp() <br>
	 * return System.currentTimeMillis();
	 */
	public static Long timestamp() {
		Clock clock = Clock.systemUTC();
		return clock.millis();
	}
	
	
	
	/**
	 * 
	 * diffDays:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author lm
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @since JDK 1.8
	 */
	public static int diffDays(Date beginDate, Date endDate) {
   	 	long margin = 0;

   	    margin = endDate.getTime() - beginDate.getTime();

   	    margin = margin/(1000*60*60*24);

   	    return Integer.parseInt(String.valueOf(margin));
	}
	public synchronized static long getToNextDay() {
		long len = 3600;
		try {
			Calendar curDate = Calendar.getInstance();
			Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
					curDate.get(Calendar.DATE) + 1, 0, 0, 0);
			len = (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
		} catch (Exception e) {
			e.printStackTrace();
			return len;
		}
		if (len <= 0 || len > 86400) {
			len = 3600;
		}
		return len;
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub

//		System.err.println(toDate(new Date(), F10));
//		System.err.println(toDate("20171221195535938", F10));
		System.err.println(timestamp());
		System.err.println(System.currentTimeMillis());
		System.err.println(System.nanoTime());
	}

}
