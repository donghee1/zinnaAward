package com.zinnaworks.svc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.zinnaworks.repo.MemberRepository;
import com.zinnaworks.vo.Mail;
import com.zinnaworks.vo.MailAuth;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MemberRepository memberRepository;
	
	private MimeMessage createMessageSignUp(String to) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		message.addRecipients(RecipientType.TO, to); // 보내는 대상
		message.setSubject("zinnaworks Award 회원가입을 환영합니다."); // 제목
		String msg = "";
		msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://zinna.works/img/zinnaworks-logo-1@1x.png\" alt=\"\" loading=\"lazy\">";
		msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
		msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 링크로 이동하여 확인버튼을 눌러주시기 바랍니다.</p>";
		msg += "</td></tr></tbody></table></div>";
		msg += "<a href=\"http://award.zinnaworks.com:8080/signUpAuth?"+to+"\">회원가입 인증하기</a>";
		message.setText(msg, "utf-8", "html"); // 내용
		return message;
	}

	private MimeMessage createMessage(String to, String code) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		message.addRecipients(RecipientType.TO, to); // 보내는 대상
		message.setSubject("zinnaworks 인증코드 발송 "); // 제목
		String msg = "";
		msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://zinna.works/img/zinnaworks-logo-1@1x.png\" alt=\"\" loading=\"lazy\">";
		msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
		msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 가입 창이 있는 브라우저 창에 입력하세요.</p>";
		msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
		msg += code;
		msg += "</td></tr></tbody></table></div>";
		msg += "<a href=\"http://award.zinnaworks.com:8080/update\">비밀번호 변경하기</a>";
		message.setText(msg, "utf-8", "html"); // 내용
		return message;
	}

	// 메일 보내기
	public boolean mailSend(MailAuth mail, String check) throws Exception {

		if (check.equals("signUp")) {
			System.out.println("signUp = ");
			MimeMessage message = createMessageSignUp(mail.getUserId().toString());
			try {// 예외처리
					// 인증번호DB 등록, 등록일, 만료일 DB 저장
					// 메일 보내는 년월일시간
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
				Date cre_dt = new Date();
				// 메일 보내는 년월일시간 + 1일
				Date exr_dt = createAuthKeyNextDT();
				mail.setCreDt(formatter.format(cre_dt));
				mail.setExpDt(formatter.format(exr_dt));
				mailSender.send(message);
				
			} catch (MailException es) {
				es.printStackTrace();
				return false;
			}


		} else if (check.equals("pwdChange")) {

			String authKey = mailAuthKey();
			String code = createCode(authKey);
			MimeMessage message = createMessage(mail.getUserId().toString(), code);
			try {// 예외처리
					// 인증번호DB 등록, 등록일, 만료일 DB 저장
					// 메일 보내는 년월일시간
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
				Date cre_dt = new Date();
				// 메일 보내는 년월일시간 + 1일
				Date exr_dt = createAuthKeyNextDT();
				mail.setCreDt(formatter.format(cre_dt));
				mail.setExpDt(formatter.format(exr_dt));
				mail.setAuthKey(Integer.parseInt(code));

				int data = memberRepository.mergeInsertAuthInfo(mail);
				if (data > 0) {
					// MailAuth result = memberRepository.selectAuthInfo(mail);
					// System.out.println("result = " + result.getAuthKey());
				}
				mailSender.send(message);
			} catch (MailException es) {
				es.printStackTrace();
				return false;
			}
		}

		return true;

	}

	// 인증 코드 만들기
	public static String mailAuthKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 6; i++) {
			key.append((rnd.nextInt(10)));
		}
		return key.toString();
	}

	public String createCode(String ePw) {
		return ePw.substring(0, 6);
	}

	public Date createAuthKeyNextDT() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		date = cal.getTime();

		return date;

	}

}
