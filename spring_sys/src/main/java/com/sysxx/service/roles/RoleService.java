package com.sysxx.service.roles;

import com.sysxx.pojo.PageModule;
import com.sysxx.pojo.Roles;
import com.sysxx.pojo.list.RolesList;
import com.sysxx.utils.Result;

public interface RoleService {
    Result findAll(RolesList pageModule);

    Result createRole(Roles roles);

    Result deleteRole(Integer id);

    Result updateRole(Roles roles);


}
