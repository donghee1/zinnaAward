

$("#header").load("html/header.html");
$("#side").load("html/side.html");
window.onload = function() {

	//탑 메뉴바 설정 필요함

	var list = "";
	var cnt = "";
	var awardDate = "";
	var teamCnt = "";
	var votPerson = "";
	let awardHtml = "";
	let historyHtml = "";
	let pageCnt = 0;

	let cookie = $.cookie("zinnaworks");
	
	console.log("cookie = " + cookie)
	
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

	if (cookieGd == 1) {
		$("#admin").css("display", "block");
		$("#voteCompensationListLink").css("display", "block");
		$("#votCreateLink").css("display", "block");
		$("#totalLink").css("display", "block");
	}
	if(cookieGd == 3){
		alert("사용할 수 없는 페이지 입니다.")
		location.href="/main"
	}

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
		"vote_id": cookieId,
		"pageCnt": pageCnt
	}

	$.ajax({
		url: '/api/voteCompensationList',
		type: 'POST',
		data: JSON.stringify(obj),
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			if (data.result == "fail") {
				alert("관리자에게 문의 바랍니다")
			} else if (data.result == "success") {

				list = data.body;
				var dataCnt = list[0].TotalCnt
				console.log("dataCnt = " + dataCnt)
				pageLoad(dataCnt)

				console.log("first list = " + JSON.stringify(list))

				getCompensationList_callBack(list)
			}

		}
	});

	function pageLoad(pageCnt) {
		//페이지당 보여줄 컨텐츠 개수
		console.log("start pageLoad")
		console.log("pageCnt = " + pageCnt)
		var listCnt = 6;
		var totalPage = pageCnt / listCnt;
		if (pageCnt % listCnt > 0) {
			totalPage++;
		}
		$('#pagination').twbsPagination({
			totalPages: totalPage, // 전체 페이지 
			startPage: 1, // 시작(현재) 페이지 
			visiblePages: 10, // 최대로 보여줄 페이지 
			prev: "‹", // Previous Button Label 
			next: "›", // Next Button Label 
			first: '«', // First Button Label 
			last: '»', // Last Button Label 
			onPageClick: function(event, page) { // Page Click event 
				console.info("current page : " + page);
				console.info("current event : " + event);
			}
		}).on('page', function(event, page) {
			mainPageLoad(page);
		});

	}

	function mainPageLoad(page) {
		console.log("page = " + page)
		$(function() {
			$('.award_list').remove();

		})
		var obj = {
			"vote_id": cookieId,
			"pageCnt": page
		}

		$.ajax({
			url: '/api/voteCompensationList',
			type: 'POST',
			data: JSON.stringify(obj),
			contentType: "application/json",
			dataType: 'json',
			success: function(data) {
				if (data.result == "fail") {
					alert("관리자에게 문의 바랍니다")
				} else if (data.result == "success") {

				var	list = data.body;
					var dataCnt = list.length;

					console.log("second list = " + JSON.stringify(list))

					getCompensationList_callBack(list)
				}

			}
		});
	}
	
	
	function getCompensationList_callBack(compensationList) {
		let status_change;
		let voterStatus = "";
		let resultId_change;
		console.log("compensationList = " + JSON.stringify(compensationList) )
		const tableBodyList = $("#listForm");
		const compensation_list = $(".compensation_list");
		compensation_list.html("");
		for (var loopCounter in compensationList) {
			var tr = $("<tr class='compensation_list'>");

			var td_votId = $('<td class="firstTd">').text(compensationList[loopCounter].VOT_ID);
			var td_votNm = $('<td class="">').text(compensationList[loopCounter].VOT_NM);
			var td_personCnt = $('<td class="">').text(compensationList[loopCounter].participantCnt + "/" + compensationList[loopCounter].personCnt);
			var td_start_end_dt = $('<td class="">').text(compensationList[loopCounter].START_DT + ' ~ ' + compensationList[loopCounter].END_DT);
			var td_userId = $('<td class="">').text(compensationList[loopCounter].USER_ID);
			if (compensationList[loopCounter].status == 0) {
				voterStatus = "-";
				status_change = '진행중';
			} else if (compensationList[loopCounter].RESULT_ID == 0) {
				voterStatus = '입력'
				status_change = '종료';
			} else {
				voterStatus = '완료'
			}
			var td_status = $('<td class="">').text(status_change);
			if (compensationList[loopCounter].RESULT_ID == 0) {
				resultId_change = '미정';
			} else {
				resultId_change = compensationList[loopCounter].RESULT_NM;
			}
			var td_resultId = $('<td class="">').text(resultId_change);

			// 수상자가 아닌경우 클릭 못하게 나중에 if조건(지금은 DB에 수상자 없음)
			//td_compensationBtn = $('<td class="lastTd">').append('<span style="" class="spanBtn" value='+compensationList[loopCounter].votId+' >' + voterStatus + '</span>')
			var td_compensationBtn = '<td class="lastTd"><div class="tdStatus" value="' + compensationList[loopCounter].VOT_ID + '" id="' + "tdStatusColor" + [loopCounter] + '">' + voterStatus
				+ '</div>' + '</td>'

			tr.append(td_votId); // 게시글 ID
			tr.append(td_votNm); // 투표명
			tr.append(td_userId); // 게시자
			tr.append(td_start_end_dt); // 기간
			tr.append(td_status); // 진행중인지, 종료인지 상태값
			tr.append(td_personCnt); // 총 사람 수
			tr.append(td_resultId); // 수상자
			tr.append(td_compensationBtn); //버튼

			tableBodyList.append(tr);


			var tdStatusColor = "";
			for (var loopCounter in compensationList) {
				tdStatusColor = "#tdStatusColor" + [loopCounter];
				if (compensationList[loopCounter].STATUS == 1 && compensationList[loopCounter].RESULT_ID != 0) {
					console.log("result != 0")
					$(tdStatusColor).css("background-color", "lightgrey");
				} else if (compensationList[loopCounter].STATUS == 1 && compensationList[loopCounter].RESULT_ID == 0) {
					$(tdStatusColor).css("background-color", "red");
				}
			}

		}

		$(document).on("click", ".tdStatus", function(e) {

			var vote_id = $(this).attr("value");

			console.log("teste = " + vote_id);

			location.href = "voteCompensation?" + vote_id;

			//	location.href = "voteDetail?" + vote_id;

		});
	}
	
	$('#totalLink').click(function(){
		alert("개발중입니다.")		
	})
	
	$(header).on('click', '#logout', function(){
		
		var check = confirm("로그아웃 하시겠습니까?")
		
		if(check){
			$.removeCookie("zinnaworks")
			window.location.href="login"
		} 
		
	})


};