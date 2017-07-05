angular.module('pitStop').controller('editVehicleController', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {

	$scope.vehicleID = $routeParams.vehicle_id;
	$scope.vehicle = $scope.vehicle;
	$scope.years = [];
    $scope.makeList = [];

    $http({
        method: 'GET',
        url: '/getVehicle',
        params: {"vehicleID": $scope.vehicleID}
    })
    .then(function(response) {
        $scope.vehicle = response.data;
        $scope.getYears();
        $scope.getMakes();
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

    $scope.getYears = function() {
        for(var i = 2018; i >= 1960; i--){
            $scope.years.push(i);
        };
    };

    $scope.updateVehicle = function (){
        var valid = true;
        //TODO: Error handling
        //TODO: Update services functionality

        if(valid) {
            //$scope.selectedServices = [];

           /* $scope.serviceTypes.forEach(function(service){
                if(service.checked == true){
                    $scope.selectedServices.push(service.id);
                };
            });*/

            $http({
                method: 'POST',
                url: '/updateVehicle',
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

	$scope.services = [
            {id: "Oil Change", checked: false},
            {id: "Rotate Tires", checked: false},
            {id: "Replace Air Filter", checked: false},
            {id: "Tune-Up", checked: false},
            {id: "Trans Fluid Change", checked: false},
            {id: "Flush/Replace Coolant", checked: false},
            {id: "Change Brake Pads", checked: false},
            {id: "Change Spark Plugs", checked: false},
            {id: "Change Brake Fluid", checked: false},
            {id: "Change Power Steering Fluid", checked: false},
            {id: "Change Washer Fluid", checked: false},
            {id: "Replace Engine Belts", checked: false}
    ]


}])