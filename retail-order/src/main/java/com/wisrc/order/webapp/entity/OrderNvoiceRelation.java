package com.wisrc.order.webapp.entity;


public class OrderNvoiceRelation {

    private String uuid;
    private String invoiceNumber;
    private String orderId;
    private String originalOrderId;
    private String createTime;
    private String createUser;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(String originalOrderId) {
        this.originalOrderId = originalOrderId;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    @Override
    public String toString() {
        return "OrderNvoiceRelation{" +
                "uuid='" + uuid + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", orderId='" + orderId + '\'' +
                ", originalOrderId='" + originalOrderId + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
