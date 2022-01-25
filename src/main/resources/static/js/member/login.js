window.onload = function() {
	$("#header").load("html/header.html");

	var userId = "";
	var userId = "";
	var member = {};

	console.log('login papge !!!')

	$("#loginSubmit").click(function() {

		userId = $("#userId").val();
		console.log("userId = " + userId)
		passwd = $("#passwd").val();
		console.log(passwd)

		if (userId == "" || userId == "undefined") {
			alert("아이디를 입력해주시기 바랍니다.")
		} else if (passwd == "" || passwd == "undefined") {
			alert("패스워드를 입력해주시기 바랍니다.")
		} else {
			member = {
				"userId": userId,
				"passwd": passwd
			};
			$.ajax({
				type: "post",
				url: "/api/login",
				data: JSON.stringify(member),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("Success = " + JSON.stringify(data.result));
					if (data.result == null) {
						alert("없는 정보 입니다.")
					} else if (data.result == false) {
						alert("아이디 또는 비밀번호가 틀렸습니다..")
					} else if (data.result == "error") {
						alert("비밀번호가 틀렸습니다..")
					} else if (data.result == "success") {
						/*console.log("data = " + JSON.stringify(data.data));
						var name = data.data.userNm;
						var grp = data.data.grpCd;
						var grade = data.data.gradeCd;
						
						console.log("name = " + name)
						console.log("grp = " + grp)
						console.log("gradeCd = " + grade)*/
						window.location.href = "http://localhost:8080/main";
					}
				}

			})


		}



	});

};