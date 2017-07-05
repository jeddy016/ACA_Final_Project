angular.module('pitStop').controller('newVehicleController', ['$scope', '$http', function($scope, $http) {

    $scope.error = false;
	$scope.year = $scope.year;
	$scope.engine = $scope.engine;
	$scope.odometerReading = $scope.odometerReading;
	$scope.nickname = $scope.nickname;
	$scope.years = [];
    $scope.makeList = [];
    $scope.serviceTypes = [];

    $http({
        method: 'GET',
        url: '/getServiceTypes'
    })
    .then(function(response) {
        response.data.forEach(function(service){
            $scope.serviceTypes.push({id : service.serviceTypeID, name : service.typeName, checked : false})
        });
    });

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
        var valid = true;

        if($scope.make == -1 || $scope.model == null || $scope.year == null || $scope.odometerReading == null || $scope.nickname == null)
        {
            valid = false;
            $scope.error = true;
        };

        if(valid) {
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
                    $scope.loginErrors = [];
                    response.data.forEach(function(error) {
                        $scope.loginErrors.push(error);
                    });
                };
            });
        }
        else {
            console.log("nope");
        };
    };
}])