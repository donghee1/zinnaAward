package com.zinnaworks.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.MemberRepository;
import com.zinnaworks.vo.Member;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	MemberRepository memberRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username = " + username);
		Member member = memberRepository.findById(username);
		System.out.println("member ?? " + member);
		return member;

	}
	
//	public UserDetails CheckLogin(String username, String pwd) throws Exception {
//		
//		Member member = new Member();
//		member.setEmail(username);
//		member.setPwd(passwordEncoder().encode(pwd));
//		
//		System.out.println("memberPwd = " + member.getPwd());
//		
//		Member result = memberRepository.checkLogin(member);
//		
//		
//		if(result != null) {
//			throw new UsernameNotFoundException("username " + result.getEmail() + "not Found !!");
//		}
//		
//		return result;
//	}
	
	public boolean InsertMemberInfo(Member memberVo) throws UsernameNotFoundException{
		//회원가입 
		//1. 가입할 이메일이 있는지 확인 -> 없으면 insert
		
		System.out.println("test Service = " + memberVo.toString());
		Member member = memberRepository.findById(memberVo.getEmail());
		if(member == null) {
			int saveMember = memberRepository.saveMember(memberVo);
			if(saveMember > 0) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean updateMemberPwd(Member member) throws Exception {
		System.out.println("service member pwd = " + member.getPwd());
		int update = memberRepository.updateMemberPwd(member);
		if(update < 0) {
			return false;
		}
		return true;
	}


}