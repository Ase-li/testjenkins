package com.chd.chd56lc.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对字符串进行处理
 *
 * @author 作者   shulan
 * @date 创建时间：2016-5-8
 * @parameter
 * @return
 */
public class StringUtils {
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value
                .trim())) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isSignEmpty(Object value) {
        value = String.valueOf(value);
        if (value != null && !"".equalsIgnoreCase(((String) value).trim()) && !"null"
                .equalsIgnoreCase(((String) value).trim()) && !value.equals("-11") && value !=
                -11 + "") {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNull(String string) {
        return TextUtils.isEmpty(string) || string.equals("null");
    }

    @SuppressLint("NewApi")
    public static String SignToString(String str) {
        if (str.isEmpty()) {
            return "";
        }
        return str + "#$!FA)A!";

    }

    public static String getTimeStamp() {
        String timestamp = "";
        try {
            timestamp = System.currentTimeMillis() + "";
        } catch (Exception e) {
            e.printStackTrace();
            timestamp = "";
        }
        return timestamp;
    }

    public static String getIntString(String floatstr) {
        float value = Float.parseFloat(floatstr);
        return String.valueOf((int) value);
    }

    //判断是否是8位以上数字字母组合
    public static boolean password(String passWord) {
        if (isEmpty(passWord)) {
            return false;
        }

        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,}$");

        return p.matcher(passWord).matches();
    }

    //判断是否是汉字
    public static boolean chanese(String name) {
        if (isEmpty(name)) {
            return false;
        }

        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]+$");

        return p.matcher(name).matches();
    }

    public static SpannableString setmultTextView(String content, String colorString, int start, int end, int flags) {
        SpannableString s = new SpannableString(content);
        s.setSpan(new ForegroundColorSpan(Color.parseColor(colorString)), start, end, flags);
        return s;
    }

    public static String parseDouble(double parseDouble, int digit) {
        BigDecimal bigDecimal = BigDecimal.valueOf(parseDouble).setScale(BigDecimal.ROUND_HALF_UP, digit);
        return String.valueOf(bigDecimal);
    }

    /**
     * 验证手机格式
     */

    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "1[3578][01379]\\d{8}";//
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        if (mobiles.matches(telRegex)) {
            return true;
        }
        telRegex = "1[34578][0123456789]\\d{8}";
        if (mobiles.matches(telRegex)) {
            return true;
        }
        telRegex = "134[0123456789]\\d{7}";
        if (mobiles.matches(telRegex)) {
            return true;
        }
        telRegex = "1[34578][0123456789]\\d{8}";
        if (mobiles.matches(telRegex)) {
            return true;
        }
        return false;
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        return Pattern.matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$", email);
    }

    /**
     * 脱敏
     *
     * @param s          目标字符串
     * @param startIndex 前半截
     * @param endIndex   后半截
     * @return
     */
    public static String desensitization(String s, int startIndex, int endIndex, String middleString) {
        if (isEmpty(s))
            return "";
        if (startIndex == 0)
            return middleString + s.substring(s.length() - endIndex);
        else
            return s.substring(0, startIndex) + middleString + s.substring(s.length() - endIndex);
    }

    public static String getDesensitizeName(String name) {
        StringBuilder realName = new StringBuilder();
        if (name.length() < 4) {
            realName.append(name.substring(0, 1));
        } else {
            realName.append(name.substring(0, 2));
        }
        int size = name.length() - realName.length();
        for (int i = 0; i < size; i++) {
            realName.append("*");
        }
        return realName.toString();
    }

    public static String replacePoint(String oldString) {
        if (!isEmpty(oldString) && oldString.contains("-")) {
            return oldString.replace("-", ".");
        }
        return oldString;
    }

    public static String replacePoint1(String oldString) {
        Calendar instance = Calendar.getInstance();
        String year = String.valueOf(instance.get(Calendar.YEAR));
        if (!isEmpty(oldString) && oldString.contains("-") && year.equals(oldString.substring(0, oldString.indexOf("-")))) {
            return oldString.substring(oldString.indexOf("-") + 1).replace("-", ".");
        }
        return oldString;
    }


}
