package com.zinnaworks.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zinnaworks.svc.AdminService;
import com.zinnaworks.svc.AwardService;
import com.zinnaworks.vo.Group;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;

	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
	
	@GetMapping("/admin")
	public String mainPageForm(Model model) {
		System.out.println("admin page");
		model.addAttribute("Group", new Group());
		return "zinna/adminGroupManage";
	}
	
	@GetMapping("/management")
	public String managementForm(Model model) {
		System.out.println("management page");
		model.addAttribute("Group", new Group());
		return "zinna/adminMemberManage";
	}
	
	@GetMapping("/cate")
	public String catePageForm(Model model) {
		return "zinna/adminCateManage";
	}
	
	@GetMapping("/admin/userInfo")
	public Object userInfoPageForm(Model model) {
		return "zinna/adminUserInfoManage";
	}
	
	@PostMapping("/api/admin")
	@ResponseBody
	public Object groupList(Model model) {
		System.out.println("groupList");
		data = adminService.groupList();
		return data;
	}
	
	@PostMapping("/api/admin.do")
	@ResponseBody
	public Map<String, Object> insertGroupInfoCD(@RequestBody Group ajaxData) throws ParseException {
		List<Map<String, Object>> data = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		data = adminService.lowGroupsList(ajaxData.getGrpCd(), ajaxData.getPreGrpCd(), ajaxData.getGrpNm(), ajaxData.getPreGrpNm());
		
		if(data.get(0).get("GRP_CD").toString().equals("")) {
			result.put("result", "fail");
		}else {
			result.put("result", "success");
		}
		return result;
	}
	
	@PostMapping("/api/grpDeleteInfo")
	@ResponseBody
	public Map<String, Object> grpDeleteInfo(@RequestBody String grpCd) throws ParseException {
		
		Map<String, Object> result = new HashMap<>();
		
		result = adminService.grpDeleteInfo(grpCd);
		
		return result;
	}
	
	@PostMapping("/api/memberSelectInfo")
	@ResponseBody
	public Map<String, Object> memberSelectInfo() throws ParseException {
		
		Map<String, Object> result = new HashMap<>();
		
		result = adminService.memberSelectInfo();
		
		return result;
	}
	
	@PostMapping("/api/memberUpdateInfo")
	@ResponseBody
	public Map<String, Object> memberUpdateInfo(@RequestBody Map<String, Object> obj) throws ParseException {
		
		Map<String, Object> result = new HashMap<>();
		
		System.out.println("obj = " + obj.toString());
		
		
		result = adminService.memberUpdateInfo(obj);
		
		return result;
	}
	
	@PostMapping("/api/memberDeleteInfo")
	@ResponseBody
	public Map<String, Object> memberDeleteInfo(@RequestBody String userId) throws ParseException {
		
		Map<String, Object> result = new HashMap<>();
		
		System.out.println("obj = " + userId.toString());
		
		
		result = adminService.memberDeleteInfo(userId);
		
		return result;
	}
	
	@PostMapping("/api/admin/cate")
	@ResponseBody
	public Map<String, Object> cateList() {
		
		Map<String, Object> result = new HashMap<>();
		
		result = adminService.cateList();
		return result;
	}
	
	@PostMapping("/api/admin/cateDeleteInfo")
	@ResponseBody
	public Map<String, Object> cateDeleteInfo(@RequestBody String productCode) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		result = adminService.cateDeleteInfo(productCode);
		return result;
	}
	
	@PostMapping("/api/admin/cateNewCodeInfo")
	@ResponseBody
	public Map<String, Object> cateNewCodeInfo() throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		result = adminService.cateNewCodeInfo();
		return result;
	}
	
	@PostMapping("/api/admin/cateCodeInsertInfo")
	@ResponseBody
	public Map<String, Object> cateCodeInsertInfo(@RequestBody Map<String, Object> obj) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		result = adminService.cateCodeInsertInfo(obj);
		return result;
	}
	
}
