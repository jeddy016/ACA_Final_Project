angular.module('pitStop').controller('homeController', ['$scope', '$window', '$http', '$route', '$rootScope', '$filter', function($scope, $window, $http, $route, $rootScope, $filter) {

    $scope.snapshotVisible = true;
    $scope.spotlightVisible = true;
    $scope.scheduleVisible = false;
    $scope.inputsVisible = false;
    $scope.logVisible = false;
    $scope.chartsVisible = false;
    $scope.passwordErrorVisible = false;
    $scope.historyVisible = false;

    $scope.nextDue = {};
    $scope.aggValues = {};

    $scope.vehicles = [];
    $scope.selectedVehicle = $scope.selectedVehicle;
    $scope.odoForm = {};
    $scope.odoForm.newOdometerReading = "";
    $scope.vehicleServices = [];
    $scope.completedServices = [];

    $scope.selectedService = $scope.vehicleServices[0];
    $scope.serviceOdometer = $scope.serviceOdometer;
    $scope.shop = $scope.shop;
    $scope.partsCost = $scope.partsCost;
    $scope.laborCost = $scope.laborCost;
    $scope.totalCost = $scope.totalCost;

    $scope.deletePassword = $scope.deletePassword;

    $scope.costByMonthValues = [];
    $scope.serviceLabels = [];
    $scope.serviceValues = [];

    $http({
        method: 'GET',
        url: '/getVehicles'
    })
    .then(function(response) {
        $scope.vehicles = response.data;
        $scope.selectedVehicle = $scope.vehicles[0];
        $scope.getServices();
        $scope.getNextDue();
        $scope.getAggValues();
        $scope.getCostByMonth();
        $scope.getCostByService();
    });

    $scope.getNextDue = function() {
        $http({
             method: 'GET',
             url: '/getNextDue'
         })
         .then(function(response) {
             $scope.nextDue = response.data;
         });
     }

    $scope.getAggValues = function() {
        $http({
             method: 'GET',
             url: '/getTotalAndAVG'
         })
         .then(function(response) {
             $scope.aggValues = response.data;
         });
     }

    $scope.getServices = function() {
        $http({
            method: 'GET',
            url: '/getServices',
            params: {"vehicleID": $scope.selectedVehicle.id}
        })
        .then(function(response) {
            $scope.vehicleServices = response.data;
            $scope.getCompletedServices();
        });
    };

    $scope.getCompletedServices = function(){
        $http({
            method: 'GET',
            url: '/getCompletedServices',
            params: {"vehicleID": $scope.selectedVehicle.id}
        })
        .then(function(response) {
            $scope.completedServices = response.data;
        });
    };

    $scope.deleteVehicle = function() {
        $http({
            method: 'POST',
            url: '/deleteVehicle/' + $scope.selectedVehicle.id,
            data: {password: $scope.deletePassword}
        })
        .then(function(response){
            if(response.data == "success") {
                $http({
                    method: 'GET',
                    url: '/getVehicles'
                })
                .then(function(response) {
                    $scope.vehicles = response.data;
                    $scope.selectedVehicle = $scope.vehicles[0];
                    $('#deleteModal').modal('toggle');
                    $http({
                        method: 'GET',
                        url: '/getServices',
                        params: {"vehicleID": $scope.selectedVehicle.id}
                    })
                    .then(function(response) {
                        $scope.vehicleServices = response.data;
                    });
                });
            }
            else {
                $scope.passwordErrorVisible = true;
            };
        });
    };

    $scope.logService = function() {
        $scope.odometerDifference = $scope.serviceOdometer - $scope.selectedVehicle.currentOdometer;
        $scope.selectedVehicle.currentOdometer = $scope.serviceOdometer;

         $scope.data = {
            vehicleID: $scope.selectedVehicle.id,
            serviceID: $scope.selectedService,
            date: $scope.formattedDate = $filter('date')($scope.dt, "yyyy-MM-dd"),
            odometer: $scope.serviceOdometer,
            shop: $scope.shop,
            partsCost: $scope.partsCost,
            laborCost: $scope.laborCost,
            totalCost: $scope.totalCost,
            odometerDifference: $scope.odometerDifference
        };

        $http({
            method: 'POST',
            url: '/logService',
            data: JSON.stringify($scope.data)
        })
        .then(function(response) {
            if(response.data == "service logged") {
                $scope.updateOdometer();
                $scope.getServices();
                $scope.getAggValues();
                $scope.getCostByMonth();
                $scope.getCostByService();

                $scope.selectedService = null;
                $scope.serviceOdometer = null;
                $scope.shop = null;
                $scope.partsCost = null;
                $scope.laborCost = null;
                $scope.totalCost = null;
            }
            else {
                console.log(response.data);
            };
        });
    };

    $scope.updateOdometer = function(){
            $scope.id = $scope.selectedVehicle.id;

            var data= {
                vehicleID : $scope.id,
                reading : $scope.selectedVehicle.currentOdometer
            };
            $http({
                method: 'POST',
                url: '/updateOdometer',
                data: JSON.stringify(data)
            })
            .then(function(response) {
               //if(response.data == 'success'){
                    $scope.vehicleServices.forEach(function(service){
                        service.milesTilDue -= response.data;
                        $scope.getNextDue();
                    })

                   // $scope.getServices();
              /*  }
                else {
                    alert("Error updating odometer. " /*+ display whatever error comes back from DB);
                };*/
            });
        };

    $scope.getCostByMonth = function() {
        $http({
            method: 'GET',
            url: '/getTotalCostByMonth',
            params: {"vehicleID": $scope.selectedVehicle.id}
        })
        .then(function(response) {
            $scope.costByMonthValues = [];
            var values = response.data;
            for(var i = 0; i < 12; i++){
                var cost = 0;
                values.forEach(function(val){
                    if(val[0] == i+1){
                        cost = val[1];
                    }
                })
                $scope.costByMonthValues.push(cost);
            };
        });
    };

    $scope.getCostByService = function() {
        $http({
            method: 'GET',
            url: '/getCostByService',
            params: {"vehicleID": $scope.selectedVehicle.id}
        })
        .then(function(response) {
            $scope.serviceLabels = [];
            $scope.serviceValues = [];

            response.data.forEach(function(val){
                $scope.serviceLabels.push(val[0]);
                $scope.serviceValues.push(val[1]);
            });
        });
    };

    $scope.updateIntervals = function() {
        var data= [];

        $scope.vehicleServices.forEach(function(service){
            data.push({serviceID: service.id, interval: service.milesInterval})
        })

        console.log(JSON.stringify(data))
        $http({
            method: 'POST',
            url: '/updateIntervals',
            data: JSON.stringify(data)
        })
        .then(function(response) {
            console.log(response.data);
        });
    };

    /*$scope.showAll = function() {
        $scope.overviewVisible = true;
        $scope.snapshotVisible = true;
        $scope.spotlightVisible = true;
        $scope.scheduleVisible = true;
        $scope.logVisible = true;
        $scope.chartsVisible = true;
    };
    $scope.hideAll = function() {
        $scope.overviewVisible = false;
        $scope.snapshotVisible = false;
        $scope.spotlightVisible = false;
        $scope.scheduleVisible = false;
        $scope.logVisible = false;
        $scope.chartsVisible = false;
    };*/

    $scope.overviewShowHide = function() {
        $scope.overviewVisible == true ? $scope.overviewVisible = false : $scope.overviewVisible = true;
    };
    $scope.snapshotShowHide = function() {
        $scope.snapshotVisible == true ? $scope.snapshotVisible = false : $scope.snapshotVisible = true;
    };
    $scope.toolsShowHide = function() {
        $scope.toolsVisible == true ? $scope.toolsVisible = false : $scope.toolsVisible = true;
    };
    $scope.spotlightShowHide = function() {
        $scope.spotlightVisible == true ? $scope.spotlightVisible = false : $scope.spotlightVisible = true;
    };
    $scope.scheduleShowHide = function() {
        $scope.scheduleVisible == true ? $scope.scheduleVisible = false : $scope.scheduleVisible = true;
    };
    $scope.logShowHide = function() {
        $scope.logVisible == true ? $scope.logVisible = false : $scope.logVisible = true;
    };
    $scope.historyShowHide = function() {
        $scope.historyVisible == true ? $scope.historyVisible = false : $scope.historyVisible = true;
    };
    $scope.intervalsShowHide= function(event) {
            event.preventDefault();
            $scope.inputsVisible = $scope.inputsVisible ? false : true;
    };
    $scope.chartsShowHide = function() {
        $scope.chartsVisible == true ? $scope.chartsVisible = false : $scope.chartsVisible = true;
    };

    $scope.odometerShow= function(vehicle) {
        var index = $scope.vehicles.indexOf(vehicle);
        $scope.selectedVehicle.odometerIsVisible = true;
    };

    $scope.odometerHide= function(vehicle) {
        var index = $scope.vehicles.indexOf(vehicle);
        $scope.selectedVehicle.odometerIsVisible = false;

        if($scope.selectedVehicle.currentOdometer != $scope.newOdometerReading) {
            $scope.selectedVehicle.currentOdometer = $scope.selectedVehicle.currentOdometer;
        }
    };

    $scope.goToEditPage = function() {
          var id = $scope.selectedVehicle.id;
          $scope.goTo('/editVehicle/' + id);
        };

//Delete modal//
    $('#deleteModal').on('shown.bs.modal', function () {
      $('#password').focus()
    });

//DatePicker//
    $scope.today = function() {
        $scope.dt = new Date();
      };
      $scope.today();

      $scope.clear = function() {
        $scope.dt = null;
      };

      $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: false
      };

      $scope.dateOptions = {
        formatYear: 'yyyy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
      };

      $scope.toggleMin = function() {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
      };

      $scope.toggleMin();

      $scope.open1 = function() {
        $scope.popup1.opened = true;
      };

      $scope.setDate = function(year, month, day) {
        $scope.dt = new Date(year, month, day);
      };

      $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
      $scope.format = $scope.formats[1];
      $scope.altInputFormats = ['M!/d!/yyyy'];

      $scope.popup1 = {
        opened: false
      };

      var tomorrow = new Date();
      tomorrow.setDate(tomorrow.getDate() + 1);
      var afterTomorrow = new Date();
      afterTomorrow.setDate(tomorrow.getDate() + 1);
      $scope.events = [
        {
          date: tomorrow,
          status: 'full'
        },
        {
          date: afterTomorrow,
          status: 'partially'
        }
      ];

      function getDayClass(data) {
        var date = data.date,
          mode = data.mode;
        if (mode === 'day') {
          var dayToCheck = new Date(date).setHours(0,0,0,0);

          for (var i = 0; i < $scope.events.length; i++) {
            var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

            if (dayToCheck === currentDay) {
              return $scope.events[i].status;
            }
          }
        }

        return '';
      }

}])