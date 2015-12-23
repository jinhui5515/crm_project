package com.atguigu.crm.handler.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.service.jpa.AuthorityService;
import com.atguigu.crm.service.jpa.RoleService;
import com.atuigu.crm.entity.Authority;
import com.atuigu.crm.entity.Role;
@Controller
@RequestMapping("/role")
public class RoleHandler extends BaseHandler<Role, Long> {
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	@RequestMapping(value="/assign",method=RequestMethod.GET)
	public String assign(@RequestParam("id")Long id,Map<String,Object> map){
		Role role = roleService.getById(id);
		List<Authority> authorities = authorityService.findAll();
		map.put("role", role);
		map.put("parentAuthorities", authorities);
		return "role/assign";
	}
}
