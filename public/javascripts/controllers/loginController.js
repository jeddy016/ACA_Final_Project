angular.module('pitStop').controller('loginController', ['$scope', '$location', '$http', function($scope, $location, $http) {

	$scope.email = $scope.email;
	$scope.password = $scope.password;

	$scope.newPassword = $scope.newPassword;
    $scope.newEmail = $scope.newEmail;
    $scope.confirmPassword= $scope.confirmPassword;

    $scope.loginVisible= false;

    $scope.loginShowHide= function() {
            $scope.loginVisible= $scope.loginVisible ? false : true;
    };

    $scope.validate = function () {
        $scope.data = {
            email: $scope.email,
            password: $scope.password
        }

        $http({
            method: 'POST',
            url: '/login',
            data: JSON.stringify($scope.data)
         })
        .then(function(response) {
            if(response.data.length < 1){
                $scope.goTo('/home');
            }
            else {
                $scope.errors = [];
                response.data.forEach(function(error) {
                    $scope.errors.push(error);
                });
            }
        })
    }
}])