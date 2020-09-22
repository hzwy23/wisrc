package com.wisrc.merchandise.outside;


import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "retail-merchandise-system", url = "http://localhost:8080")
public interface ShopOutside {
    @PostMapping("/system/archives/shop/shops")
    Result getShopName(@RequestBody Map shopIds);

    @GetMapping("/system/archives/shop/select")
    Result getShopSelect();

    @GetMapping("/system/archives/shop/{shopId}/sellerId")
    Result getSellerId(@PathVariable(value = "shopId") String shopId);

    @PostMapping("/system/archives/shop/sellerId")
    Result getSellerIdBatch(@RequestParam(value = "shopIds") List shopIds);
}
