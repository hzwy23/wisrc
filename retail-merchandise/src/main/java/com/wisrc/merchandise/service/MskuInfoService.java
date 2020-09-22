package com.wisrc.merchandise.service;


import com.wisrc.merchandise.dto.msku.GetMskuFnskuDto;
import com.wisrc.merchandise.entity.MskuSaleNumEnity;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.*;
import com.wisrc.merchandise.vo.mskuStockSalesInfo.SetBatchMskuStockSalesInfoVO;
import com.wisrc.merchandise.vo.outside.GetIdByNumAndShopVo;
import com.wisrc.merchandise.vo.outside.MskuSafetyDayEditVo;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MskuInfoService {
    Result getMskuList(String userId, String shopId, String team, String manager, Integer deliveryMode, Integer salesStatus, Integer mskuStatus,
                       String findKey, Integer currentPage, Integer pageSize, String sort);

    Result saveMsku(MskuInfoSaveVo mskuInfoSaveVo);

    Result getMsku(String id);

    Result editMsku(MskuInfoEditVo mskuInfoEditVo);

    Result mskuSwitch(MskuSwitchVo mskuSwitchVo);

    Result allDelete(IdsBatchVo idsBatchVo);

    Result checkMsku(String msku, String shopId);

    Result saveEpitaph(EpitaphSaveVo epitaphSaveVo, BindingResult bindingResult);

    Result editEpitaph(EpitaphSaveVo epitaphSaveVo, BindingResult bindingResult);

    Result changeManager(IdsBatchVo chargeEditVo);

    Result getMskuOutSide(String id);

    Result getMskuListOutside(MskuInfoPageOutsideVo mskuInfoPageOutsideVo, String userId);

    Result getMskuInfo(GetMskuListByIdVo getMskuListByIdVo);

    Result getMskuId(GetMskuIdVo getMskuIdVo, String userId);

    Result getIdByNumAndShop(GetIdByNumAndShopVo getMskuIdVo, String userId);

    Result getIdByMskuIdAndName(GetByMskuIdAndNameVo getByMskuIdAndNameVo, String userId);

    Result mskuSearch(MskuSearchVo mskuSearchVo, String userId);

    Result mskuFnsku(String fnsku);

    /**
     * 通过fnsku模糊查询msku
     *
     * @param fnsku
     * @return
     */
    List<GetMskuFnskuDto> mskuByFnSkuLike(String fnsku);

    Result getSkuId(List saleStatusList);

    Result updateSalesStock(SetBatchMskuStockSalesInfoVO vo);

    Result getSalesStockById(String id);

    Result updateSaleList(List<MskuSaleNumEnity> shipmentStockEnityList);

    Result getByKeyword(GetByKeywordVo getByKeywordVo, String userId);

    Result getMskuFBA(GetMskuFBAVo getMskuFBAVo, String userId);

    Result updateStockList(List<Map> mapList);

    List<String> getUnShelve();

    void updateShelveInfo(List<Map> mapList);

    Result searchMskuInfo(Integer pageNum, Integer pageSize, String platformName, String shopName, String mskuId, String[] excludeMskuIds);

    Result editSafetyDay (List<MskuSafetyDayEditVo> safetyDays);
    Result batchGetMsku(List<String> commodityIds);

    Map<String,Map> getWarehouseIdAndFnsku(List<Map> mapList);

    Result checkFnsku(List<Map> mapList);

    Result getMskuByEmployee(String userId);

    String checkFnCode(String mskuId, String shopId);

    Result getSkuInfoByFnSkuId(String fnSkuId);

    Result getSkuInfoByShopIdAndMskuId(String shopId, String mskuId);

    Result updateCommodity();
    void mskuExcel (String user,
                    String shopId,
                    String team,
                    String manager,
                    Integer deliveryMode,
                    Integer salesStatus,
                    Integer mskuStatus,
                    String findKey,
                    String sort,
                    HttpServletResponse response,
                    HttpServletRequest request);
}


