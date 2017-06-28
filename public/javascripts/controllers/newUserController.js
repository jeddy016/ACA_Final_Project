angular.module('pitStop').controller('newUserController', ['$scope', '$http', function($scope, $http) {

    $scope.firstName = $scope.firstName;
    $scope.firstName = $scope.lastName;
    $scope.phoneNumber = $scope.phoneNumber;
    $scope.zipCode = $scope.zipCode;
    $scope.notificationsOptIn = $scope.notificationsOptIn;
    $scope.notificationsHour = $scope.notificationsHour;
    $scope.amPm = $scope.amPm;
    $scope.notificationsDaysAhead = $scope.notificationsDaysAhead;

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
        if($scope.notificationsHour < 12 && $scope.amPm == 2){
            $scope.notificationsHour = parseInt($scope.notificationsHour) + 12;
        }
        if($scope.notificationsHour == 12 && $scope.amPm == 1){
                    $scope.notificationsHour = 24;
                }

        $scope.user = {
            firstName: $scope.firstName,
            lastName: $scope.lastName,
            phoneNumber: $scope.phoneNumber,
            zipCode: $scope.zipCode,
            notificationsOptIn: $scope.notificationsOptIn,
            notificationsHour: $scope.notificationsHour,
            notificationsDaysAhead: $scope.notificationsDaysAhead
        }

        $http({
            method: 'POST',
            url: '/addUser',
            data: JSON.stringify($scope.user)
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
        console.log($scope.user);
    }

}])