package com.zinnaworks.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zinnaworks.mapper.MemberMapper;
import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.MailAuth;
import com.zinnaworks.vo.Member;

@Repository
public class MemberRepository {

	@Autowired
	MemberMapper memberMapper;

	public Member findById(String username) {

		Member member = memberMapper.selectCheckById(username);

		return member;
	}

	public int saveMember(Member memberVo) {
		return memberMapper.insertMemberInfo(memberVo);
	}

	public Member checkLogin(Member memberVo) {

		return memberMapper.selectLoginInfo(memberVo);
	}

	public int updateMemberPwd(Member member) {
		return memberMapper.updateMemberPwd(member);
	}

	public int updateAuthInfo(Mail mail) {
		return memberMapper.mergeUpdateAuthInfo(mail);
	}

	public int mergeInsertAuthInfo(MailAuth mail) {
		return memberMapper.mergeInsertAuthInfo(mail);
	}

	public MailAuth selectAuthInfo(String email) {
		return memberMapper.selectAuthInfo(email);
	}


	public Map<String, Object> updateSignUpInfo(String email) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		int useYn = 2;
		data.put("email", email);
		data.put("useYn", useYn);
		
		
		int i = memberMapper.updateSignUpInfo(data);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> checkAuthLogin(String email) {
		
		Map<String, Object> data = new HashMap<>();
		
		Map<String, Object> result = new HashMap<>();
		
		
		String userId = email + "@zinnaworks.com";
		
		data = memberMapper.checkAuthLogin(userId);
		
		if(data == null) {
			System.out.println("result null = ");
			result.put("result", "NoId");
			return result;
		}else {
			System.out.println("result = " + result.toString());
			
			int i = 0; 
			
			userId = (String) data.get("USE_YN");
			
			i = Integer.parseInt(userId);
			System.out.println("i = " + i);
			
			if(i == 3) {
				result.put("result", "fail");
			}else {
				result.put("result", "success");
			}
			
		}
		return result;
	}

}
