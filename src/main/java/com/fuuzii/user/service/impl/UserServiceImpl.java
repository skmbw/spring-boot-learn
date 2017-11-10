package com.fuuzii.user.service.impl;

import com.fuuzii.user.dao.UserDao;
import com.fuuzii.user.model.User;
import com.fuuzii.user.service.spi.UserService;
import com.vteba.security.filter.DefaultUserAuthenticationToken;
import com.vteba.security.spi.AuthoritiesService;
import com.vteba.security.spi.UserDetailsService;
import com.vteba.security.user.Authority;
import com.vteba.security.user.IUserDetails;
import com.vteba.security.user.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author yinlei
 * @since 2017/11/9 15:05
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService, AuthoritiesService {

    @Autowired
    private UserDao userDao;

    @Override
    public IUserDetails loadUser(String username, DefaultUserAuthenticationToken token) {
        User user = new User();
        user.setAccount(username);
        return userDao.unique(user);
    }

    @Override
    public List<GrantedAuthority> buildGranted(IUserDetails user) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setAccount(username);
        return userDao.unique(user);
    }

    @Override
    public List<? extends Authority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public List<? extends Resource> getUrlResource(Authority authority) {
        return Collections.emptyList();
    }

    @Override
    public List<? extends Resource> getMethodResource(Authority authority) {
        return Collections.emptyList();
    }
}
