package com.zinnaworks.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.MailAuth;
import com.zinnaworks.vo.Member;

@Mapper
public interface MemberMapper {

	public int insertMemberInfo(Member member);

	public Member selectLoginInfo(Member member);
	
	public Member selectCheckById(String username);
	
	public Member loadUserByUsername(String username);

	public int updateMemberPwd(Member member);
	
	public int mergeUpdateAuthInfo(Mail mail);

	public int mergeInsertAuthInfo(MailAuth mail);

	public MailAuth selectAuthInfo(String email);
	
}
