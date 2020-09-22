package com.wisrc.sys.webapp.vo;

public class WarehouseDataPrivilegesVO {
    private String uuid;
    private String warehouseCd;
    private String privilegeCd;
    private String createUser;
    private String createTime;

    private String warehouseName;
    private String warehouseTypeCd;
    private String warehouseTypeName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseCd() {
        return warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public String getPrivilegeCd() {
        return privilegeCd;
    }

    public void setPrivilegeCd(String privilegeCd) {
        this.privilegeCd = privilegeCd;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseTypeCd() {
        return warehouseTypeCd;
    }

    public void setWarehouseTypeCd(String warehouseTypeCd) {
        this.warehouseTypeCd = warehouseTypeCd;
    }

    public String getWarehouseTypeName() {
        return warehouseTypeName;
    }

    public void setWarehouseTypeName(String warehouseTypeName) {
        this.warehouseTypeName = warehouseTypeName;
    }

    @Override
    public String toString() {
        return "WarehouseDataPrivilegesVO{" +
                "uuid='" + uuid + '\'' +
                ", warehouseCd='" + warehouseCd + '\'' +
                ", privilegeCd='" + privilegeCd + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseTypeCd='" + warehouseTypeCd + '\'' +
                ", warehouseTypeName='" + warehouseTypeName + '\'' +
                '}';
    }
}
