package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.service.CustomsInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.CustomsOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import static com.wisrc.webapp.utils.UploadFile.uploadFile;


@RestController
@Api(tags = "上传文件接口")
@RequestMapping(value = "/replenishment")
public class UploadCustomsDocController {
    @Autowired
    private CustomsInfoService customsInfoService;


    @ApiOperation(value = "报关单资料上传")
    @RequestMapping(value = "/upload/customs", method = RequestMethod.POST)
    public Result uploadCustoms(@RequestBody CustomsOrderVO vo) throws Exception {
        try {
            customsInfoService.addCustomsOrder(vo);
            return Result.success(vo);

        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), vo);
        }

    }

}
