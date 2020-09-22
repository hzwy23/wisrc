package com.wisrc.purchase.webapp.dto.supplierOffer;

import com.wisrc.purchase.webapp.dto.PageDto;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferVO;
import lombok.Data;

import java.util.List;

@Data
public class SupplierOfferPageDto extends PageDto {
    List<SupplierDateOfferVO> supplierDateOfferList;
}
