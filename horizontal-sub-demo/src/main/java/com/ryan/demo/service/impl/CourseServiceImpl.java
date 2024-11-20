package com.ryan.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.demo.Utils;
import com.ryan.demo.domain.Course;
import com.ryan.demo.service.CourseService;
import com.ryan.demo.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author kq
* @description 针对表【course_1】的数据库操作Service实现
* @createDate 2024-09-18 15:48:09
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {

    public Page<Course> pageCourse(String start, String end) {
        Page<Course> pager = new Page<>(1, 20);

        LambdaQueryWrapper<Course> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.between(Course::getCreatedAt, Utils.getTimestamp(start), Utils.getEndOfDayTimestamp(end));
        Page<Course> result = this.page(pager, lambdaQuery);
        return result;
    }

}




