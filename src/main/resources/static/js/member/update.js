window.onload = function() {
	
	$("#header").load("html/loginHeader.html");

	$("#pwdUpdateSubmit").click(function() {
		var email = $("#email").val();
		var code = $("#code").val();
		var pwd = $("#pwd").val();
		var pwd1 = $("#pwd1").val();

		console.log("email = " + email)
		console.log("code = " + code)
		console.log("pwd = " + pwd)
		console.log("pwd1 = " + pwd1)

		if (pwd != pwd1) {
			alert("동일한 비밀번호를 입력해 주세요.");
		} else {
			var member = {
				"email": email,
				"pwd": pwd,
				"code": code
			};
			$.ajax({
				type: "post",
				url: "/api/update",
				data: JSON.stringify(member),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("Success = " + JSON.stringify(data.result));
					if (data.result == "error") {
						alert("인증코드가 잘못 되었습니다.")
					} else if (data.result == false) {
						alert("이메일이 잘못되었습니다.")
					} else if (data.result == true) {
						alert("비밀번호 변경이 완료 되었습니다.")
						window.location.href = "http://localhost:8080/login"
					}
				}

			})

		}

	});

};

