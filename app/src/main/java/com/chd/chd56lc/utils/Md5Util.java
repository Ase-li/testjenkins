package com.chd.chd56lc.utils;

import android.text.TextUtils;

import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author 作者   shulan
 * @date 创建时间：2016-5-8
 * @parameter
 * @return
 */
public class Md5Util {

    /***
     *
     * @param plainText
     * @return 32位密文 小写
     */
    public static String encryption(String plainText) {

        String re_md5 = new String();
        try {

            if (null == plainText || TextUtils.isEmpty(plainText)) {

                plainText = "";
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();

        } catch (Exception e) {
//			e.printStackTrace();
        }
        return re_md5.toLowerCase();
    }

}
