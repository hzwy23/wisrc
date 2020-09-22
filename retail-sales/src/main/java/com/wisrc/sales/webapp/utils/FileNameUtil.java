package com.wisrc.sales.webapp.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * FileNameUtil
 * 文件名处理工具类
 *
 * @author MAJANNING
 * @date 2018/6/26
 */
public class FileNameUtil {
    // ie 系列浏览器 userAgent标志
    private static String[] IE_BROWSER_SIGNALS = {"msie", "trident", "edge"};

    /**
     * 解决在不同浏览器下导出文件导致的中文乱码问题
     *
     * @param originFileName 原始文件名
     * @param httpRequest    http请求
     * @return 经过编码后的文件名称
     */
    public static String exportTo(String originFileName, HttpServletRequest httpRequest) {

        // 首先判断是什么浏览器
        // chrome和firefox的行为是一致的
        // edge 和ie 一致
        try {
            if (isIESetBrowers(httpRequest)) {
                return URLEncoder.encode(originFileName, "UTF-8");
            } else {
                return new String(originFileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ignored) {

        }
        return originFileName;
    }

    /**
     * 判断是否是ie系列的浏览器
     *
     * @param httpRequest
     * @return 如果存在<code>User-Agent</code>同时包含ie浏览器的标志，则返回<code>true</code>
     * 否则返回<code>false</code>
     */
    private static boolean isIESetBrowers(HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader("User-Agent");
        if (userAgent == null) {
            return false;
        }
        userAgent = userAgent.toLowerCase();

        for (int i = IE_BROWSER_SIGNALS.length - 1; i >= 0; i--) {
            if (userAgent.contains(IE_BROWSER_SIGNALS[i])) {
                return true;
            }
        }
        return false;
    }
}
