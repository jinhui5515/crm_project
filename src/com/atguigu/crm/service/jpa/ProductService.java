package com.atguigu.crm.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.dao.jpa.ProductRepository;
import com.atuigu.crm.entity.Product;

@Service
public class ProductService extends BaseService<Product, Long>{
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll2() {
		
		return productRepository.findAll();
	}

}
