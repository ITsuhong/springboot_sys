package com.sysxx.service.routes;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sysxx.mapper.routes.RoutesMapper;
import com.sysxx.pojo.RoutesModule;
import com.sysxx.pojo.list.RoutesModuleList;
import com.sysxx.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RoutesServiceImp extends ServiceImpl<RoutesMapper, RoutesModule> implements RoutesService {
    @Autowired
    private RoutesMapper routesMapper;

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

}
