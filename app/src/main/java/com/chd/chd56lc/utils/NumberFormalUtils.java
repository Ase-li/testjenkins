package com.chd.chd56lc.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by li on 2018/3/29.
 */

public class NumberFormalUtils {

    public static String percentFormat(double d, int minDigits, int maxDigits) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMinimumFractionDigits(minDigits);
        percentInstance.setMaximumFractionDigits(maxDigits);
        return percentInstance.format(d);
    }

    public static String numberFormat(double d, int minDigits, int maxDigits) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setRoundingMode(RoundingMode.DOWN);
        numberInstance.setMinimumFractionDigits(minDigits);
        numberInstance.setMaximumFractionDigits(maxDigits);
        return numberInstance.format(d);
    }

}
