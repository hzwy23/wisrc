package com.wisrc.oss.webapp.vo;

import java.util.Arrays;

public class HtmlContentVO {
    private String html;
    private KeyValue[] keyValues;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public KeyValue[] getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(KeyValue[] keyValues) {
        this.keyValues = keyValues;
    }

    @Override
    public String toString() {
        return "HtmlContentVO{" +
                "html='" + html + '\'' +
                ", keyValues=" + Arrays.toString(keyValues) +
                '}';
    }
}
