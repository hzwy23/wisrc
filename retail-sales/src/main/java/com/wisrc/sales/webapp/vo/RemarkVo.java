package com.wisrc.sales.webapp.vo;

import com.wisrc.sales.webapp.utils.DateUtil;

import java.util.Date;

public class RemarkVo {
    private String EmployeeName;
    private String employeeId;
    private String createTime;
    private String Remark;

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getCreateTime() {
        Date date = DateUtil.convertStrToDate(createTime, DateUtil.DATETIME_FORMAT);
        return DateUtil.convertDateToStr(date, DateUtil.DATETIME_FORMAT);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
