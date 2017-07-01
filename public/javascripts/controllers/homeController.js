angular.module('pitStop').controller('homeController', ['$scope', '$window', '$http', '$route', function($scope, $window, $http, $route) {

    $scope.overviewVisible = true;

    $scope.vehicles = [];
    $scope.selectedVehicle = $scope.selectedVehicle;

    $http({
        method: 'GET',
        url: '/getVehicles'
    })
    .then(function(response) {
        $scope.vehicles = response.data;
        $scope.selectedVehicle = $scope.vehicles[0];
    })

    $scope.odoForm = {};
    $scope.odoForm.newOdometerReading = "";

    $scope.remove = function(item) {
      //TODO: wire me up bro
    }

    $scope.overviewShowHide = function() {
        $scope.overviewVisible == true ? $scope.overviewVisible = false : $scope.overviewVisible = true;
    }

    $scope.odometerShow= function(vehicle) {
        var index = $scope.vehicles.indexOf(vehicle);
        $scope.selectedVehicle.odometerIsVisible = true;
    };

    $scope.odometerHide= function(vehicle) {
            var index = $scope.vehicles.indexOf(vehicle);
            $scope.selectedVehicle.odometerIsVisible = false;

            if($scope.selectedVehicle.currentOdometer != $scope.newOdometerReading) {
                $scope.selectedVehicle.currentOdometer = $scope.selectedVehicle.currentOdometer;
            }
        };

    $scope.updateOdometer = function(){
        $scope.id = $scope.selectedVehicle.id;

        var data= {
            vehicleID : $scope.id,
            reading : $scope.selectedVehicle.currentOdometer
        }
        $http({
            method: 'POST',
            url: '/updateOdometer',
            data: JSON.stringify(data)
        })
        .then(function(response) {
            if(response.data == 'success'){
                console.log("odometer updated");
            }
            else {
                alert("Error updating odometer. " /*+ display whatever error comes back from DB*/);
            }
        })
    }

    $scope.goToEditPage = function(vehicle) {
          var id = $scope.vehicles.indexOf(vehicle);
          $scope.goTo('/editVehicle/' + id);
        }

    $window.scroll( function() {
            if($(this).scrollTop() > 60) {
                $('.fa-bars').fadeOut(0);
        }
            else {
                $('.fa-bars').fadeIn(250);
            }
    });


}])