
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
	let grade = cookieData[3];
	let cookieGd = grade.substr(0, 1)

	if (cookieGd > 1) {
		$("#admin").css("display", "none");
	}
	if (cookieGd > 1) {
		$("#admin").css("display", "none");
		$("#voteCompensationListLink").css("display", "none");
		$("#votCreateLink").css("display", "none");
		$("#totalLink").css("display", "none");
	}

	let memberListHtml = "";

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


	$.ajax({
		url: 'api/voteSelectList',
		type: 'POST',
		data: vote_no,
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			console.log("data = " + JSON.stringify(data.body));
			
			let memberList = data.body;
			if (memberList[0].status === 0) {
				$("#vote_span_value").text("진행중")
			} else {
				$("#vote_span_value").text("종료")
			}
			$("#vote_name_value").text(memberList[0].VOT_NM);
			$("#vote_info_value").text(memberList[0].VOT_DESC);


			voteMemberList(memberList);

		},
		error: function(e) {
			alert("일시적인 에러입니다. 다시 시도해 주세요.");
		}
	});

	function voteMemberList(memberList) {

		var i = 0;

		for (i; i < memberList.length; i++) {
			var memberNm = memberList[i].VOT_NAME;
			var memberCd = memberList[i].VOT_CD;
				
			memberListHtml += '<div id="vote_person_box">' +
				'<span class="vote_span" id="vote_person">' + memberNm + '</span>' + '</div>' +
				'<div id="vote_check_box">' +
				'<input type="checkbox" id="vote_check" value=' + memberCd + '>' + '</div>'
		}

		$("#vote_mid").html(memberListHtml);
	}


	let arr = new Array();
	$(document).on('change', 'input[id="vote_check"]', function(e) {
		//var chk_e = e.target.value;
		var chk = $(this).val();
		console.log("test = " + chk)
		var check = this.checked;
		if (check == true) {
			arr.push(chk);
			if (cookieGd == 3) {
				if (arr.length > 1) {
					alert("투표는 한 명만 가능합니다.")
				}
				if (chk == arr[1]) {
					console.log("arr0 =  " + arr)
					this.checked = false;
					arr.pop();
					console.log("arr2 =  " + arr)
				}
			}
			if (cookieGd > 3) {
				if (arr.length > 2) {
					alert("투표는 두 명만 가능합니다.")
				}
				if (chk == arr[2]) {
					console.log("arr0 =  " + arr)
					this.checked = false;
					arr.pop();
					console.log("arr2 =  " + arr)
				}
			}

		} else if (check == false) {
			arr.pop();
			console.log("arr2 = " + arr)
		}
	});


	$("#vote_submit").click(function() {
		
		if (arr.length == 0) {
			alert("투표 대상자를 체크해 주시기 바랍니다.")
		} else {

			var obj = {
				"vote_no": vote_no,
				"voterId": cookieId,
				"voterNm": cookieNm,
				"votedCd": arr,
				"voteGd": cookieGd
			};
			console.log("cookieno = " + vote_no)
			console.log("cookieid = " + cookieId)
			console.log("cookieNm = " + cookieNm)
			console.log("arr  = " + arr)



			$.ajax({
				url: 'api/vote',
				type: 'POST',
				data: JSON.stringify(obj),
				contentType: "application/json",
				dataType: 'json',
				success: function(data) {
					console.log(JSON.stringify(data))
					if (data.result == false) {
						alert("체크박스를 입력해주시기 바랍니다.")
					} else if (data.result == "updateFail") {
						alert("투표에 실해 하였습니다")
					} else if (data.result == "fail") {
						alert("투표권한이 없음으로 투표하실 수가 없습니다. ")
						//main 페이지로 이동
					}else if (data.result == "fail2") {
						alert("투표가 중복 되었습니다. 메인페이지로 돌아값니다")
						//main 페이지로 이동
					} else if (data.result == "success") {
						alert("투표를 완료하였습니다. 메인 페이지로 돌아갑니다.")
						window.location.href="/main"
					}
				},
			});

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





