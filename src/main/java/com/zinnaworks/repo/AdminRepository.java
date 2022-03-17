package com.zinnaworks.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zinnaworks.mapper.AdminMapper;

@Repository
public class AdminRepository {

	@Autowired
	AdminMapper adminMapper;

	/**
	 * 
	 * @그룹리스트 return
	 */
	public List<Map<String, Object>> groupList() { // 조직 관리 리스트

		return adminMapper.selectGroupInfo();
	}

	/**
	 * 
	 * @param String grpCd, String grp_cd, String grpNm, String pre_grp_nm
	 * @return
	 */
	public List<Map<String, Object>> lowGroupsList(String grpCd, String grp_cd, String grpNm, String pre_grp_nm) {

		insertGroupInfoCD(grp_cd, grpNm, grpCd, pre_grp_nm);

		return adminMapper.selectLowGroups(grpCd);
	}

	public List<Map<String, Object>> lowGroupsList(String grpCd) {
		return adminMapper.selectLowGroups(grpCd);
	}

	/**
	 * 하위부서 생성
	 */
	public Map<String, Object> insertGroupInfoCD(String grpCd, String grpNm, String preGrpCd, String preGrpNm) {

		Map<String, Object> result = new HashMap<>();
		int i = 0;

		i = adminMapper.insertGroupInfoCD(grpCd, grpNm, preGrpCd, preGrpNm);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	/**
	 * 
	 * @카테고리리스트 return
	 */
	public Map<String, Object> cateList() {
		Map<String, Object> result = new HashMap<>();

		List<Object> list = new ArrayList<>();
		
		list = adminMapper.selectCateList();
		
		if(list.size() == 0) {
			result.put("result", "fail");
		}else {
			result.put("result", "success");
			result.put("body", list);
		}
		
		return result;
	}

	public Map<String, Object> grpDeleteInfo(String grpCd) {

		Map<String, Object> result = new HashMap<>();

		int i = 0;
		i = adminMapper.grpDeleteInfo(grpCd);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> memberSelectInfo() {
		Map<String, Object> result = new HashMap<>();

		List<Object> data = new ArrayList<>();
		data = adminMapper.memberSelectInfo();

		if (data.size() == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
			result.put("body", data);
		}
		return result;
	}

	public Map<String, Object> memberUpdateInfo(Map<String, Object> obj) {

		Map<String, Object> result = new HashMap<>();

		int i = 0;

		i = adminMapper.memberUpdateInfo(obj);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> memberDeleteInfo(String userId) {
		Map<String, Object> result = new HashMap<>();

		int i = 0;

		i = adminMapper.memberDeleteInfo(userId);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> cateDeleteInfo(String productCode) throws Exception{
		Map<String, Object> result = new HashMap<>();

		int i = 0;

		i = adminMapper.cateDeleteInfo(productCode);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

	public Map<String, Object> cateNewCodeInfo() {
		
		Map<String, Object> result = new HashMap<>();
		
		String data;
		
		data = adminMapper.cateNewCodeInfo();
		
		if(data == null || data.equals("")) {
			result.put("result", "fail");
		}else {
			result.put("result", "success");
			result.put("body", data);
		}
		
		return result;
	}

	public Map<String, Object> cateCodeInsertInfo(Map<String, Object> obj) {
		Map<String, Object> result = new HashMap<>();
		int i = 0;

		i = adminMapper.cateCodeInsertInfo(obj);

		if (i == 0) {
			result.put("result", "fail");
		} else {
			result.put("result", "success");
		}

		return result;
	}

}
