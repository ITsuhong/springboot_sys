package com.sysxx.controller.role;


import com.sysxx.annotations.LoginValidator;
import com.sysxx.pojo.PageModule;
import com.sysxx.pojo.Roles;
import com.sysxx.pojo.list.RolesList;
import com.sysxx.service.roles.RoleServiceImp;
import com.sysxx.utils.RedisUtil;
import com.sysxx.utils.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/roles")
@RestController
@LoginValidator
@Tag(name = "权限", description = "用于管理用户信息")
public class RoleController {

    @Autowired
    private RoleServiceImp roleServiceImp;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/create")


    @Operation(summary = "根据ID获取用户信息", description = "根据用户ID查询用户的详细信息")
    public Result create(@ParameterObject Roles roles) {
        return roleServiceImp.createRole(roles);
    }

    @PostMapping("/delete")
    public Result delete(String id) {
        return roleServiceImp.deleteRole(Integer.valueOf(id));
    }

    @PostMapping("update")
    public Result update(Roles roles) {
        return roleServiceImp.updateRole(roles);
    }

    @GetMapping("/findAll")
    @ApiResponse(responseCode = "200", description = "查询成功", content = @Content(schema = @Schema(implementation = Roles.class)))
    public Result findAll(RolesList pageModule) {
        redisUtil.set("suhong:user:2", "suhong");

        return roleServiceImp.findAll(pageModule);
    }
}
