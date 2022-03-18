
$("#header").load("html/loginHeader.html");
window.onload = function() {

	console.log("signUpAuth page")
	
	let temp = location.href.split("?");
	var email = temp[1];
	
	console.log("email = " + email)


	$("#signUpAuthSubmit").click(function() {

		$.ajax({
			type: "post",
			url: "/api/signUpAuth",
			data: email,
			dataType: "json",
			contentType: "application/json",
			success: function(data) {
				console.log("Success = " + JSON.stringify(data.result));
				if (data.result == "error") {
					alert("인증코드가 잘못 되었습니다.")
				} else if (data.result == false) {
					alert("이메일이 잘못되었습니다.")
				} else if (data.result == "success") {
					alert("회원가입 인증이 완료 되었습니다. 로그인 페이지로 이동 합니다")
					window.location.href = "/login"
				}
			}

		})


	});

};