angular.module('pitStop').controller('chartsController', ['$scope', '$route', function($scope, $route) {

Chart.defaults.global.defaultFontFamily = "Roboto";
Chart.defaults.global.defaultFontColor = "white";

//Update charts when selectedVechicle changes//
$scope.$watch('completedServices', function() {

var ctx = document.getElementById("totalByMonth");
ctx.height= 455;
ctx.width= 650;
var drawTotalByMonthChart = function () {
    var totalByMonthChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            datasets: [{
                label: 'Total Maintenance Cost',
                data: $scope.costByMonthValues,
                backgroundColor: [
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)',
                    'rgba(134, 195, 50, 1)'
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
                text: "Maintenance Cost YTD by Month",
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
ctx3.height = 175;
ctx3.width = 250;

data = {
    datasets: [{
        data: $scope.serviceValues,
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
    labels: $scope.serviceLabels
};


var drawTotalByServiceChart = function() {
    totalByServiceChart = new Chart(ctx3, {
    type: "doughnut",
    data: data,
    options: {
        title: {
            display: true,
            text: "Maintenance Cost YTD by Service",
            fontSize: 25,
            fontFamily: "'Roboto', 'sans-serif'",
            fontColor: 'white'
        },
        legend: {
            display: true,
            position: 'right'
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