package com.zinnaworks.svc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.MemberRepository;
import com.zinnaworks.vo.Mail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	
	 private MimeMessage createMessage(String to)throws Exception{
		 MimeMessage  message = mailSender.createMimeMessage();
		 System.out.println("message = " + message);
	        String code = createCode(ePw);
	        message.addRecipients(RecipientType.TO, to); //보내는 대상
	        message.setSubject("zinnaworks 인증코드 발송 "); //제목
	        String msg="";
	        msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://slack.com/x-a1607371436052/img/slack_logo_240.png\" alt=\"\" loading=\"lazy\">";
	        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
	        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 Slack 가입 창이 있는 브라우저 창에 입력하세요.</p>";
	        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
	        msg += code;
	        msg += "</td></tr></tbody></table></div>";
	        msg += "<a href=\"http://localhost:8080/update\">비밀번호변경하기</a>";
	        message.setText(msg, "utf-8", "html"); //내용
	        return message;
	    }
	
	public static final String ePw = mailAuthKey();
	
	// 메일 보내기
	public boolean mailSend(Mail mail) throws Exception{
		MimeMessage message = createMessage(mail.getAddress().toString());
        try{//예외처리
        	//인증번호DB 등록, 등록일, 만료일 DB 저장
        	// 메일 보내는 년월일시간
        	SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
        	Date cre_dt = new Date();
        	// 메일 보내는 년월일시간 + 1일
        	Date exr_dt = createAuthKeyNextDT();
        	mail.setCRE_DT(formatter.format(cre_dt));
        	mail.setEXP_DT(formatter.format(exr_dt));
        	mail.setAUTH_KEY(123456);
        	System.out.println("address = " + mail.getAddress());
        	int data = memberRepository.mergeInsertAuthInfo(mail);
        	System.out.println("insertcheck = " + data);
        	
            //mailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            return false;
        }
		return true;
	}
	
	//인증 코드 만들기
	public static String mailAuthKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		for(int i=0; i < 6; i++) {
			key.append((rnd.nextInt(10)));
		}
		return key.toString();
	}
	
	public String createCode(String ePw) {
		return ePw.substring(0, 3) + "-" + ePw.substring(3, 6);
	}
	
	public Date createAuthKeyNextDT() {
		Date date = new Date();
		System.out.println("date before = " + date);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		date = cal.getTime();
		System.out.println("date = " + cal);
		System.out.println("date after = " + date);
		
		return date;
		
	}
	
}
