package com.sysxx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
public class AdminUser {
    @TableId
    private Integer id;
    private String name;
    private String account;
    private String password;
    private Integer Administrator;
    private Date createTime;
    private Date updateTime;
    @TableField(select = false)
    private Integer roleId;
    @Version
    private Integer version;
    @TableField(exist = false)
    private  String token;
}
