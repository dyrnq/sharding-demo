package com.ryan.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName course_1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="course")
public class Course implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer status;

}