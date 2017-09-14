package illiyin.mhandharbeni.utilslibrary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by root on 06/08/17.
 */

public class NumberFormat {
    public static String format(double d) {
        Double value = d;
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("Rp.");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(dfs);
        return formatter.format(value);
    }
}
