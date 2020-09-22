package com.wisrc.order.webapp.entity;

public class OrderInvoiceStatusAttrEntity {

    private long statusCd;
    private String statusDesc;


    public long getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(long statusCd) {
        this.statusCd = statusCd;
    }


    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }


    @Override
    public String toString() {
        return "OrderInvoiceStatusAttr{" +
                "statusCd=" + statusCd +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }
}
