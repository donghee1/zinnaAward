package com.zinnaworks.vo;

import lombok.Data;

@Data
public class MailAuth {

	private String userId;
	private int authKey;
	private String creDt;
	private String expDt;
}
