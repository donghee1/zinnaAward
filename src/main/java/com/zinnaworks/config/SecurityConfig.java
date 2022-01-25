package com.zinnaworks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zinnaworks.svc.MemberService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private MemberService memberService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/asset/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity http)throws Exception{
		System.out.println("http Security");
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/signUp", "/login", "/mail", "update").permitAll()
			.antMatchers("/zinna/**").hasAnyRole("MANAGER", "USER")
			.antMatchers("/admin/**").hasAnyRole("admin")
			.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/main")
					.permitAll()
					.failureUrl("/login?error=true")
	                .and()
	             .logout()
	                 .permitAll()
	                 .logoutUrl("/logout")
	                 .logoutSuccessUrl("/")
	                 .and()
	             .exceptionHandling()
	                 .accessDeniedPage("/error"); // 권한이 없는 대상이 접속을시도했을 때

		//html iframe 사용을 위한 설정
		http.headers().frameOptions().sameOrigin();
					
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("auth Security");
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
		
	}

}
