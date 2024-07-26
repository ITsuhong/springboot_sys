package com.sysxx.controller.user;

import com.sysxx.pojo.AdminUser;
import com.sysxx.service.user.UserServiceImp;
import com.sysxx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/login")
    public Result login(AdminUser user) {
        return userServiceImp.login(user);
    }
}
