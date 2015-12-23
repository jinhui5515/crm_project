package com.atguigu.crm.handler.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atuigu.crm.entity.Dict;
@Controller
@RequestMapping("/dict")
public class DictHandler extends BaseHandler<Dict, Long> {

}
