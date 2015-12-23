package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ContactService;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.service.SalesChanceService;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.SalesChance;
import com.atuigu.crm.entity.User;

@RequestMapping("/chance")
@Controller
public class SalesChanceHandler
{
	@Autowired
	private SalesChanceService salesChanceService;
	@Autowired 
	private ContactService contactService;
	@Autowired
	private CustomerService customerService;
	@RequestMapping(value="/dispatch",method=RequestMethod.GET)
	public String dispatch(Map<String,Object> map){
		
		List<User> users = salesChanceService.getUsers();
		map.put("users", users);
		
		return "chance/dispatch";
		
	}
	
	@RequestMapping(value="/dispatchOver",method=RequestMethod.PUT)
	public String dispatchOver(SalesChance salesChance
			,RedirectAttributes attributes){
		
		try{
			salesChanceService.updateDesignee(salesChance);
		
			attributes.addFlashAttribute("message", "指派完成");
		} catch (Exception e){}
		
		return "redirect:/chance/list";
	}
	
	@ModelAttribute
	public void get(@RequestParam(value="id",required=false) String id, Map<String,Object> map)
	{
		if(id != null && !id.equals("") )
		{
			SalesChance salesChance = salesChanceService.get(Integer.parseInt(id));
//			SalesChance salesChance = salesChanceService.get(id);
			map.put("salesChance", salesChance);
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(@PathVariable("id") String id,@ModelAttribute("salesChance") SalesChance salesChance,RedirectAttributes attributes)
	{
		
		try
		{
			salesChanceService.update(salesChance);
			attributes.addFlashAttribute("message", "编辑完成");
		} catch (Exception e)
		{}
		
		
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String edit(@PathVariable("id") String id ,Map<String,Object> map)
	{
		SalesChance salesChance = salesChanceService.get(Integer.parseInt(id));
		
		map.put("chance", salesChance);
		return "chance/input";
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") String id ,RedirectAttributes attributes )
	{
		try
		{
			salesChanceService.delete(Integer.parseInt(id));
			attributes.addFlashAttribute("message", "删除完成");
		} catch (NumberFormatException e)
		{}
		
		
		return "redirect:/chance/list";
	}

	
	
	
	@RequestMapping(method=RequestMethod.POST)
	public String save(SalesChance salesChance,RedirectAttributes attributes)
	{
		salesChanceService.save(salesChance);
		
		attributes.addFlashAttribute("message", "添加完成");
		
		
		return "redirect:/chance/list";
	}
	
	
	
	@RequestMapping
	public String input(Map<String,Object> map)
	{
		map.put("chance", new SalesChance());
		return "/chance/input";
	}
	
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String list(@RequestParam(value = "pageNo",required = false) 
						String pageNoStr, Map<String,Object> map, HttpServletRequest request )
	{
		int pageNo = 1;
		
		try
		{
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e)
		{
		}
		
		//1.获取查询条件的请求参数的Map：LIKES_custName
		Map<String,Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		
/*		测试params集合	
 * for (Entry<String,Object> entry : params.entrySet())
		{
			System.out.println(entry.getKey()+"@@@@@"+entry.getValue());
		}*/
		
		
		
		//2.调用Service 方法得到Page对象
		Page<SalesChance> page = salesChanceService.getPage(pageNo,4,params);
		
		//3.把Page 放到request中
		map.put("page", page);
		
		//4.为了保证到下一页  查询条件还要带着  把Map 在序列化为一个查询字符串传到页面上
		String queryString = encodeParameterStringWithPrefix(params,"filter_");
		map.put("queryString", queryString);
		
/*		Page<SalesChance> page = salesChanceService.getPage(pageNo, 4);
		map.put("page", page);*/
		
		return "chance/list";
	}
	
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
	
/*	//开发成功
	@RequestMapping(value="/finish/{id}",method=RequestMethod.PUT)
	public String finish(@PathVariable("id") Integer id){
		
		//将status字段值更新为3
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("status", 3);
		//更新salesChance表中的指定字段
		salesChanceService.updateSpecifiedField(map);
		SalesChance salesChance = salesChanceService.get(id);
		Map<String,Object> custMap = new HashMap<>();
		
		custMap.put("custName", salesChance.getCustName());
		custMap.put("no", System.currentTimeMillis() + "");
		custMap.put("state", "正常");
		customerService.insert(custMap);
		
		Map<String,Object> contactMap = salesChanceService.getContact(id);
		
		Set<Entry<String, Object>> entrySet = contactMap.entrySet();
		for(Map.Entry<String, Object> entry : entrySet){
			System.out.println(entry.getKey()+"-----------"+entry.getValue());
		}
		
		contactService.insert(contactMap);
		
		return "redirect:/plan/chance/list";
	}*/
	
	//终止开发 开发失败
	
	@RequestMapping(value="/stop/{id}",method=RequestMethod.PUT)
	public String stop(@PathVariable("id") Integer id){
		
		//将status字段值更新为3
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("status", 4);
		//更新salesChance表中的指定字段
		salesChanceService.updateSpecifiedField(map);
		return "redirect:/plan/chance/list";
	}
}
