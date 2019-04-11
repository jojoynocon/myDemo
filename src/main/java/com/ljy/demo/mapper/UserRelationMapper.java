package com.ljy.demo.mapper;

import com.ljy.demo.common.UserRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author: jyl
 * @Descriptions:
 * @Date: create in  1:07 2019/4/12/012
 */
public interface UserRelationMapper extends CrudRepository<UserRelation, Long> {
    /**
     * 通过部门ID查询所属的用户id
     * @param ids
     * @return
     */
    // select user_id from user_department_relation where department id in (）
    List<Long> getUserIdByDepartmentIds(List<Long> ids);
}
