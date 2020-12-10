package com.numberplate.numberplateserver.mapper;

import com.numberplate.numberplateserver.entity.User;

public interface UserMapper {
    int add(User user);
    User findOne(User user);
}
