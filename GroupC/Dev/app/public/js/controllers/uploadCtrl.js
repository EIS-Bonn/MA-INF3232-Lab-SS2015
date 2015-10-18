/*global angular */
(function () {
  'use strict';

  angular.module('angularFileUpload')

  .controller('uploadCtrl', ['$scope', '$modal' ,'$http', 'FileUploader', 'uploadService', function($scope, $modal, $http, FileUploader, uploadService) {

        //call the upload service
        var uploadService = new uploadService();

        var uploader = $scope.uploader = new FileUploader({
            url: '/upload'
        });


        // FILTERS

        uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });
         
         //parse                       
        $scope.parse = function(file) {
                         
                         $scope.selected = file;
                         //console.log('sel set=',$scope.selected);
                        
                         //async update the selected ontology
                        $http.post('/options', { 'fileName': $scope.selected })
                             .success(function(data, status, headers, config) {
                                    //on success redirect to parser
                                      location.href = "#parser";
                                      // $scope.loading = false;   
                                })

                             .error(function(data, status, headers, config) {
                                  //error handling
                                     console.log("Could not update options");
                          });
            };   


        $scope.parseURL = function(url) {
                         
                         console.log('Parsgin' , url);
                         location.href = "#parser";    
                         //async update the selected ontology
                        $http.post('/options', { 'url': url })
                             .success(function(data, status, headers, config) {
                                    //on success redirect to parser
                                                                   
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
        // CALLBACKS

        uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function(fileItem) {
            console.info('onAfterAddingFile', fileItem);
        };
        uploader.onAfterAddingAll = function(addedFileItems) {
            console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function(item) {
            console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function(fileItem, progress) {
            console.info('onProgressItem', fileItem, progress);
            $scope.parse(fileItem);
        };
        uploader.onProgressAll = function(progress) {
            console.info('onProgressAll', progress);
            
             $http.get('/ontology/nodes')
                    .success(function (data) {
                            
                    });  
            $http.get('/ontology/edges')
                    .success(function (data) {                            
                    }); 

             $modal({scope: $scope, template: 'views/modal.html', show: true, size: 400});
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            
            console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function(fileItem, response, status, headers) {
            console.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
   
             $http.get('/ontology/nodes')
                    .success(function (data) {
                            
                    });  
            $http.get('/ontology/edges')
                    .success(function (data) {                            
                    });              
        };
        uploader.onCompleteAll = function() {
            console.info('onCompleteAll');
        };

        console.info('uploader', uploader);

        //URI upload
        $scope.url = {
            text: 'URI'
        }
    }]);
}());