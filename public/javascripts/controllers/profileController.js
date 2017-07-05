angular.module('pitStop').controller('profileController', ['$scope', '$http', function($scope, $http) {

	$scope.formID = "profile-form";
	$scope.title = "Edit Your Profile";
    $scope.user = $scope.user;
	$scope.notificationsHour= $scope.notificationsHour;
	$scope.textOptionsVisible = $scope.textOptionsVisible;
	$scope.amPm = [{id: 1, name: "AM"}, {id: 2, name: "PM"}];
	$scope.meridian= $scope.meridian;

    $scope.times= [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    $scope.hideTextOptions = function() {
        $scope.textOptionsVisible = false;
    };
    $scope.showTextOptions= function() {
        $scope.textOptionsVisible= true;
    };

    $http({
        method: 'GET',
        url: '/getUser'
     })
    .then(function(response) {
        //TODO: form validation

       // if(response.data == 'success'){
            console.log(response.data);
            $scope.user = response.data;
            if ($scope.user.notificationsHour > 12 && < 24) {
                $scope.notificationsHour = $scope.user.notificationsHour - 12;
                $scope.meridian = 2;
            }
            else if($scope.user.notificationsHour == 24) {
                $scope.notificationsHour = 12;
                $scope.meridian = 1;
            }
            else {
                $scope.notificationsHour = $scope.user.notificationsHour;
                $scope.meridian = 1;
            }


            if ($scope.user.notificationsOptIn == 1) {
                $scope.textOptionsVisible = true;
            }
            else {
                $scope.textOptionsVisible = false;
            }
       // }
       /* else {
            $scope.loginErrors = [];
            response.data.forEach(function(error) {
                $scope.loginErrors.push(error);
            });
        }*/
    })

    $scope.updateProfile = function() {
        if($scope.notificationsHour < 12 && $scope.meridian  == 2){
            $scope.user.notificationsHour = parseInt($scope.notificationsHour) + 12;
        }
        else {
            $scope.user.notificationsHour = parseInt($scope.notificationsHour)
        }
        if($scope.notificationsHour == 12 && $scope.meridian == 1){
            $scope.user.notificationsHour = 24;
        }
        $http({
            method: 'POST',
            url: '/updateProfile',
            data: JSON.stringify($scope.user)
        })
        .then(function() {
            $scope.goTo('/home');
        });
    };


}])