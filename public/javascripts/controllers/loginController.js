angular.module('pitStop').controller('loginController', ['$scope', '$location', '$http', function($scope, $location, $http) {

	$scope.email = $scope.email;
	$scope.password = $scope.password;
	$scope.newPassword = $scope.newPassword;
    $scope.newEmail = $scope.newEmail;
    $scope.confirmPassword= $scope.confirmPassword;

    $scope.loginVisible = false;
    $scope.reqsVisible = false;

    $scope.loginShowHide= function() {
            $scope.loginVisible = $scope.loginVisible ? false : true;
    };
    $scope.showPwdReqs = function() {
            $scope.reqsVisible = true;
    };
    $scope.hidePwdReqs = function() {
            $scope.reqsVisible = false;
        };

    $scope.loginValidate = function () {

        //TODO: Validate form data before sending to server to prevent unnecessary DB trips

        $scope.loginData = {
            email: $scope.email,
            password: $scope.password
        }
        $http({
            method: 'POST',
            url: '/login',
            data: JSON.stringify($scope.loginData)
         })
        .then(function(response) {
            if(response.data == 'success'){

                $http.post('https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB-q9rUaYmRxp0xVXzsdY3EB9CZyGFOI1U')
                    .then(function(response) {
                        $scope.userLocation = response.data.location;
                        console.log($scope.userLocation);
                        $http({
                            method: 'POST',
                            url: '/setUserLocation',
                            data: JSON.stringify($scope.userLocation)
                         }); //TODO: error handling
                });
                $scope.goTo('/home');
            }
            else {
                $scope.loginErrors = [];
                response.data.forEach(function(error) {
                    $scope.loginErrors.push(error);
                });
            };
        });
    };

    $scope.signUpValidate = function () {
            $scope.newUserData = {
                email: $scope.newEmail,
                password: $scope.newPassword,
                passwordConfirm: $scope.confirmPassword
            };

            $http({
                method: 'POST',
                url: '/signup',
                data: JSON.stringify($scope.newUserData)
             })
            .then(function(response) {
                if(response.data == 'success'){
                    $scope.goTo('/newUser');
                }
                else {
                    	$scope.newPassword = "";
                    	$scope.confirmPassword = "";

                    $scope.newUserErrors = [];
                    response.data.forEach(function(error) {
                        $scope.newUserErrors.push(error);
                    });
                }
            })
        }
}])