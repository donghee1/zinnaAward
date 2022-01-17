window.onload = function() {

	$("#mailSubmit").click(function() {
		
		var email = $("#email").val();
		
		var data = {"email" : email};
		console.log("email = " + email)
		
		$.ajax({
			type:"post",
			url:"/api/mail",
			data:JSON.stringify(data),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				console.log("Success = " + JSON.stringify(data.result));
				alert("메일이 정상적으로 발송 되었습니다.")
				if(data.result == false){
					alert("없는 정보 입니다. 인증받을 이메일을 입력해 주세요.")
				}
			}
			
		})
	
	}); 
	
};

