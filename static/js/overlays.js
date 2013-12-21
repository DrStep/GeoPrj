var markers = [];
var _f_first=1;
var overlay;
var map;
var coordinates;
var markerCluster;
var i=0;
var nelat;
var nelon;
var svlat;
var svlon;
var typeOfMarkers="user";

var reqId=0;
USGSOverlay.prototype = new google.maps.OverlayView();
Picture.prototype = new google.maps.OverlayView();



function initialize() {
  var haightAshbury = new google.maps.LatLng(55.756136,37.616265);
  var mapOptions = {
    zoom: 10,
    center: haightAshbury,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    mapTypeControl: false,
    zoomControl: false,

    panControl: false,
    rotateControl: false  ,
    overviewMapControl: false
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
  markerCluster = new MarkerClusterer(map,markers);
  var srcImage = 'https://developers.google.com/maps/documentation/javascript/';
  srcImage += 'examples/full/images/talkeetna.png';
  google.maps.event.addListener(map, 'click', function(event) {
      overlay.setMap(null); 
  });


  google.maps.event.addListener(map, 'rightclick', function(event) {
    //addMarker(event.latLng);
    if (map) {
     if (_f_first)
     {
        overlay = new USGSOverlay(event.latLng, srcImage, map);
        _f_first=0;
     } else {
        overlay.setMap(null); 
        overlay = new USGSOverlay(event.latLng, srcImage, map);
   }
 }
  });
  google.maps.event.addListener(map, 'zoom_changed', function(event) {
    if (typeOfMarkers != "random_meet")
     ajaxes(); 
  });
 
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

 function ajaxes() {
            markers = [];
            nelat = map.getBounds().getNorthEast().lat();
            nelon = map.getBounds().getNorthEast().lng();
            svlat = map.getBounds().getSouthWest().lat();
            svlon = map.getBounds().getSouthWest().lng();
            switch (typeOfMarkers) {
              case "user" :
                 $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/api/users.location",
                  dataType: "json",
                  data: "fields=['name','latitude','longitude','user_id']" + "&" + "loc_range=" + "{latL:" + svlat + "," + "latR:" + nelat +"," + "lngL:" + svlon + ",lngR:" + nelon + "}",
                  beforeSend: function() {
                  },
                  success: function(res) {;
                      for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'self',res[i].user_id,res[i].name);
                     };
                  redraw();
                  },
                  error: function() {
              }
            });
              break;
           case "meet" :  
            $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/api/meet.location",
                  dataType: "json",
                  data: "fields=['title','latitude','longitude','meet_id','user_id']" + "&" + "loc_range=" + "{latL:" + svlat + "," + "latR:" + nelat +"," + "lngL:" + svlon + ",lngR:" + nelon + "}",
                  beforeSend: function() {
                  },
                  success: function(res) {;
                      for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'met',res[i].meet_id,res[i].title);
                     };
                  redraw();
                  },
                  error: function() {
              }
            }); 
            break;
            case "place" :  
            $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/api/place.location",
                  dataType: "json",
                  data: "fields=['title','latitude','longitude']" + "&" + "loc_range=" + "{latL:" + svlat + "," + "latR:" + nelat +"," + "lngL:" + svlon + ",lngR:" + nelon + "}",
                  beforeSend: function() {
                  },
                  success: function(res) {;
                      for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'met',res[i].place_id,res[i].title);
                     };
                  redraw();
                  },
                  error: function() {
              }
            }); 
            break;
            case "random_meet":
            alert('s');
            $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/random_meet",
                  dataType: "json",
                  data: "",
                  beforeSend: function() {
                  },
                  success: function(res) {
                   /*   for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'met',res[i].place_id,res[i].title);
                     };
                  redraw();*/
                  },
                  error: function() {
              }
            }); 
            break;
        }
 }

  google.maps.event.addListener(map,'dragend', function(event) {
          if (typeOfMarkers != "random_meet")  {
            ajaxes(); 
          }
         //markerclusterer

  });
  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

  var contentString = '<div id="content">'+
      'aaaaaaaaaaaaaa' + 
      '</div>';

  var infowindow = new google.maps.InfoWindow({
      content: contentString
  });

  var marker = new google.maps.Marker({
      position: myLatlng,
      map: map,
      title: 'Uluru (Ayers Rock)',
      content: infowindow
  });
  google.maps.event.addListener(marker, 'click', function() {
    infowindow.open(map,marker);
  });
}

