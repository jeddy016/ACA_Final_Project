angular.module('pitStop').controller('newVehicleController', ['$scope', '$http', function($scope, $http) {

	$scope.make = $scope.make;
	$scope.model = $scope.model;
	$scope.year = $scope.year;
	$scope.engine = $scope.engine;
	$scope.odometerReading = $scope.odometerReading;
	$scope.nickname = $scope.nickname;
	$scope.servicesList = [];

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

    $scope.addVehicle = function (){
        $scope.services.forEach(function(service){
            if(service.checked == true){
                $scope.servicesList.push(service.id);
            }
        })

        $scope.vehicle = {
            make: $scope.make,
            model: $scope.model,
            year: $scope.year,
            engine: $scope.engine,
            odometerReading: $scope.odometerReading,
            nickname: $scope.nickname,
            services: $scope.servicesList
        }

        $http({
            method: 'POST',
            url: '/addVehicle',
            data: JSON.stringify($scope.vehicle)
        })
        .then(function(response) {
            if(response.data == 'success'){
                //$scope.goTo('/home');
            }
            else {
                $scope.loginErrors = [];
                response.data.forEach(function(error) {
                    $scope.loginErrors.push(error);
                });
            }
        })

        console.log($scope.vehicle)
    }


}])