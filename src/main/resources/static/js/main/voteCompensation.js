

$("#header").load("html/header.html");
$("#side").load("html/side.html");

window.onload = function() {

	let temp = location.href.split("?");
	let vote_no = temp[1];
	let cookie = $.cookie("zinnaworks");
	
	if(cookie == null || cookie == undefined || cookie == ""){
		alert("관리자에게 문의하기 바랍니다")
		window.location.href="/login"
	}

	let cookieData = cookie.split(",");
	var str = cookieData[0];
	let cookieId = str.substr(1);
	let cookieNm = cookieData[1];
	let cookieGrp = cookieData[2];
	var str = cookieData[3];
	let cookieGd = str.substr(0, 1)
	let color = [];
	let giftCd = "";
	let gift = "";
	let result_date = "";
	let vote_id = "";

	let file = "";

	if (cookieGd > 1) {
		$("#admin").css("display", "none");
	}
	if(cookieGd == 3){
		alert("사용할 수 없는 페이지 입니다.")
		location.href="/main"
	}

	//어워드 개설 기간 formatter
	$(function() {
		$('.datetimepicker').datetimepicker({
			lang: 'ko',
			format: 'Y-m-d H:i'
		})
	})


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
			console.log("info = " + JSON.stringify(data.info));
			console.log("member = " + JSON.stringify(data.member));

			info = data.info;
			vote_id = info[0].VOT_ID;
			grp = data.grp;
			member = data.member;
			rate = data.rate;

			console.log("rate = " + JSON.stringify(rate))
			console.log("NM = " + info[0].VOT_NM);
			console.log("STATUS = " + info[0].STATUS);

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
			$("#vot_member").text(info[0].YCNT)
			$("#un_vot_member").text(info[0].NCNT)

			for (var i = 0; i < member.length; i++) {

				if (member.length > 1) {
					$("#choice_member").text(member[0].VOT_NAME + '외 ' + member.length - 1);
				} else {
					$("#choice_member").text(member[0].VOT_NAME)
				}
			}

			let arr = new Array();
			var data = {};
			//rate 값을 추출 투표자, 투표수가 높은 투표자
			for (var i = 0; i < rate.length; i++) {
				arr.push(rate[i].VOT_NAME)
				if (!data[rate[i].VOT_NAME]) {
					data[rate[i].VOT_NAME] = 0 + 1;
				} else if (data[rate[i].VOT_NAME] && rate[i].VOT_GRADE <= 3) {
					data[rate[i].VOT_NAME] = + 2;
				} else {
					data[rate[i].VOT_NAME] = + 1;
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
					keysArr.push(rate[i].VOT_NAME)
					valueArr.push(value)
				}

				if (value < min) {
					min = value;
					minVoter = rate[i].VOT_NAME
					console.log("minVoter = " + minVoter)
				} else if (value <= max) {
					max = value;
					maxVoter = rate[i].VOT_NAME
					console.log("maxVoter = " + maxVoter)
				}
			}
			if (max === min) {
				$("#choice_member").text(maxVoter);
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
				var data = valueArr[i] / info[0].NCNT * 100 + '%';
				data = data.substr(0,4);
				console.log("data = " + data)
				chartHtml += '<div class="chart_stats" id="person_info">' +
					'<div id="person_color" style="background-color:' + color[i] + ';">' + '</div>'
					+ '<span id="person_stats">' + keysArr[i] + '(' + data + '% ' + valueArr[i] + 'p' + ')' + '</span>'
					+ '</div>'
			}
			$("#chartData").html(chartHtml)


			$.ajax({
				type: "post",
				url: "api/giftSelectInfo",
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					let option = "";
					console.log("data code = " + JSON.stringify(data))
					console.log("figt size = " + data.result.length)
					if (data.result.length > 0) {
						$('#result_gift_select').empty();
						for (var i = 0; i < data.result.length; i++) {
							option = $('<option value="' + data.result[i].GRP_CD_ID + '">' + data.result[i].GRP_CD_NM + '</option>')
							console.log("??? = " + option)
							$('#result_gift_select').append(option)
						}
					}
				}
			})


			$(document).on("keyup", "#result_money", function(e) {
				$(this).val(addCommas($(this).val().replace(/[^0-9]/g, "")));
			});
			function addCommas(x) {
				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}

			$(document).on("change", "#result_date", function(e) {
				checkData = $("#result_date").val();
				if (checkData) {
					$('#start_icon').css("display", "none")
				} else {
					$('#start_icon').css("display", "block")
				}

			});

			$(document).on("change", "#ex_file", function(e) {
				file = $('#ex_file').val();

				if (file) {
					$('#file_btn').text("선택완료")
				}
			});
			
			$("#result_submit").click(function() {
				giftCd = $('#result_gift_select').val();
				var money = $('#result_money').val();
				gift = money.replace(/,/g, '');
				if (gift == undefined || gift == "") {
					alert("상품금액을 입력해주시기 바랍니다.")
				}
				var result_date = $("#result_date").val();
				$("#ex_text").val(vote_id)
				console.log("vote_id = " + $("#ex_text").val())
				var obj = {
					"vote_no": vote_id,
					"giftCd": giftCd,
					"gift": gift,
					"result_date": result_date
				}
				let fileForm = $('#uploadForm')[0];
				var formData = new FormData(fileForm);
				console.log("file = " + JSON.stringify(formData))

				$.ajax({
					type: "post",
					url: "/api/giftFinalUpdate",
					enctype: "multipart/form-data",
					processData: false,
					contentType: false,
					data: formData,
					success: function(data) {

						if(data.result == "Pfail"){
							alert("사진 업로드 실패")
						}else if(data.result == "fail"){
							alert("관리자에게 문의 바랍니다")						
						}else if(data.result == "success"){
							alert("변경이 완료 되었습니다.")
							window.location.href="/main"	
						}
					}
				})

			});




		}, error: function(e) {
			alert("ajax 통신 실패.");
			//	location.href = "/login";
		}
	});
	
	$(header).on('click', '#logout', function(){
		
		var check = confirm("로그아웃 하시겠습니까?")
		
		if(check){
			$.removeCookie("zinnaworks")
			window.location.href="login"
		} 
		
	})
};