//###############################################################################################
function Picture(location, image,map,type,id,name) {
 // alert(location);
  // Now initialize all properties.
  coordinates = location;
  this.bounds_ = location;
  this.image_ = image;
  this.map_ = map;
  this.div_ = null;
  this.type_= type;
  this.id_ = id;
  this.name_ = name;
  this.setMap(map);
}

Picture.prototype.onAdd = function(location) {
  //alert(coordinates);
  var div = document.createElement('div');
  div.setAttribute('id', 'avatar');
  switch (this.type_) {
   case 'meet':
      div.innerHTML="<div class = 'ava_round met'><img src=" + this.image_ + " class = 'ax'  onclick=meet_getMoreInformation('" + this.id_ + "')><p>" + this.name_ + "</p></div><div class='check1' onclick=openDialogs()></div> <div class='check2' onclick=window_meeting('sd')></div> <div class='check3' onclick=openDiv('friends')>друзья</div>";
      break;
   case 'place':
      div.innerHTML="<div class = 'ava_round place'><img src=" + this.image_ + " class = 'ax'  onclick=place_getMoreInformation('" + this.id_ + "')><p>" +this.name_ + "</p></div><div class='check1' onclick=openDialogs()></div> <div class='check2' onclick=window_place('sd')></div> <div class='check3' onclick=openDiv('friends')>друзья</div>";
      break;
   case 'self':
      div.innerHTML="<div class = 'ava_round self'><img src=" + this.image_ + " class = 'ax' onclick=users_getMoreInformation('" + this.id_ + "')><p>" + this.name_ + "<div class='mestopolegrnie'></p><img src='/img/dialog.ico' class='check1' onclick=openDialogs()><div class='mestopolegrnie2'> <img src='/img/ic_menu_add.ico' class='check2' onclick=add_to_friend('10','"+ this.id_ +"')><div class='mestopolegrnie3'><img src='/img/ic_menu_allfriends.ico' class='check3' onclick=openDiv('friends')></div></div></div></div>";
} 
  //div.innerHTML="   <div class='check1'></div> <div class='check2'></div> <div class='check3'></div>"
  //alert("<img src=" + this.image_ + " class = ava_round>")
  //div.innerHTML="<input type='button' class='button_rightclick' value='add meeting' onclick=addMarker(coordinates)>"
   
  this.div_ = div;

  var panes = this.getPanes();
  panes.overlayImage.appendChild(this.div_);
};

 Picture.prototype.draw = function(location) {
  var overlayProjection = this.getProjection();
  var sw = overlayProjection.fromLatLngToDivPixel(this.bounds_);
  var div = this.div_;
  div.style.left = (sw.x-35) + 'px';
  div.style.top = (sw.y-35) + 'px';
  div.style.width = 120 + 'px';
  div.style.height = 50 + 'px';
 //div.style.class = "ava_round";
  //alert(div.innerHTML);
}; 

Picture.prototype.onRemove = function() {
  this.div_.parentNode.removeChild(this.div_);
};


Picture.prototype.hide = function() {
  if (this.div_) {
    this.div_.style.visibility = 'hidden';
  }
};

Picture.prototype.getPosition = function() {
  return this.bounds_;
};

Picture.prototype.show = function() {
  if (this.div_) {
    this.div_.style.visibility = 'visible';
  }
};

Picture.prototype.toggle = function() {
  if (this.div_) {
    if (this.div_.style.visibility == 'hidden') {
      this.show();
    } else {
      this.hide();
    }
  }
};

Picture.prototype.toggleDOM = function() {
  if (this.getMap()) {
    this.setMap(null);
  }
};


//################################################################################################



function USGSOverlay(location, image,map) {
  // Now initialize all properties.
  coordinates = location;
  this.bounds_ = location;
  this.image_ = image;
  this.map_ = map;

  this.div_ = null;

  this.setMap(map);
}

