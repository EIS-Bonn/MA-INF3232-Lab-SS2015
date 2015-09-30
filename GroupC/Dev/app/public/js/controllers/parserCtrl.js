/*global angular */
(function () {
  'use strict';

  angular.module('angularApp.controllers')
    .controller('parserCtrl', ['$scope', '$http',  function($scope, $http) {
    	
    //get options
    $http.get('/options')
      				.success(function (data) {           					
           					$scope.latest = data.fileName; //only get latest uploaded file
        			});   
              

    //get metrics

    $http.get('/metrics')
        .success(function (data) {       
            $scope.metrics = data;     
            console.log("METRICS", $scope.metrics);         
        })            
      .error(function(data) {
                              //error handling
                          $scope.metrics = data;     
            console.log("METRICS", $scope.metrics);         
                          console.log("Could not get metrics");
                      });
    
               


    //get parser result
    $http.get('/parser')
      				.success(function (data) {
           				
           					$scope.parser = data;           					
           					//console.log($scope.parser);
        			});


	}]);

  
  
}());
