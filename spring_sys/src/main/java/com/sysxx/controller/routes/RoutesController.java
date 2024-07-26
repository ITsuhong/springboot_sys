package com.sysxx.controller.routes;

import com.sysxx.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
public class RoutesController {
    @PostMapping("/save")
    public Result savaRoutes() {
        return Result.ok("ok");
    }
}
