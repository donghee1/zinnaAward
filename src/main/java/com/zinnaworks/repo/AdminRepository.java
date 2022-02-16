package com.zinnaworks.repo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zinnaworks.mapper.AdminMapper;
import com.zinnaworks.vo.Group;

@Repository
public class AdminRepository {

	@Autowired
	AdminMapper adminMapper;
	
	/**
	 * 
	 * @그룹리스트 return 
	 */
	public List<Map<String, Object>> groupList(){		// 조직 관리 리스트 
		
		return adminMapper.selectGroupInfo();
	}
	
	/**
	 * 
	 * @param String grpCd, String grp_cd, String grpNm, String pre_grp_nm
	 * @return
	 */
	public List<Map<String, Object>> lowGroupsList(String grpCd, String grp_cd, String grpNm, String pre_grp_nm){
		insertGroupInfoCD(grp_cd, grpNm, grpCd, pre_grp_nm);
		return adminMapper.selectLowGroups(grpCd);
	}
	
	public List<Map<String, Object>> lowGroupsList(String grpCd){
		return adminMapper.selectLowGroups(grpCd);
	}
	
	/**
	 * 하위부서 생성 
	 */
	public Object insertGroupInfoCD(String grpCd, String grpNm, String preGrpCd, String preGrpNm) {
		return adminMapper.insertGroupInfoCD(grpCd, grpNm, preGrpCd, preGrpNm);
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * @카테고리리스트 return 
	 */
	public List<Map<String, Object>> cateList(){
		
		return adminMapper.selectCateList();
	}
	
	
	
	

}
