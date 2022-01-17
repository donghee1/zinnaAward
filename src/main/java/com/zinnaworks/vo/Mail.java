package com.zinnaworks.vo;


import org.apache.ibatis.annotations.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Mapper
@NoArgsConstructor
public class Mail {
	private String address;
	private String title;
	private String message;
	
	private int AUTH_KEY;
	private String CRE_DT;
	private String EXP_DT;
}
