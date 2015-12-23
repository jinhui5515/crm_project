package com.atguigu.crm.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.DrainService;
import com.atuigu.crm.entity.CustomerDrain;

@Controller
@RequestMapping("/drain")
public class DrainHandler {
	
	@Autowired
	private DrainService drainService;

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
	//分页
	@RequestMapping(value="/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String list(@RequestParam(value="pageNo",required=false,defaultValue="1")String pageStr,
			Map<String,Object> map,HttpServletRequest request) {
		Integer pageNo = 1;
		
		Map<String,Object> param = WebUtils.getParametersStartingWith(request, "filter_");
		try {
			pageNo = Integer.parseInt(pageStr);
		} catch (NumberFormatException e) {}
		
		Page<CustomerDrain> page = drainService.getDrains(pageNo,2,param);
		String queryString = encodeParameterStringWithPrefix(map, "filter_");
		
		map.put("queryString", queryString);
		map.put("page", page);
		
		return "drain/list";
	}
	//到delay页面
	@RequestMapping(value="/delay",method = {RequestMethod.GET,RequestMethod.POST})
	public String delay(@RequestParam(value="id")Integer id,
			Map<String,Object> map) {
		
		CustomerDrain cd = drainService.getDrainById(id);
	
		map.put("drain", cd);
		return "drain/delay";
	}
	
	//到保存流失原因界面
	@RequestMapping(value="/confirm",method = {RequestMethod.GET,RequestMethod.POST})
	public String confirm(@RequestParam(value="id")Integer id,
			Map<String,Object> map) {
		
		CustomerDrain cd = drainService.getDrainById(id);
	
		map.put("drain", cd);
		return "drain/confirm";
	}
	//保存流失原因
	@RequestMapping(value="/saveConfirm",method = RequestMethod.POST)
	public String saveConfirm(@RequestParam(value="id")Integer id,
			@RequestParam(value="reason")String reason,
			Map<String,Object> map) {
		
			drainService.saveConfirm(id,reason);
		
		return "redirect:/drain/list";
	}
	
	//ajax保存暂缓措施
	@ResponseBody
	@RequestMapping(value="/delayMethod",method = RequestMethod.POST)
	public String delayMethod(@RequestParam(value="id")Integer id,
			@RequestParam(value="delay")String delay,Map<String,Object> map) {
		if(delay !=null && !delay.equals("")){

			CustomerDrain cd = drainService.getDrainById(id);
			String olddelay = cd.getDelay();
			String newDelay =olddelay+"`"+delay;
			drainService.updateDelay(newDelay,id);
			return newDelay;
		}else{
			return "2";
		}
		
	}
}
