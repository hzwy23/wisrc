package com.wisrc.product.webapp.vo;

import com.wisrc.product.webapp.vo.declareLabel.set.SetDeclareLabelVO;
import com.wisrc.product.webapp.vo.productAccessory.set.SetProductAccessoryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class updateVO {


    //特性标签(新改)
    @Valid
    @NotNull(message = "参数[declareLabelList]不能为空")
    @ApiModelProperty(value = "特性标签(新改)", required = true)
    private List<SetDeclareLabelVO> declareLabelList;

    //配件(新增)
    @Valid
    @NotNull(message = "参数[accessoryList]不能为空")
    @ApiModelProperty(value = "配件(新增)", required = true)
    private List<SetProductAccessoryVO> accessoryList;

}
