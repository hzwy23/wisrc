package com.wisrc.sys.webapp.bo;


public class WarehouseInfoBO {
    private String warehouseId;
    private String warehouseName;
    private String typeCd;
    private Integer statusCd;
    private String shippAddress;
    private String createUser;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getShippAddress() {
        return shippAddress;
    }

    public void setShippAddress(String shippAddress) {
        this.shippAddress = shippAddress;
    }

    @Override
    public String toString() {
        return "WarehouseInfoBO{" +
                "warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", typeCd='" + typeCd + '\'' +
                ", statusCd='" + statusCd + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
