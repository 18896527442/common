package com.ll.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtils {

	/**
	 * 获取一天00:00:00
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		//将小时至0  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		 //将分钟至0  
		calendar.set(Calendar.MINUTE, 0);  
		 //将秒至0  
		calendar.set(Calendar.SECOND,0);  
		return calendar.getTime(); 
	}
	
	/**
	 * 获取一天23:59:59
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date); 
		calendar.set(Calendar.HOUR_OF_DAY, 23);   
		calendar.set(Calendar.MINUTE, 59);    
		calendar.set(Calendar.SECOND,59);  
		return calendar.getTime(); 
	}

	/**
	 * 时间格式转化 将 时间格式 0910 转化为 09:10
	 *
	 * @param time
	 * @return
	 */
	public static String getStandardTime(String time) {
		if (StrUtil.isEmpty(time)) {
			return "";
		}
		return String.join(":", time.substring(0, 2), time.substring(2, 4));
	}

	/**
	 * 时间格式转化 将 时间格式 20190820 转化为 2019-08-20
	 *
	 * @param date
	 * @return
	 */
	public static String getStandardDate(String date) {
		if (StrUtil.isEmpty(date)) {
			return "";
		}
		return String.join("-", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
	}

	/**
	 * 时间格式转化 将 时间格式 0910 转化为 09:10
	 *
	 * @param dateList
	 * @return
	 */
	public static List<String> getTicketCenterValidateDate(List<String> dateList) {
		if (CollUtil.isEmpty(dateList)) {
			return null;
		}
		String fromDate = DateUtil.format(new Date(), "yyyy-MM-dd");
		String lastDate = DateUtil.format(DateUtil.offsetDay(new Date(),15), "yyyy-MM-dd");
		List<String> returnList = new ArrayList<>();
		for (String date : dateList) {
			if (date.compareTo(fromDate) < 0 || date.compareTo(lastDate) > 0) {
				continue;
			}
			returnList.add(date);
		}
		return returnList;
	}

	public static String timeStamp2Date(String seconds) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}

	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 判断当前时间距离第二天凌晨的秒数
	 *
	 * @return 返回值单位为[s:秒]
	 */
	public static Long getSecondsNextEarlyMorning() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
	}

}
