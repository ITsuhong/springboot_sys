package com.sysxx.pojo.list;

import com.sysxx.pojo.PageModule;
import lombok.Data;

@Data
public class UserListParam extends PageModule {
    private String account = "";
    private String name = "";
}
