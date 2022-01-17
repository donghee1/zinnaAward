package com.zinnaworks.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.Member;

@Mapper
public interface MemberMapper {

	public int insertMemberInfo(Member member);

	public Member selectLoginInfo(Member member);
	
	public Member selectCheckById(String username);
	
	public Member loadUserByUsername(String username);

	public int updateMemberPwd(Member member);
	
	public int mergeUpdateAuthInfo(Mail mail);

	public int mergeInsertAuthInfo(Mail mail);
	
}
