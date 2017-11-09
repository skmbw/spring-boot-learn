package com.fuuzii.user.action;

import com.fuuzii.user.service.spi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yinlei
 * @since 2017/11/9 17:41
 */
@Controller
@RequestMapping("/employee")
public class EmployeeAction {
    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }
}
