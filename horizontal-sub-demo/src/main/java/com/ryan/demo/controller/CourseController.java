package com.ryan.demo.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryan.demo.domain.Course;
import com.ryan.demo.service.CourseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CourseController {

    @Resource
    private CourseService service;

    @PostMapping("/add")
    public void add(Long id, String createdAt) {
        Course course = new Course();
        course.setId(id);
        course.setName("java");
        course.setStatus(0);
        if (createdAt != null) {
            log.info("createdAt:{}", createdAt);
            DateTime dt = DateUtil.parse(createdAt,
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm:ss.SSS",
                    "yyyy-MM-dd HH:mm:ss.SSSSSS"
            );
            course.setCreatedAt(dt);
        }
        service.save(course);
    }

    @GetMapping("/del")
    public void del(Long id) {
        service.removeById(id);
    }

    @GetMapping("/all")
    public List<Course> getAll() {
        return service.list();
    }

    @GetMapping("/page")
    public IPage<Course> page() {
        return service.pageCourse("2024-11-20 08:21:37","2024-11-20 08:21:38");
    }

}
