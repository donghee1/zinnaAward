package com.zinnaworks.svc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import com.zinnaworks.mapper.AwardMapper;
import com.zinnaworks.repo.AwardRepository;
import com.zinnaworks.repo.MemberRepository;
import com.zinnaworks.vo.Award;
import com.zinnaworks.vo.CompensationVo;
import com.zinnaworks.vo.Member;
import com.zinnaworks.vo.RateMember;
import com.zinnaworks.vo.Vote;
import com.zinnaworks.vo.VoteMember;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Service
@Transactional
public class AwardService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	AwardRepository awardRepository;

	public Map<String, Object> awardList(Map<String, Object> obj) throws Exception {

		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();

		list = awardRepository.selectVotList(obj);
		if (list.get(0) == null || list.get(0).equals("")) {
			result.put("result", "fail");
		} else {
			if (result.get("endDt").equals("Y")) {

				Map<String, Object> dataMap;
				String vote_id = null;
				String vote_result_id = null;
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> data = (Map<String, Object>) list.get(i);
					vote_id = (String) data.get("VOT_ID");
					System.out.println("vote_id = " + vote_id);
					// 현재시간보다 종료시간이 지났을 경우 update
					result = awardRepository.checkEndDt(vote_id);

					List<Map<String, Object>> listMap = new ArrayList<>();
					dataMap = awardRepository.selectResultMember(vote_id);
					listMap = (List<Map<String, Object>>) dataMap.get("list");
					vote_result_id = (String) listMap.get(0).get("VOT_RESULT_ID");
					System.out.println("dataMap = " + dataMap.toString());
					System.out.println("dataMap1 = " + listMap.get(0).get("VOT_RESULT_ID"));
					if (dataMap.get("list").equals("") || dataMap.get("list") != null) {
						result = awardRepository.resultIdInsert(vote_id, vote_result_id);
					}

				}

			} else if (result.get("endDt").equals("N") || result.get("endDt") == null) {
				System.out.println("실패!!!!");
			}
		}
		result.put("body", list);

		return result;
	}

	public Map<String, Object> awardUserList(Map<String, Object> obj) {

		Map<String, Object> result = new HashMap<>();
		result = awardRepository.awardUserList(obj);

		return result;
	}

	public Map<String, Object> historyList(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();
		result = awardRepository.historyList(obj);

		return result;
	}

	public Map<String, Object> checkAwardList() throws Exception {

		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();

		list = awardRepository.checkAwardList();
		try {

			if (list.get(0) == null || list.get(0).equals("")) {
				result.put("result", "fail");
			} else {
				System.out.println("list = " + list.toString());
				System.out.println("listSize = " + list.size());
				// 현재시간보다 종료시간이 지났을 경우 update

				Map<String, Object> dataMap;
				String vote_id = null;
				String vote_result_id = null;
				// 리스트사이즈 변경
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> data = (Map<String, Object>) list.get(i);
					vote_id = (String) data.get("VOT_ID");
					System.out.println("vote_id = " + vote_id);
					result = awardRepository.checkEndDt(vote_id);

					List<Map<String, Object>> listMap = new ArrayList<>();
					try {
						dataMap = awardRepository.selectResultMember(vote_id);
						System.out.println("dataMap!!!! = " + dataMap.toString());
						System.out.println("dataMapresult = " + dataMap.get("result"));

						// 조회된 당첨자가 없을경우 없는 내용을 로그로 호출
						Map<String, Object> rankMap = new HashMap<>();
						Map<String, Object> map = new HashMap<>();
						if (dataMap.get("result").equals("fail")) {
							System.out.println("No member Award choice!!!");
						} else if(dataMap.get("result").equals("success")){
							listMap = (List<Map<String, Object>>) dataMap.get("list");
							System.out.println("listMapSize = " + listMap.size());
							// 투표대상이 1명 이상일 경우
							
							if (listMap.size() > 1) {
								if (dataMap.get("list").equals("") || dataMap.get("list") != null) {
									map.putAll(listMap.get(0));
									Integer checkScore = (int) listMap.get(0).get("VOT_SCORE");
									int score = 0;
									for (int z = 0; z < listMap.size(); z++) {
										// 최종 투표자는 정렬로 인해 무조건 0번째가 최종 투표자이다
										if (z >= 1) {
											score = (int) listMap.get(z).get("VOT_SCORE");
										}
										// 동률이 나왔을 경우
										if (checkScore == score) {
											map.putAll(listMap.get(i));
										}
										System.out.println("for cont = " + z);
										// if(listMap.get(z).get("ENTRY_DT") <)
									}
									rankMap = awardRepository.checkResultMember(map);
									System.out.println("rankMap = " + rankMap.toString());

									for (Map.Entry<String, Object> m : rankMap.entrySet()) {
										System.out.println("최종 데이터!!!! = " + m.getKey() + "," + m.getValue());
									}

									result = awardRepository.resultIdInsert(vote_id, vote_result_id);
								}

							} else {
								
								vote_id = (String) listMap.get(0).get("VOT_ID");
								vote_result_id = (String) listMap.get(0).get("VOT_RESULT_ID");
								System.out.println("Reward 당첨자는 1명이고 당첨자는 = " + vote_result_id + "입니다");
								System.out.println("단일 최종 데이터!!!! = " + vote_id);
								result = awardRepository.resultIdInsert(vote_id, vote_result_id);
							}
						}
					} catch (Exception e) {
						System.out.println("e = " + e.getMessage());
					}

				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		result.put("body", list);

		return result;
	}

	public Map<String, Object> awardMainList(Map<String, Object> obj) throws Exception {

		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();

		list = awardRepository.awardMainList(obj);
		if (list.get(0) == null || list.get(0).equals("")) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
			result.put("body", list);
		}

		return result;
	}

	public Map<String, Object> voteMemberList(String vote_no) throws Exception {
		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteMemberList(vote_no);

		if (result.get("body").equals("") || result.get("body") == null) {
			result.put("result", false);
		}

		return result;

	}

//	public Map<String, Object> voteInsert(Map<String, Object> obj) throws Exception {
//
//		Map<String, Object> result = new HashMap<>();
//
//		String test = "test";
//		int data = awardRepository.voteInsert(test);
//
//		if (data == 0) {
//			result.put("reslut", "false");
//		}
//
//		result.put("result", "success");
//
//		return result;
//	}

	public Map<String, Object> voteSelectId() throws Exception {

		Map<String, Object> result = new HashMap<>();
		String data = null;
		try {
			data = awardRepository.voteSelectId();
			System.out.println("data = " + data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!data.equals("")) {
			String str = data.substring(0, 6);
			System.out.println("str = " + str);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			String dateForm = format.format(date);
			Long dataLong = Long.parseLong(str);
			Long todayLong = Long.parseLong(dateForm);
			String strData = null;
			// 비교 데이터가 클수 없다. 같거나 적을 것
			if (todayLong > dataLong) {
				// 작을경우 처음 시작한다고 가정하여 신규날짜 + 01을 넣어준다;
				System.out.println("ddd");
				strData = String.valueOf(todayLong);
				strData += "-01";
			} else {
				// 같을 경우
				String split = data.substring(7);
				int no = Integer.parseInt(split);
				no += 1;
				String noStr = String.valueOf(no);
				if (noStr.length() >= 2) {
					noStr = "-" + noStr;
				} else {
					noStr = "-0" + noStr;
				}
				strData = String.valueOf(dataLong) + noStr;
			}

			result.put("VOT_ID", strData);

		}

		return result;
	}

	public Map<String, Object> voteSelectGrp() throws Exception {
		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteSelectGrp();

		return result;
	}

	public Map<String, Object> voteSelectGrpMember(String grp_no) throws Exception {

		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteSelectGrpMember(grp_no);

		return result;
	}

	public Map<String, Object> voteCreateInfo(Map<String, Object> obj) throws Exception {

		Award award = new Award();

		System.out.println("obj = " + obj.toString());

		Map<String, Object> result = new HashMap<String, Object>();

		if (obj.get("vote_id").equals("") || obj.get("vote_id") == "") {
			result.put("result", "fail");
			return result;
		} else {
			if (obj.get("start_mms").equals(true)) {
				award.setStartMMS(0);
			} else {
				award.setStartMMS(1);
			}
			if (obj.get("end_mms").equals(true)) {
				award.setEndMMS(0);
			} else {
				award.setEndMMS(1);
			}
			award.setVotId((String) obj.get("vote_id"));
			award.setVotTyp((String) obj.get("vote_type"));
			award.setVotGrp(Integer.parseInt((String) obj.get("vote_grp")));
			award.setVotNm((String) obj.get("vote_title"));
			award.setVotDesc((String) obj.get("vote_info"));
			award.setStartDt((String) obj.get("start_dt"));
			award.setEndDt((String) obj.get("end_dt"));
			award.setStatus(0);
			award.setUserId((String) obj.get("user_id") + "@zinnaworks.com");
			award.setReward(null);
			award.setRewardDt(null);
			award.setPhotoNm(null);
			award.setAutoHistory(null);

			result = awardRepository.voteCreateInfo(award);

			System.out.println("result = " + result.toString());

			if (result.get("result").equals("next")) {

				List<VoteMember> memberList = new ArrayList<>();
				List<Object> data = new ArrayList<Object>();
				data.add(obj.get("vote_member"));

				System.out.println("data = " + data.toString());
				System.out.println("data = " + data.size());
				List<String> dataList = new ArrayList<>();

				dataList = (List<String>) data.get(0);

				System.out.println("dataList = " + dataList.size());

				for (int i = 0; i < dataList.size(); i++) {
					String str = dataList.get(i);
					String[] strArr = str.split(",");
					String voteNm = strArr[0];
					String memberId = strArr[1];
					int grade = Integer.parseInt(strArr[2]);
					System.out.println("memberId = " + memberId);
					VoteMember voteMember = new VoteMember();

					voteMember.setVotId(award.getVotId());
					String code = randomCd(dataList.size());
					Long lo = Long.parseLong(code);
					voteMember.setVotCd(lo);
					voteMember.setVotNm(voteNm);
					voteMember.setMemberId(memberId);
					voteMember.setVoteGrade(grade);
					memberList.add(i, voteMember);

				}

				System.out.println("memberList = " + memberList.size());
				System.out.println("memberList = " + memberList.toString());

				List<Object> rateArr = new ArrayList<>();
				List<String> rateList = new ArrayList<>();
				List<RateMember> rateMemberList = new ArrayList<>();

				result = awardRepository.voteCreateItem(memberList);

				List<Object> authMember = new ArrayList<>();
				authMember = (List<Object>) obj.get("auth_member");

				if (authMember.size() == 0) {
					System.out.println("if문 테스트!!!!");
					for (int i = 0; i < dataList.size(); i++) {

						String str = dataList.get(i);
						String[] strArr = str.split(",");
						String voteNm = strArr[0];
						String memberId = strArr[1];
						int grade = Integer.parseInt(strArr[2]);
						RateMember rateMember = new RateMember();
						rateMember.setVotId(award.getVotId());
						rateMember.setUserId(memberId);
						rateMember.setVoteGrade(grade);
						rateMemberList.add(i, rateMember);
					}
					result = awardRepository.voteCreateRate(rateMemberList);

				} else {

					rateArr.add(obj.get("auth_member"));

					rateList = (List<String>) rateArr.get(0);

					System.out.println("rateArr = " + rateArr.toString());
					System.out.println("rateArr = " + rateArr.size());
					System.out.println("rateList = " + rateList.size());

					if (obj.get("auth_member") != null) {
						for (int z = 0; z < rateList.size(); z++) {

							String str = rateList.get(z);
							String[] strArr = str.split(",");
							String userId = strArr[1];
							String grade = strArr[0];
							int gradeCd = Integer.parseInt(grade);
							RateMember rateMember = new RateMember();
							rateMember.setUserId(userId);
							rateMember.setVotId(award.getVotId());
							rateMember.setVoteGrade(gradeCd);

							rateMemberList.add(z, rateMember);
						}
						System.out.println("rateList = " + rateMemberList.toString());

					}

					result = awardRepository.voteCreateRate(rateMemberList);

				}

			}
		}

		return result;
	}

	public Map<String, Object> voteIsert(Map<String, Object> obj) throws Exception {

		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteInsert(obj);

		return result;
	}

	public Map<String, Object> voteRate(String userId) {

		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteRateList(userId);

		return result;
	}

	public Map<String, Object> votSelectDetail(Map<String, Object> obj) {

		Map<String, Object> result = new HashMap<>();

		result = awardRepository.votSelectDetail(obj);

		return result;
	}

	public Map<String, Object> voteUpdateDetail(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteUpdateDetail(obj);

		return result;
	}

	public Map<String, Object> compensationList(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();
		result = awardRepository.compensationList(obj);
		return result;
	}

	public static String randomCd(int len) throws Exception {
		Random random = new Random();
		String numStr = ""; // 난수가 저장될 변수
		for (int i = 0; i < len; i++) {
			String ran = Integer.toString(random.nextInt(9) + 1);
			numStr += ran;
		}

		return numStr;
	}

	public Map<String, Object> voteEndStatus(String vote_no) {

		Map<String, Object> result = new HashMap<>();

		System.out.println("service update");

		result = awardRepository.voteEndStatus(vote_no);

		return result;
	}

	public Map<String, Object> voteDelete(String vote_no) {

		Map<String, Object> result = new HashMap<>();

		System.out.println("service update");

		result = awardRepository.voteDelete(vote_no);

		return result;
	}

	public Map<String, Object> giftSelectInfo() throws Exception {
		Map<String, Object> result = new HashMap<>();

		System.out.println("service update");

		result = awardRepository.giftSelectInfo();

		return result;
	}

	public Map<String, Object> giftFinalUpdate(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();

		System.out.println("service update");

		result = awardRepository.giftFinalUpdate(obj);

		return result;
	}

	public Map<String, Object> awardListPage(int page) {

		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();

		list = awardRepository.selectVotListPage(page);
		System.out.println("list = " + list);
		result.put("body", list);

		return result;
	}

	public Map<String, Object> voteUpdate(Map<String, Object> obj) {

		Map<String, Object> result = new HashMap<>();

		result = awardRepository.voteUpdate(obj);

		return result;
	}

}