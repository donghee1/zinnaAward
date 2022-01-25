package com.zinnaworks.svc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.MemberRepository;

@Service
public class AwardService {

	@Autowired
	MemberRepository memberRepository;

	public Map<String, Object> awardList(){
		
		Map<String, Object> result = new HashMap<>();
		
		return result;
	}

	
	
}