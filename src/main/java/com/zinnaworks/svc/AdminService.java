package com.zinnaworks.svc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.AdminRepository;
import com.zinnaworks.vo.Group;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	/**
	 * 
	 * @그룹리스트 return 
	 */
	public List<Map<String, Object>> groupList(){		
		return adminRepository.groupList();
	}
	
	/**
	 * 변수:(상위부서코드) 상위 부서에 대한 하위 부서들 list 
	 * @param preGrpCd
	 * @return
	 */
	public List<Map<String, Object>> lowGroupsList(String grpCd, String preGrpCd, String grpNm, String preGrpNm){
		
		int plus=10;
		// 하위부서의 코드
		String grp_cd = "";
		String grp_nm = "";
		String pre_grp_cd = "";
		String pre_grp_nm = "";
		
		if(Integer.parseInt(grpCd)%10000 == 0) {
			plus = 1000;
		} else {
			if((Integer.parseInt(grpCd)%10000)%1000 == 0) {
				plus = 100;
			} 
		}
		
		if(adminRepository.lowGroupsList(grpCd).size()!=0) {
			grp_cd = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("GRP_CD"));
			grp_nm = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("GRP_NM"));
			pre_grp_cd = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("PRE_GRP_CD"));
			pre_grp_nm = String.valueOf(adminRepository.lowGroupsList(grpCd).get(0).get("PRE_GRP_NM"));
			grp_cd = String.valueOf(Integer.parseInt(grp_cd)+plus);
												// 현재 클릭한 그룹 코드, insert될 그룹 코드
			return adminRepository.lowGroupsList(grpCd, grp_cd, grpNm, pre_grp_nm);
		} else {
			grp_cd =String.valueOf(Integer.parseInt(grpCd)+plus);
			return adminRepository.lowGroupsList(grpCd, grp_cd, grpNm, preGrpNm);
		}
		
											
	}								
	
	/**
	 * 
	 * @카테고리리스트 return 
	 */
	public List<Map<String, Object>> cateList(){		
		
		return adminRepository.cateList();
	}
	
	
	
}
