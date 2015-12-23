package com.atguigu.crm.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.ActivityMapper;
import com.atguigu.crm.dao.OrderMapper;
import com.atuigu.crm.entity.CustomerActivity;
import com.atuigu.crm.entity.Order;
import com.atuigu.crm.entity.OrderItem;

@Service
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Transactional
	public List<Order> getOrderById(Integer id) {
		return orderMapper.getOrderById(id);
	}

	public Order getByOrderId(Integer id) {
		return orderMapper.getByOrderId(id);
	}

	public Set<OrderItem> getItemsByOrderId(Integer id) {
		return orderMapper.getItemsByOrderId(id);
	}
}
