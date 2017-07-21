angular.module('pitStop').controller('newVehicleController', ['$scope', '$http', function($scope, $http) {

    $http({
        method: 'GET',
        url: '/checkForUser'
    })
    .then(function successCallback() {},
        function errorCallback() {
            $scope.goTo('/');
    });

    $scope.error = false;
	$scope.year = $scope.year;
	$scope.engine = $scope.engine;
	$scope.odometerReading = $scope.odometerReading;
	$scope.nickname = $scope.nickname;
	$scope.years = [];
    $scope.makeList = [];
    $scope.serviceTypes = [];

     $scope.serviceTypes = [
        {id: 1, name: "Oil Change", checked: false},
        {id: 2, name: "Replace Air Filter", checked: false},
        {id: 3, name: "Change Trans Fluid", checked: false},
        {id: 4, name: "Change Coolant", checked: false},
        {id: 5, name: "Replace Brake Pads", checked: false},
        {id: 6, name: "Replace Spark Plugs", checked: false},
        {id: 7, name: "Change Brake Fluid", checked: false},
        {id: 8, name: "Change Fuel Filter", checked: false},
        {id: 9, name: "Change Washer Fluid", checked: false},
        {id: 10, name: "Replace Engine Belts", checked: false},
        {id: 11, name: "Rotate Tires", checked: false},
        {id: 12, name: "Tune-Up", checked: false}
    ]

    $http({
        method: 'GET',
        url: '/getMakes'
    })
    .then(function(response) {
        response.data.forEach(function(make){
            $scope.makeList.push({name: make.name , id: '' + make.vehicleMakeID});
            $scope.make = $scope.makeList[0].id;
        });
    });

    $scope.getModels= function() {
        $http({
            method: 'GET',
            url: '/getModels',
            params: {"makeID": $scope.make}
        })
        .then(function(response) {
            $scope.modelList = [];
            response.data.forEach(function(model){
                $scope.modelList.push({name: model.name , id: '' + model.vehicleModelID});
            });
        });
    };

    for(var i = 2018; i >= 1960; i--){
        $scope.years.push(i);
    };

    $scope.addVehicle = function (){
        $scope.selectedServices = [];

        $scope.serviceTypes.forEach(function(service){
            if(service.checked == true){
                $scope.selectedServices.push(service.id);
            };
        });

        $scope.vehicle = {
            make: $scope.make,
            model: $scope.model,
            year: $scope.year,
            engine: $scope.engine,
            odometerReading: $scope.odometerReading,
            nickname: $scope.nickname,
            services: $scope.selectedServices
        };

        $http({
            method: 'POST',
            url: '/addVehicle',
            data: JSON.stringify($scope.vehicle)
        })
        .then(function(response) {
            if(response.data == 'success'){
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

}])