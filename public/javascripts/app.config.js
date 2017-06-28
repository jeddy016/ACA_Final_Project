angular.module('pitStop').config(function ($routeProvider, $locationProvider, $qProvider) {


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

	.when('/editVehicle', {
    		templateUrl: 'assets/views/editVehicleView.html',
    		controller: 'editVehicleController',
    		css: 'assets/stylesheets/vehicle.css'
    })

    .when('/services', {
        		templateUrl: 'assets/views/servicesView.html',
        		controller: 'servicesController',
        		css: 'assets/stylesheets/services.css'
    })

	.when('/logService', {
            		templateUrl: 'assets/views/logServiceView.html',
            		controller: 'logServiceController',
            		css: 'assets/stylesheets/logService.css'
    })

	.when('/findService', {
		templateUrl: 'assets/views/findServiceView.html',
		controller: 'findServiceController',
		css: 'assets/stylesheets/findService.css'
	})

	.when('/reports', {
        		templateUrl: 'assets/views/reportsView.html',
        		controller: 'reportsController',
        		css: 'assets/stylesheets/reports.css'
    })

	.when('/profile', {
		templateUrl: 'assets/views/profileView.html',
		controller: 'profileController'
	})

    $locationProvider.html5Mode(true);
});