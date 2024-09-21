package com.ryan.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ryan.demo.domain.Course;
import com.ryan.demo.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kq
 * 2024-09-20 13:28
 **/
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService service;

    @PostMapping("/write")
    public void writeCourse(Long id) {
        Course course = new Course(id, "测试读写分离", 1);
        service.save(course);
    }

    @GetMapping("/read")
    public List<Course> readCourse() {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getId, 123);
        return service.list(wrapper);
    }

}
