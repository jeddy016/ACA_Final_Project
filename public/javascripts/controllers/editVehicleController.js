angular.module('pitStop').controller('editVehicleController', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {

	$scope.vehicleID = $routeParams.vehicle_id;
	$scope.vehicle = $scope.vehicle;
	$scope.years = [];
    $scope.makeList = [];

    $scope.serviceTypes = [
        {id: 1, name: "Oil Change", checked: false},
        {id: 2, name: "Replace Air Filter", checked: false},
        {id: 3, name: "Trans Fluid Change", checked: false},
        {id: 4, name: "Replace Coolant", checked: false},
        {id: 5, name: "Change Brake Pads", checked: false},
        {id: 6, name: "Change Spark Plugs", checked: false},
        {id: 7, name: "Change Brake Fluid", checked: false},
        {id: 8, name: "Change Power Steering Fluid", checked: false},
        {id: 9, name: "Change Washer Fluid", checked: false},
        {id: 10, name: "Replace Engine Belts", checked: false},
        {id: 11, name: "Rotate Tires", checked: false},
        {id: 12, name: "Tune-Up", checked: false}
    ]

    $http({
        method: 'GET',
        url: '/getVehicle',
        params: {"vehicleID": $scope.vehicleID}
    })
    .then(function(response) {
        $scope.vehicle = response.data;
        $scope.getYears();
        $scope.getMakes();
        $scope.getTrackedServices();
    });

    $scope.getMakes = function() {
        $http({
            method: 'GET',
            url: '/getMakes'
        })
        .then(function(response) {
            $scope.getModels();
            response.data.forEach(function(make){
                $scope.makeList.push({name: make.name , id: make.vehicleMakeID});
            });
        });
    }

    $scope.getModels= function() {
        $http({
            method: 'GET',
            url: '/getModels',
            params: {"makeID": $scope.vehicle.makeID}
        })
        .then(function(response) {
            $scope.modelList = [];
            response.data.forEach(function(model){
                $scope.modelList.push({name: model.name , id: model.vehicleModelID});
            });
        });
    };

    $scope.getTrackedServices = function() {
        $http({
        method: 'GET',
        url: '/getTrackedServices',
        params: {"vehicleID": $scope.vehicleID}
    })
        .then(function(response) {
            for(var i = 0; i < $scope.serviceTypes.length; i++){
                response.data.forEach(function(type){
                      if($scope.serviceTypes[i].id == type) {
                            $scope.serviceTypes[i].checked = true;
                      }
                })
            }
        });
    };

    $scope.getYears = function() {
        for(var i = 2018; i >= 1960; i--){
            $scope.years.push(i);
        };
    };

    $scope.updateVehicle = function(){
        $http({
            method: 'POST',
            url: '/updateVehicle',
            data: JSON.stringify($scope.vehicle)
        })
        .then(function(response) {
            if(response.data == 'success'){
                $scope.updateTrackedServices();
                $scope.goTo('/home');
            }
            else {
                $scope.errorList = [];
                response.data.forEach(function(error) {
                    $scope.errorList.push(error);
                });
            };
        });
    };

    $scope.updateTrackedServices = function() {
        $scope.selectedServices = [];

        $scope.serviceTypes.forEach(function(service){
            if(service.checked == true){
                $scope.selectedServices.push(service.id);
            };
        });

        $scope.data = {
            id: $scope.vehicleID,
            services : $scope.selectedServices
        }

        $http({
            method: 'POST',
            url:'/updateTrackedServices',
            data: JSON.stringify($scope.data)
        });
    }

}])