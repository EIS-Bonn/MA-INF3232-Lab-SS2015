/*global angular */
(function () {
  'use strict';

  angular.module('angularApp.controllers')
    .controller('parserCtrl', ['$scope', '$http', '$modal',  '$rootScope', '$timeout',  function($scope, $http, $modal, $rootScope, $timeout) {    	

    //get options
    $http.get('/options')
      				.success(function (data) {           					
           					$scope.latest = data.fileName; //only get latest uploaded file
        			});   

    //get parser result    
    $scope.loading = true; 
    $scope.timeout = false; 

    //trigger timeout after 3 minutes
    $timeout(function() {                      
                    console.log('timeout fired');
                    $scope.timeout = true;
                    $http.get('/metrics').success(function (dataMetrics) {       
                         $scope.metrics = dataMetrics;     
                        console.log("RDFSTORE METRICS", $scope.metrics);    
                        $scope.loading = false;   
                        $('.modal-backdrop').remove();                                                       
                        $('.am-fade').remove();  
                    });
    //}, 3000);   
    }, 180000);          
    //use this if you want to add a modal
    //$modal({scope: $scope, templateUrl: 'views/loader.html', show: $scope.loading, size: 400});
    $http.get('/parser')
      				.success(function (data) {
             					$scope.parser = data;           					           			
                      console.log("JAVA METRICS", $scope.parser);    
                      $http.get('/metrics').success(function (dataMetrics) {       
                      $scope.metrics = dataMetrics;     

                      console.log("RDFSTORE METRICS", $scope.metrics);   
                      $scope.loading = false;                       

                      $('.modal-backdrop').remove();                                                       
                      $('.am-fade').remove();

                      }).error(function(data) {       

                       $scope.loading = false;                       

                       $('.modal-backdrop').remove();                                                       
                         $('.am-fade').remove();
                          console.log("Could not get RDFSTORE metrics");
                        });
                      })

              .error(function (data) {                  
                    
                      console.log("Could not get JAVA METRICS", $scope.parser);    
                        
                        $http.get('/metrics').success(function (dataMetrics) {       
                         $scope.metrics = dataMetrics;     
                        console.log("RDFSTORE METRICS", $scope.metrics);    
                        $scope.loading = false;   
                        $('.modal-backdrop').remove();                                                       
                        $('.am-fade').remove();
  
                        }).error(function(data) {       
                          console.log("Could not get RDFSTORE metrics");
                        });
                      });
	}]);

  
  
}());
