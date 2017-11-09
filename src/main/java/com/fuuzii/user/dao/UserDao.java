package com.fuuzii.user.dao;

import com.fuuzii.user.model.User;
import com.vteba.tx.dao.spi.BasicDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yinlei
 * @since 2017/11/9 15:04
 */
@Mapper
public interface UserDao extends BasicDao<User, String> {
}
