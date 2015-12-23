package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.DrainMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.CustomerDrain;

@Service
public class DrainService {
	
	@Autowired
	private DrainMapper drainMapper;
	
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
			}

			params.put(propertyName, propertyVal);
		}

		return params;
	}
	@Transactional
	public Page<CustomerDrain> getDrains(Integer pageNo, 
			int pageSize, Map<String, Object> map) {
		
		Page<CustomerDrain> page= new Page<CustomerDrain>();
		Map<String,Object> params = new HashMap<String, Object>();
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(map);
		//获取查询条件
		params = parseFiltersToMyBatisParams(filters);
		
		Integer preIndex =  (pageNo-1) *pageSize;
		Integer lastIndex = preIndex + pageSize;
		params.put("preIndex",preIndex );
		params.put("lastIndex", lastIndex);
		
		Integer totalElements = drainMapper.getTotalElements(params);
		List<CustomerDrain> content = drainMapper.getContent(params);
		//查询分页内容
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalElements(totalElements);
		page.setContent(content);
		return page;
	}
	@Transactional
	public CustomerDrain getDrainById(Integer id) {
		return drainMapper.getDrainById(id);
	}
	@Transactional
	public void updateDelay(String newDelay,Integer id) {
		drainMapper.updateDelay(newDelay,id);
		
	}
	@Transactional
	public void saveConfirm(Integer id,String reason) {
		// TODO Auto-generated method stub
		 drainMapper.saveConfirm(id,reason);
	}
}
