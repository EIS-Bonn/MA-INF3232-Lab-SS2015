(function () {
  'use strict';
  //TODO: move upload to factory
  angular.module('angularApp.services')

  .factory('uploadService', function($resource){
    console.log("Upload service");
    
    return $resource('/upload');
  });

}());