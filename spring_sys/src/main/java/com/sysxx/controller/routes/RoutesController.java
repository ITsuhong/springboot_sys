package com.sysxx.controller.routes;

import com.sysxx.pojo.RoutesModule;
import com.sysxx.pojo.list.RoutesModuleList;
import com.sysxx.service.routes.RoutesServiceImp;
import com.sysxx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes-module")
public class RoutesController {
    @Autowired
    private RoutesServiceImp routesServiceImp;

    @PostMapping("/create")
    public Result savaRoutes(RoutesModule routesModule) {
        return routesServiceImp.saveRoute(routesModule);
    }

    @PostMapping("/update")
    public Result update(RoutesModule routesModule) {
        return routesServiceImp.updateRoutes(routesModule);
    }

    @GetMapping("/findAll")
    public Result findAllRoutes(RoutesModuleList routesModuleList) {
        return routesServiceImp.findAllRoutes(routesModuleList);
    }

    @PostMapping("/delete")
    public Result deleteRoutes(RoutesModule routesModule) {
        return routesServiceImp.deleteRoutes(routesModule);
    }
}
