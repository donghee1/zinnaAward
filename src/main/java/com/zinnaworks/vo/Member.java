package com.zinnaworks.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class Member implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String passWd;
	private String userNm;
	private int grpCd; // 소속 팀
	private String entryDt;// 입사일
	private String phoneNum; //핸드폰 번호
	private int gradeCd; // 권한 1: 관리자, 2:부서장, 3:사원
	private int useYn; // 권한 1: 권한없음, 2:사용중, 3:가입대기
	
//	@Builder
//	public Member(String email, String pwd, String name, String EntryDate, String phone, String role) {
//		super();
//		this.email = email;
//		this.pwd = pwd;
//		this.name = name;
//		this.EntryDate = EntryDate;
//		this.phone = phone;
//		this.role = role;
//	}
	
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
//		collectors.add(new SimpleGrantedAuthority(role));
//		return collectors;
//	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getPassWd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
