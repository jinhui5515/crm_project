package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.CustomerMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper customerMapper;

	/*
	 * @Transactional public void insert(Map<String, Object> map) {
	 * customerMapper.insert(map); }
	 */

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
	public Page<Customer> getPage(Integer pageNo, Integer pageSize,
			Map<String, Object> params) {

		Page<Customer> page = new Page<Customer>();
		Integer preIndex = (pageNo - 1) * pageSize;
		Integer lastIndex = preIndex + pageSize;

		// 获取查询字符串map
		List<PropertyFilter> filters = PropertyFilter
				.parseParamsToFilters(params);
		params = parseFiltersToMyBatisParams(filters);

		// 获取总记录数
		long totalElements = customerMapper.getTotalElements(params);
		params.put("preIndex", preIndex);

		// 获取content
		params.put("lastIndex", lastIndex);
		List<Customer> content = customerMapper.getContent(params);

		page.setPageNo(pageNo);
		page.setPageSize(2);
		page.setContent(content);
		page.setTotalElements(totalElements);
		return page;
	}

	@Transactional
	public List<Object> getRegions() {
		return customerMapper.getRegions("地区");
	}

	@Transactional
	public List<Object> getLevels() {
		return customerMapper.getLevels("客户等级");
	}

	@Transactional
	public List<Object> getCredits() {
		return customerMapper.getCredits("信用度");
	}

	@Transactional
	public List<Object> getSatifys() {
		return customerMapper.getSatifys("满意度");
	}

	@Transactional
	public Customer getCustomerById(Integer id) {
		return customerMapper.getCustomerById(id);
	}

	@Transactional
	public List<Contact> getManagers() {
		return customerMapper.getManagers();
	}

	@Transactional
	public void saveCoustomer(Customer customer) {
		customerMapper.saveCoustomer(customer);
	}

	@Transactional
	public void delete(Integer id) {
		customerMapper.delete(id);
	}
}
