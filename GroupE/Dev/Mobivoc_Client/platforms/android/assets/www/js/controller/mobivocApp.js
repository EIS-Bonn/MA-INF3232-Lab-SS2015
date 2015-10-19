/**
 * Created by umut on 6/20/15.
 */

var mobivocApp = angular.module('mobivocApp', ['fsCordova', 'ui.bootstrap', 'ngAnimate', 'ngRoute', 'ngMap']);

mobivocApp.run(function($document, $rootScope) {
    $document.on('deviceready', function() {
        //screen.lockOrientation('landscape');
    });
    $rootScope.ipAddress = "http://169.254.80.245:8080/Mobivoc/";
    $rootScope.global;
    $rootScope.label;
    $rootScope.dataset;
    $rootScope.longitude;
    $rootScope.latitude;
    $rootScope.batteryData;
});

mobivocApp.config(function($routeProvider){

    $routeProvider
        .when('/', {
            templateUrl : 'js/views/initial.html',
            controller  : 'InitialPageCtrl',
            css: 'css/map.css'
        })
        .when('/fuelStation', {
            templateUrl : 'js/views/fuelStation.html',
            controller  : 'FStationSearchPageCtrl',
            css: 'css/map.css'
        })
        .when('/fuelStationList',{
            templateUrl : 'js/views/fuelStationList.html',
            controller  : 'FStationListPageCtrl'
        })
        .when('/fuelStationDetail',{
            templateUrl : 'js/views/fuelStationDetail.html',
            controller  : 'FStationDetailCtrl'
        })
        .when('/batteryStation', {
            templateUrl : 'js/views/batteryStation.html',
            controller  : 'BStationSearchPageCtrl',
            css: 'css/map.css'
        })
        .when('/batteryStationList', {
            templateUrl : 'js/views/batteryStationList.html',
            controller  : 'BStationListPageCtrl',
            css: 'css/map.css'
        })
        .when('/batteryStationDetail', {
            templateUrl : 'js/views/batteryStationDetail.html',
            controller  : 'BStationDetailCtrl',
            css: 'css/map.css'
        })
        .when('/map', {
            templateUrl : 'js/views/map.html',
            controller  : 'MapsCtrl',
            css:'css/map.css'
        })
});

mobivocApp.controller('MainController',
    function(CordovaService, $http, $scope, $location){
        CordovaService.ready.then(function(){

        });

        $scope.go = function(){
            $location.path("/");
        }



    }
);

mobivocApp.controller('InitialPageCtrl',
    function($scope, $location){

        var fillingStationChoice = null;

        $scope.fillingStationTypes=[{id:1,name:"FuelStation"},{id:2,name:"BatteryStation"}];

        $scope.changedValueFillingStation=function(item){
            if(item.name == "FuelStation"){
                fillingStationChoice = "FuelStation";
            }
            if(item.name == "BatteryStation"){
                fillingStationChoice = "BatteryStation";

            }
        }
        $scope.handleClick = function(){
            if(fillingStationChoice == null){
                $scope.error = "Please choose one of the Filling Station type";
            }else if(fillingStationChoice == "FuelStation"){
                $location.path("/fuelStation");
            }
            else if (fillingStationChoice == "BatteryStation"){
                $location.path("/batteryStation");
            }
        }
    }
);

