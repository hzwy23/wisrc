package com.wisrc.order.webapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    /**
     * 将多个字符串拼接起来，得到一个使用0x1e,0x1f的长字符串
     *
     * @param ele 可变参数，是需要拼接的字符串
     * @return String 拼接之后的字符串
     */
    public static String join(String... ele) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ele.length; i++) {
            if (i > 0) {
                sb.appendCodePoint(0x1e).appendCodePoint(0x1f).append(ele[i]);
            } else {
                sb.append(ele[i]);
            }
        }
        return sb.toString();
    }


    /**
     * sha-1 加密计算
     *
     * @param ele
     */
    public static String sha(String... ele) {
        String str = join(ele);
        return sha1(str);
    }


    /**
     * shaWithTime 拼接多个字符串，在结尾处添加当前时间戳，保证唯一性
     *
     * @param ele
     * @return String 拼接之后的字符串
     */
    public static String shaWithTime(String... ele) {
        String str = join(ele);
        StringBuilder sb = new StringBuilder(str);
        sb.appendCodePoint(0x1e).appendCodePoint(0x1f).append(Time.getCurrentDateTime());
        return sha1(sb.toString());
    }


    private static String sha1(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            byte[] m = messageDigest.digest();
            return bytesToHexString(m);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
