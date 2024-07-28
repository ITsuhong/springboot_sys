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

import java.util.List;

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
        Page<RoutesModule> routesModulePage = new Page<>(listParam.getPageNum(), listParam.getPageSize());
        LambdaQueryWrapper<RoutesModule> queryWrapper = new LambdaQueryWrapper<>();
        routesMapper.selectPage(routesModulePage, queryWrapper);
        List<RoutesModule> routesModuleList = routesModulePage.getRecords();
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

}
