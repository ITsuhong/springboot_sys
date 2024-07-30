package com.sysxx.controller.role;


import com.sysxx.pojo.PageModule;
import com.sysxx.pojo.Roles;
import com.sysxx.pojo.list.RolesList;
import com.sysxx.service.roles.RoleServiceImp;
import com.sysxx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/roles")
@RestController
public class RoleController {

    @Autowired
    private RoleServiceImp roleServiceImp;

    @PostMapping("/create")
    public Result create(Roles roles) {
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
    public Result findAll(RolesList pageModule) {
        return roleServiceImp.findAll(pageModule);
    }
}
