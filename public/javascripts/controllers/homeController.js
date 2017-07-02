angular.module('pitStop').controller('homeController', ['$scope', '$window', '$http', '$route', '$rootScope', function($scope, $window, $http, $route, $rootScope) {

    $scope.snapshotVisible = true;
    $scope.spotlightVisible = false;

    $scope.vehicles = [];
    $scope.selectedVehicle = $scope.selectedVehicle;

    $scope.odoForm = {};
    $scope.odoForm.newOdometerReading = "";

    $http({
        method: 'GET',
        url: '/getVehicles'
    })
    .then(function(response) {
        $scope.vehicles = response.data;
        $scope.selectedVehicle = $scope.vehicles[0];
    });

    $scope.remove = function(item) {
      //TODO: wire me up bro
    };

    $scope.showAll = function() {
        $scope.overviewVisible = true;
        $scope.snapshotVisible = true;
        $scope.spotlightVisible = true;
        $scope.toolsVisible = true;
    };
    $scope.hideAll = function() {
        $scope.overviewVisible = false;
        $scope.snapshotVisible = false;
        $scope.spotlightVisible = false;
        $scope.toolsVisible = false;
    };
    $scope.overviewShowHide = function() {
        $scope.overviewVisible == true ? $scope.overviewVisible = false : $scope.overviewVisible = true;
    };
    $scope.snapshotShowHide = function() {
        $scope.snapshotVisible == true ? $scope.snapshotVisible = false : $scope.snapshotVisible = true;
    };
    $scope.toolsShowHide = function() {
        $scope.toolsVisible == true ? $scope.toolsVisible = false : $scope.toolsVisible = true;
    };
    $scope.spotlightShowHide = function() {
        $scope.spotlightVisible == true ? $scope.spotlightVisible = false : $scope.spotlightVisible = true;
    };
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
    };
    $scope.goToEditPage = function(vehicle) {
          var id = $scope.vehicles.indexOf(vehicle);
          $scope.goTo('/editVehicle/' + id);
        };

}])