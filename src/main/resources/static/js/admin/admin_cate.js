


//관리자 카테고리관리 js
window.onload = function() {
	$("#header").load("/html/header.html");
	$("#side").load("/html/admin_side.html");
	
	var cateManageHtml="";
	var GRP_CD_ID="";
	var GRP_CD_NM="";
	
	$.ajax({
		url: '/api/admin/cate',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			cateList(data);

		},
		error: function(e) {
			alert("일시적인 에러입니다. 다시 시도해 주세요.");
		}
	});
	
	// 상품코드, 상품명list
	function cateList(data) {
		
		
		for(let i = 0 ; i<data.length; i++) {
			cateManageHtml += '<div class="cateManage_'+(i+1)+'">'+ data[i].GRP_CD_ID +"&nbsp"+data[i].GRP_CD_NM  +'</div>';
		}
		
		$('#leftContWrap').html(cateManageHtml);
		
		$('div[class^="cateManage_"]').click(function(){
			GRP_CD_ID=$(this).html().split("&nbsp;")[0];
			GRP_CD_NM=$(this).html().split("&nbsp;")[1];
			setValue(GRP_CD_ID, GRP_CD_NM);
		});
		
	}
	
	// 조직관리 내 오른쪽 박스 값 설정 
	function setValue(code, name) {
		$('#cateCd').val(code);
		$('#cateNm').val(name);
	}
	
};		

	
