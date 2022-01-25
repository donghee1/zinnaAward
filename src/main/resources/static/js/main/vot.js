window.onload = function() {

	$("#header").load("html/header.html");
	$("#side").load("html/side.html");
};		
	
	
	//탑 메뉴바 설정 필요함
	

	console.log("test main page!!!")
	var list = "";
	var cnt = "";
	var awardDate = "";
	var teamCnt = "";
	var votPerson = "";
	let awardHtml = "";
	let historyHtml = "";
	
	$.ajax({
		url: '/api/main',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			console.log(JSON.stringify(data));
			console.log("data = " + data);
			//리스트의카운트가 될수있다.
			awardDate = data.body[0].startDt + '시 ~ ' + data.body[0].endDt + ' 시';
			var teamPerson = data.body[0].votItem;
			teamCnt = teamPerson.split('|').length + ' 참여';
			
			//award_list 값을 기준으로 동적 테이블은 생성한다
			//alert("award_list")
			list = data.body;
			cnt = data.body.length;
			console.log("cnt = " + cnt);
			console.log("list = " + list)
			console.log("list = " + JSON.stringify(list));


			if (data.result == "fail") {
				console.log("데이터가 없습니다")
			} else if (data.result == "result") {
				console.log("깔깔")
			}

			awardList(data);

		},
		error: function(e) {
			alert("일시적인 에러입니다. 다시 시도해 주세요.");
			location.href = "/login";
		}
	});

	function awardList(data) {
		
		for (var i = 0; i < cnt; i++) {
			awardHtml += '<div id="divList" class ="awardList" >' +
						'<div id="awardList_top" class="AL">' + data.body[i].votNm + '</div>' +
						'<div id="awardList_mid" class="AL">' +
							'<div id="award-date" class="middle">' + awardDate + '</div>' +
							'<div id="award-person" class="middle">' + '1 / ' + teamCnt + '</div>' + 
						'</div>' +
						'<div id="awardList_bottm" class="AL">' +
							'<button id="awardGo" class="btn btn-primary bottom-btn" type="button">투표참여</button>' +
							'<button id="awardDetail" class="btn btn-primary bottom-btn" type="button">결과확인</button>' +
						'</div>' + 
					'</div>';
		}

	//	$('#awardList').html(awardHtml);
		
		console.log(JSON.stringify(data));
		for(var j = 0; j < cnt; j ++){
			historyHtml += '<div id="divList" class="HL">' +
						   		'<div id="his_top_title" class="his_top_title">' + "22-01 ICT인프라 어워드" + '</div>' +
						   		'<div id="his_top_award" class="his_top_title">' + '1위 한동희(1회)' + '</div>' +
						   '<div id= "historyList_mid" class="HL">' + '사진 첨부' + '</div>' +
						   '</div>' 
						    
		}
		
	//	$('#historyList').html(historyHtml);
	}
	
	


	
	