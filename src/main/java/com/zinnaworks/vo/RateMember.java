package com.zinnaworks.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RateMember {
	
	private String votId; //투표 아이디
	private String userId;
	private Long votDt;
	private String voteResultId;
	private int voteGrade;
}
