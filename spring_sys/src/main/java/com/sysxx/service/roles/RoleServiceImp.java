package com.sysxx.service.roles;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysxx.mapper.roleRoutes.RoleRoutesMapper;
import com.sysxx.mapper.roles.RolesMapper;
import com.sysxx.mapper.routes.RoutesMapper;
import com.sysxx.mapper.user.UserMapper;
import com.sysxx.pojo.PageModule;
import com.sysxx.pojo.RoleRoutes;
import com.sysxx.pojo.Roles;

import com.sysxx.pojo.RoutesModule;
import com.sysxx.pojo.list.RolesList;
import com.sysxx.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private RoleRoutesMapper roleRoutesMapper;

    @Autowired
    private RoutesMapper routesMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result findAll(RolesList pageModule) {
        if (pageModule.getPageNum() == null) {
            pageModule.setPageNum(1);
        }
        if (pageModule.getPageSize() == null) {
            pageModule.setPageSize(10);
        }
        Page<Roles> rolePage = new Page<>(pageModule.getPageNum(), pageModule.getPageSize());
        LambdaQueryWrapper<Roles> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Roles::getName, pageModule.getName());
        List<Roles> rolesList = rolesMapper.selectPage(rolePage, queryWrapper).getRecords();
        for (Roles role : rolesList) {
            LambdaQueryWrapper<RoleRoutes> roleRoutesLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleRoutesLambdaQueryWrapper.eq(RoleRoutes::getRolesId, role.getId());
            List<RoleRoutes> roleRoutesList = roleRoutesMapper.selectList(roleRoutesLambdaQueryWrapper);
            List<Integer> integerList = roleRoutesList.stream().map(RoleRoutes::getRoutesModuleId).toList();
            List<RoutesModule> routesModuleList = routesMapper.selectBatchIds(integerList);
            role.setRoutes(routesModuleList);
        }
        Result result = new Result();
        result.setData(rolesList);
        result.setPageNum(pageModule.getPageNum());
        result.setPageSize(pageModule.getPageSize());
        result.setTotal(rolePage.getTotal());

        return result.list();
    }

    @Transactional
    @Override
    public Result createRole(Roles roles) {
        rolesMapper.insert(roles);
        String[] modulesIds = roles.getModulesIds().split(",");
        for (String id : modulesIds) {
            RoleRoutes roleRoutes = new RoleRoutes();
            roleRoutes.setRoutesModuleId(Integer.valueOf(id));
            roleRoutes.setRolesId(roles.getId());

            roleRoutesMapper.insert(roleRoutes);
        }
        return Result.ok("success");
    }

    @Transactional
    @Override
    public Result deleteRole(Integer id) {
        rolesMapper.deleteById(id);
        return Result.ok("success");
    }

    @Override
    public Result updateRole(Roles roles) {
        rolesMapper.updateById(roles);
        LambdaQueryWrapper<RoleRoutes> roleRoutesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleRoutesLambdaQueryWrapper.eq(RoleRoutes::getRolesId, roles.getId());
        roleRoutesMapper.delete(roleRoutesLambdaQueryWrapper);
        String[] modulesIds = roles.getModulesIds().split(",");
        for (String id : modulesIds) {
            RoleRoutes roleRoutes = new RoleRoutes();
            roleRoutes.setRoutesModuleId(Integer.valueOf(id));
            roleRoutes.setRolesId(roles.getId());
            roleRoutesMapper.insert(roleRoutes);
        }

        return Result.ok(null);
    }


}
