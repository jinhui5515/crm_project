package com.atguigu.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atuigu.crm.entity.User;

public interface UserMapper
{
	@Select("select id,name,password,enabled from users where name = #{name}")
	public User getByName(@Param("name") String name);

}
