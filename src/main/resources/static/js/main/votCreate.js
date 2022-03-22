
$("#header").load("html/header.html");
$("#side").load("html/side.html");

window.onload = function() {


	$(".check1").css('display', 'none');
	$("#profile").css('display', 'block');
	$(".check2").css('display', 'block');

	let vote_id = "";
	let vot_type = "";
	let vot_title = "";
	let vot_info = "";
	let start_dt = "";
	let end_dt = "";
	let vote_grp = "";
	let user_id = "";
	let start_mms = "";
	let end_mms = "";
	let grpMember = "";
	let grpMemberHtml = "";
	let authorityArr = new Array();


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
	var str = cookieData[3];
	let cookieGd = str.substr(0, 1)

	console.log("cookie = " + cookie)

	if (cookieGd == 1) {
		$("#admin").css("display", "block");
		$("#voteCompensationListLink").css("display", "block");
		$("#votCreateLink").css("display", "block");
		$("#totalLink").css("display", "block");
	}	
	if (cookieGd == 3) {
		alert("사용할 수 없는 페이지 입니다.")
		location.href = "/main"
	}
	//어워드 개설 기간 formatter
	$(function() {
		$('.datetimepicker1').datetimepicker({
			lang: 'ko',
			format: 'Y-m-d H:i'
		});
		$('.datetimepicker2').datetimepicker({
			lang: 'ko',
			format: 'Y-m-d H:i'
		})
	})

	// 새로 사용할 vot_id 값
	$.ajax({
		type: "post",
		url: "/api/votCreate/votSelectId",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			console.log("select_ID = " + JSON.stringify(data))
			vote_id = data.VOT_ID
			$("#award_id_value").text(vote_id);
		}
	})

	$.ajax({
		type: "post",
		url: "/api/votCreate/voselectGrp",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			$("#profile_name").text("| " + cookieNm);
			var selectList = data.body;

			for (var z = 0; z < selectList.length; z++) {
				if (cookieGrp == selectList[z].GRP_CD) {
					console.log("grpNm = " + selectList[z].GRP_NM)
					$("#profile_grade").text(selectList[z].GRP_NM);
				}
				if (cookieGd == 1) {
					$("#profile_rank").text("| admin");
				} else if (cookieGd == 2) {
					$("#profile_rank").text("| 부서장");
				} else if (cookieGd == 3) {
					$("#profile_rank").text("| 사원");
				}
			}

			grpSelectList(selectList)
			grpChoiList(selectList)

		}
	})

	function grpSelectList(selectList) {

		var x = 0;
		console.log("data = " + JSON.stringify(selectList))
		for (x; x < selectList.length; x++) {
			var grpCd = selectList[x].GRP_CD;
			var grpNm = selectList[x].GRP_NM;
			var option = $("<option value=" + grpCd + ">" + grpNm + "</option>");
			$('#vot_grp_select').append(option);
		}

	}
	function grpChoiList(selectList) {

		var z = 0;
		for (z; z < selectList.length; z++) {
			var grpChoiNm = selectList[z].GRP_NM;
			var grpChoiCd = selectList[z].GRP_CD;
			var choiOption = $("<option value=" + grpChoiCd + ">" + grpChoiNm + "</option>");
			$('#vot_grp_choice_select').append(choiOption);
		}

	}


	$("#vot_grp_select").change(function() {
		console.log("select 1")
		var votGrpType = $("#vot_grp_select").val();
		grpMemberHtml = "";

		$.ajax({
			type: "post",
			url: "/api/votCreate/votSelectGrpInfo",
			dataType: "json",
			data: votGrpType,
			contentType: "application/json",
			success: function(data) {
				grpMember = data.body;

				console.log("grpMember = " + JSON.stringify(grpMember))
				grpMemberList(grpMember);

			}
		});
	});

	$("#vot_grp_choice_select").change(function() {
		console.log("select 2")
		var votGrpChoiType = $("#vot_grp_choice_select").val();

		grpMemberHtml = "";
		$.ajax({
			type: "post",
			url: "/api/votCreate/votSelectGrpInfo",
			dataType: "json",
			data: votGrpChoiType,
			contentType: "application/json",
			success: function(data) {
				var member = data.body;
				console.log("member = " + JSON.stringify(member))
				if (votGrpChoiType != 'fail') {
					for (var z = 0; z < member.length; z++) {
						authorityArr.push(member[z].GRADE_CD + ',' + member[z].USER_ID);
						console.log("arr = " + authorityArr)
					}
				}
				console.log("!!!!member = " + JSON.stringify(member))

			}
		});
	});

	function grpMemberList(grpMember) {

		var i = 0;

		if (grpMember.length == 0) {

			$("#award_data_change").children('div').remove();
			//$("#award_data_change").html('');
		} else {
			for (i; i < grpMember.length; i++) {
				var memberNM = grpMember[i].USER_NM;
				var memberID = grpMember[i].USER_ID;
				var grade = grpMember[i].GRADE_CD;
				grpMemberHtml += '<div id="member">' + '<label id="check_name">' + memberNM + '</label>' +
					'<input type="checkbox" id="check_input" name="test_check" value=' + memberNM + ',' + memberID +
					',' + grade + ' checked>' + '</div>'
			}
			$("#award_data_change").html(grpMemberHtml);
		}
	}

	$("#vot_type_select").change(function() {

		var votType = $("#vot_type_select").val();

		console.log("votType = " + votType);


		if (votType == 'award') {
			console.log("award!!")
			//$("#award_data_change1").css('display', 'none');
			//$("#award_data_change").css('display', 'block');
		} else if (votType == 'survey') {
			alert("개발중으로 사용하실 수 없습니다.")
			//$("#award_data_change").css('display', 'none');
			//$("#award_data_change1").css('display', 'block');
		}

	});

	let arr = new Array();
	$(document).on('change', 'input[name="test_check"]', function(e) {
		//var chk_e = e.target.value;
		var chk = $(this).val();
		var check = this.checked;
		if (check == true) {
			arr.push(chk);
		} else if (check == false) {
			arr.pop();
		}
	});

	$("#vot_start_date").change(function() {
		start_dt = $("#vot_start_date").val();
		if (start_dt !== undefined || start_dt !== null && start_dt !== "") {
			$("#start_icon").css('display', "none")
		} else {
			$("#start_icon").css('display', "block")
		}

	})

	$("#vot_end_date").change(function() {
		end_dt = $("#vot_end_date").val();
		if (end_dt !== undefined || end_dt !== null && end_dt !== "") {
			$("#end_icon").css('display', "none")
		} else {
			$("#end_icon").css('display', "block")
		}

		if (start_dt === end_dt) {
			alert("시작시간과 종료시간이 같습니다. 시간을 변경해주시기 바랍니다.")
			start_dt = $("#vot_start_date").val("");
			end_dt = $("#vot_end_date").val("");
		}
	})

	$("#create_vot").click(function() {

		user_id = cookieId;
		console.log("vote_id = " + vote_id)
		vot_type = $("#vot_type_select").val();
		console.log("vot_type = " + vot_type)
		vote_grp = $("#vot_grp_select").val();
		vot_title = $("#vot_name_text").val();
		vot_info = $("#info_text").val();
		start_mms = $("#start_mms").prop("checked");
		console.log("start_mms = " + start_mms)
		end_mms = $("#end_mms").prop("checked");
		console.log("end_mms = " + end_mms)
		console.log("arr = " + arr)
		console.log("authorityArr = " + authorityArr)

		$('input:checkbox[name=test_check]').each(function(index) {
			if ($(this).is(":checked") == true) {
				console.log($(this).val());
				arr.push($(this).val());
			}
		})


		if (vote_grp == '0') {
			alert("그룹을 지정하지 않았습니다.")
		} else if (vot_title == "") {
			alert("제목을 입력해주시기 바랍니다.")
		} else if (vot_info == "") {
			alert("내용을 입력해주시기 바랍니다.")
		} else if (start_dt == "" || end_dt == "") {
			alert("날짜를 입력해주시기 바랍니다.")
		} else {
			var obj = {
				"vote_id": vote_id,
				"member_id": cookieId,
				"vote_type": vot_type,
				"vote_grp": vote_grp,
				"vote_title": vot_title,
				"vote_info": vot_info,
				"start_dt": start_dt,
				"end_dt": end_dt,
				"user_id": user_id,
				"start_mms": start_mms,
				"end_mms": end_mms,
				"vote_member": arr,
				"auth_member": authorityArr
			}

			console.log("obj = " + JSON.stringify(obj))

			$.ajax({
				type: "post",
				url: "/api/votCreate",
				data: JSON.stringify(obj),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("data = " + JSON.stringify(data));
					console.log("data = " + data.result);
					if (data.result === "fail") {
						//info error
						alert("투표개설에 실패하였습니다. 관리자에게 문의 바랍니다.")
					} else if (data.result === "fail1") {
						//member error
						alert("투표개설에 실패하였습니다. 관리자에게 문의 바랍니다.")
					} else if (data.result === "success") {
						alert("투표개설이 완료 되었습니다. 메인페이지로 돌아갑니다.")
						window.location.href = "/main";
					}

				}

			});
		}








		$("#servey_list_insert").click(function() {

			console.log("click event")
		});

	})

	$('#totalLink').click(function() {
		alert("개발중입니다.")
	})

	$(header).on('click', '#logout', function() {

		var check = confirm("로그아웃 하시겠습니까?")

		if (check) {
			$.removeCookie("zinnaworks")
			window.location.href = "login"
		}

	})

};
