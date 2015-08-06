package com.love.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.love.framework.exception.ApplicationRuntimeException;

public class DateUtil {
	
	public final static String TYPE_YEAR = "YEAR";
	public final static String TYPE_MONTH = "MONTH";
	public final static String TYPE_DAY = "DAY";
	
	/**
	 * 增加年/月/日(输入日期格式,返回字符串)
	 * @param date 日期格式
	 * @param type YEAR/MONTH/DAY
	 * @param n 数量
	 * @return 字符串日期  yyyy-MM-dd
	 */
	public final static String addDateReStr(Date date,String type,int n){
		try {   
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   

            Calendar cd = Calendar.getInstance();   
            cd.setTime(date);   
            if(type.equals("YEAR")){
            	cd.add(Calendar.YEAR, n);//增加一年  
            }else if(type.equals("MONTH")){
            	cd.add(Calendar.MONTH, n);//增加一月   
            }else if(type.equals("DAY")){
            	cd.add(Calendar.DATE, n);//增加一日
            }else{
            	cd.add(Calendar.MONTH, n);//增加一月   
            }
            return sdf.format(cd.getTime());   
  
        } catch (Exception e) {   
        	throw new ApplicationRuntimeException("数据异常", e);
        }
	}
	
	/**
	 * 增加年/月/日(输入日期格式,返回日期格式)
	 * @param date 日期格式
	 * @param type YEAR/MONTH/DAY
	 * @param n 月数
	 * @return 日期格式
	 */
	public final static Date addDateReDate(Date date,String type,int n){
		try {   
            Calendar cd = Calendar.getInstance();   
            cd.setTime(date);   
            if(type.equals("YEAR")){
            	cd.add(Calendar.YEAR, n);//增加一年  
            }else if(type.equals("MONTH")){
            	cd.add(Calendar.MONTH, n);//增加一月   
            }else if(type.equals("DAY")){
            	cd.add(Calendar.DATE, n);//增加一日
            }else{
            	cd.add(Calendar.MONTH, n);//增加一月   
            } 
            return cd.getTime();   
        } catch (Exception e) {   
        	throw new ApplicationRuntimeException("数据异常", e);
        }
	}
	
	/**
	 * 增加年/月/日(输入字符串,返回字符串)
	 * @param date 字符串日期  yyyy-MM-dd
	 * @param type YEAR/MONTH/DAY
	 * @param n 月数
	 * @return 字符串日期   yyyy-MM-dd
	 */
	public final static String addDateStrReStr(String date,String type,int n){
		try {   
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
            Calendar cd = Calendar.getInstance();   
            cd.setTime(sdf.parse(date));
            if(type.equals("YEAR")){
            	cd.add(Calendar.YEAR, n);//增加一年  
            }else if(type.equals("MONTH")){
            	cd.add(Calendar.MONTH, n);//增加一月   
            }else if(type.equals("DAY")){
            	cd.add(Calendar.DATE, n);//增加一日
            }else{
            	cd.add(Calendar.MONTH, n);//增加一月   
            }
            return sdf.format(cd.getTime());   
        } catch (Exception e) {   
        	throw new ApplicationRuntimeException("数据异常", e);
        }
	}
	
	/**
	 * 增加年/月/日(输入日期格式,返回日期)
	 * @param date 字符串日期  yyyy-MM-dd
	 * @param type YEAR/MONTH/DAY
	 * @param n 月数
	 * @return 日期格式
	 */
	public final static Date addDateStrReDate(String date,String type,int n){
		try {   
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            Calendar cd = Calendar.getInstance();   
            cd.setTime(sdf.parse(date));
            if(type.equals("YEAR")){
            	cd.add(Calendar.YEAR, n);//增加一年  
            }else if(type.equals("MONTH")){
            	cd.add(Calendar.MONTH, n);//增加一月   
            }else if(type.equals("DAY")){
            	cd.add(Calendar.DATE, n);//增加一日
            }else{
            	cd.add(Calendar.MONTH, n);//增加一月   
            }
            return cd.getTime();   
        } catch (Exception e) {   
        	throw new ApplicationRuntimeException("数据异常", e);
        }
	}
	
