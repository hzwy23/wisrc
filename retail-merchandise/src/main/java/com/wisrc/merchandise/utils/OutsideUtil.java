package com.wisrc.merchandise.utils;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

public class OutsideUtil {
    public JSONObject relayRequest(HttpServletRequest request, HttpServletResponse response, String strURL, Map<String, String> header) throws Exception {
        URL url = new URL(strURL);
        HttpURLConnection httpConn = (HttpURLConnection)
                url.openConnection();
        httpConn.setRequestMethod("GET");
        if (request != null) {
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String nextElement = headerNames.nextElement();
                httpConn.setRequestProperty(nextElement, request.getHeader(nextElement));
            }
        }

        for (String head : header.keySet()) {
            httpConn.setRequestProperty(head, header.get(head));
        }


        httpConn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpConn.getInputStream()));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        httpConn.disconnect();

        return JSONObject.parseObject(buffer.toString());
    }
}