USGSOverlay.prototype.onAdd = function(location) {
  //alert(coordinates);
  var div = document.createElement('div');
  div.setAttribute('id', 'drop-down');
  //div.innerHTML="<input type='button' class='button_rightclick' value='add meeting' onclick=addMarker(coordinates)>"
  div.innerHTML="<div class='label_rightclick'><a onclick=add_meeting(coordinates,'meet')> add meeting </a>"+"<a onclick=add_place(coordinates,'place')>add place</a>" + "<a onclick=add_self(coordinates,'self')>check-in</a></div>";
  this.div_ = div;

  var panes = this.getPanes();
  panes.overlayImage.appendChild(this.div_);
};

 USGSOverlay.prototype.draw = function() {
  var overlayProjection = this.getProjection();
  var sw = overlayProjection.fromLatLngToDivPixel(this.bounds_);
  var div = this.div_;
  div.style.left = sw.x + 'px';
  div.style.top = sw.y + 'px';
  div.style.width = 120 + 'px';
  div.style.height = 100 + 'px';
}; 

USGSOverlay.prototype.onRemove = function() {
  this.div_.parentNode.removeChild(this.div_);
};


USGSOverlay.prototype.hide = function() {
  if (this.div_) {
    this.div_.style.visibility = 'hidden';
  }
};

USGSOverlay.prototype.show = function() {
  if (this.div_) {
    this.div_.style.visibility = 'visible';
  }
};

USGSOverlay.prototype.toggle = function() {
  if (this.div_) {
    if (this.div_.style.visibility == 'hidden') {
      this.show();
    } else {
      this.hide();
    }
  }
};


USGSOverlay.prototype.toggleDOM = function() {
  if (this.getMap()) {
    this.setMap(null);
  }
};

function redraw() {
    markerCluster.clearMarkers();
   // markerCluster.redraw()
    markerCluster.addMarkers(markers);
    markerCluster.redraw();
    //markerCluster = new MarkerClusterer(map,markers)
}
/*var markerImage = new google.maps.MarkerImage( 
            'bg-num.png',
            //  '/home/philipp/bg-num.png',
            new google.maps.Size(33,33),
            new google.maps.Point(0,0),
            new google.maps.Point(0,33)
        );*/

function addMarker(location, type, user_id,name) {
  alert('fff');
  srcImage = "/img/ava_1.png";
  picture = new Picture(location, srcImage, map, type, user_id,name);
    markers.push(picture);  
  /*var markerImage = new google.maps.MarkerImage(
            'img/ava_1.png',
            //  '/home/philipp/bg-num.png',
            new google.maps.Size(50,50),
            new google.maps.Point(0,0),
            new google.maps.Point(0,50)
        );
  //var markerShape = new google.maps.MarkerShape();
  var markerShape = {
        coords: [0,33,16],
        type: 'circle'
        }
  var marker = new google.maps.Marker({
    icon: 'img/ava_1.png',
    position: location,
    map: map,
    shape: markerShape
  });
  //redraw();*/
 // alert(markers);

}

function setAllMap(map) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }  
}

function clearMarkers() {
  setAllMap(null);
  markers = [];
}

function showMarkers() {
  setAllMap(map);
}

function deleteMarkers() {
  clearMarkers();
  markers = [];
}

google.maps.event.addDomListener(window, 'load', initialize);


google.maps.event.addListener(marker, 'mouseover', function() {
  marker.setIcon(markerImageHover);
});

google.maps.event.addListener(marker, 'mouseout', function() {
  marker.setIcon(markerImage);
});

function add_meeting(location){
  if (chatCheck==false){
    $('.add_window').delay(50).fadeIn(100);
    $('#add_button_marker').click(function() { 
      $('.add_window').fadeOut(10);
      addMarker(location, 'meet');
      chatCheck=false;
    });
    chatCheck=true;
  }
  else{
    $('.add_window').fadeOut(10);
    chatCheck=false;
  }
};
function add_place(location){
  if (chatCheck==false){
    $('.add_window_place').delay(50).fadeIn(100);
    $('#add_button_marker_place').click(function() { 
      $('.add_window_place').fadeOut(10);
      addMarker(location, 'place');
      chatCheck=false;
    });
    chatCheck=true;
  }
  else{
    $('.add_window_place').fadeOut(10);
    chatCheck=false;
  }
};
function add_self(location){
  if (chatCheck==false){
    $('.add_window_self').delay(50).fadeIn(100);
    $('#add_button_marker_self').click(function() { 
      $('.add_window_self').fadeOut(10);
      addMarker(location, 'self');
      chatCheck=false;
    });
    chatCheck=true;
  }
  else{
    $('.add_window_self').fadeOut(10);
    chatCheck=false;
  }
};

