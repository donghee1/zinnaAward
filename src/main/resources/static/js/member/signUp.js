

//회원가입 페이지
//헤더 값 변경 프로필
$("#header").load("html/loginHeader.html");
window.onload = function() {
	$("#profile").css('display', 'none');
	$("#logout").css('display', 'none');
	$("#admin").css('display', 'none');

	let password = "";
	let password2 = "";

	console.log("start signUp")


	/* 회원가입 폼 이벤트 처리 
		버튼 클릭 시 input 텍스트 체크 
		*/

	$.ajax({
		type: "post",
		url: "/api/votCreate/voselectGrp",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			var selectList = data.body;
			grpSelectList(selectList)
		}
	})


	function grpSelectList(selectList) {

		var x = 0;
		console.log("data = " + JSON.stringify(selectList))
		for (x; x < selectList.length; x++) {
			var grpCd = selectList[x].GRP_CD;
			var grpNm = selectList[x].GRP_NM;
			var option = $("<option value=" + grpCd + ">" + grpNm + "</option>");
			$('#team').append(option);
		}

	}#password

	$(document).on('focusout', '#password', function() {

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

	$(document).on('focusout', '#password2', function() {

		password2 = $(this).val();

		if (password != undefined && !password == "" && password.length >= 8) {
			if (password2 != password) {
				alert("비밀번호가 일치하지 않습니다.")
			}

		}
	})

	$("#registrySubmit").click(function() {

		let email = $("#email").val();
		let name = $("#name").val();
		password = $("#password").val();
		password2 = $("#password2").val();
		let team = $("#team").val();
		let entryDate = $("#entryDate").val();
		let phone = $("#phone").val();

		if (email == "") {
			alert("이메일을 입력해주시기 바랍니다.")
		} else if (name == "") {
			alert("성명을 입력해주시기 바랍니다.")
		} else if (password == "") {
			alert("비밀번호를 입력해주시기 바랍니다.")
		} else if (password2 == "") {
			alert("비밀번호를 입력해주시기 바랍니다.")
		} else if (entryDate == "") {
			alert("입사일을 입력해주시기 바랍니다.")
		} else if (phone == "") {
			alert("휴대폰 번호를 입력해주시기 바랍니다.")
		} else {

			console.log("email = " + email)
			console.log("name = " + name)
			console.log("password = " + password)
			console.log("phone = " + phone)

			let memberInfo = {
				"userId": email,
				"pwd": password,
				"name": name,
				"team": team,
				"entryDate": entryDate,
				"phone": phone
			}

			$.ajax({
				type: "post",
				url: "/api/signUp",
				data: JSON.stringify(memberInfo),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					if (data.result == true) {
						alert("회원가입을 위한 인증 메일을 보냈습니다. 메일 확인 후 진행해주시기 바랍니다.");
						window.location.href = "/login";
					} else {
						alert("이메일이 중복되었습니다.");
					}

				}

			})

		}

	});

};