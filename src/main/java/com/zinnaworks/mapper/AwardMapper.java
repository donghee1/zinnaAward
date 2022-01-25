package com.zinnaworks.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.MailAuth;
import com.zinnaworks.vo.Member;

@Mapper
public interface AwardMapper {
	
	public Map<String, Object> selectVotList(Map<String, Object> map);

	public int insertVotInfo(Member member);

}
