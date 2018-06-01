/**
 * @Description
 * @Author: XiongKai
 * @studentNo 15130120199
 * @Emailaddress 1249235131@qq.com
 */

package calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Cal {
    public static final String[] MONTHS = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};
    public static final String[] WEEKS = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    public static int[][] printCalendar(int year,int month) throws ParseException {
        //System.out.println("Su"+"  "+"Mo"+"  "+"Tu"+"  "+"We"+"  "+"Th"+"  "+"Fr"+"  "+"Sa");
        String monthStr;
        if(month<10){
            monthStr = "0"+month;
        } else{
            monthStr = month+"";
        }
        String yearMonthStr = year+monthStr;
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarStart = Calendar.getInstance();
        int monthDays = getMonthLastDay(year, month);
        String dateStartStr = yearMonthStr+"01";
        String dateEndStr = yearMonthStr+monthDays;

        calendarStart.setTime(sd.parse(dateStartStr));
        calendarEnd.setTime(sd.parse(dateEndStr));

        int weeks = calendarEnd.get(Calendar.WEEK_OF_MONTH);
        int dayOfWeek = calendarStart.get(Calendar.DAY_OF_WEEK);
        int day = 1;
        int[][] monthDay = new int[6][7];
        for(int i=1;i<=7;i++){
            if(i>=dayOfWeek){
            	monthDay[0][i-1] = day;
                //System.out.print(" "+day+"  ");
                day++;
            } else{
            	monthDay[0][i] = 0;
                //System.out.print("    ");
            }
        }
        System.out.println();

        for(int i=1;i<6;i++){
            for(int j=0;j<7;j++){
                if(day>monthDays){
                    break;
                }
                if(day<10){
                    //System.out.print(" "+day+"  ");
                } else{
                    //System.out.print(day+"  ");
                }
                monthDay[i][j] = day;
                day++;
            }
            //System.out.println();
        }
        return monthDay;
    }


    public static int getMonthLastDay(int year,int month){
        int monthDay;
        int[][] day = {{0,31,28,31,30,31,30,31,31,30,31,30,31},
                {0,31,29,31,30,31,30,31,31,30,31,30,31}};
        if(year%4==0 && year%100!=0 || year%400==0){
            monthDay = day[1][month];
        } else{
            monthDay = day[0][month];
        }
        return monthDay;
    }
    
    static public void main(String []args)throws ParseException {
        Calendar c = Calendar.getInstance();
        try {
            int month,year;
            month = Integer.parseInt(args[0]);
            year = Integer.parseInt(args[1]);
            if(month>0&&month<=12){
                c.set(Calendar.MONTH,month-1);
                c.set(Calendar.YEAR,year);
            }
        } catch( Exception e) {
        }
        System.out.println(MONTHS[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
        printCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
    }
    
}
