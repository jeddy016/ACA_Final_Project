angular.module('pitStop').controller('newUserController', ['$scope', '$http', function($scope, $http) {

    $scope.firstName = $scope.firstName;
    $scope.firstName = $scope.lastName;
    $scope.notificationsOptIn = $scope.notificationsOptIn;
    $scope.notificationsMilesAhead = $scope.notificationsMilesAhead;

    $scope.textOptionsVisible = false;

    $scope.times= [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    $scope.hideTextOptions = function() {
            $scope.textOptionsVisible = false;
            $scope.notificationsOptIn = 0;
    };
    $scope.showTextOptions= function() {
            $scope.textOptionsVisible= true;
            $scope.notificationsOptIn = 1;
    };

    console.log($scope.notificationsHour)

    $scope.addUser = function(){

        $scope.user = {
            firstName: $scope.firstName,
            lastName: $scope.lastName,
            notificationsOptIn: $scope.notificationsOptIn,
            notificationsMilesAhead: $scope.notificationsMilesAhead
        }

        $http({
            method: 'POST',
            url: '/addUser',
            data: JSON.stringify($scope.user)
         })
        .then(function(response) {
            if(response.data == 'success'){
                $scope.goTo('/newVehicle');
            }
            else {
            $scope.errorList = [];
            response.data.forEach(function(error) {
                $scope.errorList.push(error);
            });
            console.log(errorList);
            }
        })
        console.log($scope.user);
    }

}])