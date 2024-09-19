package com.ryan.demo.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.demo.domain.User;
import com.ryan.demo.mapper.UserMapper;
import com.ryan.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author kq
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-18 17:46:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




