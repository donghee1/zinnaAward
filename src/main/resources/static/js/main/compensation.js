window.onload = function(){
	$("#header").load("html/header.html");
	$("#side").load("html/side.html");
}

	
	$.ajax({
		url: '/api/compensationList',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			if(data.compensationList.length == 0){
				// 아직 개설된 게시글이 없음
			}else{
				compensationList = data.compensationList;
				getCompensationList_callBack(compensationList);
			}
			
		},error: function(e) {
			alert("ajax 통신 실패.");
			location.href = "/login";
		}
	});
	
	function getCompensationList_callBack(compensationList){
		let status_change;
		let resultId_change;
		const tableBodyList = $("#listForm");
		const compensation_list = $(".compensation_list");
		compensation_list.html("");
		for(var loopCounter in compensationList){
			var tr = $("<tr class='compensation_list'>");
			
			var td_votId = $('<td class="firstTd">').text(compensationList[loopCounter].votId);
			var td_votNm = $('<td class="firstTd">').text(compensationList[loopCounter].votNm);
			var td_personCnt = $('<td class="firstTd">').text(compensationList[loopCounter].participantCnt + "/" + compensationList[loopCounter].personCnt);
			var td_start_end_dt = $('<td class="firstTd">').text(compensationList[loopCounter].startDt + ' ~ ' + compensationList[loopCounter].endDt);
			var td_userId = $('<td class="firstTd">').text(compensationList[loopCounter].userId);
			if(compensationList[loopCounter].status == 0){
				status_change = '진행중';
			}else{
				status_change = '종료';
			}
			var td_status = $('<td class="firstTd">').text(status_change);
			if(compensationList[loopCounter].resultId == 0){
				resultId_change = '미정';
			}else{
				resultId_change = compensationList[loopCounter].resultId;
			}
			var td_resultId = $('<td class="firstTd">').text(resultId_change);
			
			// 수상자가 아닌경우 클릭 못하게 나중에 if조건(지금은 DB에 수상자 없음)
			td_compensationBtn = $('<td class="firstTd">').append("<span style='' class='spanBtn' onclick='compensationBtn("+compensationList[loopCounter].votId+")'>입력" + "</span>");

			tr.append(td_votId); // 게시글 ID
			tr.append(td_votNm); // 투표명
			tr.append(td_userId); // 게시자
			tr.append(td_start_end_dt); // 기간
			tr.append(td_status); // 진행중인지, 종료인지 상태값
			tr.append(td_personCnt); // 총 사람 수
			tr.append(td_resultId); // 수상자
			tr.append(td_compensationBtn); //버튼
			
			tableBodyList.append(tr);
		}
	}
	
	function compensationBtn(index){
		let votId = index
		$.ajax({
		url: '/api/rewardReceiving',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		success: function() {
			
		},error: function(e) {
			alert("ajax 통신 실패.");
			location.href = "/login";
		}
	});
	}
	
	
	