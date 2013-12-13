var chatCheck=false;
var startWidth=true;
var openCheck=false;
var prevId = '';
var locationOpen = false;

$(document).ready(init); 
function init(){ 
	$(".chat").draggable({containment:'window'});
  $('#podlogka').click(function() {
  alert("Handler for .click() called.");
});
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
    closeDiv();
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
		$('.one_friend').fadeOut(0);
		$('.one_event').fadeOut(0);
    $('.one_meeting').fadeOut(0);
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
		$('.one_friend').css({display:'none'});
		$('.one_event').css({display:'none'});
    $('.one_meeting').css({display:'none'});
		$('#'+id).css({display:'block'});
		$('#'+id).animate({width:'+=1260px'},500);
	}
	if (openCheck) {
    if (prevId=='dialogs'){
      switch(id){
        case 'friends':
          $('.one_friend').delay(400).fadeIn(200);
        break;
        case 'events':
          $('.one_event').delay(400).fadeIn(200);
        break;
        case 'meetings':
          $('.one_meeting').delay(400).fadeIn(200);
        break;
      }         
    }
    else {
      switch(id){
        case 'friends':
          $('.one_friend').delay(900).fadeIn(200);
        break;
        case 'events':
          $('.one_event').delay(900).fadeIn(200);
        break;
        case 'meetings':
          $('.one_meeting').delay(900).fadeIn(200);
        break;
      }  
    }
	}
	else { 
      switch(id){
        case 'friends':
          $('.one_friend').delay(400).fadeIn(200);
        break;
        case 'events':
          $('.one_event').delay(400).fadeIn(200);
        break;
        case 'meetings':
          $('.one_meeting').delay(400).fadeIn(200);
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
    closeDiv();
    return;
  }
  $('#dialogs').css({left:screen.width});
  $("#dialogs").css({width:'300px'});
  $('#m2 a').css({color:'#555'});
  $('#m7 a').css({color:'white'});
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
    $('.one_friend').fadeOut(100);
    $('.one_event').fadeOut(100);
    $('.one_meeting').fadeOut(100);
    $('#dialogs').fadeOut(0);
    $("#"+prevId).animate({width:'-=1260px'},500);
    $('#'+prevId).fadeOut(0);
    $('#dialogs').delay(20).fadeIn(0);
    $('#dialogs').animate({left:'-=327px'},300);
  }
  else{
    $(".brd").css({width:'0px'});
    $('.one_friend').css({display:'none'});
    $('.one_event').css({display:'none'});
    $('.one_meeting').css({display:'none'});
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
  offset = $('.global_button').offset();
  widthLoc = offset.left+50;
  $('#up-triangle').css({marginLeft:widthLoc});
  $('#up-triangle').css({display:'block'});
  $('.locationWindow').fadeIn(0);
  $('.locationWindow').css({height:'0px'},0);
  $('.locationWindow').animate({height:'+=590px'},250);
  locationOpen = true;
}

function closeDiv(){
  if (chatCheck == true){
    $('.chat').fadeOut(10);
  }
  if (locationOpen ==true){
     $('#up-triangle').fadeOut(0);
     $('.locationWindow').animate({height:'-=590px'},120);
     $('.locationWindow').fadeOut(0);
  }
  $('.changePoint1').css({background:'transparent'});
  $('.changePoint2').css({background:'transparent'});
  $('.changePoint3').css({background:'transparent'});
  $('#podlogka').css({display:'none'});
  $('#triangle').fadeOut(0);
  $('.dialogWindow').fadeOut(0);
  $('#m2 a').css({color:'white'});
	$('.one_friend').fadeOut(100);
	$('.one_event').fadeOut(100);
  $('.one_meeting').fadeOut(100);
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
		$('#startChat').animate({width:'-=90px'},70);
		$('#startChat').text('X');
		$('#descriptionBlock').delay(200).animate({width:'-=90px'},0);
		$('#buttonBlock').delay(80).animate({marginLeft:'-=90px'},10);
		/*$('#recommend').animate({width:'-=80px'},200);
		$('#recommend').text('Порекомендовать..');  */
		$('.brd').delay(50).animate({width:'-=180px'},100);
		$('.chat').delay(50).fadeIn(100);
		chatCheck=true;
	}
	else{
		$('.chat').fadeOut(10);
		$('.brd').animate({width:'+=180px'},100);
		$('#buttonBlock').delay(100).animate({marginLeft:'+=90px'},0);
        $('#descriptionBlock').delay(10).animate({width:'+=90px'},0);
  		$('#startChat').delay(70).animate({width:'+=90px'},100);
		chatCheck=false;
		$("#startChat").queue(function(){
			$('#startChat').text('Начать чат');
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


function closeAll() {
    $('.place_window').fadeOut(10);
    chatCheck=false;
     $('.meet_window').fadeOut(10);
    chatCheck=false;
    $('#podlogka').css({display:'none'});
    closeDiv();
}

