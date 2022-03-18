
$("#header").load("html/loginHeader.html");
window.onload = function() {
	
	let password = "";
	let password2 = "";
	
	$(document).on('blur', '#pwd', function() {

		console.log("????")
		
		var num = password.search(/[0-9]/g);
		var eng = password.search(/[a-z]/g);
		
		console.log("num = " + num)
		console.log("eng = " + eng)

		password = $(this).val();
		var reg = "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$";

		if (num < 0 || eng < 0 && password.length < 8) {
			alert("8자리 비밀번호 입력 및 영문 또는 숫자를 혼합하여 입력해주시기 바랍니다.")
		}

	})

	$(document).on('blur', '#pwd1', function() {

		password2 = $(this).val();

		if (password != undefined && !password == "" && password.length >= 8) {
			if (password2 != password) {
				alert("비밀번호가 일치하지 않습니다.")
			}

		}
	})

	$("#pwdUpdateSubmit").click(function() {
		var email = $("#email").val();
		var code = $("#code").val();
		var pwd = $("#pwd").val();
		var pwd1 = $("#pwd1").val();

		console.log("email = " + email)
		console.log("code = " + code)
		console.log("pwd = " + pwd)
		console.log("pwd1 = " + pwd1)

		if (email == "") {
			alert("이메일을 입력해주시기 바랍니다.")
		} else if (code == "") {
			alert("이메일로 전달받은 인증코드를 입력해주시기 바랍니다.")
		} else if (pwd == "") {
			alert("비밀번호를 입력해주시기 바랍니다.")
		} else if (pwd1 == "") {
			alert("비밀번호를 입력해주시기 바랍니다.")
		} 

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
						window.location.href = "/login"
					}
				}

			})

		}

	});

};

