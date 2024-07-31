package com.sysxx.controller.user;

import com.sysxx.annotations.LoginValidator;
import com.sysxx.pojo.AdminUser;
import com.sysxx.pojo.list.UserListParam;
import com.sysxx.service.user.UserServiceImp;
import com.sysxx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("user")
@LoginValidator
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @LoginValidator(validated = false)
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
        String token = httpServletRequest.getHeader("token");
        System.out.println(token);
        return userServiceImp.findAll(userListParam);
    }

    @PostMapping("/delete")
    public Result deleteUser(String id) {
        return userServiceImp.deleteUser(Integer.valueOf(id));
    }

    @PostMapping("/update")
    public Result updateUser(AdminUser adminUser) {
        return userServiceImp.updateUser(adminUser);
    }

    @GetMapping("/routesTree")
    public Result findRoutesTree() {
        return userServiceImp.findRoutesTree();
    }
}
