* 主要表有两张

  user_department_relation记录用户和部门间关系
  
  id bigint(20) 自增id
  
  department_id bigint(20) 部门id
  
  user_id bigint(20) 用户id
  
  department 记录部门表
  
  id bigint(20) 自增id
  
  pid bigint(20) default 0 父部门ID
  
  level tinyint(4) default 1 部门层级数
  
  父部门及子部门间通过pid来关联遍历,通过pid可活的一个部门所关联的上下级部门的id
  
  删除时只有该id以及下级id在用户关联表中不存在关联关系时方可删除
  
