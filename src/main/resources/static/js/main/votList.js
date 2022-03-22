

$("#header").load("html/header.html");
$("#side").load("html/side.html");

window.onload = function() {
	let cookie = $.cookie("zinnaworks");
	
	if(cookie == null || cookie == undefined || cookie == ""){
		alert("관리자에게 문의하기 바랍니다")
		window.location.href="/login"
	}

	let cookieData = cookie.split(",");
	var str = cookieData[0];
	let cookieId = str.substr(1);
	console.log("cookieId = " + cookieId);
	let cookieNm = cookieData[1];
	let cookieGrp = cookieData[2];
	var str = cookieData[3];
	let cookieGd = str.substr(0, 1)
	cookieGd = Number(cookieGd)
	if (cookieGd == 1) {
		$("#admin").css("display", "block");
		$("#voteCompensationListLink").css("display", "block");
		$("#votCreateLink").css("display", "block");
		$("#totalLink").css("display", "block");
	}	
	
	let list = "";
	var cnt = "";
	var awardDate = "";
	var teamCnt = "";
	var votPerson = "";
	let titleHtml = "";
	let grade = "";
	let status = "";
	let award_result = "";
	let rateData = "";

	let pageCnt = 0;

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

	obj = {
			"page": pageCnt,
			"user": cookieNm,
			"grade": cookieGd
		}

	$.ajax({
		url: '/api/awardMainList',
		type: 'POST',
		contentType: "application/json",
		data: JSON.stringify(obj),
		dataType: 'json',
		success: function(data) {
			if (data.result == "fail") {
				console.log("데이터가 없습니다")
			} else if (data.result == "success") {
				//리스트의카운트가 될수있다.
				list = data.body;
				console.log("start list = " + JSON.stringify(list))
					list = data.body;
					listCnt = list[0].totalCnt
					console.log("listCnt = " + listCnt)
				pageLoad(listCnt)
				awardList(list);
				/*$.ajax({
					url: '/api/voteRate',
					type: 'POST',
					data: cookieId,
					contentType: "application/json",
					dataType: 'json',
					success: function(result) {
						rateData = result.body;
						console.log("list = " + JSON.stringify(list))

						awardList(list, rateData);
					}
				}); */
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
		//mainPageLoad(1);
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

		obj = {
			"page": page,
			"user": cookieNm,
			"grade": cookieGd
		}

		$.ajax({
			url: '/api/awardMainList',
			type: 'POST',
			contentType: "application/json",
			data: JSON.stringify(obj),
			dataType: 'json',
			success: function(data) {
				if (data.result == "fail") {
					console.log("데이터가 없습니다")
				} else if (data.result == "success") {
					//리스트의카운트가 될수있다.
					console.log("start rateData")
					list = data.body;
					
					listCnt = list.length
					console.log("listCnt = " + listCnt)
					awardList(list);
					/*$.ajax({
						url: '/api/voteRate',
						type: 'POST',
						data: cookieId,
						contentType: "application/json",
						dataType: 'json',
						success: function(result) {
							var rateReData = result.body;
							console.log("list = " + JSON.stringify(list))
							console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!rateData = " + JSON.stringify(rateReData))

							
						}
					});*/
				}
			}
		});
	}

	function awardList(list) {

		for (var i = 1; i < list.length; i++) {
			var startDt = list[i].START_DT;
			var sHour = startDt.split('-').reverse()[0];
			startDt = startDt.substr(0, 8) + ',' + sHour;
			var endDt = list[i].END_DT;
			var eHour = endDt.split('-').reverse()[0];
			endDt = endDt.substr(0, 8) + ',' + eHour;

			if (list[i].STATUS === 0) {
				status = "진행중";
			} else if (list[i].STATUS === 1) {
				status = "종료";
			}
			
			if (list[i].RESULT_ID == 0) {
				award_result = "미정"
			} else  {
				// 어워드가 종료되면 ajax 통해서우승자를 뽑아서 바꿔야함
				award_result = list[i].RESULT_NM;
			}

			var voterStatus = "";
			const tableBodyList = $('#listForm');
			console.log("grade = " + grade)
			//const award_List = $('.award_list');
			//award_List.html("");

			if (list[i].STATUS === 1 || cookieGrp != list[i].VOT_GRP) {
				voterStatus = "&nbsp;&nbsp;" + "-";
			} else if (rateData[i].RESULT_ID != 0) {
				voterStatus = "완료";
			} else {
				voterStatus = "투표";
			}
			//grade 체크 후 돋보기 표시를 보이게 할건지 문의

			var tr = $('<tr class="award_list">');
			var td_votId = $('<td class="firstTd">').text(list[i].VOT_ID);
			var td_votNm = $('<td>').text(list[i].VOT_NM)
			var td_userId = $('<td>').text(list[i].USER_ID)
			var td_start_end_dt = $('<td>').text(startDt + ' ~ ' + endDt);
			var td_status = $('<td>').text(status)
			var td_member = $('<td>').text(list[i].YCNT + ' / ' + list[i].NCNT)
			var td_result = $('<td>').text(award_result)
			var td_vote = '<td class="lastTd"><div class="tdStatus" id="' + "tdStatusColor" + [i] + '">' + voterStatus
				+ '</div>' + '<span class="voteDetail" value="' + list[i].VOT_ID + '">' + '<i class = "fas fa-search test" value="' + list[i].VOT_ID + '">' + '</i>' + '</span>'
				+ '</td>'


			tr.append(td_votId)
			tr.append(td_votNm)
			tr.append(td_userId)
			tr.append(td_start_end_dt)
			tr.append(td_status)
			tr.append(td_member)
			tr.append(td_result)
			tr.append(td_vote)

			tableBodyList.append(tr);

		}


		var tdStatusColor = "";
		for (var i = 0; i < list.length; i++) {
			tdStatusColor = "#tdStatusColor" + [i];
			if (list[i].STATUS === 1 || cookieGrp != list[i].VOT_GRP) {
				$(tdStatusColor).css("background-color", "unset");
			} else if (rateData[i].RESULT_ID != 0) {
				$(tdStatusColor).css("background-color", "lightgrey");
			} else {
				$(tdStatusColor).css("background-color", "red");
			}
		}


		$(document).on("click", ".voteDetail", function(e) {

			var vote_id = $(this).attr("value");

			console.log("teste = " + vote_id);
			location.href = "voteDetail?" + vote_id;

		});
	}
	
	$('#totalLink').click(function(){
		alert("개발중입니다.")		
	})

	$(header).on('click', '#logout', function() {

		var check = confirm("로그아웃 하시겠습니까?")

		if (check) {
			$.removeCookie("zinnaworks")
			window.location.href = "login";
		}

	})

};