//관리자 카테고리관리 js
$("#header").load("/html/header.html");
$("#side").load("/html/adminSide.html");

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
	
	if(cookieGd != 1){
		alert("관리자에게 문의하기 바랍니다")
		window.location.href = "/main"
	}

	var cateManageHtml = "";
	var GRP_CD_ID = "";
	var GRP_CD_NM = "";
	let code = "";
	let codeNm = "";


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
		url: '/api/admin/cate',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			console.log("data = " + JSON.stringify(data))

			code = data.body;


			cateList(code);

		},
		error: function(e) {
			alert("일시적인 에러입니다. 다시 시도해 주세요.");
		}
	});

	// 상품코드, 상품명list
	function cateList(data) {
		for (let i = 0; i < data.length; i++) {
			cateManageHtml += '<div id="mainWrapContent" class="grpList" value="' + data[i].GRP_CD_ID + ',' + data[i].GRP_CD_NM + '">' + '<span id="grp_code">' + data[i].GRP_CD_ID + '</span>' +
				'<span id="grp_nm">' + data[i].GRP_CD_NM + '</span>' + '</div>'
		}
		$('#leftContWrap').html(cateManageHtml);
	}


	$(document).on('click', '.grpList', function() {

		var data = $(this).attr('value')

		data = data.split(',');
		code = data[0]
		codeNm = data[1];
		$("#right_grp_cd_value").text(code)
		$("#downGroupCd").val(codeNm);
	})

	$('#delCodeSubmit').click(function() {

		var productNm = $('#downGroupCd').val();
		var productCode = $('#right_grp_cd_value').text();

		if (productNm == "" && productCode == "") {
			alert("상품코드를 입력해주시기 바랍니다.")
		} else {


			var check = confirm("상품코드를 삭제하시겠습니까?")

			if (check) {

				$.ajax({
					url: '/api/admin/cateDeleteInfo',
					type: 'POST',
					data: productCode,
					contentType: "application/json",
					dataType: 'json',
					success: function(data) {
						if (data.result == "fail") {
							alert("관리자에게 문의 바랍니다.")
						} else if (data.result == "success") {
							alert("상품코드 삭제가 완료되었습니다.")
							window.location.href = "/cate"
						}

					},
					error: function(e) {
						alert("일시적인 에러입니다. 다시 시도해 주세요.");
					}
				});

			}
		}

	})


	$('#newCodeSubmit').click(function() {
		$.ajax({
			url: '/api/admin/cateNewCodeInfo',
			type: 'POST',
			contentType: "application/json",
			dataType: 'json',
			success: function(data) {

				var codeData = data.body;

				var codeNm = codeData.substring(0, 3)
				var codeNo = codeData.substring(3)

				codeNo = Number(codeNo) + 1;
				codeNo = String(codeNo)
				if (codeNo.length == 1) {
					codeNo = 0 + codeNo
				}

				var newCode = codeNm + codeNo
				var newCodeHtml = '<div id=mainWrapContent class="grpList" value="' + newCode + ',' + '">' +
					'<span id="grp_code">' + newCode + '</span>' + '</div>'
				$("#leftContWrap").append(newCodeHtml)
			},
			error: function(e) {
				alert("일시적인 에러입니다. 다시 시도해 주세요.");
			}
		});
	})

	$('#insertCodeSubmit').click(function() {

		var code = $('#right_grp_cd_value').text();
		var codeNm = code.substring(0, 3)
		var productNm = $('#downGroupCd').val()

		var obj = {
			"code": code,
			"codeNm": codeNm,
			"productNm": productNm

		}
		
		var check = confirm("상품코드를 생성하시겠습니까?")
		
		if(check){
			$.ajax({
			url: '/api/admin/cateCodeInsertInfo',
			type: 'POST',
			data: JSON.stringify(obj),
			contentType: "application/json",
			dataType: 'json',
			success: function(data) {
				if(data.result == "fail"){
					alert("관리자에게 문의 바랍니다")
				}else if(data.result == "success"){
					alert("생성이 완료 되었습니다.")
					window.location.href="/cate"
				}

			},
			error: function(e) {
				alert("일시적인 에러입니다. 다시 시도해 주세요.");
			}
		});

		}

	})
	
	$(header).on('click', '#logout', function(){
		
		var check = confirm("로그아웃 하시겠습니까?")
		
		if(check){
			$.removeCookie("zinnaworks")
			window.location.href="login"
		} 
		
	})

};	