window.onload = function() {

	$("#loginSubmit").click(function() {
		
		var email = $("#email").val();
		console.log("email = " + email)
		
		var passwd = $("#passwd").val();
		console.log(passwd)
		var member = {
			"email":email,
			"pwd":passwd
		};
		$.ajax({
			type:"post",
			url:"/api/login",
			data:JSON.stringify(member),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				console.log("Success = " + JSON.stringify(data.result));
				if(data.result == null){
					alert("없는 정보 입니다.")
				}else if(data.result == false){
					alert("아이디 또는 비밀번호가 틀렸습니다..")
				}else if(data.result == "success"){
					window.location.href = "http://localhost:8080/main"
				}
			}
			
		})
	
	}); 
	
};

