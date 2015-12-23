package com.atguigu.crm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerService;
import com.atuigu.crm.entity.User;

public interface ServiceMapper {

	List<CustomerService> getContent(Map<String, Object> params);

	Integer getTotalElements(Map<String, Object> params);

	@Select(value = "select id,name from customers")
	List<Customer> getCustomers();

	@Insert(value = "insert into customer_services (id,CREATE_DATE,"
			+ " SERVICE_REQUEST,SERVICE_STATE,SERVICE_TITLE, "
			+ " SERVICE_TYPE,CREATED_ID,CUSTOMER_ID) "
			+ " values (crm_seq.nextval,#{createDate} ,#{serviceRequest},"
			+ " #{serviceState},#{serviceTitle},#{serviceType},#{createdby.id,jdbcType=VARCHAR},#{customer.id})")
	void input(CustomerService customerService);

	@Select(value = "select id,name from users")
	List<User> getUsers();

	@Update(value = "update customer_services set allot_id=#{allotId},allot_date=#{date} where id=#{id}")
	void allotTo(@Param("id") Integer id, @Param("allotId") Integer allotId,
			@Param("date") Date date);

	@Select(value = "delete from customer_services where id =#{id}")
	void delete(@Param("id") Integer id);

	CustomerService getServiceById(@Param("id") Integer id);
	
	@Update(value = "update customer_services set service_deal=#{serviceDeal},service_state='已处理',deal_date=#{date} where id=#{id}")
	void deal(@Param("id")Integer id,@Param("serviceDeal") String serviceDeal, @Param("date")Date date);
	
	@Update(value = "update customer_services set deal_result=#{dealResult},satisfy=#{satisfy} where id=#{id}")
	void feedback(@Param("id")Integer id,@Param("dealResult") String dealResult,@Param("satisfy") String satisfy);

}
