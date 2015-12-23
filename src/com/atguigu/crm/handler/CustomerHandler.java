package com.atguigu.crm.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerService;
import com.atuigu.crm.entity.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerHandler {
	@Autowired
	private CustomerService customerService;
	
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String list(Map<String,Object> map,
			@RequestParam(value="pageNo",required=false)String pageStr
			,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		Integer pageNo = 1;
		if(pageStr != null){
			pageNo = Integer.parseInt(pageStr);
		}
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		Page<Customer> page = customerService.getPage(pageNo,2,params);
		
		
		map.put("regions", customerService.getRegions());
		map.put("levels", customerService.getLevels());
		
		String queryString = encodeParameterStringWithPrefix(params,"filter_");
		
	    map.put("queryString", queryString);
		map.put("page", page);
		return "customer/list";
	}
	@RequestMapping("/create")
	public String create(@RequestParam("id")Integer id,@RequestParam("pageNo")Integer pageNo,
			Map<String,Object> map){
		Customer customer = customerService.getCustomerById(id);
		map.put("customer", customer);
		map.put("regions", customerService.getRegions());
		map.put("levels", customerService.getLevels());
		map.put("satisfies", customerService.getSatifys());
		map.put("credits", customerService.getCredits());
		map.put("managers", customerService.getManagers());
		map.put("pageNo", pageNo);
		return "customer/input";
		
	}
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveCoustomer(Customer customer,RedirectAttributes attributes,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo){
		
		customerService.saveCoustomer(customer);

		try {
			
			attributes.addFlashAttribute("message", "编辑完成");
			
		} catch (Exception e) {}
		return "redirect:/customer/list?pageNo="+pageNo;
	}

	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam("id")Integer id,RedirectAttributes attributes,
			@RequestParam("pageNo")Integer pageNo){
	
		customerService.delete(id);
		try {
			attributes.addFlashAttribute("message", "删除成功");
		} catch (Exception e) {}
		
		return "redirect:/customer/list?id="+id+"&pageNo="+pageNo;
	}
}

