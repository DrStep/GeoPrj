var chatCheck=false;
var startWidth=true;

$(document).ready(init); 
function init(){ 
	$(".chat").draggable({containment:'window'}); 
  $(".add_window").draggable({containment: 'window'});
}

function openDiv (id) {
	$(".brd").css({display:'none'});
	$('#'+id).fadeIn(200);
	$('#on-button').fadeOut(50);
};

function closeDiv(){
	$(".brd").fadeOut(200);
	$('#on-button').fadeIn(200);};

function startChat(){
	if (chatCheck==false){
		$('#delete').animate({width:'-=40px'},200);
		$('#delete').text('Удалить..');
		$('.brd').delay(50).animate({width:'-=180px'},100);
		$('.chat').delay(50).fadeIn(100);
		chatCheck=true;
	}
	else{
		$('.chat').fadeOut(10);
		$('.brd').animate({width:'+=180px'},200);
		$('#delete').animate({width:'+=40px'},200);
		$("#delete").queue(function(){
			$('#delete').text('Удалить из друзей');
			$(this).dequeue();
		});
		chatCheck=false;
	}
};

function messageCheck(){
	$(".brd").fadeOut(200);
	$('#on-button').fadeIn(200);
	$('.chat').fadeIn(0);
	$('.chat').animate({height:'0px'},0);
	$('.chat').animate({height:'+=200px'},150);
	chatCheck=true;
}