angular.module('pitStop').controller('newUserController', ['$scope', function($scope) {

    $scope.formID = "new-user-form";
    	$scope.title = "Complete Your Profile"
    $scope.time= $scope.time;
    $scope.textOptionsVisible = false;

    $scope.times= [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    $scope.days= [
        {id: "Sun", checked: false},
        {id: "Mon", checked: false},
        {id: "Tues", checked: false},
        {id: "Wed", checked: false},
        {id: "Thurs", checked: false},
        {id: "Fri", checked: false},
        {id: "Sat", checked: false}
    ]

    $scope.hideTextOptions = function() {
            $scope.textOptionsVisible = false;
        };

    $scope.showTextOptions= function() {
            $scope.textOptionsVisible= true;
        };


}])