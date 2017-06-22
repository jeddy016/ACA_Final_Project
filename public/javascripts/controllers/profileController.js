angular.module('pitStop').controller('profileController', ['$scope', function($scope) {

	$scope.formID = "profile-form";
	$scope.title = "Edit Your Profile"
	$scope.time= $scope.time;
    $scope.textOptionsVisible = false;

    $scope.times= [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    $scope.days= [
        {id: "Sun", checked: false},
        {id: "Mon", checked: false},
        {id: "Tue", checked: false},
        {id: "Wed", checked: false},
        {id: "Thu", checked: false},
        {id: "Fri", checked: false},
        {id: "Sun", checked: false}
    ]

    $scope.hideTextOptions = function() {
                $scope.textOptionsVisible = false;
            };

        $scope.showTextOptions= function() {
                $scope.textOptionsVisible= true;
            };


}])