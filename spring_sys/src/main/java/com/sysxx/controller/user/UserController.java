package com.sysxx.controller.user;

import com.sysxx.pojo.AdminUser;
import com.sysxx.pojo.list.UserListParam;
import com.sysxx.service.user.UserServiceImp;
import com.sysxx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/login")
    public Result login(AdminUser user) {
        return userServiceImp.login(user);
    }

    @PostMapping("create_user")
    public Result createUser(AdminUser adminUser) {
        return userServiceImp.createUser(adminUser);
    }

    @GetMapping("/findAll")
    public Result findAll(UserListParam userListParam) {
        return userServiceImp.findAll(userListParam);
    }

    @PostMapping("/delete")
    public Result deleteUser(String id) {
        return userServiceImp.deleteUser(Integer.valueOf(id));
    }
    @PostMapping("/update")
    public  Result updateUser(AdminUser adminUser){
        return userServiceImp.updateUser(adminUser);
    }
}
