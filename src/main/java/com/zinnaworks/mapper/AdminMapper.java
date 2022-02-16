package com.zinnaworks.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zinnaworks.vo.Group;
import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.MailAuth;
import com.zinnaworks.vo.Member;

@Mapper
public interface AdminMapper {
	public List<Map<String, Object>> selectGroupInfo();
	
	public List<Map<String, Object>> selectLowGroups(String grpCd);

	public Object insertGroupInfoCD(@Param("grpCd")String grpCd, @Param("grpNm")String grpNm, @Param("preGrpCd")String preGrpCd, @Param("preGrpNm")String preGrpNm);
	
	public List<Map<String, Object>> selectCateList();
}
