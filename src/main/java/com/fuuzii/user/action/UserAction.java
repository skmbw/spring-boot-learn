package com.fuuzii.user.action;

import com.fuuzii.user.service.spi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yinlei
 * @since 2017/11/9 15:03
 */
@Controller
@RequestMapping("/user")
public class UserAction {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/list")
    public String list() {
        return "user/list";
    }
}
