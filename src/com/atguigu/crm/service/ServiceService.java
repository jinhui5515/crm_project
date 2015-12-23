package com.atguigu.crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.ServiceMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerService;
import com.atuigu.crm.entity.User;

@Service
public class ServiceService {
	
	@Autowired
	private ServiceMapper serviceMapper;
	
	private Map<String, Object> parseFiltersToMyBatisParams(
			List<PropertyFilter> filters) {

		Map<String, Object> params = new HashMap<String, Object>();

		for (PropertyFilter filter : filters) {
			String propertyName = filter.getPropertyName();
			MatchType mathType = filter.getMatchType();
			Class propertyType = filter.getPropertyType();
			Object propertyVal = filter.getPropertyVal();

			// 把传入的字符串转为实际的目标类型
			propertyVal = ReflectionUtils.convertValue(propertyVal,
					propertyType);
			// 把值进行必要的操作
			switch (mathType) {
			case LIKE:
				propertyVal = "%" + propertyVal + "%";
			case EQ:
				propertyVal = propertyVal;
			case GTE:
				propertyVal = propertyVal;
			case LTE:
				propertyVal = propertyVal;
			}

			params.put(propertyName, propertyVal);
		}

		return params;
	}
	@Transactional
	public Page<CustomerService> getCustomerService(Integer pageNo, 
			int pageSize, Map<String, Object> map,String str) {
		
		Page<CustomerService> page= new Page<CustomerService>();
		Map<String,Object> params = new HashMap<String, Object>();
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(map);
		//获取查询条件
		params = parseFiltersToMyBatisParams(filters);
		
		Integer preIndex =  (pageNo-1) *pageSize;
		Integer lastIndex = preIndex + pageSize;
		params.put("preIndex",preIndex );
		params.put("lastIndex", lastIndex);
		params.put(str, str);
		Integer totalElements = serviceMapper.getTotalElements(params);
		List<CustomerService> content = serviceMapper.getContent(params);
		//查询分页内容
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalElements(totalElements);
		page.setContent(content);
		return page;
	}
	@Transactional
	public List<Customer> getCustomers() {
		return serviceMapper.getCustomers();
	}
	@Transactional
	public void input(CustomerService customerService) {
		serviceMapper.input(customerService);
	}
	@Transactional
	public List<User> getUsers() {
		return serviceMapper.getUsers();
	}
	public void allotTo(Integer id, Integer allotId) {
		Date date = new Date();
		serviceMapper.allotTo(id,allotId,date);
	}
	public void delete(Integer id) {
		serviceMapper.delete(id);
		
	}
	public CustomerService getServiceById(Integer id) {
		return serviceMapper.getServiceById(id);
	}
	public void deal(Integer id, String serviceDeal) {
		Date date = new Date();
		serviceMapper.deal(id,serviceDeal,date);
	}
	public void feedback(Integer id, String dealResult,String satisfy) {
		serviceMapper.feedback(id,dealResult,satisfy);
		
	}
	
}
