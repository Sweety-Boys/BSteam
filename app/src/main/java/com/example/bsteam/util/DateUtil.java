package com.example.bsteam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 获取系统时间戳
     * @return
     */
    public long getCurTimeLong(){
        long time=System.currentTimeMillis();
        return time;
    }
    //获取当前系统时间
    public static String getCurDate(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(new java.util.Date());
    }

    //日期转换为字符串
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    //获取日期的“日期部分”
    public static String getStrDateFromStr(String strDate) {
        return strDate.split(" ")[0];
    }
    //获取日期的“时间部分”
    public static String getStrTimeFromStr(String strDate) {
        return strDate.split(" ")[1];
    }

    public static Date getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static Date ymdToDate(int y,int m,int d){
        String strDate = y+"-"+m+"-"+d;
        return getStringToDate(strDate);
    }

    //比较两个字符串存储的日期，str1在str2之前则返回true
    public static Boolean comepareStr(String str1,String str2){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean before = false;
        try {
            Date date1 = format.parse(str1);
            Date date2 = format.parse(str2);
            before = date1.before(date2);
            System.out.println(before);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return before;
    }
    public static String addDateMinut(String day, int hour){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return new Date().toString();
//        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
//        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return dateToString(date);
    }
}
