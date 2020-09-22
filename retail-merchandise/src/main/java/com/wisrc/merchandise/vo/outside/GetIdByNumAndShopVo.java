package com.wisrc.merchandise.vo.outside;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class GetIdByNumAndShopVo {
    @Valid
    @NotEmpty
    private List<MskuRelationVo> numAndShopVo;
}
