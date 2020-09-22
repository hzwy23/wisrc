package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.CustomsExcelEntity;
import com.wisrc.replenishment.webapp.entity.CustomsInfoEntity;
import com.wisrc.replenishment.webapp.vo.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CustomsInfoDao {
    /**
     * 完善报关资料时，新增报关单--公司报关资料，waybillId来自运单
     *
     * @param vo
     */
    @Insert("insert into customs_info (waybill_id, customs_declare_id, customs_declaration_date, trade_country, company_name, " +
            "company_address, destination_country, transport_type_cd, destination_address, trade_type_cd, out_port_cd, exempting_nature_cd, " +
            "exemption_mode_cd, goods_source, sign_address, deal_type_cd, money_type_cd, pack_type_cd, sign_mark) values (#{waybillId},#{customsDeclareId}, " +
            "#{customsDeclarationDate},#{tradeCountry},#{companyName},#{companyAddress},#{destinationCountry},#{transportTypeCd},#{destinationAddress},#{tradeTypeCd}, " +
            "#{outPortCd},#{exemptingNatureCd},#{exemptionModeCd},#{goodsSource},#{signAddress},#{dealTypeCd},#{moneyTypeCd},#{packTypeCd},#{signMark})")
    void addCustomsInfo(CompanyCustomsInfoVO vo);

    /**
     * @param vo
     */
    @Update("update customs_info set customs_declaration_date=#{customsDeclarationDate},trade_country=#{tradeCountry},company_name=#{companyName},company_address=#{companyAddress}," +
            " destination_country=#{destinationCountry},transport_type_cd=#{transportTypeCd},destination_address=#{destinationAddress},trade_type_cd=#{tradeTypeCd}, " +
            "out_port_cd=#{outPortCd},exempting_nature_cd=#{exemptingNatureCd},exemption_mode_cd=#{exemptionModeCd},goods_source=#{goodsSource},sign_address=#{signAddress}, " +
            "deal_type_cd=#{dealTypeCd},money_type_cd=#{moneyTypeCd},pack_type_cd=#{packTypeCd},sign_mark=#{signMark} where waybill_id=#{waybillId}")
    void updateCustomsInfo(CompanyCustomsInfoVO vo);

    @Select("select waybill_id, customs_declare_id, customs_declaration_date, trade_country, company_name, " +
            "  company_address, destination_country, transport_type_cd, destination_address, trade_type_cd, out_port_cd, exempting_nature_cd, " +
            "  exemption_mode_cd, goods_source, sign_address, deal_type_cd, money_type_cd, pack_type_cd, sign_mark, " +
            "  customs_declare_number, remark, customs_info_doc, declaration_number_doc, letter_release_doc from customs_info  where waybill_id=#{waybillId}")
    CustomsInfoEntity get(String waybillId);

    @Select("select waybill_id, customs_declare_id, customs_declaration_date, trade_country, company_name, " +
            "  company_address, destination_country, transport_type_cd, destination_address, trade_type_cd, out_port_cd, exempting_nature_cd, " +
            "  exemption_mode_cd, goods_source, sign_address, deal_type_cd, money_type_cd, pack_type_cd, sign_mark, " +
            "  customs_declare_number, remark, customs_info_doc, declaration_number_doc, letter_release_doc from customs_info  where waybill_id=#{waybillId}")
    SelectDeclareCustomVO getVO(String waybillId);

    /**
     * 完善报关资料时，新增报关单--上传报关单
     *
     * @param vo
     */
    @Update("update customs_info set customs_declare_number=#{customsDeclareNumber}, " +
            "  remark=#{remark},customs_info_doc=#{customsInfoDoc},declaration_number_doc=#{declarationNumberDoc},letter_release_doc=#{letterReleaseDoc} where waybill_id=#{waybillId} ")
    void addCustomsOrder(CustomsOrderVO vo);

    @Delete("delete from customs_info where waybill_id=#{waybillId}")
    void deleteCustomsInfo(String waybillId);

    @Select("select uuid,msku_id,outer_box_specification_len,outer_box_specification_width, outer_box_specification_height, packing_quantity, number_of_boxes, packing_weight, " +
            " declare_unit_price, net_weight, gross_weight, msku_unit_cd, declare_subtotal, declaration_elements, delete_status from replenishment_msku_info where uuid=#{uuid}")
    CustomsListInfoVO getCustomsMskuInfo(String uuid);

    @Select("select uuid,fba_replenishment_id,msku_id,outer_box_specification_len,outer_box_specification_width, outer_box_specification_height, number_of_boxes, packing_weight " +
            " from replenishment_msku_info where uuid=#{uuid}")
    ImproveSendDataVO getImproveMskuInfo(String uuid);

    @Select("select batch_number from fba_replenishment_info where fba_replenishment_id = #{fbaReplenishmentId}")
    String getBatchNumber(String fbaReplenishmentId);

    @Select("SELECT customs_declare_id, trade_type_cd, exempting_nature_cd, trade_country, destination_country, destination_address, customs_declaration_date, goods_source, deal_type_cd, " +
            "customs_declare_number, declare_kind_cnt, pack_type_cd, money_type_cd, exemption_mode_cd, company_name, company_address, sign_address, transport_type_cd " +
            " FROM customs_info AS ci LEFT JOIN customs_clearance_invoice_info AS ccii ON ccii.waybill_id = ci.waybill_id WHERE ci.waybill_id = #{wayBillId} ")
    CustomsExcelEntity customsExcel(@Param("wayBillId") String wayBillId);
}
