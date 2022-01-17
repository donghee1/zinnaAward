package com.zinnaworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zinnaworks.svc.MailService;
import com.zinnaworks.svc.MemberService;
import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.MailAuth;
import com.zinnaworks.vo.Member;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MailService mailService;

	@GetMapping("/signUp")
	public String signUpForm(Model model) {
		model.addAttribute("Member", new Member());
		return "signUp";
	}

	@GetMapping("/login")
	public String loginForm(Model model) {
		System.out.println("login page!");
		model.addAttribute("Member", new Member());
		return "login";
	}

	// 업데이트 전 메일 전송
	@GetMapping("/mail")
	public String pwdmailSenderForm(Model model) {
		System.out.println("login update Page!");
		model.addAttribute("Member", new Mail());
		return "mail";
	}

	// 업데이트 전 메일 전송
	@GetMapping("/update")
	public String pwdUpdateForm(Model model) {
		System.out.println("login update Page!");
		model.addAttribute("Member", new Member());
		return "update";
	}

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

		return "main";
	}

	@ResponseBody
	@PostMapping("/api/signUp")
	public Map<String, Object> signUp(@RequestBody Map<String, Object> memberInfo, Model model) throws Exception {

		Map<String, Object> result = new HashMap<>();
		Member memberVo = new Member();
		if ("ADMIN".equals(memberInfo.get("email"))) {
			memberVo.setRole("ROLE_ADMIN");
		} else if ("MANAGER".equals(memberInfo.get("email"))) {
			memberVo.setRole("ROLE_MANAGER");
		} else {
			memberVo.setRole("ROLE_USER");
		}
		memberVo.setEmail((String) memberInfo.get("email") + "@zinnaworks.com");
		memberVo.setPwd((String) passwordEncoder(memberInfo.get("pwd").toString()));
		memberVo.setName((String) memberInfo.get("name"));
		memberVo.setTeam((String) memberInfo.get("team"));
		memberVo.setEntryDate((String) memberInfo.get("entryDate"));
		memberVo.setPhone((String) memberInfo.get("phone"));

		if (memberVo.getEmail().toString().equals("") || memberVo.getEmail().toString().equals("null")) {
			result.put("result", "null");
			return result;
		}

		boolean data = memberService.InsertMemberInfo(memberVo);

		result.put("result", data);

		return result;
	}

	@ResponseBody
	@PostMapping("/api/login")
	public Map<String, Object> login(Model model, @RequestBody Map<String, Object> map, HttpServletResponse response)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		String username = (String) map.get("email");
		String pwd = (String) map.get("pwd");

		UserDetails data = memberService.loadUserByUsername(username);

		if (data != null) {
			if (passwordMatchesEncoder(pwd, data.getPassword())) {
				// true
				List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(1);
				roles.add(new SimpleGrantedAuthority(data.getAuthorities().toString()));
				User user = new User(username, "", roles);
				Authentication auth = new UsernamePasswordAuthenticationToken(user, null, roles);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// 비밀번호가 틀릴 경우
				// result 를 false 줘서 처리
				result.put("result", false);
				return result;
			}
		} else {
			// data가 null 계정이 없다
			// 없다는 알림창을 띄운다.
			result.put("result", data);
			return result;
		}
		result.put("result", "success");
		result.put("data", data);

		return result;

	}

	@ResponseBody
	@PostMapping("/api/mail")
	public Map<String, Object> mailSender(Model model, @RequestBody Map<String, Object> map,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String emailAddress = (String) map.get("email") + "@zinnaworks.com";
		Boolean data;
		if (emailAddress == null || emailAddress.equals("")) {
			result.put("result", false);
			return result;
		} else {
			//Mail mail = new Mail();
			MailAuth mail = new MailAuth();
			mail.setUserId(emailAddress);
			data = mailService.mailSend(mail);
		}
		result.put("result", data);

		return result;
	}

	@ResponseBody
	@PostMapping("/api/update")
	public Map<String, Object> PasswordUpdate(Model model, @RequestBody Map<String, Object> map,
			HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String email = (String) map.get("email");
		String code = (String) map.get("code");
		String pwd = (String) map.get("pwd");
		boolean data = false;

		Member member = new Member();
		member.setEmail(email+"@zinnaworks.com");
		member.setPwd(passwordEncoder(pwd));

		if (email == null || email.equals("") || pwd == null || pwd.equals("")) {
			result.put("result", false);
			return result;
		} else if (!code.equals("") && code != null) {
			// 메일로 전송한 인증코드 6자리 비교
			// 코드기 맞는 경우 update 진행
				data = memberService.updateMemberPwd(member, code);
		}
		result.put("result", data);
		return result;
	}

	public String passwordEncoder(String pwd) {
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		return pwEncoder.encode(pwd);
	}

	public boolean passwordMatchesEncoder(String pwd, String beforePwd) {
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		return pwEncoder.matches(pwd, beforePwd);
	}
}
