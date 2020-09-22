package com.wisrc.wms.webapp.controller;

import com.wisrc.wms.webapp.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhm
 */
//@RestController
public class WmsDownloadController {

    @RequestMapping("/download/{fbaReplenishmentId}/{fileClassId}")
    public Result downloadData(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId,
                               @PathVariable("fileClassId") String fileClassId,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        if (StringUtils.isEmpty(fbaReplenishmentId)) {
            return Result.failure(390, "fbaId不能为空", "");
        }
        if (StringUtils.isEmpty(fileClassId)) {
            return Result.failure(390, "文件类型不能为空", "");
        }
        return null;
    }

}
