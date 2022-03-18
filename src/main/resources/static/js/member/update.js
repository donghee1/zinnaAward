
$("#header").load("html/loginHeader.html");
window.onload = function() {

	let password = "";
	let password2 = "";

	$(document).on('focusout', '#pwd', function() {

		console.log("????")
		var password = $("#pwd").val();
		var num = password.search(/[0-9]/g);
		var eng = password.search(/[a-z]/g);
		var spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

		console.log("num = " + num)
		console.log("eng = " + eng)
		console.log("spe = " + spe)
		if (password.length < 8){
		alert("비밀번호는 8자리 이상으로 입력바랍니다.");
	}else {

		if (num < 0) {
			alert("비밀번호에 숫자 1자리 이상 입력해주시기 바랍니다");
		}
		if (eng < 0) {
			alert("비밀번호에 영문 1자리 이상 입력해주시기 바랍니다");
		}
		if (spe < 0) {
			alert("비밀번호에 특수문자 1자리 이상 입력해주시기 바랍니다");
		}

	}
	if (password.match(/[^a-zA-Z0-9`~!@@#$%^&*|₩₩₩'₩";:₩/?]/) != null) {
		alert("비밀번호는 숫자와 영문 또는 특수문자만 입력할 수 있습니다.");
	}

})

$(document).on('focusout', '#pwd1', function() {
	console.log("anjdia")
	password = $('#pwd').val();
	password2 = $('#pwd1').val();
	if (password2 != "") {
		console.log("pwd1 = ")
		if (password2 != password) {

			console.log(password)
			console.log(password2)
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

