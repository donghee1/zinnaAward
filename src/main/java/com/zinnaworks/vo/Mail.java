package com.zinnaworks.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
	private String user_id;
	private int auth_key;
	private String cre_dt;
	private String exp_dt;
	private String title;
	private String message;
}
