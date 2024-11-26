package com.ryan.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.demo.domain.TestOrder;
import com.ryan.demo.mapper.TestOrderMapper;
import com.ryan.demo.service.TestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestOrderServiceImpl extends ServiceImpl<TestOrderMapper, TestOrder> implements TestOrderService {
    final JdbcTemplate jdbcTemplate;

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

}