mobivocApp.controller('FStationSearchPageCtrl',
    function($scope, $http, $rootScope, $location){

        var ipAddress = $rootScope.ipAddress;
        var fuelChoice = null, locationChoice = null, parkingChoice = null, washingChoice = null, heightChoice = null, wcChoice = null,
            shoppingChoice = null, wheelChairChoice = "E";

        $scope.itemList=[];
        $scope.locations=[{id:1,name:"CurrentLocation"},{id:2,name:"Bonn"},{id:3,name:"Cologne"},{id:4,name:"Leipzig"},{id:5,name:"Frankfurt"},{id:6,name:"Berlin"},{id:7,name:"Munich"},{id:8,name:"Hamburg"}];
        $scope.fillingStationTypes=[{id:1,name:"FuelStation"},{id:2,name:"BatteryStation"}];
        $scope.fuelTypes=[{id:1,name:"Adblue"},{id:2,name:"Autogas"},{id:3,name:"Biodiesel"},{id:3,name:"CompressedNaturalGas"},{id:3,name:"E85"},{id:3,name:"Ethanol"},
            {id:3,name:"ExcelliumDiesel"},{id:3,name:"ExcelliumSuperPlus"},{id:3,name:"Hydrogen"},{id:3,name:"LiquidGas"},{id:3,name:"LiquidPetroleumGas"},
            {id:3,name:"LKWDiesel"},{id:3,name:"MaxxMotionDiesel"},{id:3,name:"MaxxMotionSuper100"},{id:3,name:"Methane"},{id:3,name:"Petrol"},
            {id:3,name:"Super(E5)"},{id:3,name:"Super(E10)"},{id:3,name:"SuperDiesel"},{id:3,name:"SuperPlus"},{id:3,name:"UltimateDiesel"},
            {id:3,name:"UltimateSuper"},{id:3,name:"VPowerDiesel"},{id:3,name:"VPowerRacing"}];
        $scope.booleans=[{id:1, name:"Yes"},{id:2, name:"No"}];
        $scope.heights=[{id:1, name:"Trucks"},{id:2, name:"Construction Vehicle"}, ];

        $scope.changedValueFuelType = function(item){
            fuelChoice = item.name;
        }
        $scope.changedParkingFacility = function(item){
            parkingChoice = item.name;
        }
        $scope.changedWashingFacility = function(item){
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
        $scope.changedValueLocation=function(item){
            locationChoice = item.name;
        }
        $scope.changedWheelChair=function(item){
            wheelChairChoice = item.name;
        }

        $scope.handleClick = function(){

            if(locationChoice == null)
                locationChoice = 'CurrentLocation';
            if(wheelChairChoice == null)
                wheelChairChoice = 'E';
            if(shoppingChoice == null)
                shoppingChoice = 'E';
            if(wcChoice == null)
                wcChoice = 'E';
            if(washingChoice == null)
                washingChoice = 'E';
            if(parkingChoice == null)
                parkingChoice = 'E';
            if(fuelChoice == null)
                fuelChoice = 'E';
            if(heightChoice == null)
                heightChoice = 'E';

            if(locationChoice == 'CurrentLocation'){

                navigator.geolocation.getCurrentPosition(function(position){
                    var longitude =  position.coords.longitude;
                    var latitude = position.coords.latitude;

                    $http.get(ipAddress+'fuelstation?longitude=' +
                        position.coords.longitude  + '&latitude=' + position.coords.latitude + '&fuelChoice=' + fuelChoice +
                        '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                        wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                        heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                        .success(function (data, status, headers, config) {
                            $rootScope.dataSet = data;
                            $location.path("/fuelStationList");
                        })
                        .error(function (data, status, headers, config) {
                            console.log("HTTP Error: " + data);
                        });
                });

            }else if(locationChoice == "Bonn"){
                var longitude = '7.0982068';
                var latitude =  '50.73743';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                    longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                    wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                    heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });

            }else if(locationChoice == "Cologne"){
                var longitude = '6.9602786';
                var latitude =  '50.937531';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                    longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                    wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                    heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });
            }else if(locationChoice == "Frankfurt"){
                var longitude = '8.68213';
                var latitude =  '50.11092';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });
            }
            else if(locationChoice == "Berlin"){
                var longitude = '13.40495';
                var latitude =  '52.52001';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });
            }
            else if(locationChoice == "Leipzig"){
                var longitude = '12.37307';
                var latitude =  '51.33970';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });
            }
            else if(locationChoice == "Munich"){
                var longitude = '11.58198';
                var latitude =  '48.13513';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });
            }
            else if(locationChoice == "Hamburg"){
                var longitude = '9.99368';
                var latitude =  '53.55108';

                $http.get(ipAddress + 'fuelstation?longitude=' +
                longitude  + '&latitude=' + latitude + '&fuelChoice=' + fuelChoice + '&parkingChoice=' + parkingChoice + '&wcChoice=' +
                wcChoice + '&shoppingChoice=' + shoppingChoice + '&washingChoice=' + washingChoice + '&heightChoice=' +
                heightChoice + '&wheelChairChoice=' + wheelChairChoice)

                    .success(function (data, status, headers, config) {
                        $rootScope.dataSet = data;
                        $location.path("/fuelStationList");
                    })
                    .error(function (data, status, headers, config) {
                        console.log("HTTP Error: " + data);
                    });
            }
        }
    }
);

