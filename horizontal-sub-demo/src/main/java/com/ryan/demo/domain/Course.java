package com.ryan.demo.domain;



import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author kq
 * 2024-09-18 14:27
 * 课程表实体类
 **/
@Data
public class Course implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程状态，0 发布，1 未发布
     */
    private Integer status;

    private Date createdAt;
}