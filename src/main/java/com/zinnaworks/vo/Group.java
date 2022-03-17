package com.zinnaworks.vo;

import lombok.Data;

@Data
public class Group {
	
	private String grpCd; // 부서코드
	private String grpNm; // 부서명
	private String preGrpCd; // 상위부서코드
	private String preGrpNm; // 상위부서명

}