mobivocApp.controller('FStationListPageCtrl',
    function($scope, $location, $rootScope, $timeout) {
        var titles =[];
        var ids = [];
        $scope.items = [];

        $scope.go = function(index){
            $scope.my = index.id;
            console.log("hi" + index.id);
            var label;
            label = index.id;

            $rootScope.global = label;
            $location.path("/fuelStationDetail");

        }
        var data = $rootScope.dataSet;
        var i = 0, k = 0;

        while(data.FillingStationList[k]){
            i = 0;
            while (data.FillingStationList[k].FillingStation[i]) {
                if (data.FillingStationList[k].FillingStation[i].Feature.Literal){
                    if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                        titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                    }
                    if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/fillingStationNumber'){
                        var id = data.FillingStationList[k].FillingStation[i].Feature.Literal;
                        var id_temp = id.split("^");
                        ids.push(id_temp[0]);
                    }
                }
                i++;
            }
            k++;
        }
        function setData() {
            for (var m = 0; m < titles.length; m++) {
                var titleName = titles[m];
                var id = ids[m];
                $scope.items.push({id: id, title: titleName});
            }
        }
        $timeout(setData, 1500);
    }
);

mobivocApp.controller('FStationDetailCtrl',
    function($scope, $location, $rootScope, $http){
        var ipAddress = $rootScope.ipAddress;
        $scope.items = [];
        var label;
        $scope.handleClick = function(){
            $location.path("/map");
        }

        $http.get(ipAddress+'fsdetail?label=' + $rootScope.global)
            .success(function (data, status, headers, config) {

                var i = 0;
                var k = 0;
                var longitude, latitude;

                while(data.FuelStationDetail[k]){
                    i = 0;
                    while (data.FuelStationDetail[k].FillingStation[i]) {
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                            $scope.items.push({property:'Label' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasParkingFacility'){
                            $scope.items.push({property:'Parking Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasShoppingFacility'){
                            $scope.items.push({property:'Shopping Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/fillingStationHeight'){
                            $scope.items.push({property:'F.Station Height' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasWCFacility'){
                            $scope.items.push({property:'WC Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasWashingFacility'){
                            $scope.items.push({property:'Washing Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasWheelChairEnabled'){
                            $scope.items.push({property:'WheelChair Enabled' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasOffer'){
                            var temp_property = data.FuelStationDetail[k].FillingStation[i].Feature.Resource;
                            var property = temp_property.split("/");
                            $scope.items.push({property:'F.Station Offer' , literal:property[4]});
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#lat'){
                            latitude = data.FuelStationDetail[k].FillingStation[i].Feature.Literal;
                            var lat = latitude.split("^");
                            $rootScope.latitude = lat[0];
                        }
                        if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#long'){
                            longitude = data.FuelStationDetail[k].FillingStation[i].Feature.Literal;
                            var long = longitude.split("^");
                            $rootScope.longitude = long[0];
                        }
                        i++;
                    }
                    k++;
                }
            })
            .error(function (data, status, headers, config) {
                console.log("Error: " + data);
            });
    }
);

mobivocApp.controller('MapsCtrl',
    function($scope, $rootScope){

        $scope.googleMapsUrl="https://maps.googleapis.com/maps/api/js?v=3.20&client=AIzaSyBM2S89PD2clh6wwZ4IE59zJrLYVCFblQU";

        navigator.geolocation.getCurrentPosition(function(position){
            $scope.long_current =  position.coords.longitude;
            $scope.lat_current = position.coords.latitude;
        });
        $scope.long_target = $rootScope.longitude;
        $scope.lat_target = $rootScope.latitude;

        $scope.handleClick = function(){
            //$scope.showWay = true;
            $scope.lat_dir_current = $scope.lat_current;
            $scope.long_dir_current = $scope.long_current

            $scope.lat_dir_target = $scope.lat_target ;
            $scope.long_dir_target = $scope.long_target;
        }
    }
);

mobivocApp.controller('BStationSearchPageCtrl',
    function($scope, $location, $http, $rootScope){

        var ipAddress, locationChoice, batteryChoice, accessChoice, identifyChoice, chargeChoice, bookingChoice;
        ipAddress = $rootScope.ipAddress;

        $scope.locations=[{id:1,name:"Bonn"}]

        $scope.batteryTypes=[{id:1,name:"ComboPlug"},{id:2,name:"CHAdeMOPlug"},{id:3,name:"EUDomesticPlug"},{id:3,name:"SchukoPlug"},{id:4,name:"Type1Plug"},{id:5,name:"Type2Plug"},
            {id:6,name:"Type3Plug"}];

        $scope.accessTypes=[{id:1,name:"BrandsCustomers"},{id:2,name:"CustomerOfPremise"},{id:3,name:"EmployeesOnly"},{id:4,name:"IdentifiedPerson"},{id:5,name:"ComboPlug"},
            {id:6,name:"InhabitantsOnly"},{id:7,name:"OpenToAllUsers"}];

        $scope.identifyTypes=[{id:1,name:"AccessCode"},{id:2,name:"BadgeHasToBeOrdered"},{id:3,name:"BadgeIsAvailable"},{id:4,name:"CreditCard"},
            {id:5,name:"Key"},{id:6,name:"MobileApplication"},{id:7,name:"PhoneCall"},{id:8,name:"TextMessage"},{id:9,name:"None"},
            {id:10,name:"Other"},];

        $scope.chargeTypes=[{id:1,name:"FreeCharging"},{id:2,name:"PaidCharging"}];

        $scope.bookingTypes=[{id:1,name:"Impossible"},{id:2,name:"Mandatory"}, {id:3,name:"Optional"}];

        $scope.changedValueBatteryType = function(item){
            batteryChoice = item.name;
        }

        $scope.changedValueLocation=function(item){
            // $scope.itemList.push(item.name);

            locationChoice = item.name;
        }
        $scope.changedValueAccessType = function(item){
            accessChoice = item.name;
        }
        $scope.changedValueIdentifyType = function(item){
            identifyChoice = item.name;
        }
        $scope.changedValueChargeType = function(item){
            chargeChoice = item.name;
        }
        $scope.changedValueBookingType = function(item){
            bookingChoice = item.name;
        }

        $scope.handleClick = function(){
            if(locationChoice == null)
                locationChoice = 'Bonn';
            if(batteryChoice == null)
                batteryChoice = 'E';
            if(accessChoice == null)
                accessChoice = 'E';
            if(identifyChoice == null)
                identifyChoice = 'E';
            if(chargeChoice == null)
                chargeChoice = 'E';
            if(bookingChoice == null)
                bookingChoice = 'E';

            $http.get(ipAddress+'batterystation?plugChoice=' + batteryChoice + '&accessChoice=' + accessChoice + '&identifyChoice=' +
            identifyChoice + '&chargeChoice=' + chargeChoice + '&bookingChoice=' + bookingChoice)

                .success(function (data, status, headers, config) {
                    console.log("Success");

                    $rootScope.batteryData = data;
                    $location.path("/batteryStationList");

                })
                .error(function (data, status, headers, config) {
                // ...
                    console.log("Error");
                });
        }
    }
);

mobivocApp.controller("BStationListPageCtrl",
    function($scope, $rootScope, $timeout, $location){

        var titles =[];
        var ids = [];
        var i = 0, k = 0;
        var counter = 0;

        $scope.items = [];

        $scope.go = function(index){
            $scope.my = index.id;
            console.log("hi" + index.id);
            var label;
            label = index.title;

            $rootScope.global = label;

            $location.path("/batteryStationDetail");
        }

        var data = $rootScope.batteryData;


        while(data.FillingStationList[k]){
            i = 0;
            while (data.FillingStationList[k].FillingStation[i]) {

                if (data.FillingStationList[k].FillingStation[i].Feature.Literal){

                    if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/ChargingPointName'){
                        titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                        ids.push(counter++);
                    }
                }
                i++;
            }
            k++;
        }

        function setData() {
            for (var m = 0; m < titles.length; m++) {
                var titleName = titles[m];
                var id = ids[m];
                $scope.items.push({id: id, title: titleName});
            }
        }
        $timeout(setData, 1500);
    }
);

mobivocApp.controller("BStationDetailCtrl",
    function($scope, $rootScope, $timeout, $location, $http){

        var ipAddress = $rootScope.ipAddress;
        $scope.items = [];
        var label;

        $scope.handleClick = function(){
            $location.path("/map");
        }

        $http.get(ipAddress+'bsdetail?label=' + $rootScope.global)

            .success(function (data, status, headers, config) {
                var i = 0;
                var k = 0;
                var temp_property, property, longitude, latitude;

                while(data.BatteryStationDetail[k]){
                    i = 0;
                    while (data.BatteryStationDetail[k].FillingStation[i]) {

                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/ChargingPointName'){
                            $scope.items.push({property:'Label' , literal: data.BatteryStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/openingHours'){
                            $scope.items.push({property:'Opening Hours' , literal: data.BatteryStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/HasParkingFacility'){
                            $scope.items.push({property:'Parking Facility' , literal: data.BatteryStationDetail[k].FillingStation[i].Feature.Literal});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/AccessType'){
                            temp_property = data.BatteryStationDetail[k].FillingStation[i].Feature.Resource;
                            property = temp_property.split("/");
                            $scope.items.push({property:'Access Type' , literal: property[4]});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/IdentificationSystem'){
                            temp_property = data.BatteryStationDetail[k].FillingStation[i].Feature.Resource;
                            property = temp_property.split("/");
                            $scope.items.push({property:'ID System ' , literal: property[4]});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/ChargingPointFees'){
                            temp_property = data.BatteryStationDetail[k].FillingStation[i].Feature.Resource;
                            property = temp_property.split("/");
                            $scope.items.push({property:'Charging Fee Type' , literal: property[4]});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/BookingType'){
                            temp_property = data.BatteryStationDetail[k].FillingStation[i].Feature.Resource;
                            property = temp_property.split("/");
                            $scope.items.push({property:'Booking Type' , literal: property[4]});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/PlugType'){
                            temp_property = data.BatteryStationDetail[k].FillingStation[i].Feature.Resource;
                            property = temp_property.split("/");
                            $scope.items.push({property:'Plug Type' , literal: property[4]});
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#lat'){
                            latitude = data.BatteryStationDetail[k].FillingStation[i].Feature.Literal;
                            var lat = latitude.split("^");
                            $rootScope.latitude = lat[0];
                        }
                        if(data.BatteryStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#long'){
                            longitude = data.BatteryStationDetail[k].FillingStation[i].Feature.Literal;
                            var long = longitude.split("^");
                            $rootScope.longitude = long[0];
                        }
                        i++;
                    }
                    k++;
                }
            })
            .error(function (data, status, headers, config) {
                console.log("Error: " + status);
            });
        function setData() {

        }
        $timeout(setData, 1500);
    }
);

mobivocApp.controller('NavBarCtrl',
    function($scope){
        $scope.isCollapsed = true;
    }
);