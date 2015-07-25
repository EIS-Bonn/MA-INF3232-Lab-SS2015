/**
 * Created by umut on 6/20/15.
 */

var mobivocApp = angular.module('mobivocApp', ['fsCordova', 'ui.bootstrap', 'ngAnimate', 'ngRoute', 'ngMap']);

mobivocApp.config(function($routeProvider, $httpProvider){

    $httpProvider.defaults.useXDomain=true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $routeProvider
        .when('/', {
            templateUrl : 'js/views/home.html',
            controller  : 'HomePageCtrl',
            css: 'css/map.css'
        })
        .when('/map', {
            templateUrl : 'js/views/map.html',
            controller  : 'MapsCtrl',
            css: 'css/map.css'
        })
        .when('/fillingStationInfo',{
            templateUrl : 'js/views/fillingStationInfo.html',
            controller  : 'FillingStationInfoCtrl'
        })
        .when('/fuelStation/:param1',{
            templateUrl : 'js/views/fuelStation.html',
            controller  : 'FuelStationCtrl'
        })



});



mobivocApp.controller('MainController',
    function(CordovaService){
        CordovaService.ready.then(function(){

        });
    }
);

mobivocApp.controller('NavBarCtrl',
    function($scope){
        $scope.isCollapsed = true;
    }
);

mobivocApp.controller('FuelStationCtrl',
    function($scope, $rootScope, $routeParams, $http, $location){

        var fillingStationType, fuelType, locationValue;

        $scope.itemList=[];
        $scope.fuelTypes=[{id:1,name:"Adblue"},{id:2,name:"Autogas"},{id:3,name:"Biodiesel"},{id:3,name:"CompressedNaturalGas"},{id:3,name:"E85"},{id:3,name:"Ethanol"},
            {id:3,name:"ExcelliumDiesel"},{id:3,name:"ExcelliumSuperPlus"},{id:3,name:"Hydrogen"},{id:3,name:"LiquidGas"},{id:3,name:"LiquidPetroleumGas"},
            {id:3,name:"LKWDiesel"},{id:3,name:"MaxxMotionDiesel"},{id:3,name:"MaxxMotionSuper100"},{id:3,name:"Methane"},{id:3,name:"Petrol"},
            {id:3,name:"Super(E5)"},{id:3,name:"Super(E10)"},{id:3,name:"SuperDiesel"},{id:3,name:"SuperPlus"},{id:3,name:"UltimateDiesel"},
            {id:3,name:"UltimateSuper"},{id:3,name:"VPowerDiesel"},{id:3,name:"VPowerRacing"}]






        function changedValueFuelType(item){
            fuelType = item.name;

            $scope.my = fuelType;
        }






        var locationValue =  $routeParams.param1;

        if(locationValue == 'CurrentLocation'){
            //$scope.my = 'im here';
            navigator.geolocation.getCurrentPosition(function(position){
                var longitude =  position.coords.longitude;
                var latitude = position.coords.latitude;

                //$scope.my = longitude;


                //$scope.my = longitude;


                $http.get('http://172.16.1.102:8080/Mobivoc/fuelstation?longitude=' + position.coords.longitude  + '&latitude=' + position.coords.latitude)


                    .success(function (data, status, headers, config) {
                        // ...

                        //$rootScope.$apply(function() {
                        $location.path("/fillingStationInfo");
                        console.log($location.path());
                        //});

                        //$scope.restCaller = data.fillingStation[0].longtitute + data.fillingStation[0].latitute;
                        //long = data.fillingStation[0].longtitute;
                        //lat =  data.fillingStation[0].latitute;

                    })
                    .error(function (data, status, headers, config) {
                        // ...
                        $scope.restCaller = status;
                    });







            });
        }else{
            $scope.my = locationValue;
        }
    }
);

