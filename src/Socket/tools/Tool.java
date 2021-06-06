package Socket.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


//获取当前时间
public class Tool {
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return simpleDateFormat.format(date);
    }


//比较时间1和2大小，小返回-1，相等返回0，大返回1
    public static int timeCompare(String time1,String time2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date1 = null;
        Date date2 = null;
        date1 = simpleDateFormat.parse(time1);
        date2 = simpleDateFormat.parse(time2);
        long ts1 = date1.getTime();//获取时间的时间戳
        long ts2 = date2.getTime();
        if (ts1<ts2){
            return -1;
        }
        else if (ts1>ts2){
            return 1;
        }
        return 0;
    }

}