function window_meeting(location){

  if (chatCheck==false){
    $('#podlogka').css({display:'block'});
    $('.meet_window').delay(50).fadeIn(100);
    chatCheck=true;
    alert('true')
  }
  else{
    alert('false');
    $('.meet_window').fadeOut(10);
    chatCheck=false;
  }
};

function window_place(location){
  if (chatCheck==false){
    $('#podlogka').css({display:'block'});
    $('.place_window').delay(50).fadeIn(100);
    chatCheck=true;
    alert('true')
  }
  else{
    alert('false');
    $('.place_window').fadeOut(10);
    chatCheck=false;
  }
  //alert('place');
};

function window_manpage(location){
 /* if (chatCheck==false){
    $('.meet_window').delay(50).fadeIn(100);
    chatCheck=true;
    alert('true')
  }
  else{
    alert('false');
    $('.meet_window').fadeOut(10);
    chatCheck=false;
  }*/ alert('man');
};


function close_window_meeting() {

};

function users_getMoreInformation(id) {

 $.ajax({
  type: "POST",
  url: "http://localhost:8090/api/user.get",
  dataType: "json",
  data: "user_id=" + id  + "&" + "fields=" + "['name','gender','photo','modifyDate']",
  beforeSend: function() {
    
  },
  success: function(res) {
    $('#podlogka').css({display:'block'});
    $('.man_window').delay(50).fadeIn(100);
    chatCheck=true;
    document.getElementById("user_name").innerHTML = res[0].name;
    if (res[0].gender == '0') {
    document.getElementById("age").innerHTML = "Пол: Женский";
  } else {
    document.getElementById("age").innerHTML = "Пол: Мужской";
  }

    document.getElementById("last_online").innerHTML = res[0].modifyDate;
  },
  error: function(res) {
    alert(res);
  },
});
}

function meet_getMoreInformation(id) {

 $.ajax({
  type: "POST",
  url: "http://localhost:8090/api/meet.get",
  dataType: "json",
  data: "meet_id=" + id  + "&" + "fields=" + "['title','desription','photo','wall_id']",
  beforeSend: function() {
    
  },
  success: function(res) {
    $('#podlogka').css({display:'block'});
    $('.meet_window').delay(50).fadeIn(100);
    chatCheck=true;
    document.getElementById("meet_title").innerHTML = res[0].title;
    document.getElementById("meet_description").innerHTML = res[0].description;
    document.getElementById("meet_img").src = res[0].photo;
  },
  error: function(res) {
    alert(res);
  },
});
}

function place_getMoreInformation(id) {

 $.ajax({
  type: "POST",
  url: "http://localhost:8090/api/place.get",
  dataType: "json",
  data: "place_id=" + id  + "&" + "fields=" + "['title','desription','photo','wall_id']",
  beforeSend: function() {
    
  },
  success: function(res) {
    $('#podlogka').css({display:'block'});
    $('.place_window').delay(50).fadeIn(100);
    chatCheck=true;
    document.getElementById("Place_title").innerHTML = res[0].title;
    document.getElementById("Place_description").innerHTML = res[0].desription;
    document.getElementById("Place_img").src = res[0].photo;
  },
  error: function(res) {
    alert(res);
  },
});
}

function add_to_friend(id1,id2) {

 $.ajax({
  type: "POST",
  url: "http://localhost:8090/api/frd.insert",
  dataType: "json",
  data: "user1=" + id1  + "&user2=" + id2,
  beforeSend: function() {
    
  },
  success: function(res) {
      alert('success');
  },
  error: function(res) {
    
  },
});
}

