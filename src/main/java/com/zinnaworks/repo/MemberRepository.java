package com.zinnaworks.repo;

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
		// TODO Auto-generated method stub
		return memberMapper.mergeInsertAuthInfo(mail);
	}

	public MailAuth selectAuthInfo(String email) {
		// TODO Auto-generated method stub
		return memberMapper.selectAuthInfo(email);
	}

	

}
