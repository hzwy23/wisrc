package com.wisrc.oss.webapp.vo;

import java.util.Map;

/**
 * RenderHTMLDataVo
 *
 * @author MAJANNING
 * @date 2018/7/17
 */
public class RenderHTMLDataVo {
    private String htmlContent;
    private Map<String, Object> data;

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
