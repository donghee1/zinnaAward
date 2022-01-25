package com.zinnaworks.vo;

import lombok.Data;

@Data
public class Award {
	
	private String votId; //투표 아이디
	private int votTp; //투표유형
	private int votGrp; //투표대상
	private String votNm; //투표명
	private String startDt; //시작일
	private String endDt; //종료일
	private String startMMS; //시작알림
	private String endMMS; //만료알림
	private int  status; //상태값
	private String votItem; //투표항목
	
	
}
