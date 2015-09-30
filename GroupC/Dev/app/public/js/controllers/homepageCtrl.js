/*global angular,_*/
(function () {
  'use strict';

  angular.module('angularApp.controllers')

  .controller('homepageCtrl', ['$scope', '$http', '$rootScope', function($scope, $http, $rootScope) {

    //get list of files async  
    $http.get('/files')
      				.success(function (data) {
           					$scope.files = _.map(data);
        			});   


              
    $scope.visualize = function(file) {
    				 
    				 $scope.selected = file;
    				 //console.log('sel set=',$scope.selected);

      	             //async update the selected ontology
                    $http.post('/options', { 'fileName': $scope.selected })
                         .success(function(data, status, headers, config) {
                                //on success redirect to parser
                      		      location.href = "#parser";
                            })

                         .error(function(data, status, headers, config) {
                              //error handling
                        	     console.log("Could not update options");
                      });
        };   

    $scope.isSelected = function(file) {
        	//console.log('sel=',$scope.selected);
        	//console.log('file=',file);

            return $scope.selected === file;
    } 

  }]);//-homepageCtrl  
}());
