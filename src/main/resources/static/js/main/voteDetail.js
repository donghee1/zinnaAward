$("#header").load("html/header.html");
$("#side").load("html/side.html");
$(".check1").css('display', 'none');

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
	var str = cookieData[3];
	let cookieGd = str.substr(0, 1)
	let pieChartData = "";
	let color = [];
	let grp_name = "";

	$(function() {
		$('.datetimepicker').datetimepicker({
			lang: 'ko',
			format: 'Y-m-d H:i'
		})
	})
	

	if (cookieGd == 3) {
		alert("사용할 수 없는 페이지 입니다.")
		window.location.href="/login"
	}

	if (cookieGd == 1) {
		$("#admin").css("display", "block");
		$("#voteCompensationListLink").css("display", "block");
		$("#votCreateLink").css("display", "block");
		$("#totalLink").css("display", "block");
	}

	let start_dt = "";
	let end_dt = "";

	let status = "";
	let type = "";

	let info = "";
	let grp = "";
	let member = "";
	let rate = "";
	let memberHtml = "";

	$.ajax({
		type: "post",
		url: "/api/votCreate/voselectGrp",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			$("#profile_name").text("| " + cookieNm);
			var result = data.body;
			grp_name = result;
			console.log("result = " + JSON.stringify(result))
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



	//어워드 개설 기간 formatter


	var obj = {
		"vote_no": vote_no,
		"userId": cookieId
	};

	$.ajax({
		url: 'api/voteSelectDetail',
		type: 'POST',
		data: JSON.stringify(obj),
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {

			info = data.info;
			grp = data.grp;
			member = data.member;
			rate = data.rate;
			console.log("rate = " + JSON.stringify(rate))
			var totalCnt = 0;
			
			for(var x = 0; x < rate.length; x++){
				if(rate[x].VOT_GRADE == 3){
					totalCnt = totalCnt + 1;
				}else if(rate[x].VOT_GRADE == 2){
					totalCnt = totalCnt + 2;
				}
			}
			if (info[0].STATUS === 0) {
				status = "진행중"
			} else {
				status = "종료"
			}
			$("#award_stats_value").text(status);
			$("#award_id_value").text(info[0].VOT_ID)
			$("#award_id_value").text(info[0].VOT_ID)

			if (info[0].VOT_TYP == "VOT") {
				type = "어워드"
			} else if (info[0].VOT_TYP == "VOT2") {
				type = "설문조사"
			}
			$("#award_type_value").text(type)

			for (var i = 0; i < grp.length; i++) {
				if (info[0].VOT_GRP === grp[i].GRP_CD) {
					$("#award_grp_target_value").text(grp[i].GRP_NM)
				}
			}
			$("#award_name_value").val(info[0].VOT_NM)
			$("#info_text").val(info[0].VOT_DESC)
			var sStr = info[0].START_DT;
			var eStr = info[0].END_DT
			var symd = sStr.substr(0, 8);
			var sHour = sStr.substr(9, 11);
			var eymd = eStr.substr(0, 8);
			var eHour = eStr.substr(9, 11);
			var sTime = symd + ',' + sHour
			var eTime = eymd + ',' + eHour

			$("#vot_start_date").val(sTime)
			$("#vot_end_date").val(eTime)

			console.log("size = " + member.length)

			for (var i = 0; i < member.length; i++) {

				var memberNM = member[i].VOT_NAME;
				memberHtml += '<div id="member">' + '<label id="check_name">' +
					memberNM + '</label>' + '</div>'
			}

			$("#award_data_change").html(memberHtml)
			$("#vot_member").text(info[0].YCNT)
			$("#un_vot_member").text(info[0].NCNT)


			let arr = new Array();
			var data = new Map();
			//rate 값을 추출 투표자, 투표수가 높은 투표자
			for (var i = 0; i < rate.length; i++) {
				arr.push(rate[i].VOT_NAME)
				if (!data[rate[i].VOT_NAME] && rate[i].VOT_GRADE == 3) {
					console.log("추천")
					data[rate[i].VOT_NAME] = 0 + 1;
				}else if(!data[rate[i].VOT_NAME] && rate[i].VOT_GRADE == 2){
					data[rate[i].VOT_NAME] = 0 + 2;
				} else if (rate[i].VOT_GRADE == 3) {
					console.log("사원 = " )
					data[rate[i].VOT_NAME] = data[rate[i].VOT_NAME] + 1;
				} else if(rate[i].VOT_GRADE == 2){
					console.log("부서장 = " )
					data[rate[i].VOT_NAME] = data[rate[i].VOT_NAME] + 2;
				}
			}

			console.log("data = " + JSON.stringify(data))

			var keys = Object.keys(data)
			var min = data[keys[0]]
			var max = data[keys[0]]
			var minVoter = "";
			var maxVoter = "";
			let keysArr = new Array();
			let valueArr = new Array();
			for (var i = 0; i < rate.length; i++) {
				var value = data[rate[i].VOT_NAME];
				if (keysArr.indexOf(rate[i].VOT_NAME) == -1) {
					console.log("test = " + i)
					keysArr.push(rate[i].VOT_NAME)
					valueArr.push(value)
				}
			}

			var test = Object.values(data);
			var choiceUser = Math.max(...test);

			var z = 0;
			for (var i = 0; i < keysArr.length; i++) {
				if (choiceUser == data[keysArr[i]]) {
					$("#choice_member").text("(" + keysArr[i] + ")")
					++z;
					console.log("z = " + z)
					if (z > 1) {
						z = z-1;
						$("#choice_member").text("(" + keysArr[i] + " 외" + z+"명" + ")")
						
					}
				}
			}
			console.log("keysArr = " + JSON.stringify(keysArr))
			console.log("valueArr = " + JSON.stringify(valueArr))

			var mainColor = ["crimson", "aqua", "blue", "coral", "chartreuse", "darksalmon"
				, "bisque", "deeppink", "olive", "bisque", "cyan", "darkorchid", "lightskyblue"
				, "khaki", "hotpink", "tan", "teal", "springgreen", "burlywood", "darkcyan"
				, "goldenrod", "blanchedalmond", "lightblue", "lightgray", "violet"]


			if (keysArr.length > mainColor.length) {
				for (var i = 0; i < keys.length; i++) {
					var colorCode = '#' + Math.round(Math.random() * 0xffffff).toString(16);
					color.push(colorCode)
				}
			} else {
				color = mainColor;
			}


			//chart.js 사용
			var ctx = $("#myChart")
			var myChart = new Chart(ctx, {
				type: 'pie',
				data: {
					datasets: [{
						data: valueArr,
						backgroundColor: color,
						label: 'line',
						borderWidth: 1,
						scaleBeginAtZero: true,
					}],
					labels: keysArr,
				}, options: {
					responsive: false,
					legend: {
						display: false
					}
				}
			});
			var chartHtml = "";
			for (var i = 0; i < keysArr.length; i++) {
				var data = valueArr[i] / totalCnt * 100;
				console.log("data = " + data)
				//data = data.substr(0, 3)
				data = Math.round(data);
				chartHtml += '<div class="chart_stats" id="person_info">' +
					'<div id="person_color" style="background-color:' + color[i] + ';">' + '</div>'
					+ '<span id="person_stats">' + keysArr[i] + '(' + data + '% ' + valueArr[i] + 'p' + ')' + '</span>'
					+ '</div>'
			}
			$("#chartData").html(chartHtml)

		}, error: function(e) {
			alert("일시적인 에러입니다. 다시 시도해 주세요.");
		}
	});

	$("#vot_start_date").change(function() {
		start_dt = $("#vot_start_date").val();
		if (start_dt !== undefined || start_dt !== null && start_dt != "") {
			$("#start_icon").css('display', "none")
		} else {
			$("#start_icon").css('display', "block")
		}

	})

	$("#vot_end_date").change(function() {
		end_dt = $("#vot_end_date").val();
		if (end_dt !== undefined || end_dt !== null && end_dt != "") {
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


	$("#award_update").click(function() {

		var title = $("#award_name_value").val();
		var desc = $("#info_text").val();
		var start_time = $("#vot_start_date").val();
		var end_time = $("#vot_end_date").val();
		var obj = {
			"vote_no": vote_no,
			"title": title,
			"desc": desc,
			"start_time": start_time,
			"end_time": end_time
		}

		var checkAlert = confirm("어워드 내용이 변경됩니다. 계속 진행 하시겠습니까?")

		if (checkAlert) {
			$.ajax({
				type: "post",
				url: "/api/voteUpdateDetail",
				data: JSON.stringify(obj),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("data = " + JSON.stringify(data))
					console.log("data = " + data.result)
					if (data.result == "success") {
						alert("변경이 완료 되었습니다.")
						location.reload();
					} else {
						alert("관리자에게 문의 바랍니다.")
					}
				}
			})
		} else {
			alert("어워드 내용 변경이 취소 되었습니다.")
		}


	});

	$("#award_end").click(function() {

		console.log("vote_no = " + vote_no)

		var checkAlert = confirm("어워드가 마감됩니다. 계속 진행 하시겠습니까?")

		if (checkAlert) {
			$.ajax({
				type: "post",
				url: "/api/voteEndStatus",
				data: vote_no,
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("data = " + JSON.stringify(data))
					console.log("data = " + data.result)
					if (data.result == "success") {
						alert("변경이 완료 되었습니다.")
						location.reload();
					} else {
						alert("관리자에게 문의 바랍니다.")
					}
				}
			})
		} else {
			alert("어워드 마감이 취소되었습니다.")
		}
	})


	$("#award_delete").click(function() {

		console.log("vote_no = " + vote_no)

		var checkAlert = confirm("어워드가 삭제됩니다. 계속 진행 하시겠습니까?")

		if (checkAlert) {
			$.ajax({
				type: "post",
				url: "/api/voteDelete",
				data:vote_no,
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					console.log("data = " + JSON.stringify(data))
					console.log("data = " + data.result)
					if (data.result == "success") {
						alert("변경이 완료 되었습니다.")
						location.reload();
					} else {
						alert("관리자에게 문의 바랍니다.")
					}
				}
			})
		} else {
			alert("어워드 삭제가 취소되었습니다.")
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