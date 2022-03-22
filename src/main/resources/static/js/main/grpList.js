
$("#header").load("html/header.html");
$("#side").load("html/adminSide.html");

window.onload = function() {


	let temp = location.href.split("?");
	let vote_no = temp[1];

	let cookie = $.cookie("zinnaworks");

	console.log("cookie = " + cookie)
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

	if (cookieGd != 1) {
		alert("관리자에게 문의하기 바랍니다")
		window.location.href = "/main"
	}
	if(cookieGd == 3){
		alert("사용할 수 없는 페이지 입니다.")
		location.href="/main"
	}

	let grpCd = "";



	$.ajax({
		type: "post",
		url: "/api/votCreate/voselectGrp",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
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
		}
	})


	var groupManageHtml = "";
	var GRP_CD = "";
	var GRP_NM = "";
	var PRE_NM = "";
	var PRE_CD = "";
	var DOWNGROUPCD = "";

	$.ajax({
		url: '/api/admin',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			groupList(data);

		},
		error: function(e) {
			location.reload();
		}
	});

	// 부서코드, 부서명list
	let grpNm = "";
	let preCd = "";
	function groupList(data) {
		console.log("data = " + JSON.stringify(data))
		// 상위부서에 대한 info 배열

		for (let i = 0; i < data.length; i++) {
			groupManageHtml += '<div id="mainWrapContent" class="grpList" value="' + data[i].GRP_CD + '">' + '<span id="grp_code">' + data[i].GRP_CD + '</span>' +
				'<span id="grp_nm">' + data[i].GRP_NM + '</span>' + '</div>'
		}

		$('#leftContWrap').html(groupManageHtml);

		let sum = "";
		let min = "";
		$(document).on('click', '.grpList', function(e) {
			grpCd = $(this).attr('value')


			for (var i = 0; i < data.length; i++) {

				sum = Number(grpCd) + 1000;
				min = Number(grpCd) + -1000;

				if (grpCd == 10000 && data[i].GRP_CD == 10000) {
					$('.grp_cd_span_value').text(grpCd)
					$('.grp_upcd_span_value').text(data[i].GRP_NM)
					grpNm = data[i].GRP_NM;
					preCd = grpCd;
				} else if (grpCd == data[i].GRP_CD && grpCd > min && grpCd <= sum) {
					$('.grp_cd_span_value').text(grpCd)
					$('.grp_upcd_span_value').text(data[i].PRE_GRP_NM)
					grpNm = data[i].GRP_NM;
					preCd = data[i].PRE_GRP_CD;
				}
			}
		});
	};




	$('#downGroupSubmit').click(function() {

		let grpCd = $("#right_grp_cd_value").text();
		console.log("grpCd = " + grpCd)
		console.log("grpCd = " + grpNm)
		let newNm = $("#downGroupCd").val();

		if (!newNm) {
			alert("하위부서를 입력해주시기 바랍니다.")

		} else {

			console.log("TLqkf = " + grpCd)
			console.log("newNm = " + newNm)
			console.log("preCd = " + preCd)

			dataArr = {};

			dataValue = {
				"grpCd": grpCd,
				"preGrpNm": grpNm,		// 현재 그룹 이름 
				"grpNm": newNm,
				"preGrpCd": preCd
			};

			dataArr = dataValue;

			$.ajax({
				url: '/api/admin.do',
				type: 'POST',
				data: JSON.stringify(dataArr),
				contentType: "application/json",
				dataType: 'json',
				success: function(data) {

					console.log("data = " + JSON.stringify(data))


					if (data.result == "fail") {
						alert("관리자에게 문의 바랍니다.")
					} else {
						alert("하위부서 생성이 완료 되었습니다.")
						window.location.href = "/admin"
					}
				},
				error: function() {
					alert("일시적인 에러입니다. 다시 시도해 주세요.");
				}
			});
		}

	});



	$('#delGroupSubmit').click(function() {

		var grp_cd = $('#right_grp_cd_value').text();

		console.log("삭제")
		console.log("grpCd = " + grp_cd)

		var check = confirm(grp_cd + "그룹코드를 삭제하시겠습니까?")
		if (check == true) {
			$.ajax({
				url: '/api/grpDeleteInfo',
				type: 'POST',
				data: grp_cd,
				contentType: "application/json",
				dataType: 'json',
				success: function(data) {

					console.log("data = " + JSON.stringify(data))


					if (data.result == "fail") {
						alert("관리자에게 문의 바랍니다.")
					} else {
						alert("그룹코드 삭제가 완료 되었습니다.")
						window.location.href = "/admin"
					}
				},
				error: function() {
					alert("일시적인 에러입니다. 다시 시도해 주세요.");
				}
			});
		}
	});

	$(header).on('click', '#logout', function() {

		var check = confirm("로그아웃 하시겠습니까?")

		if (check) {
			$.removeCookie("zinnaworks")
			window.location.href = "login"
		}

	})
};
