package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ActivityService;
import com.atguigu.crm.service.CustomerService;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerActivity;

@Controller
@RequestMapping("/activity")
public class ActivityHandler {
	@Autowired
	private ActivityService activityService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping
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
		List<CustomerActivity> list = activityService.getActivityById(id);
		List<CustomerActivity> content = new ArrayList<CustomerActivity>();
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
		Page<CustomerActivity> page = new Page<CustomerActivity>();
		page.setContent(content);
		page.setTotalElements(list.size());
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		map.put("page", page);
		map.put("customer", customerService.getCustomerById(id));
		return "activity/list";
	}

	@RequestMapping(value = "/save", method = {RequestMethod.POST,
			RequestMethod.PUT })
	public String save(CustomerActivity customerActivity,
			RedirectAttributes attributes,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo) {

		if(customerActivity.getId() != null){
			activityService.update(customerActivity);
			try {
				attributes.addFlashAttribute("message", "编辑完成");
			} catch (Exception e) {}
		}else{
			activityService.save(customerActivity);
			try {
				attributes.addFlashAttribute("message", "添加成功");
			} catch (Exception e) {}
			
		}
		return "redirect:/activity?id=" +customerActivity.getCustomer().getId()+ "&pageNo=" + pageNo;
		
	}

	@RequestMapping("/create")
	public String create(@RequestParam("id") Long id,
			@RequestParam(value = "editId", required = false) Long editId,
			Map<String, Object> map,@RequestParam(value="pageNo",required=false)Integer pageNo) {
		
		
		if (editId != null) {
			map.put("pageNo", pageNo);
			CustomerActivity CustomerActivity = activityService
					.getActivity(editId);
			map.put("activity", CustomerActivity);
			
		} else {
			
			Customer customer = new Customer();
			customer.setId(id);
			CustomerActivity activity = new CustomerActivity();
			activity.setCustomer(customer);
			map.put("activity", activity);
			
		}

		return "activity/input";
	}
	@RequestMapping(value="/delete",method = RequestMethod.GET)
	public String delete(@RequestParam(value="id")Integer id,
			@RequestParam(value="customerId")Integer customerId,
			RedirectAttributes attributes,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo) {

			activityService.deleteById(id);
			try {
				attributes.addFlashAttribute("message", "删除成功");
			} catch (Exception e) {}
			
		return "redirect:/activity?id=" +customerId+ "&pageNo=" + pageNo;
		
	}

}
