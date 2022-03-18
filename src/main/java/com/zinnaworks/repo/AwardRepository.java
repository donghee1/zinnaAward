package com.zinnaworks.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zinnaworks.mapper.AwardMapper;
import com.zinnaworks.vo.Award;
import com.zinnaworks.vo.CompensationVo;
import com.zinnaworks.vo.RateMember;
import com.zinnaworks.vo.Vote;
import com.zinnaworks.vo.VoteMember;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Repository
public class AwardRepository {

	@Autowired
	AwardMapper awardMapper;

	public List<Object> selectVotList(Map<String, Object> obj) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<>();

		int page = (int) obj.get("pageCnt");
		System.out.println("pageCnt = " + page);
		int status = (int) obj.get("status");
		System.out.println("status = " + status);
		int before = 0;
		int after = 0;
		int contentSize = 6;

		if (page == 0 || page == 1) {
			before = 0;
			after = contentSize;
		} else if (page > 1) {
			before = (page - 1) * contentSize;
			after = contentSize;
		}
		map.put("status", status);
		map.put("before", before);
		map.put("after", after);

		list = (List<Object>) awardMapper.selectVotList(map);

		return list;

	}

	public Map<String, Object> awardUserList(Map<String, Object> obj) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();

		int page = (int) obj.get("pageCnt");
		System.out.println("pageCnt = " + page);
		String user = (String) obj.get("user");
		int before = 0;
		int after = 0;
		int contentSize = 6;
		if (page == 0 || page == 1) {
			before = 0;
			after = contentSize;
		} else if (page > 1) {
			before = (page - 1) * contentSize;
			after = contentSize;
		}
		map.put("user", user);
		map.put("before", before);
		map.put("after", after);

		int grade = (int) obj.get("grade");

		System.out.println("grade = " + grade);

		if (grade >= 2) {
			list = (List<Object>) awardMapper.awardUserList(map);
		} else if (grade <= 1) {
			list = (List<Object>) awardMapper.awardAdminList(map);
		}

		if (list.size() == 0) {
			result.put("result", "No");
		} else {
			result.put("result", "success");
			result.put("body", list);
		}

		return result;
	}

	public Map<String, Object> historyList(Map<String, Object> obj) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();

		int page = (int) obj.get("pageCnt");
		int before = 0;
		int after = 0;
		int contentSize = 6;
		if (page == 0 || page == 1) {
			before = 0;
			after = contentSize;
		} else if (page > 1) {
			before = (page - 1) * contentSize;
			after = contentSize;
		}
		map.put("before", before);
		map.put("after", after);

		list = (List<Object>) awardMapper.historyList(map);

		if (list.size() == 0) {
			result.put("result", "No");
		} else {
			result.put("result", "success");
			result.put("body", list);
		}

		return result;
	}

	public List<Object> selectVotListPage(int page) {

		List<Object> list = new ArrayList<>();

		// paging 처리

		Map<String, Object> map = new HashMap<>();

		list = (List<Object>) awardMapper.selectVotListPage(map);

		return list;
	}

	public Map<String, Object> voteMemberList(String vote_no) throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<Object> list = new ArrayList<>();
		System.out.println("repo vote_id = " + vote_no);

		list = awardMapper.voteMemberList(vote_no);

		if (list.get(0).equals("") || list.get(0) == null) {
			result.put("result", "error");
		} else {
			result.put("body", list);
		}
		return result;
	}

	public Map<String, Object> voteInsert(Map<String, Object> obj) throws Exception {
		int z = 0;
		Map<String, Object> result = new HashMap<>();
		Map<String, String> checkItem = new HashMap<>();
		String checkVotId = (String) obj.get("vote_no");
		System.out.println("checkVotId = " + checkVotId);
		String checkNm = (String) obj.get("voterNm");
		System.out.println("check = " + checkNm);

		String checkNmData = awardMapper.selectCheckNm(checkVotId, checkNm);

		System.out.println("checkNmData = " + checkNmData);
		// 내가 투표권한이 있는지확인
		if (checkNmData == null || checkNmData.equals(null)) {
			System.out.println("true");
			result.put("result", "fail");
		} else {
			// 권한이 있다면, 중복체크가 됬는지 확인
			System.out.println("false");
			System.out.println("false obj = " + obj.toString());
			String userId = awardMapper.selectUserId(obj);
			if (userId != null) {
				result.put("result", "fail1");
				return result;
			} else {
				List<Vote> voteList = new ArrayList<Vote>();
				List<Object> votedSize = (List<Object>) obj.get("votedCd");
				Vote vote = new Vote();
				SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
				for (int i = 0; i < votedSize.size(); i++) {
					vote = new Vote();
					Date date = new Date();
					vote.setVotId((String) obj.get("vote_no"));
					vote.setUserId((String) obj.get("voterId"));
					vote.setVoterNm((String) obj.get("voterNm"));
					vote.setVoteGrade(Integer.parseInt((String) obj.get("voteGd")));
					String code = (String) votedSize.get(i);
					Long lo = Long.parseLong(code);
					vote.setVotResultId(lo);
					vote.setVotYn("Y");
					String dt = format.format(date);
					vote.setVotDt(dt);

				}
				voteList.add(vote);
				try {
					z = awardMapper.voteInsert(voteList);
				} catch (Exception e) {
					result.put("result", "fail2");
					return result;
				}

			}
		}
		if (z == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}

		return result;

	}

	public String voteSelectId() throws Exception {

		String data = awardMapper.selectVotId();

		return data;
	}

	public Map<String, Object> voteSelectGrp() {
		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();

		list = awardMapper.selectVotGrp();

		if (list.size() == 0) {
			result.put("result", "fail");
		}
		result.put("result", "success");
		result.put("body", list);

		return result;
	}

	public Map<String, Object> voteSelectGrpMember(String grp_no) throws Exception {

		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();

		if (grp_no.equals("10000")) {

			System.out.println("if 문");
			list = awardMapper.voteSelectGrpAllMember();
			System.out.println("list = " + list.toString());
		} else {
			list = awardMapper.voteSelectGrpMember(grp_no);
		}

		if (list.size() == 0) {
			result.put("result", "fail");
		}
		result.put("result", "success");
		result.put("body", list);

		return result;
	}

	public Map<String, Object> voteCreateInfo(Award voteInfo) throws Exception {

		Map<String, Object> result = new HashMap<>();
		int i;

		i = awardMapper.voteCreateInfo(voteInfo);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "next");
		}

		return result;
	}

	public Map<String, Object> voteCreateItem(List<VoteMember> awardMember) throws Exception {
		Map<String, Object> result = new HashMap<>();
		int i;
		System.out.println("member!!!! = " + awardMember.toString());
		i = awardMapper.voteCreateItem(awardMember);

		if (i == 0) {
			result.put("result", "fail1");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> voteCreateRate(List<RateMember> rateMemberList) {
		Map<String, Object> result = new HashMap<>();
		int i;
		System.out.println("member!!!! = " + rateMemberList.toString());
		i = awardMapper.voteCreateRate(rateMemberList);

		if (i == 0) {
			result.put("result", "fail1");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> voteCheckRate(String userId) {

		Map<String, Object> result = new HashMap<>();
		List<Object> list = new ArrayList<>();

		list = awardMapper.voteCheckRate(userId);
		System.out.println("list = " + list.toString());

		if (list.size() == 0) {
			result.put("result", "fail");
		}
		result.put("result", "success");
		result.put("body", list);

		return result;
	}

	public Map<String, Object> votSelectDetail(Map<String, Object> obj) {

		Map<String, Object> result = new HashMap<>();

		String vote_no = (String) obj.get("vote_no");
		System.out.println("vote_no = " + vote_no);

		String userId = (String) obj.get("userId");

		List<Object> list = new ArrayList<>();
		List<Object> data = new ArrayList<>();
		List<Object> rate = new ArrayList<>();
		List<Object> grp = new ArrayList<>();

		list = awardMapper.voteMemberList(vote_no);
		System.out.println("list = " + list.toString());
		if (list.size() == 0) {
			result.put("result", "fail");
		} else {
			data = awardMapper.votSelectDetail(vote_no);
			System.out.println("data = " + data.toString());
			if (data.size() == 0) {
				result.put("result", "fail");
			} else {
				rate = awardMapper.voteCheckRate(vote_no);
				System.out.println("rata = " + rate.toString());
			}

			grp = awardMapper.selectVotGrp();
		}
		result.put("result", "success");
		result.put("info", data);
		result.put("member", list);
		result.put("rate", rate);
		result.put("grp", grp);

		return result;
	}

	public Map<String, Object> voteRateList(String userId) {
		Map<String, Object> result = new HashMap<>();
		List<Object> list = new ArrayList<>();

		list = awardMapper.voteRateList(userId);
		System.out.println("list = " + list.toString());

		if (list.size() == 0) {
			result.put("result", "fail");
		}
		result.put("result", "success");
		result.put("body", list);

		return result;
	}

	public Map<String, Object> voteUpdateDetail(Map<String, Object> obj) {

		int i = 0;

		Map<String, Object> result = new HashMap<>();

		i = awardMapper.voteUpdateDetail(obj);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> checkEndDt(String vote_id) {

		Map<String, Object> result = new HashMap<>();
		int i = 0;
		i = awardMapper.checkEndDt(vote_id);
		System.out.println("updaste Cnt = " + i);
		if (i == 0) {
			result.put("endDt", "N");
		} else {
			result.put("endDt", "Y");
			result.put("cnt", i);
		}

		return result;
	}

	public List<Object> selectResultInfo(String vote_id) {
		List<Object> list = new ArrayList<>();
		list = awardMapper.selectResultInfo(vote_id);

		System.out.println("repoList = " + list.toString());

		return list;
	}

	public Map<String, Object> selectResultMember(String vote_id) {
		Map<String, Object> result = new HashMap<>();
		List<Object> list = new ArrayList<>();
		list = awardMapper.selectResultMember(vote_id);

		System.out.println("list repo = " + list.size());

		if (list.size() == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
			result.put("list", list);
		}
		return result;
	}

	public Map<String, Object> resultIdInsert(String vote_id, String vote_result_id) {

		Map<String, Object> result = new HashMap<>();
		int i = 0;
		i = awardMapper.resultIdInsert(vote_id, vote_result_id);

		if (i <= 0) {
			result.put("resull", "fail");
		} else {
			result.put("result", "OK");
		}
		return result;
	}

	// 보상관리 리스트
	public Map<String, Object> compensationList(Map<String, Object> obj) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		int page = (int) obj.get("pageCnt");

		System.out.println("obj = " + obj.toString());
		System.out.println("pageCnt = " + page);
		int before = 0;
		int after = 0;
		int contentSize = 6;

		if (page == 0 || page == 1) {
			before = 0;
			after = contentSize;
		} else if (page >= 2) {
			before = (page - 1) * contentSize;
			after = contentSize;
		}

		System.out.println("before = " + before);
		System.out.println("after = " + after);
		map.put("before", before);
		map.put("after", after);

		list = awardMapper.compensationList(map);

		if (list.size() == 0) {
			result.put("result", "fail");
		} else if (list.size() > 0) {
			result.put("result", "success");
			result.put("body", list);
		}

		return result;
	}

	public Map<String, Object> voteEndStatus(String vote_no) {

		Map<String, Object> result = new HashMap<>();
		int i;
		i = awardMapper.voteEndStatus(vote_no);
		if (i > 0) {
			result.put("result", "success");
			// 투표 마감에 대한 기능 개발
			// 1. 투표수가 높을 떄 * 선택
			// 2. 투표수가 동표 일 때
			// 2-1 직급 확인 -> 부서장 과 사원일 경우 사원에게
			// 2-2 직급이 같을 떄 -> 받은 어워드 수가 적은 사람이 받기
			// 2-3 근속년수가 오래된 순
			try {
				Map<String, Object> data = new HashMap<String, Object>();
				// votid,resultid,email,name,grade,entrydt 정보를 가지고 옴
				data = selectResultMember(vote_no);

				// 수상자가 없을경우
				if (data == null) {
					result.put("result", "fail");
				} else {
					// 수상자가 있고, 한명일 경우
					if (data.size() == 1) {
						// 수상자로 선정 및 진행

					} else {
						// 투표수가 같은지 확인
						// 투표수가 제일 높은사람이 0번째
						String result_id = null;
						//투표 1위 정보 뽑기
						for (int z = 0; z < 1; z++) {
							String str = (String) data.get("VOT_SCORE");
							int score = Integer.parseInt(str); // 1,2,3,3
							String userId = (String) data.get("USER_ID");
							String gradeStr = (String) data.get("GRADE_CD");
							int grade = Integer.parseInt(gradeStr);
							String entryDt = (String) data.get("ENTRY_DT");
							for (int x = 1; x <= data.size(); x++) {
								String afterStr = (String) data.get("VOT_SCORE");  
								int afterScore = Integer.parseInt(afterStr); 
								//투표수가 같다면
								if(score == afterScore) {
									String afterUserId = (String) data.get("USER_ID");
									String afterGradeStr = (String) data.get("GRADE_CD");
									int afterGrade = Integer.parseInt(afterGradeStr);
									String afterEntryDt = (String) data.get("ENTRY_DT");
									//직급 비교
									if(grade < afterGrade) {
										
									}
									
									
									
								}else {
									
								}
								
							}

						}

					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} else {
			result.put("result", "fail");
		}

		return result;
	}

	public Map<String, Object> voteDelete(String vote_no) {
		Map<String, Object> result = new HashMap<>();

		System.out.println("repo test");
		System.out.println("vote_no = " + vote_no);

		int i;

		i = awardMapper.voteDelete(vote_no);
		System.out.println("i = " + i);
		if (i > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		return result;
	}

	public Map<String, Object> giftSelectInfo() {

		Map<String, Object> result = new HashMap<>();
		List<Object> list = new ArrayList<>();
		list = awardMapper.giftSelectInfo();

		if (list.size() == 0) {
			result.put("result", "fail");
			return result;
		} else {
			result.put("result", list);
		}

		return result;
	}

	public Map<String, Object> giftFinalUpdate(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();

		System.out.println("repo test");
		System.out.println("vote_no = " + obj.toString());

		int i;
		i = awardMapper.giftFinalUpdate(obj);
		System.out.println("i = " + i);
		if (i > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		return result;
	}

	public List<Object> awardMainList(Map<String, Object> obj) {

		System.out.println("page = " + obj);
		Map<String, Object> map = new HashMap<>();
		int page = (int) obj.get("page");
		String user = (String) obj.get("user");
		int grade = (int) obj.get("grade");
		int before = 0;
		int after = 0;
		int contentSize = 6;
		Map<String, Object> totalSize = new HashMap<>();

		if (page == 0 || page == 1) {
			before = 0;
			after = contentSize;
		} else if (page >= 2) {
			before = (page - 1) * contentSize;
			after = contentSize;
		}
		map.put("user", user);
		map.put("before", before);
		map.put("after", after);
		List<Object> list = new ArrayList<>();
		System.out.println("grade = " + grade);
		if (grade == 3) {
			totalSize = awardMapper.awardMainListSize(user);
			list = awardMapper.awardMainList(map);
			list.add(0, totalSize);
		} else if (grade <= 2) {
			System.out.println("admin");
			totalSize = awardMapper.awardMainAdminListSize();
			list = awardMapper.awardMainAdminList(map);
			list.add(0, totalSize);
		}

		return list;
	}

	public List<Object> checkAwardList() {
		List<Object> list = new ArrayList<>();
		list = awardMapper.checkAwardList();
		return list;
	}

	public Map<String, Object> checkResultMember(Map<String, Object> map) {

		Map<String, Object> result = new HashMap<>();
		result = awardMapper.checkResultMember(map);
		return result;
	}

	public Map<String, Object> voteUpdate(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("obj!!!!aw = " + obj.toString());

		Map<String, Object> data = awardMapper.checkVoteChoice(obj);

		if (data == null) {
			result.put("result", "fail");
			return result;
		} else {

			Long loData;
			List<Object> list = new ArrayList<>();
			list = (List<Object>) obj.get("votedCd");

			loData = Long.valueOf((String) list.get(0));

			System.out.println("loData!!!!aw = " + loData);

			obj.put("votedCd", loData);

			int i = 0;

			i = awardMapper.voteUpdate(obj);

			if (i == 0) {
				result.put("result", "updateFail");
			} else {
				result.put("result", "success");
			}
		}

		return result;

	}

}
