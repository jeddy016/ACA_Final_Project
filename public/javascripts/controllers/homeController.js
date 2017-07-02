angular.module('pitStop').controller('homeController', ['$scope', '$window', '$http', '$route', function($scope, $window, $http, $route) {

    $scope.snapshotVisible = true;
    $scope.spotlightVisible = false;
    $scope.toolsVisible = false;

    $scope.vehicles = [];
    $scope.selectedVehicle = $scope.selectedVehicle;

    $http({
        method: 'GET',
        url: '/getVehicles'
    })
    .then(function(response) {
        $scope.vehicles = response.data;
        $scope.selectedVehicle = $scope.vehicles[0];
    })

    $scope.odoForm = {};
    $scope.odoForm.newOdometerReading = "";

    $scope.remove = function(item) {
      //TODO: wire me up bro
    }

    $scope.showAll = function() {
        $scope.overviewVisible = true;
        $scope.snapshotVisible = true;
        $scope.spotlightVisible = true;
        $scope.toolsVisible = true;
    }

     $scope.hideAll = function() {
        $scope.overviewVisible = false;
        $scope.snapshotVisible = false;
        $scope.spotlightVisible = false;
        $scope.toolsVisible = false;
    }

    $scope.overviewShowHide = function() {
        $scope.overviewVisible == true ? $scope.overviewVisible = false : $scope.overviewVisible = true;
    }
    
    $scope.snapshotShowHide = function() {
        $scope.snapshotVisible == true ? $scope.snapshotVisible = false : $scope.snapshotVisible = true;
    }
    
    $scope.toolsShowHide = function() {
        $scope.toolsVisible == true ? $scope.toolsVisible = false : $scope.toolsVisible = true;
    }
    
    $scope.spotlightShowHide = function() {
        $scope.spotlightVisible == true ? $scope.spotlightVisible = false : $scope.spotlightVisible = true;
    }

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

    $scope.updateOdometer = function(){
        $scope.id = $scope.selectedVehicle.id;

        var data= {
            vehicleID : $scope.id,
            reading : $scope.selectedVehicle.currentOdometer
        }
        $http({
            method: 'POST',
            url: '/updateOdometer',
            data: JSON.stringify(data)
        })
        .then(function(response) {
            if(response.data == 'success'){
                console.log("odometer updated");
            }
            else {
                alert("Error updating odometer. " /*+ display whatever error comes back from DB*/);
            }
        })
    }

    $scope.goToEditPage = function(vehicle) {
          var id = $scope.vehicles.indexOf(vehicle);
          $scope.goTo('/editVehicle/' + id);
        }

    $window.scroll( function() {
            if($(this).scrollTop() > 60) {
                $('.fa-bars').fadeOut(0);
        }
            else {
                $('.fa-bars').fadeIn(250);
            }
    });


Chart.defaults.global.defaultFontFamily = "Roboto";
Chart.defaults.global.defaultFontColor = "white";

//Update charts when selectedVechicle changes//
$scope.$watch('selectedVehicle', function() {

var ctx = document.getElementById("totalByMonth");
ctx.height= 550;
ctx.width= 800;
var drawTotalByMonthChart = function () {
    var totalByMonthChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            datasets: [{
                label: 'Total Maintenance Cost',
                data: [90, 30, 5, 0, 75, 0, 0, 100, 50, 0, 120, 40],
                backgroundColor: [
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff',
                    '#33c2ff'
                ]
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true,
                        fontFamily: "'Roboto', 'sans-serif'",
                        fontColor: 'white'
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, .8)',
                        zeroLineColor: 'rgba(255, 255, 255, .8)'
                    }
                }],
                xAxes: [{
                    ticks: {
                        beginAtZero:true,
                        fontColor: 'white',
                        fontFamily: "'Roboto', 'sans-serif'"
                    },
                    gridLines: {
                        display: false
                    }
                }]
            },
            title: {
                 display: true,
                text: "Total Maintenance Cost YTD by Month",
                fontSize: 25,
                fontFamily: "'Roboto', 'sans-serif'",
                fontColor: 'white'
            },
            legend: {
                display: false
            }
        }
})};

