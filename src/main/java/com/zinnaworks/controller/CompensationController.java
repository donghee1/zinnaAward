package com.zinnaworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zinnaworks.svc.CompensationService;
import com.zinnaworks.vo.CompensationVo;

@Controller
public class CompensationController {
	
	private static final Logger logger = LoggerFactory.getLogger(CompensationController.class);
	
	@Autowired
	CompensationService compensationService;
	
	@GetMapping("/compensation")
	public String compensationHome() {
		
		return "/zinna/compensationManage";
	}
	
	
	@PostMapping("/api/compensationList")
	@ResponseBody
	public Map<String, Object> compensationList() throws Exception {
		
		CompensationVo compensationVo = new CompensationVo();
		
		Map<String, Object> map = new HashMap<>();
		ArrayList<CompensationVo> compensationList = compensationService.compensationList(compensationVo);
		
		logger.info(">>>>> compensationList.size() :: " + compensationList.size());
		logger.info(">>>>> compensationList :: " + compensationList);
		
		if(compensationList.size() <= 0) {
			map.put("compensationList", 0);
		}else {
			map.put("compensationList", compensationList);	
		}
	
		return map;
	}
	
	
}
