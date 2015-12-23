package com.atguigu.crm.handler.jpa;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.service.jpa.ProductService;
import com.atguigu.crm.service.jpa.StorageService;
import com.atuigu.crm.entity.Storage;
@Controller
@RequestMapping("/storage")
public class StorageHandler extends BaseHandler<Storage, Long> {
	@Autowired
	private StorageService storageService;
	@Autowired
	private ProductService productService;
	@ModelAttribute()
	public void model(@RequestParam(value="productId",required=false)Long id,
			Map<String,Object> map){
		if(id!=null){
		map.put("product", productService.getById(id));
		}else{
			map.put("products", productService.findAll2());
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Storage storage,@RequestParam("incrementCount") Integer incrementCount) {
		Long id = storage.getId();
		storageService.update(storage,incrementCount,id);
		return "redirect:/storage/list";

	}
}
