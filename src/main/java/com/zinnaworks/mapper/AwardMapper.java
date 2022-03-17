package com.zinnaworks.mapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zinnaworks.vo.Award;
import com.zinnaworks.vo.CompensationVo;
import com.zinnaworks.vo.Member;
import com.zinnaworks.vo.RateMember;
import com.zinnaworks.vo.Vote;
import com.zinnaworks.vo.VoteMember;

@Mapper
public interface AwardMapper {
	

	public int insertVotInfo(Member member);

	public List<Object> selectVotList(Map<String, Object> map);

	public String selectVotId();
	
	public List<Object> selectVotGrp();

	public List<Object> voteSelectGrpMember(String grp_no);

	public int voteCreateInfo(Award voteInfo);

	public int voteCreateItem(List<VoteMember> awardMember);
	
	public int voteCreateRate(List<RateMember> rateMemberList);

	public List<Object> voteMemberList(String vote_no);

	public int voteInsert(List<Vote> voteList);

	public String selectUserId(Map<String, Object> vote);

	public String selectCheckNm(String param1, String param2);

	public List<Object> voteCheckRate(String vote_no);

	public List<Object> votSelectDetail(String vote_no);

	public List<Object> voteRateList(String vote_no);

	public List<Object> compensationList(Map<String, Object> obj);

	public int voteUpdateDetail(Map<String, Object> obj);

	public int checkEndDt(String vote_id);

	public List<Object> selectResultInfo(String vote_id);

	public List<Object> selectResultMember(String vote_id);

	public int resultIdInsert(String param1, String param2);

	public int voteEndStatus(String vote_no);

	public int voteDelete(String vote_no);

	public List<Object> giftSelectInfo();

	public int giftFinalUpdate(Map<String, Object> obj);

	public List<Object> selectVotListPage(Map<String, Object> map);

	public List<Object> awardMainList(Map<String, Object> map);

	public List<Object> checkAwardList();

	public List<Object> awardUserList(Map<String, Object> map);

	public List<Object> awardAdminList(Map<String, Object> map);

	public List<Object> historyList(Map<String, Object> map);

	public List<Object> awardMainAdminList(Map<String, Object> map);

	public Map<String, Object> checkResultMember(Map<String, Object> map);

	public List<Object> voteSelectGrpAllMember();

	public int voteUpdate(Map<String, Object> obj);

	public Map<String, Object> checkVoteChoice(Map<String, Object> obj);

	public Map<String, Object> awardMainListSize(String user);

	public Map<String, Object> awardMainAdminListSize();

	
	

}
