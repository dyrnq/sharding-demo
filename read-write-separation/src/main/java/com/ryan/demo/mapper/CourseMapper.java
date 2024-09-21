package com.ryan.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ryan.demo.domain.Course;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kq
* @description 针对表【course_1】的数据库操作Mapper
* @createDate 2024-09-20 13:28:38
*/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}




