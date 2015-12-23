package com.atguigu.crm.handler.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atuigu.crm.entity.Product;
@Controller
@RequestMapping("/product")
public class ProductHandler extends BaseHandler<Product, Long> {

}
