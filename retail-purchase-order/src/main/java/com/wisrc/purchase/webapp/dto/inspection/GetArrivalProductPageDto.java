package com.wisrc.purchase.webapp.dto.inspection;

import com.wisrc.purchase.webapp.dto.PageDto;
import lombok.Data;

import java.util.List;

@Data
public class GetArrivalProductPageDto extends PageDto {
    private List<GetArrivalProductDto> arrivalProducts;
}
