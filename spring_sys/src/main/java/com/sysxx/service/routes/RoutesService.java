package com.sysxx.service.routes;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sysxx.pojo.RoutesModule;
import com.sysxx.pojo.list.RoutesModuleList;
import com.sysxx.utils.Result;

public interface RoutesService extends IService<RoutesModule> {
    Result saveRoute(RoutesModule routesModule);

    Result findAllRoutes(RoutesModuleList routesModuleList);

    Result deleteRoutes(RoutesModule routesModule);
}
