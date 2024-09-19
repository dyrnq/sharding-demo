package com.ryan.demo.controller;

import com.ryan.demo.domain.Course;
import com.ryan.demo.service.CourseService;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kq
 * 2024-09-18 16:18
 **/
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService service;

    @PostMapping("/add")
    public void add(Long id){
        Course course = new Course();
        course.setId(id);
        course.setName("java");
        course.setStatus(0);
        service.save(course);
    }

    @GetMapping("/all")
    public List<Course> getAll() {
        return service.list();
    }

}
