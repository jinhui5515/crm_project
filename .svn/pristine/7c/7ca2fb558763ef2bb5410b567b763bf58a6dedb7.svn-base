package com.atguigu.crm.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.atuigu.crm.entity.Contact;



public interface ContactMapper {
	
	@SelectKey(before=true,keyColumn="id",keyProperty="id",statement="select crm_contact_seq.nextVal from dual",resultType=Long.class)
	@Insert("insert into contacts(id,name,tel,customer_id) values(#{id},#{name},#{tel},#{custId})")
	public void insert(Map<String,Object> contactMap);
}
