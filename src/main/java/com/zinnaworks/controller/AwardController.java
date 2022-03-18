package com.zinnaworks.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zinnaworks.svc.AdminService;
import com.zinnaworks.svc.AwardService;
import com.zinnaworks.vo.Award;
import com.zinnaworks.vo.CompensationVo;
import com.zinnaworks.vo.Group;
import com.zinnaworks.vo.Member;

@Controller
public class AwardController {

	@Autowired
	AwardService awardService;

	@Autowired
	AdminService adminService;

	@GetMapping("/main")
	public String mainPageForm(Model model, HttpServletResponse res) {
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("main page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "zinna/main";
	}

	@GetMapping("/votList")
	public String votingForm(Model model, HttpServletResponse res) {
		System.out.println("vot page !!");
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("main page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "/zinna/votList";
	}

	@GetMapping("/voteDetail")
	public String votDetailForm(Model model, HttpServletResponse res) {
		System.out.println("/voteDetail page !!");
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("main page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "/zinna/voteDetail";
	}

	@GetMapping("/voteCompensationList")
	public String voteCompensationListForm(Model model, HttpServletResponse res) {
		System.out.println("vot page !!");
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("voteResultList page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
		}

		return "/zinna/voteCompensationList";
	}

	@GetMapping("/voteCompensation")
	public String voteCompensationForm(Model model, HttpServletResponse res) {
		System.out.println("vot page !!");
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("voteResultList page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
		}

		return "/zinna/voteCompensation";
	}

	@GetMapping("/vote")
	public String voteForm(Model model, HttpServletResponse res) {
		System.out.println("vot page !!");
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("main page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "/zinna/vote";
	}

	@GetMapping("/votCreate")
	public String votCreateForm(Model model, HttpServletResponse res) {
		System.out.println("vot page !!");
		Object currentAuth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("main page !!" + currentAuth);
		UserDetails principal = null;
		try {
			if (!(currentAuth instanceof String)) {
				principal = (UserDetails) currentAuth;
				System.out.println("main principal !!" + principal);
				// 투표현황을 select 해서 가지고와서 뿌려줄 생각;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "/zinna/votCreate";
	}

//	@GetMapping("/admin")
//	public String mainPageForm(Model model) {
//		System.out.println("admin page");
//		model.addAttribute("Group", new Group());
//		return "/zinna/adminGroupManage";
//	}
//	
//	@GetMapping("/admin/cate")
//	public String catePageForm(Model model) {
//		return "/zinna/adminCateManage";
//	}
//	
//	@GetMapping("/admin/userInfo")
//	public Object userInfoPageForm(Model model) {
//		return "/zinna/adminUserInfoManage";
//	}

	@PostMapping("/api/main")
	@ResponseBody
	public Map<String, Object> awardMain(@RequestBody Map<String, Object> obj) throws Exception {

		System.out.println("/api/main gogo");
		System.out.println("/api/main = " + obj.toString());

		Map<String, Object> result = new HashMap<>();

		result = awardService.awardList(obj);

		result.put("result", "success");

		return result;
	}
	@PostMapping("/api/main/awardList")
	@ResponseBody
	public Map<String, Object> awardList(@RequestBody Map<String, Object> obj) throws Exception {

		System.out.println("/api/main gogo");
		System.out.println("/api/main = " + obj.toString());

		Map<String, Object> result = new HashMap<>();

		result = awardService.awardUserList(obj);

		result.put("result", "success");

		return result;
	}
	
	@PostMapping("/api/main/historyList")
	@ResponseBody
	public Map<String, Object> historyList(@RequestBody Map<String, Object> obj) throws Exception {

		System.out.println("/api/historyList gogo");
		System.out.println("/api/historyList = " + obj.toString());

		Map<String, Object> result = new HashMap<>();

		result = awardService.historyList(obj);

		result.put("result", "success");

		return result;
	}
	
	@PostMapping("/api/awardMainList")
	@ResponseBody
	public Map<String, Object> awardMainList(@RequestBody Map<String, Object> obj) throws Exception {

		System.out.println("/api/main gogo");
		System.out.println("/api/awardMainList gogo = " + obj.toString());
		
		Map<String, Object> result = new HashMap<>();
		
		result = awardService.awardMainList(obj);

		result.put("result", "success");

		return result;
	}
	
	@PostMapping("/api/mainPage")
	@ResponseBody
	public Map<String, Object> awardMainPage(@RequestBody String cnt) throws Exception {

		System.out.println("/api/main gogo");
		System.out.println("/api/mainPage = " + cnt);

		Map<String, Object> result = new HashMap<>();
		
		int page =Integer.parseInt(cnt);

		result = awardService.awardListPage(page);

		result.put("result", "success");

		return result;
	}
	
	

	@PostMapping("/api/voteSelectList")
	@ResponseBody
	public Map<String, Object> awardVote(@RequestBody String vote_no) throws Exception {
		Map<String, Object> result = new HashMap<>();
		// 투표 메서드
		System.out.println("/api/vote gogo");
		System.out.println("vot_no =" + vote_no);

		if (vote_no.equals("")) {
			result.put("result", "fail");
		} else {
			result = awardService.voteMemberList(vote_no);
			result.put("result", "success");
		}

		return result;
	}

	@PostMapping("/api/votCreate")
	@ResponseBody
	public Map<String, Object> voteCreate(@RequestBody Map<String, Object> obj) throws Exception {
		// 투표개설
		System.out.println("/api/voteCreate gogo");
		System.out.println("obj =" + obj.toString());

		Map<String, Object> result = new HashMap<>();

		result = awardService.voteCreateInfo(obj);

		return result;
	}

	@PostMapping("/api/votCreate/votSelectId")
	@ResponseBody
	public Map<String, Object> voteCreateSelectId() throws Exception {

		System.out.println("/api/voteCheckId gogo");

		// 실제론 Map - List - Json 순으로 감싸야 한다.

		Map<String, Object> result = new HashMap<>();

		result = awardService.voteSelectId();

		return result;
	}

	@PostMapping("/api/votCreate/voselectGrp")
	@ResponseBody
	public Map<String, Object> voteCreateSelectGrp() throws Exception {

		System.out.println("/api/voteCheckGrp gogo");

		// 실제론 Map - List - Json 순으로 감싸야 한다.

		Map<String, Object> result = new HashMap<>();

		result = awardService.voteSelectGrp();

		return result;
	}

	@PostMapping("/api/votCreate/votSelectGrpInfo")
	@ResponseBody
	public Map<String, Object> voteCreateSelectGrpInfo(@RequestBody String grp_no) throws Exception {

		System.out.println("/api/votSelectGrpInfo gogo = " + grp_no);

		// 실제론 Map - List - Json 순으로 감싸야 한다.

		Map<String, Object> result = new HashMap<>();

		result = awardService.voteSelectGrpMember(grp_no);

		return result;
	}

	@PostMapping("/api/vote")
	@ResponseBody
	public Map<String, Object> vote(@RequestBody Map<String, Object> obj) throws Exception {
		// 투표개설
		System.out.println("/api/vote gogo");
		System.out.println("obj =" + obj.toString());
		System.out.println("obj =" + obj.get("votedCd"));

		Map<String, Object> result = new HashMap<>();

		if (obj == null || obj.isEmpty() || obj.get("votedCd") == null) {
			System.out.println("if test = ");
			result.put("result", false);
			return result;
		}
		result = awardService.voteUpdate(obj);

		return result;
	}

	@PostMapping("/api/voteRate")
	@ResponseBody
	public Map<String, Object> voteRate(@RequestBody String userId) throws Exception {
		// 투표개설
		System.out.println("/api/voteRate gogo");
		System.out.println("/api/voteRate gogo = " + userId);

		Map<String, Object> result = new HashMap<>();

		result = awardService.voteRate(userId);

		return result;
	}

	@PostMapping("/api/voteSelectDetail")
	@ResponseBody
	public Map<String, Object> voteSelectDetail(@RequestBody Map<String, Object> obj) throws Exception {
		Map<String, Object> result = new HashMap<>();
		// 투표 메서드
		System.out.println("/api/voteSelectDetail gogo");
		System.out.println("obj =" + obj.toString());

		if (obj.get("userId") != null) {
			result = awardService.votSelectDetail(obj);
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		return result;
	}

	@PostMapping("/api/voteUpdateDetail")
	@ResponseBody
	public Map<String, Object> voteUpdateDetail(@RequestBody Map<String, Object> obj) throws Exception {
		Map<String, Object> result = new HashMap<>();
		// 투표 메서드
		System.out.println("/api/voteSelectDetail gogo");
		System.out.println("obj =" + obj.toString());

		if (obj.get("title") != null) {
			result = awardService.voteUpdateDetail(obj);
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		return result;
	}

	@PostMapping("/api/voteCompensationList")
	@ResponseBody
	public Map<String, Object> compensationList(@RequestBody Map<String, Object> obj) throws Exception {


		Map<String, Object> result = new HashMap<>();
		
		result = awardService.compensationList(obj);

		return result;
	}

	@PostMapping("/api/voteEndStatus")
	@ResponseBody
	public Map<String, Object> voteEndStatus(@RequestBody String vote_no) throws Exception {

		Map<String, Object> result = new HashMap<>();
		// 투표 메서드
		System.out.println("/api/voteEndStatus gogo");
		System.out.println("vote_no =" + vote_no);

		if (vote_no.equals("")) {
			result.put("result", "fail");
			return result;
		} else {
			result = awardService.voteEndStatus(vote_no);
		}
		return result;
	}

	@PostMapping("/api/voteDelete")
	@ResponseBody
	public Map<String, Object> voteDelete(@RequestBody String vote_no) throws Exception {

		Map<String, Object> result = new HashMap<>();
		// 투표 메서드
		System.out.println("/api/voteDelete gogo");
		System.out.println("vote_no =" + vote_no);

		if (vote_no.equals("")) {
			result.put("result", "fail");
			return result;
		} else {
			result = awardService.voteDelete(vote_no);
		}
		return result;
	}

	@PostMapping("/api/giftSelectInfo")
	@ResponseBody
	public Map<String, Object> giftSelectInfo() throws Exception {

		Map<String, Object> result = new HashMap<>();
		// 투표 메서드
		System.out.println("/api/giftSelectInfo gogo");

		result = awardService.giftSelectInfo();
		return result;
	}

	@PostMapping("/api/giftFinalUpdate")
	@ResponseBody
	public Map<String, Object> giftFinalUpdate(HttpServletRequest req) throws Exception {

		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		String filePath = "";
		// 투표 메서드
		System.out.println("vote_id = " + req.getParameter("vote_id"));
		data.put("vote_no", req.getParameter("vote_id"));
		data.put("gift", new String(req.getParameter("result_money")));
		data.put("result_date", new String(req.getParameter("result_date")));
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
		MultipartFile file = request.getFile("fileUpload");
		System.out.println("/api/giftFinalUpdate = " + file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		try {
			String path = "/Users/donghee/Documents/zinnaAward/src/main/resources/static/uploadFile/";
			filePath = Paths.get(path, fileName).toString();
			System.out.println("filePath = " + filePath);
			// save file local
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			stream.write(file.getBytes());
			stream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result.put("result", "Pfail");
			return result;
		}
		String picturePath = "../../uploadFile/" + fileName;
		data.put("photoNm", picturePath);
		System.out.println("data = " + data.toString());
		result = awardService.giftFinalUpdate(data);
		
		return result;
	}


}
