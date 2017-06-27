angular.module('pitStop').controller('profileController', ['$scope', function($scope) {

	$scope.formID = "profile-form";
	$scope.action = "updateProfile";
	$scope.title = "Edit Your Profile";
	$scope.time= $scope.time;
    $scope.textOptionsVisible = false;

    $scope.times= [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    $scope.hideTextOptions = function() {
                $scope.textOptionsVisible = false;
    };

    $scope.showTextOptions= function() {
            $scope.textOptionsVisible= true;
    };


}])