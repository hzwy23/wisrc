package com.wisrc.merchandise.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FbaOnWayQuantityAndOnStockQuantityDTO
 * 查询msku在途和在仓的实时库存传输对象
 *
 * @author MAJANNING
 * @date 2018/8/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FbaOnWayQuantityAndOnStockQuantityDTO {

    private String shopId;
    private String mskuId;
    private String fnSkuId;
    //fba 实时在途库存
    private Integer fbaTransportQuantityRealTime;
    //fba 实施在仓库存
    private Integer fbaStockQuantityRealTime;

}