mobivocApp.controller('HomePageCtrl',
    function($scope, $http, $rootScope, $location){


        var fillingStationType, fuelType, locationValue;

        $scope.itemList=[];

        //$scope.fuelTypes=[{id:1,name:"Adblue"},{id:1,name:"Adblue"}];

        $scope.fuelTypes=[];


        $scope.fillingStationTypes=[{id:1,name:"FuelStation"},{id:2,name:"BatteryStation"}];

        $scope.type = "Fuel/Battery-Plug Type";


        $scope.changedValueFillingStation=function(item){
            //$scope.itemList.push(item.name);

            fillingStationType = item.name;


            if(fillingStationType == 'FuelStation'){

                $scope.type = "Fuel Type";

                $scope.fuelTypes=[{id:1,name:"Adblue"},{id:2,name:"Autogas"},{id:3,name:"Biodiesel"},{id:3,name:"CompressedNaturalGas"},{id:3,name:"E85"},{id:3,name:"Ethanol"},
                    {id:3,name:"ExcelliumDiesel"},{id:3,name:"ExcelliumSuperPlus"},{id:3,name:"Hydrogen"},{id:3,name:"LiquidGas"},{id:3,name:"LiquidPetroleumGas"},
                    {id:3,name:"LKWDiesel"},{id:3,name:"MaxxMotionDiesel"},{id:3,name:"MaxxMotionSuper100"},{id:3,name:"Methane"},{id:3,name:"Petrol"},
                    {id:3,name:"Super(E5)"},{id:3,name:"Super(E10)"},{id:3,name:"SuperDiesel"},{id:3,name:"SuperPlus"},{id:3,name:"UltimateDiesel"},
                    {id:3,name:"UltimateSuper"},{id:3,name:"VPowerDiesel"},{id:3,name:"VPowerRacing"}]

            }else{
                $scope.type = "Battery Plug Type";

                $scope.fuelTypes=[{id:1,name:"A"},{id:2,name:"B"},{id:3,name:"C"}];



            }

        }


        $scope.locations=[{id:1,name:"CurrentLocation"},{id:2,name:"Bonn"},{id:3,name:"Cologne"}]

        $scope.changedValueLocation=function(item){
           // $scope.itemList.push(item.name);

            locationValue = item.name;
        }


        $scope.handleClick = function(){

            if(fillingStationType == 'FuelStation'){

                $location.path('/fuelStation/' + locationValue);

            }else {

            }



            //$scope.my = "I clicked";
            /*

            if(fillingStationType != null && fuelType != null && locationValue != null){


                $scope.my = " choose all" + fillingStationType + fuelType + locationValue;

                if(locationValue == "CurrentLocation"){

                    navigator.geolocation.getCurrentPosition(function(position){
                        var longitude =  position.coords.longitude;
                        var latitude = position.coords.latitude;


                        $scope.coord = longitude + latitude;

                        $http.get('http://172.16.1.102:8080/Mobivoc/fuelstation?longitude=' + position.coords.longitude  + '&latitude=' + position.coords.latitude)


                            .success(function (data, status, headers, config) {
                                // ...

                                //$rootScope.$apply(function() {
                                    $location.path("/fillingStationInfo");
                                    console.log($location.path());
                                //});

                                //$scope.restCaller = data.fillingStation[0].longtitute + data.fillingStation[0].latitute;
                                //long = data.fillingStation[0].longtitute;
                                //lat =  data.fillingStation[0].latitute;

                            })
                            .error(function (data, status, headers, config) {
                                // ...
                                $scope.restCaller = status;
                            });




                    });


                }else if(locationValue == "Bonn"){

                }else if(locationValue == "Cologne"){

                }





            }else{
                $scope.my = "Need to choose all!!!" ;
            }


    */

        }


        /*
       // $scope.restCaller = 212313;

        var long, lat;


        navigator.geolocation.getCurrentPosition(function(position){
            alert('Latitude: '          + position.coords.latitude          + '\n' +
            'Longitude: '         + position.coords.longitude         + '\n' +
            'Altitude: '          + position.coords.altitude          + '\n' +
            'Accuracy: '          + position.coords.accuracy          + '\n' +
            'Altitude Accuracy: ' + position.coords.altitudeAccuracy  + '\n' +
            'Heading: '           + position.coords.heading           + '\n' +
            'Speed: '             + position.coords.speed             + '\n' +
            'Timestamp: '         + position.timestamp                + '\n');



            $http.get('http://172.16.1.102:8080/Mobivoc/fuelstation?longitude=' + position.coords.longitude  + '&latitude=' + position.coords.latitude)


                .success(function (data, status, headers, config) {
                    // ...

                    //$scope.restCaller = data.fillingStation[0].longtitute + data.fillingStation[0].latitute;
                    //long = data.fillingStation[0].longtitute;
                    //lat =  data.fillingStation[0].latitute;

                })
                .error(function (data, status, headers, config) {
                    // ...
                    $scope.restCaller = status;
                });


            var marker, map;
            $scope.$on('mapInitialized', function(evt, evtMap) {
                map = evtMap;
                marker = map.markers[0];

                var myLatLng = new google.maps.LatLng(lat, long);
                map.setCenter(myLatLng);
                marker.setPosition(new google.maps.LatLng(lat, long));

            });

            $scope.click = function(event) {
                map.setZoom(8);
                //map.setCenter(marker.getPosition());
            }




        });


        function geolocationSuccess(position) {
            alert('Latitude: '          + position.coords.latitude          + '\n' +
            'Longitude: '         + position.coords.longitude         + '\n' +
            'Altitude: '          + position.coords.altitude          + '\n' +
            'Accuracy: '          + position.coords.accuracy          + '\n' +
            'Altitude Accuracy: ' + position.coords.altitudeAccuracy  + '\n' +
            'Heading: '           + position.coords.heading           + '\n' +
            'Speed: '             + position.coords.speed             + '\n' +
            'Timestamp: '         + position.timestamp                + '\n');
        }








*/



    }

);

mobivocApp.controller('MapsCtrl',
    function(){

    }

);

mobivocApp.controller('FillingStationInfoCtrl',
    function(){

    }

);