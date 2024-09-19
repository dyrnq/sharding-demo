package com.ryan.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ryan.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kq
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-18 17:46:49
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




