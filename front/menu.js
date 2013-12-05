var chatCheck=false;
var startWidth=true;
var openCheck=false;
var prevId = '';

$(document).ready(init); 
function init(){ 
	$(".chat").draggable({containment:'window'}); 
}

function openDiv (id) {
	if (openCheck) {
		$('#friend_block').fadeOut(100);
		$('#event_block').fadeOut(100);
		$("#"+prevId).animate({width:'-=1260px'},500);
		$('#'+id).css({display:'block'});
		if (id==prevId) {
			$('#'+id).animate({width:'+=1260px'},500);
		}
		else {
			$('#'+id).delay(500).animate({width:'+=1260px'},500);
		}
	}
	else{
		$(".brd").css({width:'0px'});
		$('#friend_block').css({display:'none'}); 
		$('#event_block').css({display:'none'}); 
		$('#'+id).css({display:'block'});
		$('#'+id).animate({width:'+=1260px'},500);
	}
	if (openCheck) {
		$('#friend_block').delay(800).fadeIn(100);
		$('#event_block').delay(800).fadeIn(100);
	}
	else { 
		$('#friend_block').delay(400).fadeIn(100);
		$('#event_block').delay(400).fadeIn(100);
	} 
	openCheck = true;
	prevId = id;
};

function closeDiv(){
	$('#friend_block').fadeOut(100);
	$('#event_block').fadeOut(100);
	$(".brd").animate({width:'-=1260px'},500);
	$(".brd").queue(function(){
		$(".brd").css({display:'none'});
		$(this).dequeue();	
	});
	openCheck = false;
}

function startChat(){
	if (chatCheck==false){
		$('#delete').animate({width:'-=40px'},200);
		$('#delete').text('Удалить..');
		$('#recommend').animate({width:'-=80px'},200);
		$('#recommend').text('Порекомендовать..');
		$('.brd').delay(50).animate({width:'-=180px'},100);
		$('.chat').delay(50).fadeIn(100);
		chatCheck=true;
	}
	else{
		$('.chat').fadeOut(10);
		$('.brd').animate({width:'+=180px'},200);
		$('#delete').animate({width:'+=40px'},200);
		$('#recommend').animate({width:'+=80px'},200);
		$("#delete").queue(function(){
			$('#delete').text('Удалить из друзей');
			$('#recommend').text('Порекомендовать мероприятие/место');
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
