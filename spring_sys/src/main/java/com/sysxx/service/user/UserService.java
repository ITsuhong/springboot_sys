package com.sysxx.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sysxx.pojo.AdminUser;
import com.sysxx.utils.Result;

public interface UserService extends IService<AdminUser> {

    Result login(AdminUser user);
}
