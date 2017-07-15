angular.module('pitStop').controller('profileController', ['$scope', '$http', function($scope, $http) {

	$scope.formID = "profile-form";
	$scope.title = "Edit Your Profile";
    $scope.user = $scope.user;
	$scope.textOptionsVisible = $scope.textOptionsVisible;
    $scope.notificationsMilesAhead = $scope.notificationsMilesAhead;


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
    .then(function successCallback(response) {
        $scope.user = response.data;
        if ($scope.user.notificationsOptIn == 1) {
            $scope.textOptionsVisible = true;
        }
        else {
            $scope.textOptionsVisible = false;
        }
    }, function errorCallback(){
        $scope.goTo('/');
    })

    $scope.updateProfile = function() {
        $http({
            method: 'POST',
            url: '/updateProfile',
            data: JSON.stringify($scope.user)
        })
        .then(function(response) {
            console.log(response.data)
            if(response.data == 'success'){
                $scope.goTo('/home');
            }
            else {
                $scope.errorList = [];
                response.data.forEach(function(error) {
                    $scope.errorList.push(error);
                });
                console.log($scope.errorList)
            };
        });
    };


}])