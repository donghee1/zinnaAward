//$(document).ready(function(){

//});
var header = $("#header").load("html/header.html");

window.onload = function() {
	let list = "";
	let listCnt = "";
	var awardDate = "";
	var teamCnt = "";
	var votPerson = "";
	let awardHtml = "";
	let historyHtml = "";

	let cookie = $.cookie("zinnaworks");

	if (cookie == null || cookie == undefined || cookie == "") {
		alert("관리자에게 문의하기 바랍니다")
		window.location.href = "/login"
	}

	console.log("cookie = " + cookie)
	let cookieData = cookie.split(",");
	var str = cookieData[0];
	let cookieId = str.substr(1);
	let cookieNm = cookieData[1];
	let cookieGrp = cookieData[2];
	var str = cookieData[3];
	let cookieGd = str.substr(0, 1)
	console.log('cookieId = ' + cookieId)
	let firstCnt = 1;
	let awardTotalCnt = "";
	let historyTotalCnt = "";
	let CurrentPage = 1;
	let pageCnt = 0;

	//paging 처리를 위한 변수선언
	if (cookieGd == 1) {
		$("#admin").css("display", "block");
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
				console.log("?????")
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

	var status = 0;
	var obj = {
		"pageCnt": pageCnt,
		"user": cookieId,
		"grade": Number(cookieGd)
	}

	$.ajax({
		url: '/api/main/awardList',
		type: 'POST',
		data: JSON.stringify(obj),
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			if (data.result == "fail") {
				console.log("데이터가 없습니다")
				alert("데이터가 없습니다.")
			} else if (data.result == "success") {
				console.log("awardList = " + JSON.stringify(data));
				//리스트의카운트가 될수있다.
				var awardList = data.body;
				if (awardList == "" || awardList == undefined) {
					$('#newAwardList').css("visibility", "hidden")
				} else {
					awardTotalCnt = awardList.length
					makeAwardList(awardList);
				}

				if (awardTotalCnt < 3) {
					console.log("test smoking")
					$('#newAwardList').css("visibility", "hidden")
				}

				//메인 페이지 어워드 데이터 동적 태그 생성

				var obj = {
					"pageCnt": pageCnt,
				}
				$.ajax({
					url: '/api/main/historyList',
					type: 'POST',
					data: JSON.stringify(obj),
					contentType: "application/json",
					dataType: 'json',
					success: function(data) {
						if (data.result == "fail") {
							console.log("데이터가 없습니다")
							alert("데이터가 없습니다.")
						} else if (data.result == "success") {
							console.log("historyList = " + JSON.stringify(data));
							//리스트의카운트가 될수있다.
							var historyList = data.body;
							if (historyList == undefined) {
								$("#historyBtn").css('display', 'none')
							} else {
								historyTotalCnt = historyList[0].StatusCnt;
								//메인 페이지 어워드 데이터 동적 태그 생성
								makeHisotryList(historyList);
							}
						}
					}
				});
			}

		}
	});

	function makeAwardList(awardListData) {
		let i = 0;
		console.log("makeAwardList start = ")
		for (i; i < awardListData.length; i++) {
			var award_start_ymd = awardListData[i].START_DT.substring(0, [8])
			var award_start_day = awardListData[i].START_DT.substring(9, [11]) + '시'
			var award_end_ymd = awardListData[i].END_DT.substring(0, [8])
			var award_end_day = awardListData[i].END_DT.substring(9, [11]) + '시'
			var totalPerson = awardListData[i].NCNT;
			var choiPerson = awardListData[i].YCNT;
			var voteCheck = awardListData[i].VOT_CHECK;
			var resultBtn = '<button id="awardDetail" class="btn btn-primary bottom-btn" value="' + awardListData[i].VOT_ID + '" style="display:none;" type="button">결과확인</button>';
			var voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '" style="display:none;" type="button">투표참여</button>'
			var successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:none;" disabled>투표완료</button>';
			//계정 별 권한에 따른 버튼 노출

			if (cookieGd == 1) {
				var resultBtn = '<button id="awardDetail" class="btn btn-primary bottom-btn" value="' + awardListData[i].VOT_ID + '" style="display:block;" type="button">결과확인</button>';
				console.log("Btn admin start = ")
			} else if (cookieGd == 2) {
				if (voteCheck == 0) {
					console.log("Btn user start = ")
					voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '" type="button" style="display:block; left:3%;">투표참여</button>'
					var resultBtn = '<button id="awardDetail" class="btn btn-primary bottom-btn" value="' + awardListData[i].VOT_ID + '" style="display:block; left:6%; top:5px;" type="button">결과확인</button>';
				} else if (voteCheck != 0) {
					console.log("Btn success vote = ")
					successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:block; left:3%;" disabled>투표완료</button>'
					voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '" type="button" style="display:none;">투표참여</button>'
					var resultBtn = '<button id="awardDetail" class="btn btn-primary bottom-btn" value="' + awardListData[i].VOT_ID + '" style="display:block; left:6%; top:5px;" type="button">결과확인</button>';
				}
			} else if (cookieGd ==3) {
				if (voteCheck == 0) {
					console.log("Btn user start = ")
					voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '" type="button" style="display:block;">투표참여</button>'
				} else if (voteCheck != 0) {
					console.log("Btn success vote = ")
					successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:block; left:25%;" disabled>투표완료</button>'
				}
			}

			if (i > 2) {
				awardHtml += '<div id="divList" class ="awardList" style="display:none;">' +
					'<div id="awardList_top" class="AL">' + awardListData[i].VOT_NM + '</div>' +
					'<div id="awardList_mid" class="AL">' +
					'<div id="award-date" class="middle">' + award_start_ymd + ' ' + award_start_day + ' ~ ' + award_end_ymd + ' ' + award_end_day + '</div>' +
					'<div id="award-person" class="middle">' + choiPerson + ' / ' + totalPerson + '  참여' + '</div>' +
					'</div>' +
					'<div id="awardList_bottm" class="AL">' +
					voteBtn +
					successBtn +
					resultBtn +
					'</div>' +
					'</div>';
			} else {
				awardHtml += '<div id="divList" class ="awardList" >' +
					'<div id="awardList_top" class="AL">' + awardListData[i].VOT_NM + '</div>' +
					'<div id="awardList_mid" class="AL">' +
					'<div id="award-date" class="middle">' + award_start_ymd + ' ' + award_start_day + ' ~ ' + award_end_ymd + ' ' + award_end_day + '</div>' +
					'<div id="award-person" class="middle">' + choiPerson + ' / ' + totalPerson + '  참여' + '</div>' +
					'</div>' +
					'<div id="awardList_bottm" class="AL">' +
					voteBtn +
					successBtn +
					resultBtn +
					'</div>' +
					'</div>';

			}
		}
		$('#awardList').html(awardHtml);

		console.log("awardTotalCnt = " + awardTotalCnt)
		var awardSpan = awardTotalCnt - 3;


		var text = '외 ' + awardSpan + ' 건 진행중';
		$("#clickText").text(text)


		$("#awardListClick").click(function() {

			$(".awardList").css("display", "block");
			$("#newAwardList").css("display", "none");
			$("#footer").css("visibility", "hidden");
			$("#paging").css("display", "block");
			pageLoad(awardTotalCnt, "awardList")

		})
	}


	function makeHisotryList(historyListData) {
		for (var z = 0; z < historyListData.length; z++) {
			var imageSrc = historyListData[z].PHOTO_NM;
			var image = "";
			if (imageSrc == 0) {
				image = '<div id= "historyList_mid" class="HL">' + '사진없음' + '</div>'
			} else {
				image = '<div id= "historyList_mid" class="HL" style="background-image:url(' + imageSrc+ ');">'
			}
			if (z > 2) {
				historyHtml += '<div id="divList" class="historyList" style="display:none;">' +
					'<div id="his_top_title" class="his_top_title">' + historyListData[z].VOT_NM + '</div>' +
					'<div id="his_top_award" class="his_top_title">' + '1위 한동희(1회)' + '</div>' +
					image + '</div>' +
					'</div>'
			} else {
				historyHtml += '<div id="divList" class="historyList">' +
					'<div id="his_top_title" class="his_top_title">' + historyListData[z].VOT_NM + '</div>' +
					'<div id="his_top_award" class="his_top_title">' + '1위 한동희(1회)' + '</div>' +
					image + '</div>' +
					'</div>'
			}

		}
		$('#historyList').html(historyHtml);

		$("#historyBtn").click(function() {
			console.log("historyBtn = " + historyTotalCnt)
			$(".historyList").css("display", "block");
			$("#historyBtn").css("display", "none");
			$("#footer").css("top", "unset");
			$("#nav").css("display", "none");
			$("#paging").css("display", "block");
			pageLoad(historyTotalCnt, "hisotryList")
		})
	}


	function mainPageLoad(cnt, content) {
		console.log("mainPageLoad Start!!!")
		console.log("mainPageLoad cnt = " + cnt)
		console.log("mainPageLoad content = " + content)

		var obj = {
			"pageCnt": cnt,
		}


		if (content == "awardList") {
			$(function() {
				console.log("awardList test")
				$('#awardList').empty();
			})

			$.ajax({
				type: "post",
				url: "/api/main/awardList",
				data: JSON.stringify(obj),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					var awardList = data.body;
					if (data.result == "fail") {
						alert("관리자에게 문의 바랍니다")
					} else {
						var awardPageHtml = "";
						for (var i = 0; awardList.length; i++) {
							var award_start_ymd = awardListData[i].START_DT.substring(0, [8])
							var award_start_day = awardListData[i].START_DT.substring(9, [11]) + '시'
							var award_end_ymd = awardListData[i].END_DT.substring(0, [8])
							var award_end_day = awardListData[i].END_DT.substring(9, [11]) + '시'
							var totalPerson = awardListData[i].NCNT;
							var choiPerson = awardListData[i].YCNT;
							var voteCheck = awardListData[i].VOT_CHECK;
							console.log("voteCheck = " + voteCheck)
							var resultBtn = '<button id="awardDetail" class="btn btn-primary bottom-btn" type="button">결과확인</button>';
							var voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '"type="button">투표참여</button>'
							var successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:none;">투표완료</button>';
							//계정 별 권한에 따른 버튼 노출
							if (voteCheck == 0 && cookieNm == "관리자") {
								console.log("Btn admin start = ")
								successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:none;">투표완료</button>'
								voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '"type="button" style="display:none;">투표참여</button>'
							} else if (voteCheck == 0) {
								console.log("Btn user start = ")
								successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:none;">투표완료</button>'
								voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '"type="button" style="display:block;">투표참여</button>'
							} else if (voteCheck != 0) {
								console.log("Btn success vote = ")
								successBtn = '<button id="awardSuccess" class="btn btn-secondary bottom-btn" type="button" style="display:block;">투표완료</button>'
								voteBtn = '<button id="awardGo"  class="btn btn-primary bottom-btn"' + 'value="' + awardListData[i].VOT_ID + '"type="button" style="display:none;">투표참여</button>'
							}
							awardPageHtml += '<div id="divList" class ="awardList" style="display:none;">' +
								'<div id="awardList_top" class="AL">' + awardListData[i].VOT_NM + '</div>' +
								'<div id="awardList_mid" class="AL">' +
								'<div id="award-date" class="middle">' + award_start_ymd + ' ' + award_start_day + ' ~ ' + award_end_ymd + ' ' + award_end_day + '</div>' +
								'<div id="award-person" class="middle">' + choiPerson + ' / ' + totalPerson + '  참여' + '</div>' +
								'</div>' +
								'<div id="awardList_bottm" class="AL">' +
								voteBtn +
								successBtn +
								resultBtn +
								'</div>' +
								'</div>';

						}
						$('#awardList').html(awardPageHtml);
					}
				}
			})

		} else if (content == "hisotryList") {
			$(function() {
				console.log("tlqkf test")
				$('#historyList').empty();
			})

			$.ajax({
				type: "post",
				url: "/api/main/historyList",
				data: JSON.stringify(obj),
				dataType: "json",
				contentType: "application/json",
				success: function(data) {
					var historyList = data.body;
					if (data.result == "fail") {
						alert("관리자에게 문의 바랍니다")
					} else {
						var historyPageHtml = "";
						for (var z = 0; z < historyList.length; z++) {
							var imageSrc = historyList[z].PHOTO_NM
							var image = "";
							if (imageSrc == 0) {
								image = '<div id= "historyList_mid" class="HL">' + '사진없음' + '</div>'
							} else {
								image = '<div id= "historyList_mid" class="HL" style="background-image:url(' + imageSrc + ');">'
							}
							historyPageHtml += '<div id="divList" class="historyList">' +
								'<div id="his_top_title" class="his_top_title">' + historyList[z].VOT_NM + '</div>' +
								'<div id="his_top_award" class="his_top_title">' + '1위 한동희(1회)' + '</div>' +
								image + '</div>' +
								'</div>'
						}
						$('#historyList').html(historyPageHtml);
					}
				}
			})
		}
	}
	console.log("start click")
	$(document).on("click", "#awardGo", function(e) {

		var vot_id = e.target.value;

		console.log("awardGo = " + vot_id);

		location.href = "vote?" + vot_id;

	});

	$(document).on("click", "#awardDetail", function(e) {

		var vot_id = e.target.value;

		console.log("awardDetail = " + vot_id);

		location.href = "voteDetail?" + vot_id;

	});


	//page logic

	//pageLoad(firstCnt)

	function pageLoad(pageCnt, content) {
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
			}
		}).on('page', function(event, page) {
			mainPageLoad(page, content);
		});

	}

	$(header).on('click', '#logout', function() {

		var check = confirm("로그아웃 하시겠습니까?")

		if (check) {
			$.removeCookie("zinnaworks")
			window.location.href = "login"
		}

	})

};







