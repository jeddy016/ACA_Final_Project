angular.module('pitStop').controller('newUserController', ['$scope', function($scope) {

    $scope.formID = "new-user-form";
    $scope.title = "Complete Your Profile"
    $scope.action= "addUser"

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