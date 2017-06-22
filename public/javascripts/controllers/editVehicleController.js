angular.module('pitStop').controller('editVehicleController', ['$scope', function($scope) {
	
	$scope.services = [
            {id: "Oil Change", checked: false},
            {id: "Rotate Tires", checked: false},
            {id: "Replace Air Filter", checked: false},
            {id: "Tune-Up", checked: false},
            {id: "Trans Fluid Change", checked: false},
            {id: "Flush/Replace Coolant", checked: false},
            {id: "Change Brake Pads", checked: false},
            {id: "Change Spark Plugs", checked: false},
            {id: "Change Brake Fluid", checked: false},
            {id: "Change Power Steering Fluid", checked: false},
            {id: "Change Washer Fluid", checked: false},
            {id: "Replace Engine Belts", checked: false}
        ]


}])