package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.ActivityMapper;
import com.atuigu.crm.entity.CustomerActivity;

@Service
public class ActivityService {

	@Autowired
	private ActivityMapper activityMapper;

	@Transactional
	public List<CustomerActivity> getActivityById(Integer id) {
		return activityMapper.getActivityById(id);
	}

	@Transactional
	public void save(CustomerActivity customerActivity) {
		activityMapper.save(customerActivity);
	}

	@Transactional
	public CustomerActivity getActivity(Long id) {
		return activityMapper.getActivity(id);
	}

	@Transactional
	public void update(CustomerActivity customerActivity) {
		if(customerActivity.getDate()!=null){
			
			activityMapper.update(customerActivity);
		}
	}
	@Transactional
	public void deleteById(Integer id) {
		activityMapper.deleteById(id);
		
	}

}
