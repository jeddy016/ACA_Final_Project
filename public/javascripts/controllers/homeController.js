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
    $scope.odometerShow= function(vehicle) {
        var index = $scope.vehicles.indexOf(vehicle);
        $scope.selectedVehicle.odometerIsVisible = true;
    };

    $scope.chartsShowHide = function() {
        $scope.chartsVisible == true ? $scope.chartsVisible = false : $scope.chartsVisible = true;
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

    //TODO: make this actually work for each vehicle
    $scope.services = [
                {
                id: "Oil Change",
                milesDue: 3250,
                milesInterval: 5000,
                recommendedMiles: 5000,
                daysDue: 17,
                daysInterval: 30,
                recommendedDays: 60
                },
                {
                id: "Change Spark Plugs",
                milesDue: 5637,
                milesInterval: 7000,
                recommendedMiles: 10000,
                daysDue: 50,
                daysInterval: 80,
                recommendedDays: 90
                },
                {
                id: "Change Brake Pads",
                milesDue: 5,
                milesInterval: 15000,
                recommendedMiles: 15000,
                daysDue: 1,
                daysInterval: 120,
                recommendedDays: 90
                },
                {
                id: "Tire Rotation",
                milesDue: 7500,
                milesInterval: 10000,
                recommendedMiles: 10000,
                daysDue: 34,
                daysInterval: 120,
                recommendedDays: 120
                }
            ]


}])