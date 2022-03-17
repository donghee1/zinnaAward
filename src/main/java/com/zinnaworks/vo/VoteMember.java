package com.zinnaworks.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteMember {
	
	private String votId; //투표 아이디
	private String votNm;
	private Long votCd;
	private String memberId;
	private int voteGrade;
}