function changetype(str) {
  typeOfMarkers=str;
  switch (str) {
    case 'place':
      document.getElementById('place').className = "global_button_place_active";
      document.getElementById('meet').className = "global_button_meet";
      document.getElementById('user').className = "global_button_self";
      document.getElementById('random_meet').className = "global_button_random_meet";
      break;
    case 'user':
      document.getElementById('place').className = "global_button_place";
      document.getElementById('meet').className = "global_button_meet";
      document.getElementById('user').className = "global_button_self_active";
      document.getElementById('random_meet').className = "global_button_random_meet";
      break;
    case 'meet': 
      document.getElementById('place').className = "global_button_place";
      document.getElementById('meet').className = "global_button_meet_active";
      document.getElementById('user').className = "global_button_self";
      document.getElementById('random_meet').className = "global_button_random_meet";
      break;
    case 'random_meet':
      document.getElementById('place').className = "global_button_place";
      document.getElementById('meet').className = "global_button_meet ";
      document.getElementById('user').className = "global_button_self";
      document.getElementById('random_meet').className = "global_button_random_meet_active";

      break;
  };
  //ajaxes();
   markers = [];
            nelat = map.getBounds().getNorthEast().lat();
            nelon = map.getBounds().getNorthEast().lng();
            svlat = map.getBounds().getSouthWest().lat();
            svlon = map.getBounds().getSouthWest().lng();
            switch (typeOfMarkers) {
              case "user" :
                 $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/api/users.location",
                  dataType: "json",
                  data: "fields=['name','latitude','longitude','user_id']" + "&" + "loc_range=" + "{latL:" + svlat + "," + "latR:" + nelat +"," + "lngL:" + svlon + ",lngR:" + nelon + "}",
                  beforeSend: function() {
                  },
                  success: function(res) {;
                      for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'self',res[i].user_id,res[i].name);
                     };
                  redraw();
                  },
                  error: function() {
              }
            });
              break;
           case "meet" :  
            $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/api/meet.location",
                  dataType: "json",
                  data: "fields=['title','latitude','longitude','meet_id','user_id']" + "&" + "loc_range=" + "{latL:" + svlat + "," + "latR:" + nelat +"," + "lngL:" + svlon + ",lngR:" + nelon + "}",
                  beforeSend: function() {
                  },
                  success: function(res) {;
                      for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'met',res[i].meet_id,res[i].title);
                     };
                  redraw();
                  },
                  error: function() {
              }
            }); 
            break;
            case "place" :  
            $.ajax({
                   type: "POST",
                  url: "http://localhost:8090/api/place.location",
                  dataType: "json",
                  data: "fields=['title','latitude','longitude']" + "&" + "loc_range=" + "{latL:" + svlat + "," + "latR:" + nelat +"," + "lngL:" + svlon + ",lngR:" + nelon + "}",
                  beforeSend: function() {
                  },
                  success: function(res) {;
                      for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'met',res[i].place_id,res[i].title);
                     };
                  redraw();
                  },
                  error: function() {
              }
            }); 
            break;
            case "random_meet":
            $.ajax({
                   type: "GET",
                  url: "http://localhost:8090/random_meet",
                  dataType: "json",
                  data:  "reqId=",
                  beforeSend: function() {
                  },
                  success: function(res) {

                    if (res.reqId >=0 )
                    {
                      reqId = res.reqId; 
                      function Req() {
                        $.ajax({
                        type: "GET",
                        url: "http://localhost:8090/random_meet",
                        dataType: "json",
                        data: {'reqId' : reqId },
                        beforeSend: function() {
                        },
                        success: function(res) {
                        if (res.meet)
                        {
                            alert(res.meet)
                             $.ajax({
                              type: "POST",
                               url: "http://localhost:8090/api/meet.get",
                                dataType: "json",
                                data: "fields=['title','photo']&meet_id="+ res.meet,
                                beforeSend: function() {
                                },
                                success: function(ress) {
                                var loc = new google.maps.LatLng(55.756136,37.616265);
                                addMarker(loc,'self',res.meet,ress[i].title); 
                       /*   for(i=0;i<res.length;i++) {
                                var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                               addMarker(location,'met',res[i].place_id,res[i].title);
                         };
                      redraw();*/
                      },
                      error: function() {
                        }
                      }); 
                        }
                        else {
                          reqId = res.reqId;
                          setTimeout(function () { Req() }, 1500);

                        }
                       /*   for(i=0;i<res.length;i++) {
                                var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                               addMarker(location,'met',res[i].place_id,res[i].title);
                         };
                      redraw();*/
                      },
                      error: function() {
                        }
                      }); 
                    }
                    Req();
        }

                    }

                   /*   for(i=0;i<res.length;i++) {
                            var location = new google.maps.LatLng(res[i].latitude,res[i].longitude);
                           addMarker(location,'met',res[i].place_id,res[i].title);
                     };
                  redraw();*/
                  , //cajax
                  error: function() {
              }
            }); 
            break;
        }
}
