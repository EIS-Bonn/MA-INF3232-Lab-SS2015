/**
 * Created by umut on 6/20/15.
 */

var mobivocApp = angular.module('mobivocApp', ['fsCordova', 'ui.bootstrap', 'ngAnimate', 'ngRoute', 'ngMap']);

mobivocApp.config(function($routeProvider, $httpProvider){

    //$httpProvider.defaults.useXDomain=true;
    //delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $httpProvider.defaults.timeout = 5000;

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
        .when('/result',{
            templateUrl : 'js/views/result.html',
            controller  : 'ResultCtrl'
        })
        .when('/detail',{
            templateUrl : 'js/views/detail.html',
            controller  : 'DetailCtrl'
        })
        .when('/result2',{
            templateUrl : 'js/views/result2.html',
            controller  : 'Result2Ctrl'
        })
        .when('/fuelStation/:param1',{
            templateUrl : 'js/views/fuelStation.html',
            controller  : 'FuelStationCtrl'
        })



});

mobivocApp.directive('helloWorld', function($compile, $timeout) {
    return {
        restrict: 'EA',
        scope: {
            text: '@'
        },
        //template: '<h3 ng-click="click()">Click me</h3> Clicked {{clicked}} times {{text}}',
        //template: '<div ng-click="click()"><h3>Click me</h3> Clicked {{clicked}} times {{text}}</div>',
        //template: '<div><h3>Click me</h3> Clicked {{clicked}} times {{text}}  ' +
        template: '<div><h3>Click me</h3> ' +
        '<table>' +
        '<hr>' +
        '<hr>' +
        '<tr ng-click="go(item)" ng-repeat="item in items">' +
        '<td>{{item.id}}<hr></td>' +
        '<td>{{item.title}}<hr></td>' +
        '<td>More Info<hr></td>' +
        '</tr>' +
        '</table>' +
        '</div>',
        controller: function($scope, $element, $location, $rootScope, $http ){

            var fillingStationChoice, fuelChoice, locationChoice, parkingChoice, washingChoice, heightChoice, wcChoice, shoppingChoice, wheelChairChoice;
            var ipAddress = "http://172.16.1.102:8080/Mobivoc/";

            $scope.clicked = 0;

            $scope.items=[];

            console.log("Im here1");


            function setData() {

                $scope.items = [
                    { id: 1, title: 'F. Station A' },
                    { id: 2, title: 'F. Station B' },
                    { id: 5, title: 'F. Station C' },
                    { id: 4, title: 'F. Station D' },
                    { id: 3, title: 'F. Station E' }
                ];

               
            }
            $timeout(setData, 500);

            var myJSONObject = {"bindings": [
                {"ircEvent": "PRIVMSG", "method": "newURI", "regex": "^http://.*"},
                {"ircEvent": "PRIVMSG", "method": "deleteURI", "regex": "^delete.*"},
                {"ircEvent": "PRIVMSG", "method": "randomURI", "regex": "^random.*"}
            ]
            };

            var size = myJSONObject.bindings.length;

            $scope.go = function(index){
                $scope.my = index.id;
                console.log("hi" + index.id);
                $location.path("/detail");
            }


            $scope.click = function(){
            $scope.clicked++;
            var el = $compile("<div hello-world>text='n'</div>")($scope);
            $element.parent().append(el);
            }
        }
    };
});

