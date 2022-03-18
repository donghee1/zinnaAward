

$("#header").load("html/loginHeader.html");


window.onload = function() {

	var userId = "";
	var userId = "";
	var member = {};
	$.removeCookie("zinnaworks")

	$("#loginSubmit").click(function() {

		userId = $("#userId").val();
		passwd = $("#passwd").val();

		if (userId == "" || userId == "undefined") {
			alert("아이디를 입력해주시기 바랍니다.")
		} else if (passwd == "" || passwd == "undefined") {
			alert("패스워드를 입력해주시기 바랍니다.")
		} else {


			$.ajax({
				type: "post",
				url: "/api/checkAuthLogin",
				data: userId,
				dataType: "json",
				contentType: "application/json",
				success: function(data) {

					console.log("data = " + JSON.stringify(data.result))

					if (data.result == "fail") {
						alert("가입 대기중인 계정입니다")
					} else if (data.result == "success") {
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
									console.log("data = " + JSON.stringify(data.body));

									var userInfo = data.body;

									var str = userInfo.userId;
									var data = str.split('@');
									var user_id = data[0];

									console.log("userNm = " + userInfo.userNm)
									console.log("userNm = " + userInfo.gradeCd)
									var user_nm = userInfo.userNm;
									var user_grp = userInfo.grpCd;
									var grade = userInfo.gradeCd;

									obj = user_id + "," + user_nm + "," + user_grp + "," + grade

									$.cookie("zinnaworks", JSON.stringify(obj));
									window.location.href = "/main";
								}
							}

						})


					}

				}

			})











		}

	});
};





