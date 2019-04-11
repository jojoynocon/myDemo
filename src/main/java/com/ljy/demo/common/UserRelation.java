package com.ljy.demo.common;

import afu.org.checkerframework.checker.igj.qual.I;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author: jyl
 * @Descriptions:
 * @Date: create in  1:06 2019/4/12/012
 */
public class UserRelation {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
