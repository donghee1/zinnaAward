package com.zinnaworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zinnaworks.svc.AwardService;
import com.zinnaworks.vo.Award;

@Controller
public class AwardController {

	@Autowired
	AwardService awardService;

	@GetMapping("/main")
	public String mainPageForm(Model model, HttpServletResponse res) {
		System.out.println("main page !!");
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

		return "/zinna/main";
	}
	
	@GetMapping("/vot")
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
	
	@GetMapping("/votDetail")
	public String votDetailForm(Model model, HttpServletResponse res) {
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

		return "/zinna/votDetail";
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

	@PostMapping("/api/main")
	@ResponseBody
	public Map<String, List<Object>> awardMain(Map<String, Object> map) throws Exception {

		System.out.println("/api/main gogo");

		// 실제론 Map - List - Json 순으로 감싸야 한다.

		Map<String, Object> data = new HashMap<>();
		Map<String, Object> data1 = new HashMap<>();
		List<Object> list = new ArrayList<>();

		Map<String, List<Object>> result = new HashMap<String, List<Object>>();

		Award award = new Award();

		award.setVotId("22012101");
		award.setVotTp(001);
		award.setVotGrp(12100);
		award.setVotNm("22-02 ICT인프라 어워드");
		award.setStartDt("22012113");
		award.setEndDt("22012213");
		award.setStartMMS("N");
		award.setEndMMS("N");
		award.setStatus(001);
		award.setVotItem("1^이정재|2^박문수|3^한동희|4^이지연|5^백시라");

		data.put("votId", award.getVotId());
		data.put("votTp", award.getVotTp());
		data.put("votGrp", award.getVotGrp());
		data.put("votNm", award.getVotNm());
		data.put("startDt", award.getStartDt());
		data.put("endDt", award.getEndDt());
		data.put("startMMS", award.getStartMMS());
		data.put("endMMS", award.getEndMMS());
		data.put("status", award.getStatus());
		data.put("votItem", award.getVotItem());

		Award award2 = new Award();
		award2.setVotId("22012201");
		award2.setVotTp(001);
		award2.setVotGrp(12200);
		award2.setVotNm("22-02 ICT플랫폼 어워드");
		award2.setStartDt("22012213");
		award2.setEndDt("22012313");
		award2.setStartMMS("N");
		award2.setEndMMS("N");
		award2.setStatus(001);
		award2.setVotItem("1^변종성|2^김병현|3^한예지|4^김영수|5^이준표");

		data1.put("votId", award2.getVotId());
		data1.put("votTp", award2.getVotTp());
		data1.put("votGrp", award2.getVotGrp());
		data1.put("votNm", award2.getVotNm());
		data1.put("startDt", award2.getStartDt());
		data1.put("endDt", award2.getEndDt());
		data1.put("startMMS", award2.getStartMMS());
		data1.put("endMMS", award2.getEndMMS());
		data1.put("status", award2.getStatus());
		data1.put("votItem", award2.getVotItem());
		
		list.add(0, data);
		list.add(1, data1);

		result.put("body", list);

		return result;
	}

}
