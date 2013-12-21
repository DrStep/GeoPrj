 var chatCheck=false;
var startWidth=true;
var openCheck=false;
var prevId = '';
var locationOpen = false;
var global_dialog_id;
var selfname = "Заур";

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
      $.ajax({
                    type: "POST",
                    url: "http://localhost:8090/api/users.friends",
                    dataType: "json",
                    data: "fields=['name','age','photo','online']"+ "&" + "user_id=10",
                    beforeSend: function() {
                    },
                    success: function(res) {
                                $('#content').html(function(){
                                var result='';
                                for(i=0;i<res.length;i++) {
                                    result+="<div class='one_friend'><div class='friend_block'> <div id='circle_mini'> <img src='img/ava_1.png' width=100 height=100></div>  <div id='descriptionBlock'><span id='friend_font'>"+res[i].name+"</span> <br><span>Последний раз был в сети: "+res[i].online+"</span><br><span>Пол:"+ (res[i].age=="0" ? " Женский" : " Мужской")+"</span> <br><span>Интересы:</span><br><br></div><div id='buttonBlock'>  <button class='button_friend' id='delete'>Удалить из друзей</button> </div> </div> </div>";
                                }
                                return result;
                             })
                     },
                    error: function() {
                               $('#content').html(function(){
                                var result='';
                                for(i=0;i<5;i++) {
                                    result+="<div class='one_friend'> <div class='friend_block'><div id='circle_mini'> <img src='"+i+"'></div> <div id='descriptionBlock'><span id='friend_font'>"+i+"</span><button class='button_friend' id='startChat' style='width:130px'>Начать чат</button> <br><span>Последний раз был в сети:"+i+"</span><br><span>Возраст:"+i+"</span> <br><span>Интересы:</span><br><br></div><div id='buttonBlock'> <button class='button_friend'>Пригласить...</button> <button class='button_friend' id='recommend'>Порекомендовать...</button>  <button class='button_friend' id='delete'>Удалить из друзей</button> </div> </div> </div>";
                                }
                                return result;
                             })
                    }
      });
      break;
    case 'events':                   //места!!!!
      $('.changePoint2').css({background:'white'});
            $.ajax({
                          type: "POST",
                          url: "http://localhost:8090/api/users.place",      //??????????????
                          dataType: "json",
                          data: "fields=['title','description','status','img']"+ "&" + "user_id=10",
                          beforeSend: function() {
                          },
                          success: function(res) {
                              $('#content3').html(function(){
                              var result='';
                              for(i=0;i<res.length;i++) {
                                       result+="<div class='one_event'>  <div class='event_block'> <div id='event_square'><img src='"+res[i].img+"'></div> <div id='descriptionBlock'> <span id='event_font''>"+res[i].title+"</span> <br>  <span>Статус:"+res[i].status+"</span> <br> <span>Описание:"+res[i].description+"</span> </div> <div id='buttonBlock'> <button class='button_event'>Добавить в закладки</button> <button class='button_event'>Не интересует</button>  <button class='button_event'>Напоминание</button> </div>  </div> </div>";
                              }
                              return result;
                              })
                           },
                          error: function() {
                               $('#content3').html(function(){
                                var result='';
                                for(i=0;i<5;i++) {
                                       result+="<div class='one_event'> <div class='event_block'><div id='event_square'><img src='"+i+"'></div> <div id='descriptionBlock'> <span id='event_font''>"+i+"</span> <br>  <span>Статус:"+i+"</span> <br> <span>Описание:"+i+"</span> </div> <div id='buttonBlock'> <button class='button_event'>Добавить в закладки</button> <button class='button_event'>Не интересует</button>  <button class='button_event'>Напоминание</button> </div>  </div> </div>";
                                }
                                return result;
                             })
                          }
            });
      break;
    case 'meetings':
      $('.changePoint3').css({background:'white'});
            $.ajax({
                          type: "POST",
                          url: "http://localhost:8090/api/meets.for_userid",
                          dataType: "json",
                          data: "fields=['title','status','last_update','date_time']"+ "&" + "user_id=10",
                          beforeSend: function() {
                          },
                          success: function(res) {
                              $('#content4').html(function(){
                              var result='';
                              for(i=0;i<res.length;i++) {
                                     result+="   <div class='one_meeting'> <div class='meeting_block'> <div id='meeting_square'><img src='"+res[i].photo+"'></div> <div id='descriptionBlock'> <span id='meeting_font'>"+res[i].title+"</span> <br> <span>Статус:"+res[i].status+"</span> <br> <span>Время начала:"+res[i].date_time+"</span> <br> <span>Последнее изменение:"+res[i].last_update+"</span> <br> </div> <div id='buttonBlock'> </div></div>";
                              }
                              return result;
                              })
                           },
                          error: function() {
                               $('#content4').html(function(){
                                var result='';
                                for(i=0;i<5;i++) {
                                     result+="   <div class='one_meeting'> <div class='meeting_block'> <div id='meeting_square'></div> <div id='descriptionBlock'> <span id='meeting_font'>Поход в парк</span> <br> <span>Дата начала мероприятия:</span> <br> <span>Время начала:</span> <br> <span>Описание:</span> <br> <span>Время начала:</span><br> <span>Время начала:</span><br> <span>Время начала:</span><br> <span>Время начала:</span><br> <span>Время начала:</span> </div> <div id='buttonBlock'> <button class='button_meeting'>Присоединиться</button> <button class='button_meeting'>Не интересует</button><button class='button_meeting'>Напоминание</button></div> </div> </div> ";
                                }
                                return result;
                                 })
                          }
            });
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
    $('#content').fadeOut(0);
    $('#content2').fadeOut(0);
    $('#content3').fadeOut(0);
    $('#content4').fadeOut(0);

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
       $('#content').delay(400).fadeIn(200);
       $('#content2').delay(400).fadeIn(200);
       $('#content3').delay(400).fadeIn(200);
       $('#content4').delay(400).fadeIn(200);
    }
    else {
        $('#content').delay(900).fadeIn(200);
        $('#content2').delay(900).fadeIn(200);
        $('#content3').delay(900).fadeIn(200);
        $('#content4').delay(900).fadeIn(200);
      }
  }
  else {
        $('#content').delay(450).fadeIn(200);
        $('#content2').delay(450).fadeIn(200);
        $('#content3').delay(450).fadeIn(200);
        $('#content4').delay(450).fadeIn(200);
  } 
  openCheck = true;
  prevId = id;
};

