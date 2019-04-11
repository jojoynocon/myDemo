package com.ljy.demo.common;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * @Author: jyl
 * @Descriptions:
 * @Date: create in  23:30 2019/4/11/011
 */
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id; //主键ID
    private String name; //名称
    private Long pid; //父级主键ID 如果为最高级则是0
    private Integer level; //当前是第几层
    private Integer status; //0.禁用 1.可用
    private List<Department> departmentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
