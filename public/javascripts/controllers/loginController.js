angular.module('pitStop').controller('loginController', ['$scope', '$location', '$http', function($scope, $location, $http) {

	$scope.email;
	$scope.password = $scope.password;

	$scope.newPassword = $scope.newPassword;
    $scope.newEmail = $scope.newEmail;
    $scope.confirmPassword= $scope.confirmPassword;

    $scope.loginVisible= false;
    $scope.error = "";

    $scope.loginShowHide= function() {
                $scope.loginVisible= $scope.loginVisible ? false : true;
    };

    $scope.validate = function () {
        var data = {
                email: $scope.email,
                password: $scope.password
        }

        if ($scope.email == "admin" && $scope.password == "admin")
        {
            $http({
                method: 'POST',
                url: '/login',
                data: JSON.stringify(data)
             })
            .then(function(response) {
                if(response.status == 200){
                    $scope.goTo('/home');
                }
                else {
                    $scope.error= "Username or Password Incorrect";
                }
            })
        }
        else
        {
            $scope.error= "Username or Password Incorrect";
        }
    }

}])