package com.zinnaworks.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {

	public List<Map<String, Object>> selectGroupInfo();

	public List<Map<String, Object>> selectLowGroups(String grpCd);

	public int insertGroupInfoCD(@Param("grpCd") String grpCd, @Param("grpNm") String grpNm,
			@Param("preGrpCd") String preGrpCd, @Param("preGrpNm") String preGrpNm);

	public List<Object> selectCateList();

	public int grpDeleteInfo(String grpCd);

	public List<Object> memberSelectInfo();

	public int memberUpdateInfo(Map<String, Object> obj);

	public int memberDeleteInfo(String userId);

	public int cateDeleteInfo(String productCode);

	public String cateNewCodeInfo();

	public int cateCodeInsertInfo(Map<String, Object> obj);

}
