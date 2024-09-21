package com.ryan.demo.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.demo.domain.Course;
import com.ryan.demo.mapper.CourseMapper;
import com.ryan.demo.service.CourseService;
import org.springframework.stereotype.Service;

/**
* @author kq
* @description 针对表【course_1】的数据库操作Service实现
* @createDate 2024-09-20 13:28:38
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {

}




