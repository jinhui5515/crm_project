package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.SalesChanceMapper;
import com.atguigu.crm.dao.SalesPlanMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.SalesChance;
import com.atuigu.crm.entity.SalesPlan;
import com.atuigu.crm.entity.User;

@Service
public class SalesChanceService
{
	@Autowired
	private SalesChanceMapper salesChanceMapper;
	
	@Autowired
	private SalesPlanMapper salesPlanMapper;

		//更新SalesChance数据表中的指定字段
	@Transactional
	public void updateSpecifiedField(Map<String,Object> map){
		salesChanceMapper.updateSpecifiedField(map);
	}
	
@Transactional
	public Map<String,Object> getContact(Integer id){
		return salesChanceMapper.getContact(id);
	}
	
		@Transactional
	public List<User> getUsers() {

	return salesChanceMapper.getUsers();
	}
	@Transactional
	public void update(SalesChance salesChance){
		salesChanceMapper.update(salesChance);
	}
	
	@Transactional(readOnly=true)
	public SalesChance get(Integer id){
		return salesChanceMapper.get(id);
	}
	
	@Transactional
	public void delete(Integer id)
	{
		salesChanceMapper.delete(id);
	}
	
		@Transactional
	public void updateDesignee(SalesChance salesChance){
		
		salesChance.setStatus(2);
		salesChanceMapper.updateDesignee(salesChance);
		
	}
	
	@Transactional
	public void save(SalesChance salesChance )
	{
		salesChance.setStatus(1);
		salesChanceMapper.save(salesChance);
	}
	

	
	
	@Transactional(readOnly=true)
	public Page<SalesChance> getPage(int pageNo,int pageSize,Map<String,Object> params)
	{
		Page<SalesChance> page = new Page<>();
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		//1.把 params 转为PropertyFilter的集合
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		//2.把 PropertyFilter 的集合再转为实际上可以转为myBatis的Map 类型的参数
		Map<String,Object> myBatisParams = parseFiltersToMyBatisParams(filters);
		myBatisParams.put("status", 1);
		
		
		long totalElements = salesChanceMapper.getTotalElements(myBatisParams);
		page.setTotalElements(totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = fromIndex + page.getPageSize();
		
		
		myBatisParams.put("fromIndex", fromIndex);
		myBatisParams.put("endIndex", endIndex);
		
		List<SalesChance> content = salesChanceMapper.getContent(myBatisParams);
		page.setContent(content);
		
		
		return page;
	}

	private Map<String, Object> parseFiltersToMyBatisParams(
			List<PropertyFilter> filters)
	{
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		for (PropertyFilter filter : filters)
		{
			String propertyName = filter.getPropertyName();
			MatchType mathType = filter.getMatchType();
			Class propertyType = filter.getPropertyType();
			Object propertyVal = filter.getPropertyVal();
			
			//把传入的字符串转为实际的目标类型
			propertyVal = ReflectionUtils.convertValue(propertyVal, propertyType);
			//把值进行必要的操作
			switch(mathType)
			{
				case LIKE:
					propertyVal = "%" + propertyVal + "%";
			}		
			
			params.put(propertyName, propertyVal);
		}
		
		return params;
	}
	
	
	//显示开发计划的list
	@Transactional(readOnly=true)
	public Page<SalesChance> getSalesChancePage(int pageNo, int pageSize,
			Map<String, Object> params)
	{
		Page<SalesChance> page = new Page<>();
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		User user = (User)params.get("user");
		params.remove("user");
		//1.把 params 转为PropertyFilter的集合
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		//2.把 PropertyFilter 的集合再转为实际上可以转为myBatis的Map 类型的参数
		Map<String,Object> myBatisParams = parseFiltersToMyBatisParams(filters);
/*		List<Integer> stautus = new ArrayList<>();
			stautus.add(2);
			stautus.add(3);
			stautus.add(4);
			*/
		myBatisParams.put("status", 2);
		myBatisParams.put("user", user);
		
		long totalElements = salesChanceMapper.getTotalElements(myBatisParams);
		page.setTotalElements(totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = fromIndex + page.getPageSize();
		
		
		myBatisParams.put("fromIndex", fromIndex);
		myBatisParams.put("endIndex", endIndex);
		
		List<SalesChance> content = salesChanceMapper.getContent(myBatisParams);
		page.setContent(content);
		
		
		return page;
	}
	@Transactional
	public SalesChance getChance(Integer id) {
		
		return  salesChanceMapper.getSalesChance(id);
	}
	@Transactional
	public SalesChance getSalesChanceById(Integer id) {
		return salesChanceMapper.getSalesChance(id);
	}
	
	@Transactional
	public void stop(Integer id) {
		salesChanceMapper.stop(id);
	}
	
	@Transactional
	public void savePlan(SalesPlan plan) {
		salesChanceMapper.savePlan(plan);
	}
	
	@Transactional
	public void resultUpdate(Map<String,Object> map) {
		
		salesChanceMapper.resultUpdate(map);
	}
	@Transactional
	public SalesPlan getPlanById(Integer id) {
		
		return salesChanceMapper.getPlanById(id);
	}
	
	@Transactional
	public SalesPlan getSalesPlanById(Long id){
		return salesPlanMapper.get(id);
	}
	@Transactional
	public void delete(Long id) {
		salesPlanMapper.delete(id);
	}
	
	public void updateSalesPlan(SalesPlan salesPlan){
		
		salesPlanMapper.updateSalesPlan(salesPlan);
	}
	@Transactional
	public void save(SalesPlan plan)
	{
		salesChanceMapper.savePlan(plan);
	}
}








