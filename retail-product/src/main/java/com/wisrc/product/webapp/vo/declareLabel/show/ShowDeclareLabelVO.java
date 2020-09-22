package com.wisrc.product.webapp.vo.declareLabel.show;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;

@Data
@ApiModel
@Valid
public class ShowDeclareLabelVO {
    private Integer labelCd;

    private String labelText;
}
