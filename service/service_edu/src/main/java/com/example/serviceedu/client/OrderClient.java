package com.example.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order", fallback = OrderFile.class)
public interface OrderClient {
    @GetMapping("/serviceorder/order/isBuyCourse/{memberid}/{id}")
    boolean isBuyCourse(@PathVariable("memberid") String memberid, @PathVariable("id") String id);
}
