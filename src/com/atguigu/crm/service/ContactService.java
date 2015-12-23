package com.atguigu.crm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.ContactMapper;
import com.atuigu.crm.entity.Contact;

@Service
public class ContactService {

	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional
	public void insert(Map<String,Object> contactMap){
		contactMapper.insert(contactMap);
	}
}
