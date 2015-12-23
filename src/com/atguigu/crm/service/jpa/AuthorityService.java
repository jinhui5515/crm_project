package com.atguigu.crm.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.dao.jpa.AuthorityRepository;
import com.atuigu.crm.entity.Authority;
@Service
public class AuthorityService extends BaseService<Authority, Long>{
	@Autowired
	private AuthorityRepository authorityRepository;
	public List<Authority> findAll() {
		// TODO Auto-generated method stub
		return authorityRepository.findAll();
	}

}