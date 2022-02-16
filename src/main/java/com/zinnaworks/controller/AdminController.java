package com.zinnaworks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zinnaworks.svc.AdminService;
import com.zinnaworks.vo.Group;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	
	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
	
	@GetMapping("/admin")
	public String mainPageForm(Model model) {
		
		model.addAttribute("Group", new Group());
		return "/zinna/adminGroupManage";
	}
	
	@PostMapping("/api/admin")
	@ResponseBody
	public Object groupList(Model model) {
		
		data = adminService.groupList();
		return data;
	}
	
	@PostMapping("/api/admin.do")
	public @ResponseBody Object insertGroupInfoCD(@RequestBody Group ajaxData) throws ParseException {
		data = adminService.lowGroupsList(ajaxData.getGrpCd(), ajaxData.getPreGrpCd(), ajaxData.getGrpNm(), ajaxData.getPreGrpNm());
		return data;
	}
	
	@GetMapping("/admin/cate")
	public String catePageForm(Model model) {
		return "/zinna/adminCateManage";
	}
	
	@PostMapping("/api/admin/cate")
	@ResponseBody
	public Object cateList(Model model) {
		
		data = adminService.cateList();
		return data;
	}
	
	
	@GetMapping("/admin/userInfo")
	public Object userInfoPageForm(Model model) {
		return "/zinna/adminUserInfoManage";
	}
	
	
	
}
