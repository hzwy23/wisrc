package com.wisrc.product.webapp.vo;

import com.wisrc.product.webapp.vo.declareLabel.add.AddDeclareLabelVO;
import com.wisrc.product.webapp.vo.productAccessory.add.AddProductAccessoryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class addVO {

    //特性标签(新改)
    @Valid
    @ApiModelProperty(value = "特性标签(新改)")
    private List<AddDeclareLabelVO> declareLabelList;

    //配件(新增)
    @Valid
    @ApiModelProperty(value = "配件(新增)")
    private List<AddProductAccessoryVO> accessoryList;
}
