package com.ryan.demo.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.github.f4b6a3.tsid.TsidCreator;
import com.ryan.demo.domain.TestOrder;
import com.ryan.demo.service.TestOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author kq
 * 2024-09-18 16:18
 **/
@RestController
@RequestMapping("/test-order")
@Slf4j
public class TestOrderController {

    @Resource
    private TestOrderService service;

    @PostMapping("/add")
    public void add(String createdAt) {
        TestOrder item = new TestOrder();
        long number = TsidCreator.getTsid().toLong();
        item.setOrderId(number+"");
        item.setCreateTime(LocalDateTime.now());
        if (createdAt != null) {
            log.info("createdAt:{}",createdAt);
            DateTime dt = DateUtil.parse(createdAt,
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm:ss.SSS",
                    "yyyy-MM-dd HH:mm:ss.SSSSSS"
            );
            item.setCreateTime(dt.toLocalDateTime());
        }
        service.save(item);

//        service.insertTestOrder();

    }

}
