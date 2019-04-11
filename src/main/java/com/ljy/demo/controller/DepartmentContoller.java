package com.ljy.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.ljy.demo.common.Department;
import com.ljy.demo.common.UserRelation;
import com.ljy.demo.mapper.DepartmentMapper;
import com.ljy.demo.mapper.UserRelationMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: jyl
 * @Descriptions:
 * @Date: create in  17:51 2019/4/11/011
 */
@RestController
public class DepartmentContoller {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserRelationMapper userRelationMapper;

    @RequestMapping(value = "/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    /**
     * 新增部门接口
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/department/add")
    public Map<String, String> add(@RequestBody JSONObject jsonObject){
        Map<String, String> respMap = new HashMap<>();
        Long id = jsonObject.getLong("id");
        Long pid = jsonObject.getLong("pid");
        String name = jsonObject.getString("name");
        Department param = new Department();
        if(pid != 0){ //如果不是顶级部门， 需判断趁层级是否大于4
            Optional<Department> optional = departmentMapper.findById(pid);
            if(!optional.isPresent()){ //如果上级实例不存在
                respMap.put("code", "999");
                respMap.put("msg", "上级部门不存在");
                return respMap;
            }
            Department pDepart = optional.get();
            if(pDepart.getStatus() != 1){ //如果上级实例被禁用
                respMap.put("code", "998");
                respMap.put("msg", "上级部门已被禁用");
                return respMap;
            }
            if(pDepart.getLevel() >= 4){ //如果层级数已经大于4
                respMap.put("code", "997");
                respMap.put("msg", "最多只能有4级部门");
                return respMap;
            }
            param.setName(name);
            param.setPid(pid);
            param.setLevel(pDepart.getLevel()+1);
            param.setStatus(1);
        }else{
            //新增一级部门
            param.setName(name);
            param.setPid(0L);
            param.setLevel(1);
            param.setStatus(1);
        }
        departmentMapper.save(param); //新增的部门入库
        respMap.put("code", "0");
        return respMap;
    }

    /**
     * 新增员工接口
     */
    @PostMapping(value = "user/add")
    public Map<String, String> addUser(@RequestBody JSONObject jsonObject){
        Map<String, String> resultMap = new HashMap<>();
        Long userId = jsonObject.getLong("userId");
        Long departmentId = jsonObject.getLong("departmentId");
        Department department = departmentMapper.findById(departmentId).get();
        if(department.getStatus() != 1){
            resultMap.put("code", "999");
            resultMap.put("msg", "部门不存在");
            return resultMap;
        }
        UserRelation param = new UserRelation();
        param.setUserId(userId);
        param.setDepartmentId(departmentId);
        userRelationMapper.save(param);
        resultMap.put("code", "0");
        return resultMap;
    }

    /**
     * 获取部门列表接口
     * @return
     */
    @GetMapping(value = "department/list")
    public Map<String, Object> findAll(){
        Map<String, Object> resultMap = new HashMap<>();
        Department param = new Department();
        param.setPid(0L);
        //先获取所有的一级部门
        List<Department> departmentList = (List<Department>) departmentMapper.findAll();
        for (Department department : departmentList) {
            getDepartmentListByPid(department);
        }
        resultMap.put("code", "0");
        resultMap.put("data", JSONObject.toJSONString(departmentList));
        return resultMap;
    }

    private void getDepartmentListByPid(Department department){
        if(department.getLevel() == 4) return;
        List<Department> departmentList = departmentMapper.findByPid(department.getPid());
        if(departmentList == null || departmentList.size() == 0) return;
        //如果还有子目录则继续遍历
        for (Department depart : departmentList) {
            getDepartmentListByPid(depart);
        }
        department.setDepartmentList(departmentList);
    }

    /**
     * 删除部门接口
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "department/delete")
    public Map<String, Object> delete(@RequestBody JSONObject jsonObject){
        Map<String, Object> resultMap = new HashMap<>();
        Long id = jsonObject.getLong("id");
        Department department = departmentMapper.findById(id).get();
        Integer level = department.getLevel();
        List<Long> ids = new ArrayList<>();
        getDepartmentListByPid(ids, id);
        //获取到要删除的的类目ID及其上级目录的id
        List<Long> userIds = userRelationMapper.getUserIdByDepartmentIds(ids);
        if(userIds == null || userIds.size() == 0){
            //如果该部门及子部门都没有员工则可以删除
            departmentMapper.deleteById(id);
        }
        resultMap.put("code", 0);
        return resultMap;
    }

    /**
     * 获取所有子目录的id
     * @param ids
     * @param id
     */
    private void getDepartmentListByPid(List<Long> ids, Long id){
        List<Department> list = departmentMapper.findByPid(id);
        if(list == null || list.size() == 0){
            return;
        }
        for (Department department : list) {
            ids.add(department.getId());
            getDepartmentListByPid(ids, department.getId());
        }
    }

}
