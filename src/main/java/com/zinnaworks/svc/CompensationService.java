package com.zinnaworks.svc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.CompensationRepository;
import com.zinnaworks.vo.CompensationVo;

@Service
public class CompensationService {
	@Autowired
	CompensationRepository compensationRepository;

	public ArrayList<CompensationVo> compensationList(CompensationVo compensationVo) {
		ArrayList<CompensationVo> compensationList = compensationRepository.compensationList(compensationVo);
		return compensationList;
	}
	
}
