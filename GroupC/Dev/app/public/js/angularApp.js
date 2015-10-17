/*global angular*/
(function () {
  'use strict';

  angular.module('angularApp.controllers', []);
  angular.module('angularFileUpload');       
  angular.module('angularApp.services', []);
  angular.module('angularApp.factory', []);
  angular.module('angularApp.directives', []);
  // Declare app level module which depends on filters, and services
  angular.module('angularApp', [
    'ngRoute',
    'ngResource',
    'ngAnimate',
    'mgcrea.ngStrap',
    'angularApp.controllers',
    'angularApp.directives',
    'angularFileUpload',
    'angularApp.services'
  ])
      .config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
      //handle AngularJS routing
      $routeProvider.when('/', {templateUrl:  'views/homepage.html' , controller: 'homepageCtrl'});
      $routeProvider.when('/help', {templateUrl: 'views/help.html', controller: 'helpCtrl'});
      $routeProvider.when('/parser', {templateUrl: 'views/parser.html', controller: 'parserCtrl'});
      $routeProvider.when('/upload', {templateUrl: 'views/upload.html', controller: 'uploadCtrl'});
      $routeProvider.when('/partition', {templateUrl: 'views/partition.html', controller: 'partitionCtrl'});
      $routeProvider.when('/visualize', {templateUrl: 'views/visualize.html', controller: 'visualizeCtrl'});

    }])
      .run(function ($rootScope, $http) {
            //constructs the URL of the localhost
             $http.get('/options')
                .success(function (data) {
                      //you may add other variables in rootScope
                      //http://localhost:4000 
                      $rootScope.$url = "http://" + data.hostname + ":" + data.port;   //construct the URL              
                });              
          });
}());
