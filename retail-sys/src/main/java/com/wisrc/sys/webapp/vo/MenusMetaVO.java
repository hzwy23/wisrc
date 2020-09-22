package com.wisrc.sys.webapp.vo;

public class MenusMetaVO {
    private String title;
    private String icon;
    private boolean noCache;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    @Override
    public String toString() {
        return "MenusMetaVO{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", noCache=" + noCache +
                '}';
    }
}
