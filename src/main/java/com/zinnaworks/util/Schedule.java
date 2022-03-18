package com.zinnaworks.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zinnaworks.svc.AwardService;

@Component
public class Schedule {
	
	
	@Autowired
	AwardService awardService;

	@Scheduled(cron = "0 0/10 * * * ?")
	public void cronRun() {
		
		try {
			awardService.checkAwardList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
