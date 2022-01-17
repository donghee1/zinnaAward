$( document ).ready( function() {
	
	/* 회원가입 폼 이벤트 처리 
		버튼 클릭 시 input 텍스트 체크 
		*/
	$("#registrySubmit").click(function(){
		
	let email = $("#email").val();
	let name = $("#name").val();
	let password = $("#password").val();
	let password2 = $("#password2").val();
	let team = $("#team").val();
	let entryDate = $("#entryDate").val();
	let phone = $("#phone").val();
		
		if(email == ""){
			alert("이메일을 입력해주시기 바랍니다.")
		}else if(name == ""){
			alert("성명을 입력해주시기 바랍니다.")
		}else if(password == ""){
			alert("비밀번호를 입력해주시기 바랍니다.")
		}else if(password2 == ""){
			alert("비밀번호를 입력해주시기 바랍니다.")
		}else if(entryDate == ""){
			alert("입사일을 입력해주시기 바랍니다.")
		}else if(phone == ""){
			alert("휴대폰 번호를 입력해주시기 바랍니다.")
		}
		
		
		console.log("email = " + email)
		console.log("name = " + name)
		console.log("password = " + password)
		console.log("phone = " + phone)
		
		let memberInfo = {"email" : email,
						"pwd" : password,
						"name" : name,
						"team" : team,
						"entryDate" : entryDate,
						"phone" : phone}
		
		$.ajax({
			type:"post",
			url:"/api/signUp",
			data: JSON.stringify(memberInfo),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				if(data.result == true){
					alert("회원가입이 완료 되었습니다.");
					window.location.href="/login";
				}else{
					alert("이메일이 중복되었습니다.");
				}
				
			}
			
		})
			
	});
	
});