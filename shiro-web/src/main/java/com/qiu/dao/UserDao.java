package com.qiu.dao;

import com.qiu.vo.User;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserDao {
    User getUserByUserName(String userName);

    List<String> queryRolesByUserName(String userName);
}
