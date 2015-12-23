package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.service.OrderService;
import com.atuigu.crm.entity.CustomerActivity;
import com.atuigu.crm.entity.Order;
import com.atuigu.crm.entity.OrderItem;

@Controller
@RequestMapping("/order")
public class OrderHandler {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	

	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list(@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value = "pageNo", required = false) String pageStr,
			Map<String, Object> map) {
		Integer pageNo = 1;
		if (pageStr != null) {
			try {
				pageNo = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {}
		}
		int pageSize = 2;
		List<Order> list = orderService.getOrderById(id);
		List<Order> content = new ArrayList<Order>();
		int preIndex = (pageNo - 1) * pageSize;
		int endIndex = preIndex + pageSize;
		if (list.size() != 0) {

			if (list.size() - (pageNo - 1) * pageSize >= pageSize) {
				for (int i = preIndex; i < endIndex; i++) {
					content.add(list.get(i));
				}
			} else {
				for (int i = 0; i < list.size() - (pageNo - 1) * pageSize; i++) {
					content.add(list.get(i));
				}
			}
		}
		Page<Order> page = new Page<Order>();
		page.setContent(content);
		page.setTotalElements(list.size());
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		map.put("page", page);
		map.put("customer", customerService.getCustomerById(id));
		return "order/list";
	}

	@RequestMapping(value="/details",method = RequestMethod.GET)
	public String details(@RequestParam(value="id",required=false) Integer id,
			Map<String,Object> map){
		Order order = orderService.getByOrderId(id);
		Set<OrderItem> items = orderService.getItemsByOrderId(id);
		System.out.println(items.toString());
		order.setItems(items);
		map.put("order",order );
		return "order/details";
	}
		
}