mobivocApp.controller('Result2Ctrl',
    function($scope, $location, $rootScope, $timeout) {

        var titles =[];
        $scope.items = [];

        $scope.go = function(index){
            $scope.my = index.id;
            console.log("hi" + index.id);
            $location.path("/detail");
        }

        $rootScope.$on('result', function (event, data) {

                var obj = data[0];
                var i = 0, k = 0;
                //console.log(obj.FillingStation[0].Feature.Property);

                console.log("Lenght: " + data.length);


                while(data[k]){
                    i = 0;
                    while (data[k].FillingStation[i]) {

                        //console.log(obj.FillingStation[i].Feature.Property);
                        if (data[k].FillingStation[i].Feature.Literal){
                            //console.log("Im back " + data[k].FillingStation[i].Feature.Property + data[k].FillingStation[i].Feature.Literal);

                            if(data[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                                console.log("TITLE = " + data[k].FillingStation[i].Feature.Literal )
                                titles.push(data[k].FillingStation[i].Feature.Literal);
                            }

                        }

                        //if (data[k].FillingStation[i].Feature.Resource)
                            //console.log(data[k].FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                        i++;
                        console.log("I" + i);
                    }
                    console.log("K" + k);
                    k++;
                }
            }

        );

        //for (var i = 0; i < titles.length; i++) {
          //  $scope.items.push({id: i, title: titles[i]});
        //}

        function setData() {
            for (var m = 0; m < titles.length; m++) {
              var titleName = titles[m];
              $scope.items.push({id: m, title: titleName});
            }
        }
        $timeout(setData, 1500);



    }
);



mobivocApp.controller('DetailCtrl',
    function($scope, $location){

        $scope.go = function(){
            $location.path("/map");
        }

    }
);

mobivocApp.controller('ResultCtrl',
    function($scope){


    }
);


mobivocApp.controller('MainController',
    function(CordovaService, $http){
        CordovaService.ready.then(function(){

        });

        $http.get('http://169.254.57.216:8080/Mobivoc/testallfs')


            .success(function (data, status, headers, config) {
                // ...

                //$rootScope.$apply(function() {
                //$location.path("/result");
                //console.log($location.path());
                //});

                //$scope.restCaller = data.fillingStation[0].longtitute + data.fillingStation[0].latitute;
                //long = data.fillingStation[0].longtitute;
                //lat =  data.fillingStation[0].latitute;

            })
            .error(function (data, status, headers, config) {
                // ...
                //$scope.restCaller = status;
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
                        //$location.path("/result");
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
            $location.path("/result");
            console.log($location.path());
        }
    }
);

mobivocApp.controller('HomePageCtrl',
    function($scope, $http, $rootScope, $location, $timeout){

        var ipAddress = "http://172.16.1.102:8080/Mobivoc/";


        var fillingStationChoice, fuelChoice, locationChoice, parkingChoice, washingChoice, heightChoice, wcChoice, shoppingChoice, wheelChairChoice;

        $scope.fuelPropertyVisibility = true;

        $scope.itemList=[];

        //$scope.fuelTypes=[{id:1,name:"Adblue"},{id:1,name:"Adblue"}];

        $scope.fuelTypes=[];

        $scope.parkingSelected = "Yes";


        $scope.fillingStationTypes=[{id:1,name:"FuelStation"},{id:2,name:"BatteryStation"}];
        $scope.booleans=[{id:1, name:"Yes"},{id:2, name:"No"}];
        $scope.heights=[{id:1, name:"Trucks"},{id:2, name:"Construction Vehicle"}, ];

        $scope.type = "Fuel/Battery-Plug Type";


        $scope.changedValueFillingStation=function(item){
            //$scope.itemList.push(item.name);

            fillingStationChoice = item.name;


            if(fillingStationChoice == 'FuelStation'){

                $scope.type = "Fuel Type";

                $scope.fuelTypes=[{id:1,name:"Adblue"},{id:2,name:"Autogas"},{id:3,name:"Biodiesel"},{id:3,name:"CompressedNaturalGas"},{id:3,name:"E85"},{id:3,name:"Ethanol"},
                    {id:3,name:"ExcelliumDiesel"},{id:3,name:"ExcelliumSuperPlus"},{id:3,name:"Hydrogen"},{id:3,name:"LiquidGas"},{id:3,name:"LiquidPetroleumGas"},
                    {id:3,name:"LKWDiesel"},{id:3,name:"MaxxMotionDiesel"},{id:3,name:"MaxxMotionSuper100"},{id:3,name:"Methane"},{id:3,name:"Petrol"},
                    {id:3,name:"Super(E5)"},{id:3,name:"Super(E10)"},{id:3,name:"SuperDiesel"},{id:3,name:"SuperPlus"},{id:3,name:"UltimateDiesel"},
                    {id:3,name:"UltimateSuper"},{id:3,name:"VPowerDiesel"},{id:3,name:"VPowerRacing"}]


                //$scope.fuelPropertyVisibility = false;
                //$scope.fuelProperty = "Fuel Property";


            }else{
                $scope.type = "Battery Plug Type";

                $scope.fuelTypes=[{id:1,name:"A"},{id:2,name:"B"},{id:3,name:"C"}];


                //$scope.fuelPropertyVisibility = true;

            }

        }

        $scope.changedValueFuelType = function(item){
            fuelChoice = item.name;
        }

        $scope.changedParkingFacility = function(item){
            $scope.my = item.name;
            parkingChoice = item.name;

            console.log("Parking is: " + parkingChoice)

        }
        $scope.changedWashingFacility = function(item){
            $scope.coord = item.name;
            washingChoice = item.name;
        }
        $scope.changedStationHeight = function(item){
            heightChoice= item.name;
        }
        $scope.changedWC = function(item){
            wcChoice= item.name;
        }

        $scope.changedShopping = function(item){
            shoppingChoice= item.name;
        }


        $scope.locations=[{id:1,name:"CurrentLocation"},{id:2,name:"Bonn"},{id:3,name:"Cologne"}]

        $scope.changedValueLocation=function(item){
           // $scope.itemList.push(item.name);

            locationChoice = item.name;
        }

        $scope.changedWheelChair=function(item){
            // $scope.itemList.push(item.name);

            wheelChairChoice = item.name;
        }


        $scope.handleClick = function(){


           // if(locationChoice != null){


                //Default Choice of the application

                if(fillingStationChoice == null)
                    fillingStationChoice = 'FuelStation';
                if(fuelChoice == null)
                    fuelChoice = 'Petrol';
                if(parkingChoice == null)
                    parkingChoice = 'Yes';
                if(washingChoice == null)
                    washingChoice = 'Yes';
                if(heightChoice == null)
                    heightChoice = 'Car';
                if(wcChoice == null)
                    wcChoice = 'Yes';
                if(shoppingChoice == null)
                    shoppingChoice = 'Yes';
                if(wheelChairChoice == null)
                    wheelChairChoice = 'No';
                if(locationChoice == null)
                    locationChoice = 'CurrentLocation';



                var queryParameter = fillingStationChoice + ", " + fuelChoice + ", " + parkingChoice + ", " + washingChoice + ", " +
                    heightChoice + ", " + wcChoice + ", " + shoppingChoice + ", " + wheelChairChoice + ", " + locationChoice;
            $location.path("/result");


                //$rootScope.$apply(function() {

           // $timeout(function() {
             //   $rootScope.$emit('queryParameter', queryParameter);
            //}, 1000)
                    //$rootScope.$emit('queryParameter', queryParameter);
            //$rootScope.$broadcast('queryParameter', queryParameter);
                  //  $location.path("/result");
                //});

                //$rootScope.$apply(function() {


/*


                if(locationChoice == 'CurrentLocation'){

                    navigator.geolocation.getCurrentPosition(function(position){
                        var longitude =  position.coords.longitude;
                        var latitude = position.coords.latitude;


                        $scope.coord = longitude + latitude;

                        //$http.get('http://172.16.1.102:8080/Mobivoc/fuelstation?longitude=' + position.coords.longitude  + '&latitude=' + position.coords.latitude)
                        $http.get(ipAddress+'fuelstation?longitude=' +
                            position.coords.longitude  + '&latitude=' + position.coords.latitude + '&fillingStationChoice=' +
                            fillingStationChoice + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                            wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                            heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                            .success(function (data, status, headers, config) {
                                // ...

                                //$rootScope.$apply(function() {
                                //$location.path("/result");
                                //console.log(data.FillingStation);






                                var obj = data[0];
                                var i = 0;
                                console.log(obj.FillingStation[0].Feature.Property);

                                while(obj.FillingStation[i]){

                                    //console.log(obj.FillingStation[i].Feature.Property);
                                    if(obj.FillingStation[i].Feature.Literal)
                                        console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Literal);
                                    if(obj.FillingStation[i].Feature.Resource)
                                        console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                                    i++;
                                }


                                $timeout(function() {
                                    $rootScope.$emit('result', data);
                                }, 500)

                                //$rootScope.$emit('result', data);


                                $location.path("/result2");

                                //var obj = JSON.parse(data.FillingStation);
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

                }else if(locationChoice == "Bonn"){
                   // $location.path("/result");
                   // console.log($location.path());
                    var longitude = '7.0982068';
                    var latitude =  '50.73743';

                    $http.get(ipAddress + 'fuelstation?longitude=' +
                    longitude  + '&latitude=' + latitude + '&fillingStationChoice=' +
                    fillingStationChoice + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                    wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                    heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                        .success(function (data, status, headers, config) {
                            // ...

                            //$rootScope.$apply(function() {
                            //$location.path("/result");
                            //console.log(data.FillingStation);






                            var obj = data[0];
                            var i = 0;
                            console.log(obj.FillingStation[0].Feature.Property);

                            while(obj.FillingStation[i]){

                                //console.log(obj.FillingStation[i].Feature.Property);
                                if(obj.FillingStation[i].Feature.Literal)
                                   // console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Literal);
                                if(obj.FillingStation[i].Feature.Resource)
                                    //console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                                i++;
                            }

                            $timeout(function() {
                                $rootScope.$emit('result', data);
                            }, 500)
                            $location.path("/result2");

                            //var obj = JSON.parse(data.FillingStation);
                            //});

                            //$scope.restCaller = data.fillingStation[0].longtitute + data.fillingStation[0].latitute;
                            //long = data.fillingStation[0].longtitute;
                            //lat =  data.fillingStation[0].latitute;

                        })
                        .error(function (data, status, headers, config) {
                            // ...
                            $scope.restCaller = status;
                        });



                }else if(locationChoice == "Cologne"){
                    var longitude = '6.9602786';
                    var latitude =  '50.937531';

                    console.log("Im here");

                    $http.get(ipAddress + 'fuelstation?longitude=' +
                    longitude  + '&latitude=' + latitude + '&fillingStationChoice=' +
                    fillingStationChoice + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                    wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                    heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                        .success(function (data, status, headers, config) {
                            // ...

                            //$rootScope.$apply(function() {
                            //$location.path("/result");
                            //console.log(data.FillingStation);





                            var obj = data[0];
                            var i = 0;
                            console.log(obj.FillingStation[0].Feature.Property);

                            while(obj.FillingStation[i]){

                                //console.log(obj.FillingStation[i].Feature.Property);
                                if(obj.FillingStation[i].Feature.Literal)
                                   // console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Literal);
                                if(obj.FillingStation[i].Feature.Resource)
                                   // console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                                i++;


                            $timeout(function() {
                                $rootScope.$emit('result', data);
                            }, 500)

                            $location.path("/result2");

                            //var obj = JSON.parse(data.FillingStation);
                            //});

                            //$scope.restCaller = data.fillingStation[0].longtitute + data.fillingStation[0].latitute;
                            //long = data.fillingStation[0].longtitute;
                            //lat =  data.fillingStation[0].latitute;

                        })
                        .error(function (data, status, headers, config) {
                            // ...
                            $scope.restCaller = status;
                        });
                }

*/

            //}


            //else{
                //please enter the location screen
            //}



        }






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