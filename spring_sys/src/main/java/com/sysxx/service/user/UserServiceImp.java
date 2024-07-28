package com.sysxx.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sysxx.mapper.user.UserMapper;
import com.sysxx.pojo.AdminUser;
import com.sysxx.utils.JwtHelper;
import com.sysxx.utils.Result;
import com.sysxx.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp extends ServiceImpl<UserMapper, AdminUser> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

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
        return Result.ok(null);
    }
}
