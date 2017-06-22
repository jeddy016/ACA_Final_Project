angular.module('pitStop').controller('servicesController', ['$scope', function($scope) {

    $scope.inputsVisible = false;

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

    $scope.intervalsShowHide= function() {
            $scope.inputsVisible= $scope.inputsVisible ? false : true;
        };

}])