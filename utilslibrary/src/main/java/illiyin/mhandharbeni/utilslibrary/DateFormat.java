package illiyin.mhandharbeni.utilslibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by root on 10/3/17.
 */

public class DateFormat {
    public String format(String dates) throws ParseException {
        String conMonth = "";

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(dates);
        SimpleDateFormat sDay = new SimpleDateFormat("EE");
        SimpleDateFormat sDate = new SimpleDateFormat("dd");
        SimpleDateFormat sMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("dd MMMM yyyy hh:mm");
        String csDay = "";
        switch (sDay.format(date)){
            case "Sun":
                csDay  = "Minggu";
                break;
            case "Mon":
                csDay  = "Senin";
                break;
            case "Tue":
                csDay  = "Selasa";
                break;
            case "Wed":
                csDay  = "Rabu";
                break;
            case "Thu":
                csDay  = "Kamis";
                break;
            case "Fri":
                csDay  = "Jumat";
                break;
            case "Sat":
                csDay  = "Sabtu";
                break;
        }
        String csMonth = "";
        switch (sMonth.format(date)){
            case "01" :
                csMonth = "Januari";
                break;
            case "02" :
                csMonth = "Februari";
                break;
            case "03" :
                csMonth = "Maret";
                break;
            case "04" :
                csMonth = "April";
                break;
            case "05" :
                csMonth = "Mei";
                break;
            case "06" :
                csMonth = "Juni";
                break;
            case "07" :
                csMonth = "Juli";
                break;
            case "08" :
                csMonth = "Agustus";
                break;
            case "09" :
                csMonth = "September";
                break;
            case "10" :
                csMonth = "Oktober";
                break;
            case "11" :
                csMonth = "November";
                break;
            case "12" :
                csMonth = "Desember";
                break;
        }
        conMonth += csDay+", "+sDate.format(date)+" "+csMonth+" "+sYear.format(date);
//        conMonth += month.format(date);
        return conMonth;
    }
}