//Destroy old chart and replace with new values
if (typeof totalByMonthChart != 'undefined') {
         totalByMonthChart.destroy();
         drawTotalByMonthChart();
}
else {
    drawTotalByMonthChart();
}

/*var totalByCarData = [];
$scope.vehicles.forEach(function(vehicle){
    if(vehicle.id != 0){
        totalByCarData.push(vehicle.totalCostYTD);
    }
})
var ctx2 = document.getElementById("totalByCar");
ctx2.height = 325;
ctx2.width = 400;
var drawTotalByVehicleChart = function() {
    var totalByVehicleChart = new Chart(ctx2, {
        type: "doughnut",
        data: {
            labels: ["Farm truck", "Bike", "Kiley"],
            datasets: [{
                label: 'Total Maintenance Cost',
                data: totalByCarData,
                backgroundColor: [
                    'rgba(251, 161, 0, 0.75)',
                    'rgba(94, 180, 217, 0.75)',
                    'rgba(134, 195, 50, 0.7)'
                ],
                borderColor: [
                    '#ff9a1f',
                    '#33c2ff',
                    '#97ff05'
                ]
            }]
        },
        options: {
            title: {
                display: true,
                text: "Total Maintenance Cost YTD by Vehicle",
                fontSize: 20,
                fontFamily: "'Roboto', 'sans-serif'",
                fontColor: 'white'
            },
            legend: {
                display: true,
                position: 'bottom'
            },
            pieceLabel: {
                        mode: 'percentage',
                        fontSize: 15,
                        fontColor: 'white',
                        fontFamily: "Roboto",
                        position: 'inside',
                        format: function (value) {
                                return '$' + value;
                        }
             }
        }
})};

//Destroy old chart and replace with new values
if (typeof totalByVehicleChart != 'undefined') {
         totalByVehicleChart.destroy();
         drawTotalByVehicleChart();
}
else {
    drawTotalByVehicleChart();
}*/


var serviceData = [];
var serviceLabels = [];

var ctx3 = document.getElementById("totalByService");
ctx3.height = 325;
ctx3.width = 400;

data = {
    datasets: [{
        data: [100, 50, 30, 150],
        backgroundColor: [
                'rgba(251, 161, 0, 0.75)',
                'rgba(94, 180, 217, 0.75)',
                'rgba(134, 195, 50, 0.7)',
                'rgba(220, 9, 9, 0.75)'
        ],
        borderColor: [
            '#ff9a1f',
            '#33c2ff',
            '#97ff05',
            'rgba(255, 10, 10, 1)'
        ]
    }],
    labels: [
        'Replace Spark Plugs',
        'Oil Change',
        'Tire Rotation',
        'Tune-Up'
    ]

};


var drawTotalByServiceChart = function() {
    totalByServiceChart = new Chart(ctx3, {
    type: "doughnut",
    data: data,
    options: {
        title: {
            display: true,
            text: "Total Maintenance Cost YTD by Service",
            fontSize: 25,
            fontFamily: "'Roboto', 'sans-serif'",
            fontColor: 'white'
        },
        legend: {
            display: true,
            position: 'bottom'
        },
        pieceLabel: {
                    mode: 'value',
                    fontSize: 15,
                    fontColor: 'white',
                    fontFamily: "Roboto",
                    position: 'inside',
                    format: function (value) {
                            return '$' + value;
                    }
         }
    }

})};

//Destroy old chart and replace with new values
if (typeof totalByServiceChart != 'undefined') {
         totalByServiceChart.destroy();
         drawTotalByServiceChart();
    }
    else {
        drawTotalByServiceChart();
    }

});

}])