package com.ryan.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.demo.Utils;
import com.ryan.demo.domain.Course;
import com.ryan.demo.service.CourseService;
import com.ryan.demo.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author kq
* @description 针对表【course_1】的数据库操作Service实现
* @createDate 2024-09-18 15:48:09
*/
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {
    final JdbcTemplate jdbcTemplate;

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public Page<Course> pageCourse(String start, String end) {
        Page<Course> pager = new Page<>(1, 20);

        LambdaQueryWrapper<Course> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.between(Course::getCreatedAt, Utils.getTimestamp(start), Utils.getEndOfDayTimestamp(end));
        Page<Course> result = this.page(pager, lambdaQuery);
        return result;
    }

    public Page<Course> pageCourseByJDBC(String start, String end){
        int pageSize=4;
        Page<Course> pager = new Page<>(1, pageSize);
        MapSqlParameterSource para = new MapSqlParameterSource();
        para.addValue("a", Utils.getTimestamp(start));
        para.addValue("b", Utils.getEndOfDayTimestamp(end));

        Long total = namedParameterJdbcTemplate.queryForObject("select count(*) from course where 1=1 AND ( created_at between :a AND :b )",para,Long.class);

        String sql = "select * from course where 1=1 AND ( created_at between :a AND :b ) limit 0," +pageSize;
        final List<Course> list = new ArrayList<Course>();



        namedParameterJdbcTemplate.query(sql, para, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Course item = new Course();
                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setStatus(rs.getInt("status"));

                item.setCreatedAt(DateUtil.date(rs.getTimestamp("created_at")));
                list.add(item);
            }
        });
        pager.setRecords(list);
        pager.setTotal(total);
        return pager;
    }

}