	/**
	 * 获取当前日期（yyyyMMdd）
	 */
	public final static String getCurrDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
        return sdf.format(new Date());   
	}
	
	public static int datesBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
            
       return Integer.parseInt(String.valueOf(between));           
    } 
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
        long days=between/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(days));           
    }    
   /* 
    *//**  
     * 计算两个日期之间相差的小时数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差小时数 
     * @throws ParseException  
     *//*    
    public static int hoursBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
        long days=between/(1000*3600*24);  
        long hours=(between/(60*60*1000)-days*24);
            
       return Integer.parseInt(String.valueOf(hours));           
    } 
    
    *//**  
     * 计算两个日期之间相差的分钟数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差分钟数 
     * @throws ParseException  
     *//*    
    public static int minsBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
        long days=between/(1000*3600*24);  
        long hours=(between/(60*60*1000)-days*24);
        long mins=((between/(60*1000))-days*24*60-hours*60);
            
       return Integer.parseInt(String.valueOf(mins));           
    } 
    
    *//**  
     * 计算两个日期之间相差的秒数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差秒数 
     * @throws ParseException  
     *//*    
    public static int secondsBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
        long days=between/(1000*3600*24);  
        long hours=(between/(60*60*1000)-days*24);
        long mins=((between/(60*1000))-days*24*60-hours*60);
        long seconds=(between/1000-days*24*60*60-hours*60*60-mins*60);
            
       return Integer.parseInt(String.valueOf(seconds));           
    } */
    
    public static int datesBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
            
       return Integer.parseInt(String.valueOf(between));     
    }
      
/** 
*字符串的日期格式的计算 (天数)
*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();  
        long between = time2-time1;
        long days=between/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(days));     
    }
    
   /* *//** 
    *字符串的日期格式的计算 (小时)
    *//*  
        public static int hoursBetween(String smdate,String bdate) throws ParseException{  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Calendar cal = Calendar.getInstance();    
            cal.setTime(sdf.parse(smdate));    
            long time1 = cal.getTimeInMillis();                 
            cal.setTime(sdf.parse(bdate));    
            long time2 = cal.getTimeInMillis(); 
            long between = time2-time1;
            long days=between/(1000*3600*24);
            long hours=(between/(60*60*1000)-days*24);
                
           return Integer.parseInt(String.valueOf(hours));     
        }
        
        *//** 
         *字符串的日期格式的计算 (分钟)
         *//*  
             public static int minsBetween(String smdate,String bdate) throws ParseException{  
                 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                 Calendar cal = Calendar.getInstance();    
                 cal.setTime(sdf.parse(smdate));    
                 long time1 = cal.getTimeInMillis();                 
                 cal.setTime(sdf.parse(bdate));    
                 long time2 = cal.getTimeInMillis(); 
                 long between = time2-time1;
                 long days=between/(1000*3600*24);
                 long hours=(between/(60*60*1000)-days*24);
                 long mins=((between/(60*1000))-days*24*60-hours*60);
                     
                return Integer.parseInt(String.valueOf(mins));     
             }
             
             *//** 
              *字符串的日期格式的计算 (秒)
              *//*  
                  public static int secondsBetween(String smdate,String bdate) throws ParseException{  
                      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                      Calendar cal = Calendar.getInstance();    
                      cal.setTime(sdf.parse(smdate));    
                      long time1 = cal.getTimeInMillis();                 
                      cal.setTime(sdf.parse(bdate));    
                      long time2 = cal.getTimeInMillis(); 
                      long between = time2-time1;
                      System.out.println(between);
                      long days=between/(1000*3600*24);
                      long hours=(between/(60*60*1000)-days*24);
                      long mins=((between/(60*1000))-days*24*60-hours*60);
                      long seconds=(between/1000-days*24*60*60-hours*60*60-mins*60);
                          
                     return Integer.parseInt(String.valueOf(seconds));     
                  }*/
    
    /**  
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式  
     * @param date2 被比较的时间  为空(null)则为当前时间  
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年  
     * @return  
     */ 
    public static int compareDate(String date1,String date2,int stype){  
        int n = 0;  
        String[] u = {"天","月","年"};  
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";  
        date2 = date2==null?getCurrentDate():date2;  
        DateFormat df = new SimpleDateFormat(formatStyle);  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        try {  
            c1.setTime(df.parse(date1));  
            c2.setTime(df.parse(date2));  
        } catch (Exception e3) {  
            System.out.println("wrong occured");  
        }  
        //List list = new ArrayList();  
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果  
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来  
            n++;  
            if(stype==1){  
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1  
            }  
            else{  
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1  
            }  
        }  
        n = n-1;  
        if(stype==2){  
            n = (int)n/365;  
        }     
        System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);        
        return n;  
    }
    
    /**  
     * 得到当前日期  
     * @return  
     */ 
    public static String getCurrentDate() {  
        Calendar c = Calendar.getInstance();  
        Date date = c.getTime();  
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");  
        return simple.format(date);  
    } 
    
}