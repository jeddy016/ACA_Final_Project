angular.module('pitStop').config(function ($routeProvider, $locationProvider, $qProvider) {

    $locationProvider.html5Mode(true);
    $qProvider.errorOnUnhandledRejections(false);

	$routeProvider
        .when('/', {
            templateUrl: 'assets/views/loginView.html',
            controller: 'loginController',
            css: 'assets/stylesheets/login.css'
        })

        .when('/newUser', {
                templateUrl: 'assets/views/newUserView.html',
                controller: 'newUserController',
                css: 'assets/stylesheets/newUser.css'
        })

        .when('/home', {
            templateUrl: 'assets/views/homeView.html',
            controller: 'homeController',
            css: 'assets/stylesheets/home.css'
        })

        .when('/newVehicle', {
            templateUrl: 'assets/views/newVehicleView.html',
            controller: 'newVehicleController',
            css: 'assets/stylesheets/vehicle.css'
        })

        .when('/editVehicle/:vehicle_id', {
                templateUrl: 'assets/views/editVehicleView.html',
                controller: 'editVehicleController',
                css: 'assets/stylesheets/vehicle.css'
        })

        .when('/findService', {
            templateUrl: 'assets/views/findServiceView.html',
            controller: 'findServiceController',
            css: 'assets/stylesheets/findService.css'
        })

        .when('/profile', {
            templateUrl: 'assets/views/profileView.html',
            controller: 'profileController',
            css: 'assets/stylesheets/newUser.css'
        })
});