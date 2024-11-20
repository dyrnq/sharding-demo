package com.ryan.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.demo.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author kq
* @description 针对表【course_1】的数据库操作Service
* @createDate 2024-09-18 15:48:09
*/
public interface CourseService extends IService<Course> {
    Page<Course> pageCourse(String start, String end);
}
