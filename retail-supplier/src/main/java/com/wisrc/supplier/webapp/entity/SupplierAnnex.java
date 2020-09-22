package com.wisrc.supplier.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 供应商附件
 *
 * @author linguosheng
 */
public class SupplierAnnex {

    private Integer id;

    @ApiModelProperty(hidden = true)
    private String sId;

    @ApiModelProperty(value = "类型：0执照 1附件")
    private Integer type;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件地址")
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
