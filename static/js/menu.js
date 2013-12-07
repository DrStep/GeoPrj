var chatCheck=false;
var startWidth=true;
var openCheck=false;
var prevId = '';
var locationOpen = false;

$(document).ready(init); 
function init(){ 
	$(".chat").draggable({containment:'window'}); 
}

function openDiv (id) {
  $('#podlogka').css({display:'block'});
  if (locationOpen ==true){
    $('#up-triangle').fadeOut(0);
    $('.locationWindow').animate({height:'-=590px'},120);
    $('.locationWindow').fadeOut(0);
  }
  $('#triangle').fadeOut(0);
  $('.dialogWindow').fadeOut(0);
  $('#m2 a').css({color:'#555'});
  if (id==prevId){
    return;
  }
	switch(id){
		case 'friends':
			//$('#m3').css({width:'+=20px'});
			//$('#m3').css({borderBottom:'1px transparent'});
			//$('#m3').css({background:'white'});
      //$('#m3 a').css({color:'white'});
      $('.changePoint1').css({background:'white'});
      break;
    case 'events':
      $('.changePoint2').css({background:'white'});
      break;
    case 'dialogs':
      $('#m7 a').css({color:'white'});
      break;
    case 'meetings':
      $('.changePoint3').css({background:'white'});
      break;
	}
	if (openCheck) {
  switch(prevId){
    case 'friends':
      $('.changePoint1').css({background:'transparent'});
      break;
    case 'events':
      $('.changePoint2').css({background:'transparent'});
      break;
    case 'dialogs':
      $('#m7 a').css({color:'#555'});
      break;
    case 'meetings':
      $('.changePoint3').css({background:'transparent'});
      break;
  }    
		$('#friend_block').fadeOut(0);
		$('#event_block').fadeOut(0);
    $('#meeting_block').fadeOut(0);
		if (id!=prevId) {
			$('#'+id).fadeOut(0);
		}
    if (prevId=='dialogs'){
      $("#"+prevId).animate({left:'+=327px'},300);
    }
    else {
		  $("#"+prevId).animate({width:'-=1260px'},500);
    }
		$('#'+prevId).fadeOut(0);
    if (prevId=='dialogs'){
      $('#'+id).fadeIn(0);
      $('#'+id).animate({width:'+=1260px'},500);

    }
		else {
			$('#'+id).delay(400).fadeIn(0);
      $('#'+id).delay(50).animate({width:'+=1260px'},500);
		}
	}
	else{
		$(".brd").css({width:'0px'});
		$('#friend_block').css({display:'none'}); 
		$('#event_block').css({display:'none'});
    $('#meeting_block').css({display:'none'});  
		$('#'+id).css({display:'block'});
		$('#'+id).animate({width:'+=1260px'},500);
	}
	if (openCheck) {
    if (prevId=='dialogs'){
      switch(id){
        case 'friends':
          $('#friend_block').delay(400).fadeIn(100);
        break;
        case 'events':
          $('#event_block').delay(400).fadeIn(100);
        break;
        case 'meetings':
          $('#meeting_block').delay(400).fadeIn(100);
        break;
      }         
    }
    else {
      switch(id){
        case 'friends':
          $('#friend_block').delay(900).fadeIn(100);
        break;
        case 'events':
          $('#event_block').delay(900).fadeIn(100);
        break;
        case 'meetings':
          $('#meeting_block').delay(900).fadeIn(100);
        break;
      }  
    }
	}
	else { 
      switch(id){
        case 'friends':
          $('#friend_block').delay(400).fadeIn(100);
        break;
        case 'events':
          $('#event_block').delay(400).fadeIn(100);
        break;
        case 'meetings':
          $('#meeting_block').delay(400).fadeIn(100);
        break;
      }  ;
	} 
	openCheck = true;
	prevId = id;
};

function openDialogs() {
  $('#podlogka').css({display:'block'});
  console.log(prevId);
  if (locationOpen ==true){
    $('#up-triangle').fadeOut(0);
    $('.locationWindow').animate({height:'-=590px'},120);
    $('.locationWindow').fadeOut(0);
  }
  $('#triangle').fadeOut(0);
  $('.dialogWindow').fadeOut(0);
  if (prevId=='dialogs'){
    return;
  }
  $('#dialogs').css({left:screen.width});
  $("#dialogs").css({width:'300px'});
  $('#m2 a').css({color:'#555'});
  $('#m7 a').css({color:'white'});
  if (openCheck) {
  switch(prevId){
    case 'friends':
      $('#m3 a').css({color:'#555'});
      break;
    case 'events':
      $('#m4 a').css({color:'#555'});
      break;
    case 'dialogs':
      $('#m7 a').css({color:'#555'});
      break;
    case 'meetings':
      $('#m5 a').css({color:'#555'});
      break;
  }    
    $('#friend_block').fadeOut(100);
    $('#event_block').fadeOut(100);
    $('#meeting_block').fadeOut(100);
    $('#dialogs').fadeOut(0);
    $("#"+prevId).animate({width:'-=1260px'},500);
    $('#'+prevId).fadeOut(0);
    $('#dialogs').delay(20).fadeIn(0);
    $('#dialogs').animate({left:'-=327px'},300);
  }
  else{
    $(".brd").css({width:'0px'});
    $('#friend_block').css({display:'none'}); 
    $('#event_block').css({display:'none'}); 
    $('#meeting_block').css({display:'none'}); 
    $('#dialogs').css({display:'block'}); 
    $("#dialogs").css({width:'300px'});
    $('#dialogs').animate({left:'-=327px'},300);
  }
  openCheck = true;
  prevId = 'dialogs';
}

function oneDialog(number){
  $('#podlogka').css({display:'block'});
  widthDial = screen.width - 1045;
  console.log(widthDial);
  $('.dialogWindow').css({marginLeft:widthDial+'px'});
  $('.dialogWindow').fadeIn(100);
  widthDial = widthDial + 694;
  offset = $('#check'+number).offset();
  $('#triangle').css({marginLeft:widthDial+'px'});
  $('#triangle').css({marginTop:offset.top});
  $('#triangle').fadeIn(0);
}

function openLocations() {
  $('#podlogka').css({display:'block'});
  if (openCheck) {
    $('#dialogs').animate({left:'+=327px'},200);
  }
  offset = $('#locationButton').offset();
  widthLoc = offset.left+50;
  $('#up-triangle').css({marginLeft:widthLoc});
  $('#up-triangle').css({display:'block'});
  $('.locationWindow').fadeIn(0);
  $('.locationWindow').css({height:'0px'},0);
  $('.locationWindow').animate({height:'+=590px'},250);
  locationOpen = true;
}

function closeDiv(){
  $('#podlogka').css({display:'none'});
  $('#triangle').fadeOut(0);
  $('.dialogWindow').fadeOut(0);
  $('#m2 a').css({color:'white'});
	$('#friend_block').fadeOut(100);
	$('#event_block').fadeOut(100);
  $('#meeting_block').fadeOut(100);
  if (prevId=='dialogs'){
    $("#dialogs").animate({left:'+=300px'},300);
    $("#dialogs").queue(function(){
    $("#dialogs").css({display:'none'});
    $(this).dequeue();  
  });
  }
  else {
	 $(".brd").animate({width:'-=1260px'},500);
     $(".brd").queue(function(){
    $(".brd").css({display:'none'});
    $(this).dequeue();  
  });
	}

  $('#m3 a').css({color:'#555'});
  $('#m4 a').css({color:'#555'});
  $('#m5 a').css({color:'#555'});
  $('#m7 a').css({color:'#555'});
  prevId = '';
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