function openDialogs() {
$('#m7 a').css({color:'white'});
             $.ajax({
                          type: "POST",
                          url: "http://localhost:8090/api/dialogs.get",
                                 dataType: "json",
                                 data: "fields=['dialog_id','name','title']"+ "&" + "user_id=10",
                                 beforeSend: function() {
                                 },
                                 success: function(res) {
                                     $('#content2').html(function(){
                                      var result='';
                                      for(i=0;i<res.length;i++) {
                                             result+="<a href='#' class='one_dialog' id='check1' onclick='oneDialog(" + res[i].dialog_id + ")'> <div class='circle_dialog''></div>  <div class='messageBlock'> <div style='font-size:23px;padding:2px 0 7px 15px'>" +res[i].title +"</div>  <div style='padding:0 0 0px 6px'>Привет</div> </div> </a>";
                                       }
                                     return result;
                                 });
                                 },
                                 error: function() {
                               $('#content2').html(function(){
                                var result='';
                                for(i=0;i<5;i++) {
                                             result+="<a href='#' class='one_dialog' id='check1' onclick='oneDialog(1)'> <div class='circle_dialog''></div>  <div class='messageBlock'> <div style='font-size:23px;padding:2px 0 7px 15px'>Дмитрий Петров</div>  <div style='padding:0 0 0px 6px'>Привет</div> </div> </a>";
                                }
                                return result;
                                 })
                                 }
              });
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
    $('#content').fadeOut(0);
    $('#content2').fadeOut(0);
    $('#content3').fadeOut(0);
    $('#content4').fadeOut(0);

    $('#dialogs').fadeOut(0);
    $("#"+prevId).animate({width:'-=1260px'},500);
    $('#'+prevId).fadeOut(0);
    $('#dialogs').delay(20).fadeIn(0);
    $('#dialogs').animate({left:'-=327px'},300);
    $('#content2').delay(120).fadeIn(100);
  }
  else{
    $(".brd").css({width:'0px'});
    $('.content').css({display:'none'});
    $('.content3').css({display:'none'});
    $('.content4').css({display:'none'});
    $('#dialogs').css({display:'block'}); 
    $("#dialogs").css({width:'300px'});
    $('#dialogs').animate({left:'-=327px'},300);
    $('#content2').delay(120).fadeIn(100);
  }
  openCheck = true;
  prevId = 'dialogs';
}

