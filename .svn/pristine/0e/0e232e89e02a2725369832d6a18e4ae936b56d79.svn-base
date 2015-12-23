package com.atguigu.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.SalesPlan;

public interface SalesPlanMapper {
	
	//辅助查询SalesChance
	public List<SalesPlan> getSalesPalnByChance(Integer chanceId);
	
	//查询salesPlan
	public SalesPlan getSlaesPlan(Integer id);
	
	//修改salesPlan
	@Update("UPDATE sales_plan set todo=#{todo} WHERE id=#{id}")
	public void updateSalesPlan(SalesPlan salesPlan);
	
	
	@Delete("DELETE FROM sales_plan WHERE id=#{id}")
	public void delete(Long id);
	
	@Select("SELECT id, plan_date, plan_result, todo, chance_id FROM sales_plan WHERE id=#{id}")
	public SalesPlan get(Long id);
}
