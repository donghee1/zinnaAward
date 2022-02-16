package com.zinnaworks.vo;

import lombok.Data;

/**
 *
 * 1/26 추가
 * Auther : Backsira
 * CompensationVo : 보상 관련 VO
 *
 **/
@Data
public class CompensationVo {

	private String votId; // 투표id (게시물번호)
	private String votNm; // 투표명 (게시물이름)
	private String userId; // 게시자 (ex.관리자)
	private int status; // 상태 (0 : 진행중, 1 : 종료)
	private int participantCnt; // [별칭]투표참여완료자 카운트
	private String startDt; // 시작일
	private String endDt; // 만료일
	private String resultId; // 수상자
	private String personCnt; // 투표항목 (이걸로 후에 투표항목인원에 대한 카운트를 받아올 것임)
}
