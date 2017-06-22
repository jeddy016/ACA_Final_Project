angular.module('pitStop').controller('reportsController', ['$scope', function($scope) {

$scope.vehicles =
    [{
        id: 0,
        nickname: "All Vehicles",
        totalServices: 20,
        servicesMissed: 3,
        totalCostYTD: 3350,
        avgServiceCost: 167.50,
        services: []
    },
    {
        id : 1,
        nickname: "Farm Truck",
        totalServices: 3,
        servicesMissed: 0,
        totalCostYTD: 150,
        avgServiceCost: 50.00,
        services: [
            {
            id: "Oil Change",
            totalCostYTD: 200
            },
            {
            id: "Change Spark Plugs",
            totalCostYTD: 50
            },
            {
            id: "Change Brake Pads",
            totalCostYTD: 150
            },
            {
            id: "Tire Rotation",
            totalCostYTD: 30
            }]
    },
    {
        id : 2,
        nickname: "Bike",
        totalServices: 2,
        servicesMissed: 1,
        totalCostYTD: 200,
        avgServiceCost: 100.00,
        services: [
            {
            id: "Oil Change",
            totalCostYTD: 100
            },
            {
            id: "Change Spark Plugs",
            totalCostYTD: 75
            },
            {
            id: "Change Brake Pads",
            totalCostYTD: 150
            },
            {
            id: "New Tires",
            totalCostYTD: 300
            }]
    },
    {
        id : 3,
        nickname: "Kiley",
        totalServices: 15,
        servicesMissed: 2,
        totalCostYTD: 3000,
        avgServiceCost: 200.00,
        services: [
            {
            id: "Oil Change",
            totalCostYTD: 250
            },
            {
            id: "Replace Brake Fluid",
            totalCostYTD: 50
            },
            {
            id: "New Wipers",
            totalCostYTD: 25
            },
            {
            id: "Tire Rotation",
            totalCostYTD: 30
            }]
    }];

$scope.selectedVehicle = $scope.vehicles[0];

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
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)',
                    'rgba(94, 180, 217, 1)'
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
                fontSize: 20,
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

var totalByCarData = [];
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
}

var serviceData = [];
var serviceLabels = [];

$scope.selectedVehicle.services.forEach(function(service){
    serviceData.push(service.totalCostYTD);
});

$scope.selectedVehicle.services.forEach(function(service){
    serviceLabels.push(service.id);
});


var ctx3 = document.getElementById("totalByService");
ctx3.height = 325;
ctx3.width = 400;

var drawTotalByServiceChart = function() {
    totalByServiceChart = new Chart(ctx3, {
    type: "doughnut",
    data: {
        labels: serviceLabels,
        datasets: [{
            label: 'Total Maintenance Cost',
            data: serviceData,
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
        }]
    },
    options: {
        title: {
            display: true,
            text: "Total Maintenance Cost YTD by Service",
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
if (typeof totalByServiceChart != 'undefined') {
         totalByServiceChart.destroy();
         drawTotalByServiceChart();
    }
    else {
        drawTotalByServiceChart();
    }

});
}])