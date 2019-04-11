package com.ljy.demo.mapper;

import com.ljy.demo.common.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author: jyl
 * @Descriptions:
 * @Date: create in  23:49 2019/4/11/011
 */
public interface DepartmentMapper extends CrudRepository<Department, Long> {
    List<Department> findByPid(Long pid);
}
