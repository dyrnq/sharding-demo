package com.ryan.demo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class TestOrder implements Serializable {
    @TableId
    private String orderId;
    private LocalDateTime createTime;
}
