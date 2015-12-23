package com.atguigu.crm.handler.jpa;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.service.jpa.BaseService;
import com.atguigu.crm.utils.ReflectionUtils;

public class BaseHandler<T, PK extends Serializable> {

	@Autowired
	private BaseService<T, PK> baseService;

	private String model;
	private Class<T> clazz;

	public BaseHandler() {
		clazz = ReflectionUtils.getSuperGenericType(this.getClass());
		model = clazz.getSimpleName();
		model = model.substring(0, 1).toLowerCase() + model.substring(1);
	}

	public void getList(String pageNoStr, HttpServletRequest request) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		if (pageNoStr != null) {
			pageNo = Integer.parseInt(pageNoStr);
		}

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "filter_");

		Page<T> page = baseService.getList(pageNo - 1, 5, params);

		String queryString = encodeParameterStringWithPrefix(params, "filter_");
		request.setAttribute("page", page);
		request.setAttribute("queryString ", queryString);

	}

	public static String encodeParameterStringWithPrefix(
			Map<String, Object> params, String prefix) {
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
			queryStringBuilder.append(prefix).append(entry.getKey())
					.append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(
			HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr) {

		getList(pageNoStr, request);

		return model + "/list";

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@RequestParam(value = "id") PK id) {

		T t = baseService.getById(id);
		map.put(model, t);
		return model + "/input";

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Map<String, Object> map) {

		try {
			map.put(model, clazz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model + "/input";

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(T t) {

		baseService.save(t);
		return "redirect:/" + model + "/list";

	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String delete(@RequestParam(value = "id") PK id) {
		String date = null;
		try {
			baseService.delete(id);
			date = "1";
		} catch (Exception e) {}
		return date;
	}

}
