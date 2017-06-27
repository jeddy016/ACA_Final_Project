angular.module('pitStop').controller('findServiceController', ['$scope', '$http', function($scope, $http) {

$scope.placeIDs = [];
$scope.placeDetails = [];

var map;
var infowindow;

var initMap = function(location) {
    var userLocation = location;

    map = new google.maps.Map(document.getElementById('map'), {
      center: userLocation,
      zoom: 12
    });

    infowindow = new google.maps.InfoWindow();

    var service = new google.maps.places.PlacesService(map);

    service.textSearch({
        location: userLocation,
        radius: '15',
        query: 'oil change'
    }, callback);
}

var callback = function(results, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {

        var getPlaceIds = $scope.$apply(function() {
          results.forEach(function (place) {
              $scope.placeIDs.push({placeId: place.place_id});
          });
        });

        $scope.$apply(function(getPlaceIds) {
           getPlaceDetails();
        });
    }
}

var getPlaceDetails = function() {
     var service = new google.maps.places.PlacesService(map);

    $scope.placeIDs.forEach(function(id) {
     service.getDetails(id, callback);

       function callback(loc, status) {
         if (status == google.maps.places.PlacesServiceStatus.OK) {
             $scope.$apply(function() {
               $scope.placeDetails.push(loc);
               createMarker(loc);
             });
         }
       }
    })

}

function createMarker(place) {
    var service = new google.maps.places.PlacesService(map);
    var marker = new google.maps.Marker({
      map: map,
      position: place.geometry.location
    });

    google.maps.event.addListener(marker, 'click', function() {
      infowindow.setContent(place.name);
      infowindow.open(map, this);
    });
}

$http.post('https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB-q9rUaYmRxp0xVXzsdY3EB9CZyGFOI1U')
    .then(function(response) {
        var location= {lat: response.data.location.lat, lng: response.data.location.lng};
        initMap(location);
    });
}])