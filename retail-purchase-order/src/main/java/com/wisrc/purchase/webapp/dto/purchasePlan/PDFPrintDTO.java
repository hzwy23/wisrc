package com.wisrc.purchase.webapp.dto.purchasePlan;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * PDFPrintDTO
 *
 * @author MAJANNING
 * @date 2018/7/16
 */
@Data
public class PDFPrintDTO {
    private String htmlContent;
    private Map<String, String> data;

    public PDFPrintDTO(String htmlContent) {
        this.htmlContent = htmlContent;
        data = new HashMap<>();
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void addData(String key, String value) {
        this.data.put(key, value);
    }
}
