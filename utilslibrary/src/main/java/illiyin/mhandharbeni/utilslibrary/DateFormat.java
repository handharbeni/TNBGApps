package illiyin.mhandharbeni.utilslibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 10/3/17.
 */

public class DateFormat {
    public String format(String dates) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(dates);
        SimpleDateFormat dt1 = new SimpleDateFormat("EE, dd MMMM yyyy hh:mm");
        return dt1.format(date);
    }
}
