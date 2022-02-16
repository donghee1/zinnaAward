package com.zinnaworks.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.zinnaworks.vo.CompensationVo;

@Mapper
public interface CompensationMapper {

	public ArrayList<CompensationVo> compensationList(CompensationVo compensationVo);
}
