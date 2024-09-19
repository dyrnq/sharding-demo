package com.ryan.demo.controller;

import com.ryan.demo.domain.User;
import com.ryan.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author kq
 * 2024-09-18 17:54
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public void add(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("ryan");
        user.setAge(18);
        userService.save(user);
    }

}
