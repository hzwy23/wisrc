package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

public class FbaMskuSpecificationVO {

    @ApiModelProperty(value = "修改补货数量")
    protected List<FbaMskuPackUpdateVO> mskuPackUpdateList;
    @ApiModelProperty(value = "补货商品ID")
    private String replenishmentCommodityId;
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Timestamp modifyTime;
    @ApiModelProperty(value = "修改人", hidden = true)
    private String modifyUser;

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public List<FbaMskuPackUpdateVO> getMskuPackUpdateList() {
        return mskuPackUpdateList;
    }

    public void setMskuPackUpdateList(List<FbaMskuPackUpdateVO> mskuPackUpdateList) {
        this.mskuPackUpdateList = mskuPackUpdateList;
    }
}
