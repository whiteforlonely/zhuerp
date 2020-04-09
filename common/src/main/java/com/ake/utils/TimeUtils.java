package com.ake.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeUtils {
	
	//时间的毫秒数
	//一秒
	public static final int A_SECOND = 1000 * 1;
	//一分钟
	public static final int A_MINUTE = A_SECOND * 60;
	//一小时
	public static final int A_HOUR = A_MINUTE * 60;
	//一天
	public static final int A_DAY = A_HOUR * 24;
	//一周
	public static final int A_WEEK = A_DAY * 7;
	//30天
	public static final int A_MONTH = A_DAY * 30;

	private static final String PATTERN_DATE = "yyyy-MM-dd";
	private static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	public static String TimeInterval(Date d) {
		long time = System.currentTimeMillis() - d.getTime();
		if (time >= 0) {
			if ((time / 60000) < 60)// 小于1小时
			{
				if (time / 60000 < 1) {
					if (time / 1000 < 1) {
						return "1秒前";
					} else
						return "" + time / 1000 + "秒前";
				}
				return Math.abs(time / 60000) + "分钟前";
			} else if (time / 60000 < (60 * 24 * 1)) // 小于1天
			{
				int temp = (int) time / 3600000;
				return Math.abs(temp) + "小时前";
			} else if (time / 60000 < (60 * 24 * 3)) // 小于三天
			{
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c1.setTime(d);
				int betweenDays = getBetweenDays(c1, c2);
				return betweenDays + "天前";
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
				try {
					return sdf.format(d);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return "1秒前";
	}

	/**
	 * 后者减前者 只能比较天 不能比较到时、分、秒
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static int getBetweenDays(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return getBetweenDays(c1, c2);
	}
	
	/**
	 * 后者减前者 只能比较天 不能比较到时、分、秒
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static int getBetweenDays(Calendar c1, Calendar c2) {
		int betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}

	/**
	 * 
	 * 用途:获取距离当前时间点之前的时间
	 * 
	 * @author liuyang 2008-8-21
	 * @param d
	 * @return
	 */
	public static String getBetweenDays1(int days) {
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.DAY_OF_YEAR, -days);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c1.getTime());
	}

	/**
	 * 
	 * 用途:获取距离当前时间点之前的时间
	 * 
	 * @author liuyang 2008-8-21
	 * @param d
	 * @return
	 */
	public static String getBetweenHour(int hours) {
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.HOUR, -hours);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c1.getTime());
	}

	/**
	 * 
	 * 用途:获取距离当前时间点之前的时间
	 * 
	 * @author liuyang 2008-8-21
	 * @param d
	 * @return
	 */
	public static String getBetweenMinute(int minute) {
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MINUTE, -minute);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c1.getTime());
	}

	/**
	 * 
	 * 用途:重载TimeInterval，用于取2个时间点之间的间隔 精确到天
	 * 
	 * @author Bijia 2008-8-21
	 * @param d
	 * @return
	 */

	public static String TimeInterval2(Date d) {
		long time = System.currentTimeMillis() - d.getTime();
		if (time >= 0) {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d);
			int betweenDays = getBetweenDays(c1, c2);
			return betweenDays + "";
		}
		return "1";
	}

	/**
	 * 
	 * 用途:重载TimeInterval，计算距离某天的相隔天数 精确到天
	 * 
	 * @author liuyang 2008-9-8
	 * @return
	 */

	public static String TimeInterval3() {
		Date d = StringToDate("2008-09-26 00:00:10.0");
		long time = d.getTime() - System.currentTimeMillis();
		if (time >= 0) {
			Calendar calendar = Calendar.getInstance();
			long nowMillis = System.currentTimeMillis();
			int day_now = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.setTime(d);
			long agoMillis = calendar.getTimeInMillis();
			int day_ago = calendar.get(Calendar.DAY_OF_YEAR);
			long distanceMillis = nowMillis - agoMillis;
			long yearMillis = 1000 * 60 * 60 * 24 * 365;
			if (distanceMillis > yearMillis) {
				return Math.abs(distanceMillis / (1000 * 60 * 60 * 24)) + "";
			} else {
				return (day_now - day_ago) + "";
			}
		}
		return "1";
	}

	/**
	 * 
	 * 用途:重载TimeInterval，计算年月日的格式，比如08/10/9，如果小于一天则显示PM 3:40
	 * 
	 * @author liuyang 2008-10-9
	 * @return
	 */

	public static String TimeInterval4(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm,yyyy-MM-dd");

		try {
			return sdf.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "刚刚";
	}

	public static String TimeInterval5(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

		try {
			return sdf.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "刚刚";
	}

	public static String TimeInterval6(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			return sdf.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "1970-07-01 00:00:00";
	}
	
	public static String TimeInterval7(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

		try {
			return sdf.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "刚刚";
	}

	/**
	 * 
	* @Title: TimeInterval8
	* @Description: 生成yyyyMMddHHmmss格式的时间
	* @param @param d
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String TimeInterval8(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			return sdf.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "19700701000000";
	}
	
	/**
	 * 
	 * @Title: TimeInterval9
	 * @Description: yyyyMMddHHmmssSSS000Z 加时区格式，始终保持24位
	 * @param @param d
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String TimeInterval9(Date d) {

		String timeStr = null;
		if(d == null) {
			return timeStr;
		}
		
		try {
			// 定义要进行Format的格式
			String p_pattern = "yyyyMMddHHmmssSSS000Z";
			SimpleDateFormat p_sdf = new SimpleDateFormat(p_pattern);

			// 定义要转换的Date对象，我的例子中使用了当前时间
			timeStr = p_sdf.format(d);
			
			if (timeStr.contains("+") || timeStr.contains("-")) {

				if (timeStr.contains("+")) {

					String[] stringArray = timeStr.split("\\+");

					if (stringArray.length > 1) {

						String timeStrTemp = stringArray[0];
						String timeQzoneStr = stringArray[1];
						
						if (timeQzoneStr != null) {
							if (timeQzoneStr.length() > 3
									&& timeQzoneStr.startsWith("0")) {
								timeQzoneStr = timeQzoneStr.substring(1);

							} else if (timeQzoneStr.length() > 3
									&& timeQzoneStr.endsWith("0")) {
								timeQzoneStr = timeQzoneStr.substring(0,
										timeQzoneStr.length() - 1);
							}
						}

						timeStr = timeStrTemp + "+" + timeQzoneStr;
					}

				} else if (timeStr.contains("-")) {

					String[] stringArray = timeStr.split("\\-");

					if (stringArray.length > 1) {

						String timeStrTemp = stringArray[0];
						String timeQzoneStr = stringArray[1];
						
						if (timeQzoneStr != null) {
							if (timeQzoneStr.length() > 3
									&& timeQzoneStr.startsWith("0")) {
								timeQzoneStr = timeQzoneStr.substring(1);

							} else if (timeQzoneStr.length() > 3
									&& timeQzoneStr.endsWith("0")) {
								timeQzoneStr = timeQzoneStr.substring(0,
										timeQzoneStr.length() - 1);
							}
						}

						timeStr = timeStrTemp + "-" + timeQzoneStr;
					}

				}
			}

			if (timeStr != null && timeStr.length() < 24) { // 
				int zeroCount = 24 - timeStr.length();
				if (zeroCount > 0) {
					for (int i = 0; i < zeroCount; i++) {
						timeStr += "0";
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return timeStr;
	}
	
	/**
	 * 
	 * 用途:重载TimeInterval，计算急急如律令剩余时间，用于取2个时间点之间的差值 精确到小时分秒
	 * 
	 * @author cuker 2008-10-11
	 * @param addDate发送急急如律令的时间
	 *            ,hours有效期
	 * @return long[]
	 */

	public static int[] commandTimeInterval(Date addDate, int hours) {
		long time = 24 * 1000 * 60 * 60
				- (System.currentTimeMillis() - addDate.getTime());
		int[] commandTime = new int[3];
		if (time >= 0) {
			long hour = (int) time / (1000 * 60 * 60);
			long minute = (int) ((time / (1000 * 60)) % 60);
			long second = (int) ((time / 1000) % 60);

			commandTime[0] = (int) hour;
			commandTime[1] = (int) minute;
			commandTime[2] = (int) second;
		}
		return commandTime;
	}

	public static long DifferenceTime(Date d) {
		long time = System.currentTimeMillis() - d.getTime();
		if (time >= 0) {
			return time;
		} else {
			return 5;
		}
	}

	public static java.util.Date StringToDate(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		java.util.Date ud = null;
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		try {
			ud = df.parse(dateStr.trim());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ud;
	}

	public static Date stringToDate(String dateStr, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}

		Date date = df.parse(dateStr, new ParsePosition(0));
		return date;
	}

	/**
	 * 
	 * 用途:转换成df1格式
	 * 
	 * @author liuyang 2008-8-21
	 * @param String
	 *            dateStr
	 * @return
	 */
	public static String StringToDate1(String dateStr) {
		DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		String ud = null;
		try {
			ud = df1.format(StringToDate(dateStr));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ud;
	}

	public static java.util.Date LongToDate(Long dateStr) {

		Date d = new Date();
		try {
			d.setTime(dateStr);
			d = new Date(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String nowTimeStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public static String dateToString(Date date) {
		if(date == null)
			return "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public static String dateToQtShortString(Date date) {
		DateFormat df = new SimpleDateFormat("MM/dd");
		return df.format(date);
	}
	
	public static String dateToString(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static String dateToShortString(Date date) {
		if(date == null){
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public static boolean getIsNight() {
		boolean isNight = false;
		// 判断时间点取不同的背景
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour < 6 || hour >= 18) {
			isNight = true;
		}
		return isNight;
	}

	// 获取当月第一天
	public static Date getFirstDayOfMonth(Date startDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(startDate);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		try {
			date = sdf.parse(sdf.format(lastDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	// 获取当月最后一天
	public static Date getLastDayOfMonth(Date startDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(startDate);
		lastDate.set(Calendar.DATE, lastDate.getActualMaximum(Calendar.DAY_OF_MONTH));// 设为当前月的1号
		try {
			date = sdf.parse(sdf.format(lastDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	// 获取一个星期后的一天
	public static Date getDayForNextWeek(Date startDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(startDate);
		lastDate.add(Calendar.DATE, 7);
		try {
			date = sdf.parse(sdf.format(lastDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//	获取星期几 
	public static String getWeek(Date date){  
        String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
        if(week_index<0){  
            week_index = 0;  
        }   
        return weeks[week_index];  
    }
	
	public static String getWeekByNum(int weekNo){  
		String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};  
		return weeks[weekNo];
    }
	
	//	获取星期几 返回值是int
	public static int getWeekInt(Date date){  
        Integer[] weeks = {0,1,2,3,4,5,6};  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
        if(week_index<0){  
            week_index = 0;  
        }   
        return weeks[week_index];  
    }
	
	/**
	 * 根据日期索引获取星期几  
	 * @param weekNo 0-6  星期天到星期六
	 * @param fullName 是否返回全称还是简称   如 Sun/Sunday
	 * @return
	 */
	public static String getWeekNameByWeekNo(int weekNo, boolean fullName){  
		String[] fullWeeks = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};  
        String[] weeks = {"Sun","Mon","Tues","Wed","Thur","Fri","Sat"};  
        if(fullName) {
        	return fullWeeks[weekNo];
        }else {
        	return weeks[weekNo];
        }
    }
	
	
	/**
	 * 是否是午夜晚上1点到早上9点
	 * 
	 * @return 如果是返回true,否则返回false
	 */
	public static boolean getIsMidnight() {
		boolean isMidnight = false;
		// 判断时间点取不同的背景
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour >= 1 && hour <= 8) {
			isMidnight = true;
		}
		return isMidnight;
	}

	/**
	 * 
	 * @Title: addDays
	 * @Description: 在当前时间增加天数
	 * @param days
	 * @return:
	 */
	public static Date addDays(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int beforeHour = c.get(Calendar.HOUR_OF_DAY);
		c.add(Calendar.DAY_OF_MONTH, days);
		int afterHour = c.get(Calendar.HOUR_OF_DAY);
		if (beforeHour != afterHour) {
			c.add(Calendar.HOUR_OF_DAY, beforeHour - afterHour);
		}
		return c.getTime();
	}

	public static Date addMonths(int months) {
		return addMonths(new Date(), months);
	}

	public static Date addMonths(Date date, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: addDays
	 * @Description: 在日期增加天数
	 * @param days
	 *            增加天数
	 * @return: jja
	 */
	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int beforeHour = c.get(Calendar.HOUR_OF_DAY);
		c.add(Calendar.DAY_OF_MONTH, days);
		int afterHour = c.get(Calendar.HOUR_OF_DAY);
		if (beforeHour != afterHour) {
			c.add(Calendar.HOUR_OF_DAY, beforeHour - afterHour);
		}
		return c.getTime();
	}
	
	public static Date addHours(int hrs) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR_OF_DAY, hrs);
		return c.getTime();
	}
	
	public static Date addMins(int mins) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, mins);
		return c.getTime();
	}
	
	public static String getXinZuo(Date time){
		Calendar ca = Calendar.getInstance();
		ca.setTime(time);
		int month = ca.get(Calendar.MONTH) + 1;
		int date = ca.get(Calendar.DAY_OF_MONTH);

		if ((month == 1 && date >= 20 )||( month == 2 && date <= 18)) {
			return "水瓶座";
		} else if ((month == 2 && date >= 19 )||( month == 3 && date <= 20)) {
			return "双鱼座";
		} else if ((month == 3 && date >= 21 )||( month == 4 && date <= 19)) {
			return "白羊座";
		} else if ((month == 4 && date >= 20 )||( month == 5 && date <= 20)) {
			return "金牛座";
		} else if ((month == 5 && date >= 21 )||( month == 6 && date <= 21)) {
			return "双子座";
		} else if ((month == 6 && date >= 22 )||( month == 7 && date <= 22)) {
			return "巨蟹座";
		} else if ((month == 7 && date >= 23 )||( month == 8 && date <= 22)) {
			return "狮子座";
		} else if ((month == 8 && date >= 23 )||( month == 9 && date <= 22)) {
			return "处女座";
		} else if ((month == 9 && date >= 23 )||( month == 10 && date <= 23)) {
			return "天秤座";
		} else if ((month == 10 && date >= 24 )||( month == 11 && date <= 22)) {
			return "天蝎座";
		} else if ((month == 11 && date >= 23 )||( month == 12 && date <= 21)) {
			return "射手座";
		} else if ((month == 12 && date >= 22 )||( month == 1 && date <= 19)) {
			return "摩羯座";
		} else{
			return "未知";
		}
	}
	
	public static String getNowHour(){
		SimpleDateFormat formater = new SimpleDateFormat("HH");
		String hour=formater.format(new Date());
		return hour;
	}
	

	
	/**
	 * 获取date所在星期的一个星期的日期
	 * date 日其
	 * map    key: 0-6 //星期天到星期六
	 *        value :  //最近一周的日期字符串
	 * isChinese  true 中式   false 美式星期(星期天是一周的开始)
	 * @return
	 */
	public static Map<Integer, String> getDateWeekStr(Date date, boolean isChinese){
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    map.put(1, TimeUtils.dateToShortString(cal.getTime()));
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
	    map.put(2, TimeUtils.dateToShortString(cal.getTime()));
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
	    map.put(3, TimeUtils.dateToShortString(cal.getTime()));
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
	    map.put(4, TimeUtils.dateToShortString(cal.getTime()));
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
	    map.put(5, TimeUtils.dateToShortString(cal.getTime()));
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	    map.put(6, TimeUtils.dateToShortString(cal.getTime()));
	    if(isChinese) {
	        cal.add(Calendar.WEEK_OF_YEAR, 1);
	    }
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    map.put(0, TimeUtils.dateToShortString(cal.getTime()));
		
		return map;
	}
	
	public static Date getNearestWeekNumDate(int weekDateNo, String startTime){
		Date curDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        int curWeekDateNo = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int gaps = weekDateNo - curWeekDateNo;
        if(gaps >= 0){	// 时间在本周
        	Date resultTime = addDays(gaps);
        	return coverToSpecificTime(resultTime, startTime);
        } else{	//在下周
        	Date resultTime = addDays(gaps+7);
        	return coverToSpecificTime(resultTime, startTime);
        }
    }
	
	// 1700, 1900
	public static Date coverToSpecificTime(Date date, String timeStr){
		int hours = Integer.valueOf(timeStr.substring(0, 2));
		int mins = Integer.valueOf(timeStr.substring(2, 4));
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, mins);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
	}
	
	public static List<String> getWeeklyMutilActualCleanTime(String firstCleaning, List<String> mutilPeriodList){
		List<String> mutilActualCleanTime = new ArrayList<String>();
		if(mutilPeriodList != null && mutilPeriodList.size() > 0){
			Date firstCleanTime = StringToDate(firstCleaning);
			for(String period : mutilPeriodList){
				String[] items = period.split(":", -1);	// 1:1700:1900
				int weekNum = Integer.valueOf(items[0]);
				String startTime = items[1];
				Date tmpDate = getNearestWeekNumDate(weekNum, startTime);
				if(!tmpDate.before(firstCleanTime)){
					mutilActualCleanTime.add(dateToString(tmpDate));
				} else {
					mutilActualCleanTime.add(dateToString(addDays(tmpDate, 7)));
				}
			}
		}
		return mutilActualCleanTime;
	}
	
	public static Long getDayBetweenDate(Date beforDate, Date afterDate) {
        return (afterDate.getTime() - beforDate.getTime()) / (1000 * 60 * 60 * 24);
    }
	
	public static String getCurrentYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(new Date(System.currentTimeMillis()));
    }
	
	public static Date getDefaultTime() {
        return StringToDate("0001-01-01 00:00:00");
    }
    
	public static String get18HourTime(){
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour >= 18) {
			date = TimeUtils.addDays(1);
		}
		return formater.format(date) + " 18:00:00";
		
	}
	
	public static Date getDateHourMinsTime(Date date, int hours, int mins) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, mins);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
	
	public static Date getNextTimeByMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
	
	public static Date getNextTimeByHour(Date date, double hrs) {
    	int hours = (int) hrs;
    	int mins = (int) ((hrs - hours) * 60);
        Calendar c = Calendar.getInstance();
    	c.setTime(date);
        if(hours > 0){
            c.add(Calendar.HOUR_OF_DAY, hours);
        }
        if(mins > 0){
            c.add(Calendar.MINUTE, mins);
        }
        Date endTime = getDateHourMinsTime(date, 22, 0);	// 最终不能超过22点
        if(c.getTime().after(endTime)){
        	return endTime;
        }
        return c.getTime();
    }
	
	public static List<String> get15MinsHourBetweenTimeRange(Date fromTime, double hours) {
        Date endTime = getNextTimeByHour(fromTime, hours);
        /*if(endTime.getDate() == fromTime.getDate()){
        	return null;
        }*/
        if(fromTime.after(endTime)){
        	return null;
        }
    	
        List<String> result = new ArrayList<String>();
        Date calTime = fromTime;
        do{
        	result.add(dateToString(calTime, "HHmm"));
        	calTime = getNextTimeByMinute(calTime, 15);
        }while(endTime.after(calTime));
        
        return result;
    }
	
	public static List<Date> getDateRange(Date fromDate, Date toDate) {
		List<Date> result = new ArrayList<Date>();
		do{
			result.add(fromDate);
			fromDate = addDays(fromDate, 1);
		}while(!fromDate.after(toDate));
        return result;
    }
	
	public static Integer getWeekDateNum(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
	
	/**
     * 获取日期的当天开始时间
     * 
     * @return
     */
    public static Date getDayStart(Date day) {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE);
        String beginDate = formatter.format(day);
        try {
            Date date = formatter.parse(beginDate);
            return date;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期的当天结束时间
     * 
     * @return
     */
    public static Date getDayEnd(Date day) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATETIME);
        try {
            Date date = format.parse(getDayEndStr(day));
            return date;
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期的当天结束时间字符串
     * 
     * @return
     */
    public static String getDayEndStr(Date day) {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE);
        String endDate = formatter.format(day);
        return endDate + " 23:59:59";
    }
    
    /**
     * 获取日期的当天开始时间字符串
     * 
     * @return
     */
    public static String getDayStartStr(Date day) {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE);
        String beginDate = formatter.format(day);
        return beginDate + " 00:00:00";
    }
    
	// 获取日期时间，带AM-PM
	public static String getDateStrWithAmPm(Date time) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(time);
		int ampmSign = cld.get(Calendar.AM_PM);  
		
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		String datetimeStr = df.format(time);
		
		StringBuffer sb = new StringBuffer();
		sb.append(datetimeStr).append(ampmSign==0?"AM":"PM");
		return sb.toString();
	}

	public static int getBetweenMonths(Date start, Date end) {  
        if (start.after(end)) {  
            Date t = start;  
            start = end;  
            end = t;  
        }  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.setTime(start);  
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.setTime(end);  
        
        int yearStart = startCalendar.get(Calendar.YEAR);
        int yearEnd = endCalendar.get(Calendar.YEAR);
        int monthStart = startCalendar.get(Calendar.MONTH);
        int monthEnd = endCalendar.get(Calendar.MONTH);
        int dayStart = startCalendar.get(Calendar.DATE);
        int dayEnd = endCalendar.get(Calendar.DATE);
        
        int yeargap = yearEnd - yearStart;
        boolean isMonthBefore = monthEnd<monthStart;
        
        int result = 0;
        if(isMonthBefore){
        	result = ((yeargap-1)*12)+(12-(monthStart-monthEnd));
        } else {
        	result = (yeargap*12)+(monthEnd-monthStart);
        }
        if(dayEnd > dayStart){
        	result = result +1;
        }
        
        return result; 
    } 
	
	public static void main(String[] args) {
		Date fromTime = TimeUtils.StringToDate("2017-01-01 01:00:00");
		List<String> list = TimeUtils.get15MinsHourBetweenTimeRange(fromTime, 0.75);
		for(String s : list){
			System.out.println(s);
		}
	}
	

	
}
