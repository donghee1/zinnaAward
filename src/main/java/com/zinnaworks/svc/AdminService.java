package com.zinnaworks.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.AdminRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;

	/**
	 * 
	 * @그룹리스트 return
	 */
	public List<Map<String, Object>> groupList() {
		return adminRepository.groupList();
	}

	/**
	 * 변수:(상위부서코드) 상위 부서에 대한 하위 부서들 list
	 * 
	 * @param preGrpCd
	 * @return
	 */
	public List<Map<String, Object>> lowGroupsList(String grpCd, String preGrpCd, String grpNm, String preGrpNm) {

		int plus = 10;
		// 하위부서의 코드
		String grp_cd = "";
		String grp_nm = "";
		String pre_grp_cd = "";
		String pre_grp_nm = "";

		if (Integer.parseInt(grpCd) % 10000 == 0) {
			plus = 1000;
		} else {
			if ((Integer.parseInt(grpCd) % 10000) % 1000 == 0) {
				plus = 100;
			}
		}

		if (adminRepository.lowGroupsList(grpCd).size() != 0) {
			grp_cd = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("GRP_CD"));
			grp_nm = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("GRP_NM"));
			pre_grp_cd = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("PRE_GRP_CD"));
			pre_grp_nm = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("PRE_GRP_NM"));
			grp_cd = String.valueOf(Integer.parseInt(grp_cd) + plus);
			// 현재 클릭한 그룹 코드, insert될 그룹 코드
			return adminRepository.lowGroupsList(grpCd, grp_cd, grpNm, pre_grp_nm);
		} else {
			grp_cd = String.valueOf(Integer.parseInt(grpCd) + plus);
			return adminRepository.lowGroupsList(grpCd, grp_cd, grpNm, preGrpNm);
		}

	}

	/**
	 * 
	 * @카테고리리스트 return
	 */
	public Map<String, Object> cateList() {

		return adminRepository.cateList();
	}

	public Map<String, Object> grpDeleteInfo(String grpCd) {

		System.out.println("grpCd = " + grpCd);
		Map<String, Object> result = new HashMap<>();

		result = adminRepository.grpDeleteInfo(grpCd);

		return result;
	}

	public Map<String, Object> memberSelectInfo() {
		Map<String, Object> result = new HashMap<>();

		result = adminRepository.memberSelectInfo();

		return result;
	}

	public Map<String, Object> memberUpdateInfo(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();

		result = adminRepository.memberUpdateInfo(obj);

		return result;
	}

	public Map<String, Object> memberDeleteInfo(String userId) {
		Map<String, Object> result = new HashMap<>();

		result = adminRepository.memberDeleteInfo(userId);

		return result;

	}

	public Map<String, Object> cateDeleteInfo(String productCode)throws Exception {
		Map<String, Object> result = new HashMap<>();
		result = adminRepository.cateDeleteInfo(productCode);

		return result;
	}

	public Map<String, Object> cateNewCodeInfo() {
		Map<String, Object> result = new HashMap<>();
		result = adminRepository.cateNewCodeInfo();

		return result;
	}

	public Map<String, Object> cateCodeInsertInfo(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();
		result = adminRepository.cateCodeInsertInfo(obj);

		return result;
	}


}
