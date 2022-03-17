package com.zinnaworks.vo;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Award {
	
	private String votId; //투표 아이디
	private String memberId; //투표 아이디
	private String votTyp; //투표유형
	private int votGrp; //투표대상
	private String votNm; //투표명
	private String votDesc; //투표설명
	private String startDt; //시작일
	private String endDt; //종료일
	private int startMMS; //시작알림
	private int endMMS; //만료알림
	private int  status; //어워드상태값
	private String userId; //투표개설자
	private String reward; //투표항목
	private String rewardDt;
	private String photoNm;
	private String autoHistory;
	
}
