angular.module('pitStop').controller('indexController', ['$scope', '$location', function($scope, $location) {

    $scope.isVisible= false;
    $scope.menuVisible= false;

    $scope.hide = function() {
        $scope.isVisible = false;
    };

    $scope.show= function() {
        $scope.isVisible= true;
    };

    $scope.showHide= function() {
        $scope.isVisible= $scope.isVisible ? false : true;
    };

    $scope.menuShowHide= function() {
        $scope.menuVisible= $scope.menuVisible ? false : true;
    };

    $scope.goTo = function(path) {
        $location.url(path);
    };

}])