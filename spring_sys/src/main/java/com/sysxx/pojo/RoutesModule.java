package com.sysxx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class RoutesModule {
    private Integer id;
    private Integer pid;
    private String name;
    private Integer sort;
    private String path;
    private String description;
    @Version
    private int version;
    @TableField(exist = false)
    private Object children;

}
