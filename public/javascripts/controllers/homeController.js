angular.module('pitStop').controller('homeController', ['$scope', '$window', function($scope, $window) {

    $scope.vehicles =
    [{
        id: 0,
        nickname: "Kiley",
        make : "Ford",
        model : "Mustang",
        year : 2014,
        engine: "v8",
        odometer: 25000,
        nextService: "Oil Change",
        daysToService: 12,
        milesToService: 2034,
        odometerIsVisible: false
    },
    {
        id : 1,
        nickname: "Farm Truck",
        make : "Chevrolet",
        model : "Silverado",
        year : 2017,
        engine: "i4",
        odometer: 100000,
        nextService: "Rotate Tires",
        daysToService: 37,
        milesToService: 10374,
        odometerIsVisible: false
    },
    {
        id : 2,
        nickname: "Bike",
        make : "Suzuki",
        model : "GSXR-750",
        year : 2011,
        engine: "4cyl",
        odometer: 1000,
        nextService: "Lube Chain",
        daysToService: 10,
        milesToService: 74,
        odometerIsVisible: false
    }];

    $scope.remove = function(item) {
      var index = $scope.vehicles.indexOf(item);
      $scope.vehicles.splice(index, 1);
    }

    $scope.odometerShowHide= function(vehicle) {
        var index = $scope.vehicles.indexOf(vehicle);
        $scope.vehicles[index].odometerIsVisible = $scope.vehicles[index].odometerIsVisible ? false : true;

    };

    $window.scroll( function() {
            if($(this).scrollTop() > 60) {
                $('.fa-bars').fadeOut(0);
        }
            else {
                $('.fa-bars').fadeIn(250);
            }
    });


}])