


//관리자 조직관리 js




window.onload = function() {

	$("#header").load("html/header.html");
	$("#side").load("html/admin_side.html");
	
	var groupManageHtml="";
	var GRP_CD="";
	var GRP_NM="";
	var PRE_NM="";
	var PRE_CD="";
	var DOWNGROUPCD ="";
	
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
	function groupList(data) {
		
		// 상위부서에 대한 info 배열
		var arrPRENM={};
		var arrPRECD={};
		
		for(let i = 0 ; i<data.length; i++) {
			groupManageHtml += '<div class="groupManage_'+(i+1)+'">'+ data[i].GRP_CD +"&nbsp"+data[i].GRP_NM  +'</div>';
			arrPRENM[i+1] = data[i].PRE_GRP_NM;
			arrPRECD[i+1] = data[i].PRE_GRP_CD;
		}
		
		$('#leftContWrap').html(groupManageHtml);
		
		$('div[class^="groupManage_"]').click(function(){
			GRP_CD=$(this).html().split("&nbsp;")[0];
			GRP_NM=$(this).html().split("&nbsp;")[1];
			PRE_NM=arrPRENM[$(this).attr("class").split("_")[1]];
			PRE_CD=arrPRECD[$(this).attr("class").split("_")[1]];
			setValue(GRP_CD, PRE_NM);
			
		});
		
		$('#downGroupSubmit').click(function(){
			DOWNGROUPC  = $('#downGroupCd').val()
			dataArr={};
			dataValue = {
					        "grpCd" : GRP_CD,
					        "preGrpNm" : GRP_NM,		// 현재 그룹 이름 
					        "grpNm" : DOWNGROUPC,
							"preGrpCd" : PRE_CD
					    	};
			dataArr = dataValue;
			
				$.ajax({
					url: '/api/admin.do',
					type: 'POST',
					/* data : JSON.stringify ({
					        "grpCd" : GRP_CD,
							"preGrpCd" : PRE_CD
					    	}),
					*/
					data :  JSON.stringify (dataArr),
					contentType: "application/json",
					dataType: 'json',
					success: function(data) {
					},
					error: function() {
						alert("일시적인 에러입니다. 다시 시도해 주세요.");
					}
				});
			});
	}
	
	// 조직관리 내 오른쪽 박스 값 설정 
	function setValue(code, name) {
		$('#groupCd').val(code);
		$('#preGroupCd').val(name);
	}
	
};		

	
