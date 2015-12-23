package com.atguigu.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.CustomerDrain;

public interface DrainMapper {

	Integer getTotalElements(Map<String, Object> map);

	List<CustomerDrain> getContent(Map<String, Object> map);
	
	CustomerDrain getDrainById(Integer id);
	@Update(value="update customer_drains set delay = #{newDelay} where id=#{id}")
	void updateDelay(@Param("newDelay")String newDelay,@Param("id")Integer id);
	
	@Update(value="update customer_drains set reason = #{reason},status='流失' where id=#{id}")
	void saveConfirm(@Param("id")Integer id,@Param("reason")String reason);

}
