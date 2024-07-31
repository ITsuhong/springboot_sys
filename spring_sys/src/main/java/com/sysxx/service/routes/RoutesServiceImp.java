package com.sysxx.service.routes;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sysxx.mapper.roleRoutes.RoleRoutesMapper;
import com.sysxx.mapper.roles.RolesMapper;
import com.sysxx.mapper.routes.RoutesMapper;
import com.sysxx.mapper.user.UserMapper;
import com.sysxx.pojo.AdminUser;
import com.sysxx.pojo.RoleRoutes;
import com.sysxx.pojo.Roles;
import com.sysxx.pojo.RoutesModule;
import com.sysxx.pojo.list.RoutesModuleList;
import com.sysxx.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutesServiceImp extends ServiceImpl<RoutesMapper, RoutesModule> implements RoutesService {
    @Autowired
    private RoutesMapper routesMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private RoleRoutesMapper roleRoutesMapper;

    @Override
    public Result saveRoute(RoutesModule routesModule) {
        LambdaQueryWrapper<RoutesModule> queryWrapper = new LambdaQueryWrapper<>();
        Integer conut = routesMapper.insert(routesModule);

        return Result.ok("ok");
    }

    @Override
    public Result findAllRoutes(RoutesModuleList listParam) {
        if (listParam.getPageNum() == null) {
            listParam.setPageNum(1);
        }
        Page<RoutesModule> routesModulePage = new Page<>(listParam.getPageNum(), listParam.getPageSize());
        LambdaQueryWrapper<RoutesModule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoutesModule::getPid, 0);
        routesMapper.selectPage(routesModulePage, queryWrapper);
        List<RoutesModule> routesModuleList = routesModulePage.getRecords();
        for (RoutesModule data : routesModuleList) {
            LambdaQueryWrapper<RoutesModule> routeWrapper = new LambdaQueryWrapper<>();
            routeWrapper.eq(RoutesModule::getPid, data.getId());
            List<RoutesModule> list = routesMapper.selectList(routeWrapper);
            data.setChildren(list);
        }
        Result result = new Result();
        result.setData(routesModuleList);
        result.setPageNum(listParam.getPageNum());
        result.setPageSize(listParam.getPageSize());
        result.setTotal(routesModulePage.getTotal());
        return result.list();
    }

    @Override
    public Result deleteRoutes(RoutesModule routesModule) {
        routesMapper.deleteById(routesModule.getId());
        return Result.ok("ok");
    }

    @Override
    public Result updateRoutes(RoutesModule routesModule) {
//        routesMapper.selectById(routesModule.getId());
        routesMapper.updateById(routesModule);
        return Result.ok("ok");
    }

    @Override
    public Result findByUserId(Integer id) {
        AdminUser adminUser = userMapper.selectById(id);
        if (adminUser.getAdministrator() == 1) {
            RoutesModuleList routesModuleList = new RoutesModuleList();
            routesModuleList.setPageNum(1);
            routesModuleList.setPageSize(9999);
            return this.findAllRoutes(routesModuleList);
        } else {
            LambdaQueryWrapper<Roles> queryRoleWrapper = new LambdaQueryWrapper<>();
            queryRoleWrapper.eq(Roles::getId, adminUser.getRoleId());
            Roles roles = rolesMapper.selectOne(queryRoleWrapper);
            LambdaQueryWrapper<RoleRoutes> roleRoutesLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleRoutesLambdaQueryWrapper.eq(RoleRoutes::getRolesId, roles.getId());
            List<RoleRoutes> roleRoutesList = roleRoutesMapper.selectList(roleRoutesLambdaQueryWrapper);
            List<Integer> list = roleRoutesList.stream().map(RoleRoutes::getRoutesModuleId).toList();
            List<RoutesModule> routesModuleList = routesMapper.selectBatchIds(list);
            List<RoutesModule> resultList = routesModuleList.stream().filter(r -> r.getPid() == 0).toList();
            for (RoutesModule result : resultList) {
                for (RoutesModule routes : routesModuleList) {

                    if (Objects.equals(routes.getPid(), result.getId())) {
                        System.out.println(routes);
                        List<RoutesModule> r = new ArrayList<>();
                        if (result.getChildren() == null) {
                            r.add(routes);
                            result.setChildren(new LinkedList<>(r));
                        } else {
                            r.addAll(result.getChildren());
                            r.add(routes);
                            result.setChildren(r);
                        }

                    }
                }
            }
            return Result.ok(resultList);
        }

    }


}
