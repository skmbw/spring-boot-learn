package com.fuuzii.user.service.impl;

import com.fuuzii.user.dao.UserDao;
import com.fuuzii.user.service.spi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinlei
 * @since 2017/11/9 15:05
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

}
