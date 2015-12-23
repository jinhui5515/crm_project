package com.atguigu.crm.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.CustomerActivity;
import com.atuigu.crm.entity.Order;
import com.atuigu.crm.entity.OrderItem;

public interface OrderMapper {

	@Select(value = "select id,address,order_date as \"date\",no,status,"
			+ "customer_id as \"customer.id\" from orders where customer_id=#{id}")
	List<Order> getOrderById(Integer id);

	@Select(value = "select id,address,order_date as \"date\",no,status,"
			+ "customer_id as \"customer.id\" from orders where id=#{id}")
	Order getByOrderId(Integer id);

	@Select(value = "select o.id,o.item_count as \"count\",o.money,"
			+ " p.name as \"product.name\",p.unit as \"product.unit\", "
			+ " o.order_id as \"order.id\","
			+ " o.product_id as \"product.id\" "
			+ " from order_items o left outer join"
			+ " products p on p.id=o.product_id "
			+ " where order_id=#{id}")
	Set<OrderItem> getItemsByOrderId(Integer id);

}
