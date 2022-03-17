
$("#header").load("html/header.html");
$("#side").load("html/adminSide.html");

window.onload = function() {


	let temp = location.href.split("?");
	let vote_no = temp[1];

	let cookie = $.cookie("zinnaworks");

	if (cookie == null || cookie == undefined || cookie == "") {
		alert("관리자에게 문의하기 바랍니다")
		window.location.href = "/login"
	}

	let cookieData = cookie.split(",");
	var str = cookieData[0];
	let cookieId = str.substr(1);
	let cookieNm = cookieData[1];
	let cookieGrp = cookieData[2];
	let grade = cookieData[3];
	let cookieGd = grade.substr(0, 1)

	let grp = "";
	let grpSize = "";
	let member = "";
	
	if(cookieGd != 1){
		alert("관리자에게 문의하기 바랍니다")
		window.location.href = "/main"
	}
	if(cookieGd > 1){
		alert("사용할 수 없는 페이지 입니다.")
		location.href="/main"
	}


	$.ajax({
		type: "post",
		url: "/api/votCreate/voselectGrp",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			grp = data.body;
			grpSize = grp.length;
			$("#profile_name").text("| " + cookieNm);
			var result = data.body;
			for (var z = 0; z < result.length; z++) {
				if (cookieGrp == result[z].GRP_CD) {
					$("#profile_grade").text(result[z].GRP_NM);
				}
				if (cookieGd == 1) {
					$("#profile_rank").text("| admin");
				} else if (cookieGd == 2) {
					$("#profile_rank").text("| 부서장");
				} else if (cookieGd == 3) {
					$("#profile_rank").text("| 사원");
				}
			}


			$.ajax({
				type: "post",
				url: "/api/memberSelectInfo",
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					member = data.body;
					console.log("member = " + JSON.stringify(member))
					memberList(member)
				}
			})

			function memberList(member) {

				let tableBodyList = $("#listForm");
				let memberList = $(".memberList");
				memberList.html("");
				for (var i = 0; i < member.length; i++) {
					var tr = $('<tr class="memberList">');
					var td_user_nm = $('<td>').text(member[i].USER_NM);
					var selectBox = $('<select class="grpSelect" id="grpSelect' + i + '">');

					var option = "";
					for (var z = 0; z < grp.length; z++) {
						//option = '<option value="' + grp[z].GRP_CD + '">' + grp[z].GRP_NM + '</option>'
						option += '<option value="' + grp[z].GRP_CD + '">' + grp[z].GRP_NM + '</option>'
					}
					selectBox.append(option);

					var td_user_grp = $('<td>').append(selectBox);
					var td_user_phone = $('<td>').append('<input type="text" class="memberInput" id="phone' + i + '" value="' + member[i].PHONENUM + '">')
					var td_user_entry = $('<td>').append('<input type="text" class="memberInput" id="entryDt' + i + '" value="' + member[i].ENTRY_DT + '">')
					var td_grade_check = $('<td>').append('<input type="checkbox" class="memberCheck" id="memberCheck' + i + '" value="2" >')
					if (member[i].GRADE_CD == 2) {
						var td_grade_check = $('<td>').append('<input type="checkbox" class="memberCheck" id="memberCheck' + i + '" value="2" checked>')
					}
					var td_buttons = '<td><button type="button" class="btn btn-primary" value="' + member[i].USER_ID + ',' + i + '" id="memberUpdateInfo">' + '수정' + '</button>' +
						'<button type="button" class="btn btn-danger" value="' + member[i].USER_ID + ',' + i + '" id="memberDeleteInfo">' + '삭제' + '</button>' +
						'</td>'

					tr.append(td_user_nm)
					tr.append(td_user_grp)
					tr.append(td_user_phone)
					tr.append(td_user_entry)
					tr.append(td_grade_check)
					tr.append(td_buttons)

					tableBodyList.append(tr)
					
					var selectId = '#grpSelect' + i;
					
					$(selectId).val(member[i].GRP_CD).prop("selected", true)
					
					
				}
			}

		}
	})

	$(document).on('click', '#memberUpdateInfo', function() {

		var check = $(this).val()
		check = check.split(',');
		var no = check[1];
		var userId = check[0];
		var phone = $('#phone' + no + '').val();
		var entryDt = $('#entryDt' + no + '').val();
		var gradeCd = '';
		var gradeCheck = $('#memberCheck' + no + '').is(':checked');
		var grpCd = $('#grpSelect' + no + '').val();
		var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
		var date = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;

		if (gradeCheck) {
			gradeCd = $('#memberCheck' + no + '').val();
		} else {
			gradeCd = '3';
		}

		if (gradeCheck === true) {

		} else {

		}

		if (phone == "" && entryDt == "") {
			alert("변경할 값이 없습니다.")
		} else if (regPhone.test(phone) === false) {
			alert("연락처 정보가 잘못 되었습니다.")
		} else if (date.test(entryDt) === false) {
			alert("입사일 정보가 잘못 되었습니다.")
		} else {

			var obj = {
				"userId": userId,
				"phone": phone,
				"entryDt": entryDt,
				"gradeCd": gradeCd,
				"grpCd": grpCd
			}

			var check = confirm("회원정보를 변경하시겠습니까?")

			if (check) {
				$.ajax({
					type: "post",
					url: "/api/memberUpdateInfo",
					data: JSON.stringify(obj),
					dataType: "json",
					contentType: "application/json",
					success: function(data) {
						console.log("data = " + JSON.stringify(data))

						if (data.result == "fail") {
							alert("관리자에게 문의 바랍니다.")
						} else if (data.result == "success") {
							alert("회원정보 변경이 완료 되었습니다.")
							window.location.href = "/management"
						}
					}
				})

			}

		}

	})

	$(document).on('click', '#memberDeleteInfo', function() {

		console.log("delete = ")

		var check = $(this).val()
		check = check.split(',');
		var no = check[1];
		var userId = check[0];

		console.log("no = " + no)
		console.log("userId = " + userId)


		var check = confirm("회원정보를 삭제하시겠습니까?")

		if (check) {
			$.ajax({
				type: "post",
				url: "/api/memberDeleteInfo",
				data: userId,
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("data = " + JSON.stringify(data))

					if (data.result == "fail") {
						alert("관리자에게 문의 바랍니다.")
					} else if (data.result == "success") {
						alert("회원정보 삭제가 완료되었습니다.")
						window.location.href = "/management"
					}
				}
			})

		}

	})
	$(header).on('click', '#logout', function() {

		var check = confirm("로그아웃 하시겠습니까?")

		if (check) {
			$.removeCookie("zinnaworks")
			window.location.href = "login"
		}

	})
};
