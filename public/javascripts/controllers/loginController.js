angular.module('pitStop').controller('loginController', ['$scope', '$location', function($scope, $location) {

	$scope.email= $scope.email;
	$scope.password = $scope.password;
	$scope.newPassword = $scope.newPassword;
	$scope.email = $scope.email;
    $scope.newEmail = $scope.newEmail;
    $scope.confirmPassword= $scope.confirmPassword;
    $scope.loginVisible= false;
    $scope.error = "";

    $scope.loginShowHide= function() {
                $scope.loginVisible= $scope.loginVisible ? false : true;
            };

    $scope.validate = function () {
        if ($scope.email == "admin" && $scope.password == "admin")
        {
            $scope.goTo('/home');
        }
        else
        {
            $scope.error= "Username or Password Incorrect";
        }
    }

}])