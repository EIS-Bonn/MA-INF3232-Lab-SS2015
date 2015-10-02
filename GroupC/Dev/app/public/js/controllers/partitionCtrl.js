/*global angular */
(function () {
  'use strict';

  angular.module('angularApp.controllers')
    .controller('partitionCtrl', ['$scope',
	      function ($scope) {
			
		        $scope.graphs = [{
		            id:1,
		            content:'Content of the graph details '
		        }]
		        
		        $scope.counter = 1;
		    
		        $scope.addTab = function(){
		            $scope.counter++;
		            $scope.graphs.push({id:$scope.counter,content:'Graph content'});
		            $scope.selectedGraph = $scope.graphs.length - 1;  
		        }
		        
		    
		        $scope.deleteTab = function(index){
		            $scope.graphs.splice(index,1); 
		        }
		        
		        $scope.selectedGraph = 0; 
		        
		    
		        $scope.selectTab = function(index){
		            $scope.selectedGraph = index;
		        }
		}]);
  
}());
