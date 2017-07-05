angular.module('pitStop').controller('homeController', ['$scope', '$window', '$http', '$route', '$rootScope', function($scope, $window, $http, $route, $rootScope) {

    $scope.snapshotVisible = true;
    $scope.spotlightVisible = true;
    $scope.scheduleVisible = false;
    $scope.inputsVisible = false;
    $scope.logVisible = false;
    $scope.chartsVisible = false;

    $scope.vehicles = [];
    $scope.selectedVehicle = $scope.selectedVehicle;
    $scope.odoForm = {};
    $scope.odoForm.newOdometerReading = "";
    $scope.vehicleServices = [];

    $http({
        method: 'GET',
        url: '/getVehicles'
    })
    .then(function(response) {
        $scope.vehicles = response.data;
        $scope.selectedVehicle = $scope.vehicles[0];
        $http({
            method: 'GET',
            url: '/getServices',
            params: {"vehicleID": $scope.selectedVehicle.id}
        })
        .then(function(response) {
            $scope.vehicleServices = response.data;
            console.log(response.data);
        });
    });

    $scope.getServices = function() {
        $http({
            method: 'GET',
            url: '/getServices',
            params: {"vehicleID": $scope.selectedVehicle.id}
        })
        .then(function(response) {
            $scope.vehicleServices = response.data;
        });
    };

    $scope.remove = function(item) {
      //TODO: wire me up bro
    };


    $scope.showAll = function() {
        $scope.overviewVisible = true;
        $scope.snapshotVisible = true;
        $scope.spotlightVisible = true;
        $scope.scheduleVisible = true;
        $scope.logVisible = true;
        $scope.chartsVisible = true;
    };
    $scope.hideAll = function() {
        $scope.overviewVisible = false;
        $scope.snapshotVisible = false;
        $scope.spotlightVisible = false;
        $scope.scheduleVisible = false;
        $scope.logVisible = false;
        $scope.chartsVisible = false;
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
    $scope.scheduleShowHide = function() {
        $scope.scheduleVisible == true ? $scope.scheduleVisible = false : $scope.scheduleVisible = true;
    };
    $scope.logShowHide = function() {
        $scope.logVisible == true ? $scope.logVisible = false : $scope.logVisible = true;
    };
    $scope.intervalsShowHide= function(event) {
            event.preventDefault();
            $scope.inputsVisible = $scope.inputsVisible ? false : true;
    };
    $scope.chartsShowHide = function() {
        $scope.chartsVisible == true ? $scope.chartsVisible = false : $scope.chartsVisible = true;
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
        };
        $http({
            method: 'POST',
            url: '/updateOdometer',
            data: JSON.stringify(data)
        })
        .then(function(response) {
           //if(response.data == 'success'){
                $scope.vehicleServices.forEach(function(service){
                    service.milesTilDue -= response.data;
                })


               // $scope.getServices();
          /*  }
            else {
                alert("Error updating odometer. " /*+ display whatever error comes back from DB);
            };*/
        });
    };

    $scope.goToEditPage = function() {
          var id = $scope.selectedVehicle.id;
          $scope.goTo('/editVehicle/' + id);
        };

}])