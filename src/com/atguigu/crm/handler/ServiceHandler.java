package com.atguigu.crm.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ServiceService;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerService;
import com.atuigu.crm.entity.User;

@Controller
@RequestMapping("/service")
public class ServiceHandler {
	
	@Autowired
	private ServiceService serviceService;

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
	//base分页
	public Map<String,Object>  getList(String pageStr,String str,
			Map<String,Object> map,HttpServletRequest request) {
		Integer pageNo = 1;
		
		Map<String,Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		try {
			pageNo = Integer.parseInt(pageStr);
		} catch (NumberFormatException e) {}
		
		Page<CustomerService> page = serviceService.getCustomerService(pageNo,2,params,str);
		List<User> users = serviceService.getUsers();
		map.put("users", users);
		map.put("page", page);
		String queryString = encodeParameterStringWithPrefix(params, "filter_");
		map.put("queryString", queryString);
		
		return map;
		
	}
	//allot 分页
	@RequestMapping(value="/allot/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String allotList(@RequestParam(value="pageNo",required=false,defaultValue="1")String pageStr,
			Map<String,Object> map,HttpServletRequest request) {
		
		Map<String, Object> map2 = getList(pageStr,"allot", map, request);
		Set<Entry<String, Object>> entrySet = map2.entrySet();
		
		for (Entry<String, Object> entry : entrySet) {
			
			map.put(entry.getKey(), entry.getValue());
			
		}
		return "service/allot/list";
	}
	//deal 分页
	@RequestMapping(value="/deal/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String dealList(@RequestParam(value="pageNo",required=false,defaultValue="1")String pageStr,
			Map<String,Object> map,HttpServletRequest request) {
		
		Map<String, Object> map2 = getList(pageStr,"deal", map, request);
		Set<Entry<String, Object>> entrySet = map2.entrySet();
		
		for (Entry<String, Object> entry : entrySet) {
			
			map.put(entry.getKey(), entry.getValue());
			
		}
		return "service/deal/list";
	}
	//feedback 分页
	@RequestMapping(value="/feedback/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String feedbackList(@RequestParam(value="pageNo",required=false,defaultValue="1")String pageStr,
			Map<String,Object> map,HttpServletRequest request) {
		
		Map<String, Object> map2 = getList(pageStr,"feedback", map, request);
		Set<Entry<String, Object>> entrySet = map2.entrySet();
		
		for (Entry<String, Object> entry : entrySet) {
			
			map.put(entry.getKey(), entry.getValue());
			
		}
		return "service/feedback/list";
	}
	//archive 分页
	@RequestMapping(value="/archive/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String archiveList(@RequestParam(value="pageNo",required=false,defaultValue="1")String pageStr,
			Map<String,Object> map,HttpServletRequest request) {
		
		Map<String, Object> map2 = getList(pageStr,"archive", map, request);
		Set<Entry<String, Object>> entrySet = map2.entrySet();
		
		for (Entry<String, Object> entry : entrySet) {
			
			map.put(entry.getKey(), entry.getValue());
			
		}
		return "service/archive/list";
	}
	@RequestMapping("/input")
	public String input(Map<String,Object> map){
		CustomerService cs =new CustomerService();
		List<Customer> customers = serviceService.getCustomers();
		map.put("customers", customers);
		map.put("customerService", cs);
		return "service/input";
		
	}
	//去到deal.jsp
	@RequestMapping(value="/deal",method=RequestMethod.GET)
	public String deal(@RequestParam("id")Integer id,
			Map<String,Object> map){
		CustomerService service = serviceService.getServiceById(id);
		map.put("service", service);
		return "service/deal/deal";
	}
	//处理服务
	@RequestMapping(value="/deal/save",method=RequestMethod.POST)
	public String deal(@RequestParam("id")Integer id,
			@RequestParam("serviceDeal")String serviceDeal,
			RedirectAttributes attributes){
		serviceService.deal(id,serviceDeal);
		attributes.addFlashAttribute("message", "处理完成！");
		return "redirect:/service/deal/list";
	}
	
	//去到feedback.jsp
	@RequestMapping(value="/feedback",method=RequestMethod.GET)
	public String feedback(@RequestParam("id")Integer id,
			Map<String,Object> map){
		CustomerService service = serviceService.getServiceById(id);
		map.put("service", service);
		return "service/feedback/feedback";
	}
	//服务反馈
	@RequestMapping(value="/feedback/save",method=RequestMethod.POST)
	public String feedback(@RequestParam("id")Integer id,
			@RequestParam("dealResult")String dealResult,
			@RequestParam("satisfy")String satisfy,
			RedirectAttributes attributes){
		serviceService.feedback(id,dealResult,satisfy);
		attributes.addFlashAttribute("message", "反馈完成！");
		return "redirect:/service/feedback/list";
	}
	
	//去到archive.jsp
	@RequestMapping(value="/archive",method=RequestMethod.GET)
	public String archive(@RequestParam("id")Integer id,
			Map<String,Object> map){
		CustomerService service = serviceService.getServiceById(id);
		map.put("service", service);
		return "service/archive/archive";
	}
	
	//添加服务
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(CustomerService customerService,RedirectAttributes attributes){
		
	
			serviceService.input(customerService);
			attributes.addFlashAttribute("message", "添加了一个服务！");
		
		
		return "redirect:/service/allot/list";
		
	}
	@ResponseBody
	@RequestMapping(value="/allot",method=RequestMethod.POST)
	public String allot(@RequestParam("id")Integer id,
			@RequestParam("allotId")Integer allotId,RedirectAttributes attributes){
		String date =null;
		
		if(allotId != null && id != null){
			
				serviceService.allotTo(id,allotId);
				date = "1";	
		}
	
		return date;
	}
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam("id")Integer id,RedirectAttributes attributes){
		
		try {
			serviceService.delete(id);
			attributes.addFlashAttribute("message", "删除成功！");
		} catch (Exception e) {}
		
		return "redirect:/service/allot/list";
		
	}

}
