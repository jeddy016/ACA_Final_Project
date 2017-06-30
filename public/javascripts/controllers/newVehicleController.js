angular.module('pitStop').controller('newVehicleController', ['$scope', '$http', function($scope, $http) {

    $scope.make = $scope.make;
	$scope.year = $scope.year;
	$scope.engine = $scope.engine;
	$scope.odometerReading = $scope.odometerReading;
	$scope.nickname = $scope.nickname;
    $scope.makeList = [{name: 'Select' , id: '0'}];
    $scope.modelList = [{name: 'Select' , id: '0'}];
    $scope.model = $scope.modelList[0].id;

    $http({
        method: 'GET',
        url: '/getMakes'
    })
    .then(function(response) {
        response.data.forEach(function(make){
            $scope.makeList.push({name: make.name , id: '' + make.vehicleMakeID});
            $scope.make = $scope.makeList[0].id;
            $scope.model = $scope.modelList[0].id;
        });
    })

    $scope.getModels = function() {
        if($scope.make == 0){
            console.log('nope');
            $scope.modelList = [{name: 'Select' , id: '0'}];
            $scope.model = $scope.modelList[0].id;
        }
        else {
            $scope.modelList = [{name: 'Select' , id: '0'}];
            console.log('success!')
            $http({
                method: 'GET',
                url: '/getModels',
                params: {"makeID": $scope.make}
            })
            .then(function(response) {
                console.log(response.data);
                response.data.forEach(function(model){
                    $scope.modelList.push({name: model.name , id: '' + model.vehicleModelID});
                });
            });
        }
    }

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

    $scope.years = [];
    for(var i = 2018; i >= 1960; i--){
        $scope.years.push(i);
    }

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
                $scope.goTo('/home');
            }
            else {
                $scope.loginErrors = [];
                response.data.forEach(function(error) {
                    $scope.loginErrors.push(error);
                });
            }
        })
    }


}])