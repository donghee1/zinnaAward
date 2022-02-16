package com.zinnaworks.repo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zinnaworks.mapper.CompensationMapper;
import com.zinnaworks.vo.CompensationVo;

@Repository
public class CompensationRepository {

	@Autowired
	CompensationMapper compensationMapper;

	// 보상관리 리스트
	public ArrayList<CompensationVo> compensationList(CompensationVo compensationVo) {
		ArrayList<CompensationVo> compensationList = compensationMapper.compensationList(compensationVo);
		return compensationList;
	}

}
