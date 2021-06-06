package Socket.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
        return simpleDateFormat.format(date);
    }

}
