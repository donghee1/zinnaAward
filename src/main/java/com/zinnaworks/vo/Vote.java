package com.zinnaworks.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Vote {
	
	private String votId; //투표 아이디
	private String userId;
	private String voterNm;
	private Long votResultId;
	private String votYn;
	private String votDt;
	private Integer voteGrade;
}
