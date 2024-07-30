package com.sysxx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
public class Roles {
    private Integer id;
    private String name;
    private String description;
    private Date createTime;
    private Date updateTime;
    @Version
    private Integer version;
    private String creator;
    @TableField(exist = false)
    private String modulesIds;
    @TableField(exist = false)
    private Object routes;
}
