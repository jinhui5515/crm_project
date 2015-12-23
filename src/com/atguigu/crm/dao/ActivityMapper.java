package com.atguigu.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.CustomerActivity;

public interface ActivityMapper {
	
	@Select(value = "select id,activity_date as \"date\",description,place,title,customer_id as \"customer.id\" from customer_activities where customer_id=#{id}")
	List<CustomerActivity> getActivityById(Integer id);

	@Insert(value = "insert into customer_activities "
			+ " (id,activity_date,description,place,title,customer_id)"
			+ " values(crm_seq.nextval+200,#{date},"
			+ " #{description},#{place},#{title},#{customer.id})")
	void save(CustomerActivity customerActivity);

	@Update(value = "update customer_activities set activity_date=#{date},"
			+ " description=#{description},place=#{place},title=#{title},customer_id=#{customer.id}"
			+ " where id =#{id}")
	void update(CustomerActivity customerActivity);

	@Select(value="select id,activity_date as \"date\",description,place,title,customer_id as \"customer.id\" "
			+ " from customer_activities where id=#{id}")
	CustomerActivity getActivity(Long id);
	
	@Delete(value="delete from customer_activities where id=#{id}")
	void deleteById(Integer id);

}
