var markers = [];
var _f_first=1;
var overlay;
var map;
var coordinates;
var markerCluster
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
     redraw();
  });
 
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

  google.maps.event.addListener(map,'drag', function(event) {
       //здесь я плучаю некторые данные, в теории тут будет ajax выхов функции, пока не делаю, ибо она все равно ничего не получит, пока просто получаем захордкоженный массив с json координатами объектов.
       overlay.setMap(null);
       clearMarkers();
       //markerCluster.clearMarkers(); 
         for (var i = 0; i < 100; i++) {
          var dataPhoto = data.photos[i];
          var latLng = new google.maps.LatLng(dataPhoto.latitude,
              dataPhoto.longitude)
          addMarker(latLng);
         }
         redraw();
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
function Picture(location, image,map) {
 // alert(location);
  // Now initialize all properties.
  coordinates = location;
  this.bounds_ = location;
  this.image_ = image;
  this.map_ = map;
  this.div_ = null;
  this.setMap(map);
}

Picture.prototype.onAdd = function(location) {
  //alert(coordinates);
  var div = document.createElement('div');
  div.setAttribute('id', 'avatar');
  //alert("<img src=" + this.image_ + " class = ava_round>")
  //div.innerHTML="<input type='button' class='button_rightclick' value='add meeting' onclick=addMarker(coordinates)>"
    div.innerHTML="<img src=" + this.image_ + " class = ava_round><p>Party in my House</p>";
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
  div.innerHTML="<div class='label_rightclick'><a onclick='add_meeting(coordinates)'> add meeting </a>"+"<a onclick='addMarker(coordinates)'>check</a></div>";
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
  div.style.height = 250 + 'px';
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
    markerCluster.redraw()
    markerCluster = new MarkerClusterer(map, markers);
}
/*var markerImage = new google.maps.MarkerImage(
            'bg-num.png',
            //  '/home/philipp/bg-num.png',
            new google.maps.Size(33,33),
            new google.maps.Point(0,0),
            new google.maps.Point(0,33)
        );*/

function addMarker(location) {
  srcImage = "img/ava_1.png";
  picture = new Picture(location, srcImage, map);
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
  markers.push(picture);

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
      addMarker(location);
      chatCheck=false;
    });
    chatCheck=true;
  }
  else{
    $('.add_window').fadeOut(10);
    chatCheck=false;
  }
};