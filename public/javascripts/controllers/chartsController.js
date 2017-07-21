angular.module('pitStop').controller('chartsController', ['$scope', '$route', function($scope, $route) {

Chart.defaults.global.defaultFontFamily = "Roboto";
Chart.defaults.global.defaultFontColor = "#F7F7F7";

//Update charts when selectedVechicle changes//
$scope.$watch('completedServices', function() {

    //Delete old charts
    document.getElementById("chart1").innerHTML = '&nbsp;';
    document.getElementById("chart1").innerHTML = '<canvas id="totalByMonth"></canvas>';
    document.getElementById("chart2").innerHTML = '&nbsp;';
    document.getElementById("chart2").innerHTML = '<canvas id="totalByService"></canvas>';

    var data1 = {
       labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
       datasets: [{
           label: 'Total Maintenance Cost',
           data: $scope.costByMonthValues,
           backgroundColor: [
               '#6F2F9D',
               '#006DBE',
               '#FC0000',
               '#00AE4F',
               '#00AEED',
               '#6F2F9D',
               '#006DBE',
               '#FC0000',
               '#001F5F',
               '#00AEED',
               '#6F2F9D',
               '#006DBE'
           ]
       }]
    };

    var ctx = document.getElementById("totalByMonth");
    ctx.height= 455;
    ctx.width= 650;
    var drawTotalByMonthChart = function () {
        var totalByMonthChart = new Chart(ctx, {
            type: 'bar',
            data: data1,
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

    var ctx3 = document.getElementById("totalByService");
    ctx3.height = 175;
    ctx3.width = 250;

    var data2 = {
        datasets: [{
            data: $scope.serviceValues,
            backgroundColor: [
                '#006FB9',
                '#6F2F9F',
                '#00AEED',
                '#FC0000',
                '#00AE4D',
                '#001F5F'
            ],
            borderWidth: [
                0.5, 0.5, 0.5, 0.5, 0.5
            ]
        }],
        labels: $scope.serviceLabels
    };

    var drawTotalByServiceChart = function() {
        totalByServiceChart = new Chart(ctx3, {
        type: "doughnut",
        data: data2,
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
            }
        }
    })};
    drawTotalByMonthChart();
    drawTotalByServiceChart();
});

}])