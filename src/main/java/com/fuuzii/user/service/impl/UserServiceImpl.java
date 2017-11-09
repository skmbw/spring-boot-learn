package com.fuuzii.user.service.impl;

import com.fuuzii.user.dao.UserDao;
import com.fuuzii.user.service.spi.UserService;
import com.vteba.security.filter.DefaultUserAuthenticationToken;
import com.vteba.security.spi.UserDetailsService;
import com.vteba.security.user.IUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinlei
 * @since 2017/11/9 15:05
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public IUserDetails loadUser(String username, DefaultUserAuthenticationToken token) {
        return null;
    }

    @Override
    public List<GrantedAuthority> buildGranted(IUserDetails user) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
