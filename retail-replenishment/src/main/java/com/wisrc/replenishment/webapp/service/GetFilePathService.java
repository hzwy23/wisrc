package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "retail-replenishment-code-16", url = "http://localhost:8080")
public interface GetFilePathService {
    @GetMapping(value = "code/upload/file/{itemId}")
    Result getFilePathService(@PathVariable("itemId") String itemId);
}
