package com.sysxx.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sysxx.mapper.roles.RolesMapper;
import com.sysxx.mapper.user.UserMapper;
import com.sysxx.pojo.AdminUser;
import com.sysxx.pojo.Roles;
import com.sysxx.pojo.list.UserListParam;
import com.sysxx.utils.JwtHelper;
import com.sysxx.utils.MD5Util;
import com.sysxx.utils.Result;
import com.sysxx.utils.ResultCodeEnum;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImp extends ServiceImpl<UserMapper, AdminUser> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public Result login(AdminUser user) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getAccount, user.getAccount());
        AdminUser adminUser = userMapper.selectOne(queryWrapper);

        if (adminUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        if (adminUser.getAdministrator() == 1 && user.getPassword().equals(adminUser.getPassword())) {
            String token = jwtHelper.createToken(adminUser.getId());
            adminUser.setToken(token);
            adminUser.setPassword(null);
            return Result.ok(adminUser);
        }
        if (adminUser.getPassword().equals(MD5Util.encrypt(user.getPassword()))) {
            String token = jwtHelper.createToken(adminUser.getId());
            adminUser.setToken(token);
            adminUser.setPassword(null);
            return Result.ok(adminUser);
        }


        //密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result findAll(UserListParam userListParam) {
        LambdaQueryWrapper<AdminUser> adminUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminUserLambdaQueryWrapper.like(AdminUser::getAccount, userListParam.getAccount())
                .like(AdminUser::getName, userListParam.getName());
        Page<AdminUser> adminUserPage = new Page<>(userListParam.getPageNum(), userListParam.getPageSize());
        userMapper.selectPage(adminUserPage, adminUserLambdaQueryWrapper);
        List<AdminUser> adminUserList = adminUserPage.getRecords();
        for (AdminUser adminUser : adminUserList) {
            Roles roles = rolesMapper.selectById(adminUser.getRoleId());
            adminUser.setRole(roles);
        }
        Result result = new Result<>();
        result.setData(adminUserList);
        result.setPageSize(userListParam.getPageSize());
        result.setPageNum(userListParam.getPageNum());
        result.setTotal(adminUserPage.getTotal());
        return result.list();
    }

    @Override
    public Result createUser(AdminUser adminUser) {
        adminUser.setPassword(MD5Util.encrypt("123456"));
        userMapper.insert(adminUser);
        return Result.ok(null);
    }

    @Override
    public Result deleteUser(Integer id) {
        userMapper.deleteById(id);
        return Result.ok(null);
    }

    @Transactional
    @Override
    public Result updateUser(AdminUser adminUser) {
        userMapper.updateById(adminUser);
        return Result.ok(null);
    }
}