function oneDialog(dialog_id){
document.getElementById('incomeBig').innerHTML="";
global_dialog_id=dialog_id;
 $.ajax({
            type: "POST",
            url: "http://localhost:8090/api/dialog.get",
            dataType: "json",
            data: "fields=['date_time','is_read','msg','name']" + "&dialog_id=" + dialog_id,
            beforeSend: function() {
            },
            success: function(res) {
             $('#podlogka').css({display:'block'});
                widthDial = screen.width - 1045;
                console.log(widthDial);
               $('.dialogWindow').css({marginLeft:widthDial+'px'});
               $('.dialogWindow').fadeIn(100);
                widthDial = widthDial + 694;
               //offset = $('#check'+number).offset();
               for(i=0;i<res.length;i++) {
                if (res[i].msg=="") {
                  res[i].msg="<div class='empty_msg'>пустое сообщение</div>";
                } else {
                  res[i].msg="<div class='msg'>"+ res[i].msg +"</div>";
                }
                  document.getElementById('incomeBig').innerHTML +="<div class='name'>" + res[i].name + "</div><div class='date_time'>" + res[i].date_time + "</div><br>" + res[i].msg + "<br>";              }
                 },
            error: function() {
              $('#content2').html(function(){
                  var result='';
                  for(i=0;i<5;i++) {
                          result+="<a href='#' class='one_dialog' id='check1' onclick='oneDialog(1)'> <div class='circle_dialog''></div>  <div class='messageBlock'> <div style='font-size:23px;padding:2px 0 7px 15px'>Дмитрий Петров</div>  <div style='padding:0 0 0px 6px'>Привет</div> </div> </a>";
                  }
                  return result;
                  })
            }
              });
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
    $('#content').fadeOut(100);
    $('#content2').fadeOut(100);
    $('#content3').fadeOut(100);
    $('#content4').fadeOut(100);
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
    $('.man_window').fadeOut(10);
    $('#podlogka').css({display:'none'});
    closeDiv();
}

function addmsg(user_id) {
  message = document.getElementById('yourtextBig').value;
  var currentdate = new Date(); 
  var datetime = "Last Sync: " + currentdate.getFullYear() + "-"
                + (currentdate.getMonth()+1)  + "-" 
                + currentdate.getDate() + " "  
                + currentdate.getHours() + ":"  
                + currentdate.getMinutes() + ":" 
                + currentdate.getSeconds();
  $.ajax({
  type: "POST",
  url: "http://localhost:8090/api/insert",
  dataType: "json",
  data: "table=" + "messanger" + "&" + "fields={'dialog_id': 634, 'user_id':10, 'msg':'dsadsad', 'is_read': 0}",
  beforeSend: function() {
    
  },
  success: function(res) {
      document.getElementById('incomeBig').innerHTML +="<div class='name'>" + selfname + "</div><div class='date_time'>" + datetime + "</div><br>" + message + "<br>"; 
  },
  error: function(res) {
      document.getElementById('incomeBig').innerHTML +="<div class='name'>" + selfname + "</div><div class='date_time'>" + datetime + "</div><br>" + message + "<br>"; 
  },
});
}

