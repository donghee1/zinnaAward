package com.zinnaworks.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;

@Data
public class Member implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int user_no;
	private String email;
	private String pwd;
	private String name;
	private String team;
	private String EntryDate;
	private String phone;
	private String role;
	
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
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
		collectors.add(new SimpleGrantedAuthority(role));
		return collectors;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getPwd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getEmail();
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

	
}
