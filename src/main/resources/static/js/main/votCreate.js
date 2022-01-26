window.onload = function() {

	$("#header").load("html/header.html");
	$("#side").load("html/side.html");


};
$(".check1").css('display', 'none');
$("#profile").css('display', 'block');
$(".check2").css('display', 'block');



$("#vot_type_select").change(function() {

	var votType = $("#vot_type_select").val();

	console.log("votType = " + votType);

	if (votType == 'award') {
		console.log("award!!")
		$("#award_data_change1").css('display', 'none');
		$("#award_data_change").css('display', 'block');
	} else if (votType == 'survey') {
		$("#award_data_change").css('display', 'none');
		$("#award_data_change1").css('display', 'block');
	}


});













var i = 2;
var e = 5;

//chart(i,e);
function chart(i, e) {

	//그룹에 대한 인워수 파악
	// 그 인원을 뽑아서 나누고,
	// 나눈 인원 개개인의 투표 현황을 파악해야 한다.
	// 그룹 개인의 투표수 / 그룹 전체인원 * 100;
	// 색상 7개 정도를 변수로 선언
	// for문을 돌릴 떄 색상을 차례대로
	// 동적태그를 통해 원형 그래프를 만들어야함
	//console.log(i)
	//console.log(e)
	var result = i / e * 100;

	console.log("result = " + result)

}






$("#servey_list_insert").click(function() {

	console.log("click event")



});






