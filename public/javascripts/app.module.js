var pitStop= angular.module('pitStop', ['ngRoute', 'angularCSS'])
    .config(["$routeProvider", function($routeProvider) {
          return $routeProvider.when("/", {
            templateUrl: 'C:/Users/Jeddy/Documents/ACA_FInal_Project/app/views/loginView.html',
            controller: 'loginController'
          }).otherwise({
            redirectTo: "/"
          });
        }
      ]);

