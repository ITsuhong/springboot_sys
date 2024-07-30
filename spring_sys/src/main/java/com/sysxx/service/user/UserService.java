package com.sysxx.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sysxx.pojo.AdminUser;
import com.sysxx.pojo.list.UserListParam;
import com.sysxx.utils.Result;

public interface UserService extends IService<AdminUser> {

    Result login(AdminUser user);

    Result findAll(UserListParam userListParam);

    Result createUser(AdminUser adminUser);

    Result deleteUser(Integer id);

    Result updateUser(AdminUser adminUser);
}
