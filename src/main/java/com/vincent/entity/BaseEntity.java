package com.vincent.entity;

import lombok.Data;

import java.util.Date;

/**
 * 最基本的对象，也就是其他类会用到的属性
 */
@Data
public class BaseEntity {
    private Long id;
    private Date created;   //创建时间
    private Date modified;  //更新时间
}
