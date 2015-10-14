/**
 * Created by umut on 6/20/15.
 */

var mobivocApp = angular.module('mobivocApp', ['fsCordova', 'ui.bootstrap', 'ngAnimate', 'ngRoute', 'ngMap']);

mobivocApp.run(function($document, $rootScope) {
    // was missing comma here \/
    $document.on('deviceready', function() {
        //alert("deviceready");
        screen.lockOrientation('landscape');
    });

    //screen.lockOrientation('landscape');

    $rootScope.ipAddress = "http://169.254.54.221:8080/Mobivoc/";
    $rootScope.global;
    $rootScope.label;
    $rootScope.dataset;
    $rootScope.longitude;
    $rootScope.latitude;

    $rootScope.batteryData;

});

mobivocApp.config(function($routeProvider, $httpProvider){

    //delete $http.defaults.headers.common['X-Requested-With'];

    //$httpProvider.defaults.useXDomain=true;
   // delete $httpProvider.defaults.headers.common['X-Requested-With'];

    //$httpProvider.defaults.timeout = 5000;

    $routeProvider
        .when('/', {
            templateUrl : 'js/views/initial.html',
            controller  : 'InitialPageCtrl',
            css: 'css/map.css'
        })
        .when('/fuelStation', {
            templateUrl : 'js/views/fuelStation.html',
            controller  : 'HomePageCtrl',
            css: 'css/map.css'
        })
        .when('/batteryStation', {
            templateUrl : 'js/views/batteryStation.html',
            controller  : 'BatteryPageCtrl',
            css: 'css/map.css'
        })
        .when('/batteryStationDetail', {
            templateUrl : 'js/views/batteryStationDetail.html',
            controller  : 'BatteryDetailCtrl',
            css: 'css/map.css'
        })
        .when('/map', {
            templateUrl : 'js/views/map.html',
            controller  : 'MapsCtrl',
            css:'css/map.css'
        })
        .when('/fillingStationInfo',{
            templateUrl : 'js/views/fillingStationInfo.html',
            controller  : 'FillingStationInfoCtrl'
        })
        .when('/result',{
            templateUrl : 'js/views/result.html',
            controller  : 'ResultCtrl',
            reloadOnSearch: false

        })
        .when('/detail',{
            templateUrl : 'js/views/detail.html',
            controller  : 'DetailCtrl'

        })
        .when('/result2',{
            templateUrl : 'js/views/result2.html',
            controller  : 'Result2Ctrl',
            reloadOnSearch: false
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

mobivocApp.controller('BatteryPageCtrl',
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
            $http.get(ipAddress+'batterystation?plugChoice=' + batteryChoice + '&accessChoice=' + accessChoice + '&identifyChoice=' +
            identifyChoice + '&chargeChoice=' + chargeChoice + '&bookingChoice=' + bookingChoice)

                .success(function (data, status, headers, config) {
                    console.log("Success");

                    $rootScope.batteryData = data;

                })
                .error(function (data, status, headers, config) {
                // ...
                    console.log("Error");
                });
        }
    }
)

mobivocApp.controller('InitialPageCtrl',
    function($scope, $location){
        console.log('Orientation is ' + screen.orientation);

        $scope.fillingStationTypes=[{id:1,name:"FuelStation"},{id:2,name:"BatteryStation"}];

        var fillingStationChoice = null;

        $scope.changedValueFillingStation=function(item){
            console.log("Choice: " + item.name);


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

)

mobivocApp.controller("BatteryDetailCtrl", 
    function($scope, $rootScope){
        var data = $rootScope.batteryData;
        var i = 0, k = 0;
        //console.log(obj.FillingStation[0].Feature.Property);

        //console.log("Lenght: " + data.length);


        while(data.FillingStationList[k]){
            i = 0;
            while (data.FillingStationList[k].FillingStation[i]) {

                //console.log(obj.FillingStation[i].Feature.Property);
                if (data.FillingStationList[k].FillingStation[i].Feature.Literal){
                    //console.log("Im back " + data[k].FillingStation[i].Feature.Property + data[k].FillingStation[i].Feature.Literal);

                    if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                        // console.log("TITLE = " + data.FillingStationList[k].FillingStation[i].Feature.Literal )
                        titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                    }
                    if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/fillingStationNumber'){
                        // console.log("TITLE = " + data.FillingStationList[k].FillingStation[i].Feature.Literal )


                        var id = data.FillingStationList[k].FillingStation[i].Feature.Literal;
                        var id_temp = id.split("^");

                        ids.push(id_temp[0]);
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
        //}

        //);

        //for (var i = 0; i < titles.length; i++) {
        //  $scope.items.push({id: i, title: titles[i]});
        //}

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

mobivocApp.controller('Result2Ctrl',
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

            $location.path("/detail");
            //$rootScope.$broadcast('label', label);
        }
        //$rootScope.$emit('label', label);


        //$rootScope.$on('result', function (event, data) {

                var data = $rootScope.dataSet;
                var i = 0, k = 0;
                //console.log(obj.FillingStation[0].Feature.Property);

                //console.log("Lenght: " + data.length);


                while(data.FillingStationList[k]){
                    i = 0;
                    while (data.FillingStationList[k].FillingStation[i]) {

                        //console.log(obj.FillingStation[i].Feature.Property);
                        if (data.FillingStationList[k].FillingStation[i].Feature.Literal){
                            //console.log("Im back " + data[k].FillingStation[i].Feature.Property + data[k].FillingStation[i].Feature.Literal);

                            if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                               // console.log("TITLE = " + data.FillingStationList[k].FillingStation[i].Feature.Literal )
                                titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FillingStationList[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/fillingStationNumber'){
                                // console.log("TITLE = " + data.FillingStationList[k].FillingStation[i].Feature.Literal )


                                var id = data.FillingStationList[k].FillingStation[i].Feature.Literal;
                                var id_temp = id.split("^");

                                ids.push(id_temp[0]);
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
            //}

        //);

        //for (var i = 0; i < titles.length; i++) {
          //  $scope.items.push({id: i, title: titles[i]});
        //}

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



mobivocApp.controller('DetailCtrl',
    function($scope, $location, $rootScope, $http, $timeout){

        //var ipAddress = "http://169.254.115.37:8080/Mobivoc/";
        //var ipAddress = "http://169.254.88.44:8080/Mobivoc/";

        var ipAddress = $rootScope.ipAddress;

        $scope.items = [];
        var label;

        console.log("IM EHERE ");

        $scope.handleClick = function(){
            $location.path("/map");
        }

        var data = $rootScope.dataSet;




            $http.get(ipAddress+'fsdetail?label=' + $rootScope.global)


                .success(function (data, status, headers, config) {

                    var obj = data[0];
                    var i = 0;
                    var k = 0;
                    //console.log(obj.FillingStation[0].Feature.Property);

                    while(data.FuelStationDetail[k]){
                        i = 0;
                        while (data.FuelStationDetail[k].FillingStation[i]) {

                            //console.log(obj.FillingStation[i].Feature.Property);

                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'Label' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasParkingFacility'){
                                console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'Parking Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasShoppingFacility'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'Shopping Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/fillingStationHeight'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'F.Station Height' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasWCFacility'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'WC Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasWashingFacility'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'Washing Facility' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasWheelChairEnabled'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                $scope.items.push({property:'WheelChair Enabled' , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/fillingStationNumber'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )



                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://eccenca.com/mobivoc/hasOffer'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )

                                var temp_property = data.FuelStationDetail[k].FillingStation[i].Feature.Resource;

                                var property = temp_property.split("/");

                                $scope.items.push({property:'F.Station Offer' , literal:property[4]});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#lat'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )


                                //$scope.items.push({property:'F.Station Latitude' , literal: data.FillingStationList[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }
                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#long'){
                                //console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )


                               // $scope.items.push({property:'F.Station Longitude' , literal: data.FillingStationList[k].FillingStation[i].Feature.Literal});


                                // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }


                            /*
                            if (data.FuelStationDetail[k].FillingStation[i].Feature.Literal){
                                //console.log("Im back " + data[k].FillingStation[i].Feature.Property + data[k].FillingStation[i].Feature.Literal);


                                var temp_property = data.FuelStationDetail[k].FillingStation[i].Feature.Property;

                                var property = temp_property.split("/");


                                $scope.items.push({property:property[4] , literal: data.FuelStationDetail[k].FillingStation[i].Feature.Literal});

                                if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2000/01/rdf-schema#label'){
                                     console.log("TITLE = " + data.FuelStationDetail[k].FillingStation[i].Feature.Literal )


                                   // titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                                }

                            }*/
                            //if (data[k].FillingStation[i].Feature.Resource)
                            //console.log(data[k].FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                            i++;
                            //console.log("I" + i);
                        }
                        //console.log("K" + k);
                        k++;
                    }

                })
                .error(function (data, status, headers, config) {
                    // ...
                    $scope.restCaller = status;
                });


        function setData() {

        }
        $timeout(setData, 1500);


    }
);

mobivocApp.controller('ResultCtrl',
    function($scope){


    }
);


mobivocApp.controller('MainController',
    function(CordovaService, $http, $scope, $location){
        CordovaService.ready.then(function(){

        });

        $scope.go = function(){
            $location.path("/");
        }



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

        //var ipAddress = "http://172.16.1.100:8080/Mobivoc/";
        //var ipAddress = "http://169.254.115.37:8080/Mobivoc/";
        var ipAddress = $rootScope.ipAddress;

        var fillingStationChoice, fuelChoice, locationChoice, parkingChoice, washingChoice, heightChoice, wcChoice, shoppingChoice, wheelChairChoice;

        $scope.fuelPropertyVisibility = true;

        $scope.itemList=[];

        //$scope.fuelTypes=[{id:1,name:"Adblue"},{id:1,name:"Adblue"}];

        $scope.fuelTypes=[];

        $scope.parkingSelected = "Yes";


        $scope.fillingStationTypes=[{id:1,name:"FuelStation"},{id:2,name:"BatteryStation"}];
        $scope.fuelTypes=[{id:1,name:"Adblue"},{id:2,name:"Autogas"},{id:3,name:"Biodiesel"},{id:3,name:"CompressedNaturalGas"},{id:3,name:"E85"},{id:3,name:"Ethanol"},
            {id:3,name:"ExcelliumDiesel"},{id:3,name:"ExcelliumSuperPlus"},{id:3,name:"Hydrogen"},{id:3,name:"LiquidGas"},{id:3,name:"LiquidPetroleumGas"},
            {id:3,name:"LKWDiesel"},{id:3,name:"MaxxMotionDiesel"},{id:3,name:"MaxxMotionSuper100"},{id:3,name:"Methane"},{id:3,name:"Petrol"},
            {id:3,name:"Super(E5)"},{id:3,name:"Super(E10)"},{id:3,name:"SuperDiesel"},{id:3,name:"SuperPlus"},{id:3,name:"UltimateDiesel"},
            {id:3,name:"UltimateSuper"},{id:3,name:"VPowerDiesel"},{id:3,name:"VPowerRacing"}]

        $scope.booleans=[{id:1, name:"Yes"},{id:2, name:"No"}];
        $scope.heights=[{id:1, name:"Trucks"},{id:2, name:"Construction Vehicle"}, ];

        $scope.type = "Fuel/Battery-Plug Type";


        $scope.changedValueFillingStation=function(item){
            //$scope.itemList.push(item.name);

            fillingStationChoice = item.name;
/*

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
*/
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
                    heightChoice = '10';
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
            //$location.path("/result");


                //$rootScope.$apply(function() {

           // $timeout(function() {
             //   $rootScope.$emit('queryParameter', queryParameter);
            //}, 1000)
                    //$rootScope.$emit('queryParameter', queryParameter);
            //$rootScope.$broadcast('queryParameter', queryParameter);
                  //  $location.path("/result");
                //});

                //$rootScope.$apply(function() {





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



                                $rootScope.dataSet = data;


                                //console.log("LENGHT: " + $rootScope.dataSet.FillingStationList[0].FillingStation[0].Feature.Property );

                                var obj = data[0];
                                var i = 0;
                                //console.log(obj.FillingStation[0].Feature.Property);
/*
                                while(obj.FillingStation[i]){

                                    //console.log(obj.FillingStation[i].Feature.Property);
                                    if(obj.FillingStation[i].Feature.Literal)
                                        console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Literal);
                                    if(obj.FillingStation[i].Feature.Resource)
                                        console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                                    i++;
                                }
*/

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


                            var obj = data;
                            var i = 0;
                            //console.log(obj.FillingStationList[0].FillingStation[0].Feature.Property);



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
                            //console.log(obj.FillingStation[0].Feature.Property);
/*
                            while(obj.FillingStation[i]) {

                                //console.log(obj.FillingStation[i].Feature.Property);
                                if (obj.FillingStation[i].Feature.Literal)
                                // console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Literal);
                                    if (obj.FillingStation[i].Feature.Resource)
                                    // console.log(obj.FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                                        i++;
                            }
*/
                            $timeout(function() {
                                $rootScope.$emit('result', data);
                            }, 500);

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



            //}


            //else{
                //please enter the location screen
            //}



        }






    }

);




mobivocApp.controller('MapsCtrl',
    function($http, $scope, $rootScope, $timeout, $document ){

        //var ipAddress = "http://172.16.1.100:8080/Mobivoc/";
        //var ipAddress = "http://169.254.115.37:8080/Mobivoc/";

        var ipAddress = $rootScope.ipAddress;

        var label;
        var longitude, latitude;

        console.log("LABEL IS in maps:asda "  + $rootScope.global);

        $scope.googleMapsUrl="https://maps.googleapis.com/maps/api/js?v=3.20&client=AIzaSyBM2S89PD2clh6wwZ4IE59zJrLYVCFblQU";
        //$scope.googleMapsUrl="http://maps.googleapis.com/maps/api/js?key=AIzaSyDyn2qkSGbW3qwtJRDAxBmqh3YsOyX9iTY";

        navigator.geolocation.getCurrentPosition(function(position){
                $scope.long_current =  position.coords.longitude;
                $scope.lat_current = position.coords.latitude;
        });

        $http.get(ipAddress+'fsdetail?label=' + $rootScope.global)


            .success(function (data, status, headers, config) {

                var obj = data[0];
                var i = 0;
                var k = 0;
                //console.log(obj.FillingStation[0].Feature.Property);

                while(data.FuelStationDetail[k]){
                    i = 0;
                    while (data.FuelStationDetail[k].FillingStation[i]) {

                        //console.log(obj.FillingStation[i].Feature.Property);
                        if (data.FuelStationDetail[k].FillingStation[i].Feature.Literal){
                            //console.log("Im back " + data[k].FillingStation[i].Feature.Property + data[k].FillingStation[i].Feature.Literal);

                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#long'){
                                // console.log("TITLE = " + data.FillingStationList[k].FillingStation[i].Feature.Literal )

                                longitude = data.FuelStationDetail[k].FillingStation[i].Feature.Literal;
                                console.log("LONGITUTE: " +longitude );

                                var long = longitude.split("E");

                                $scope.long_target = long[0];

                               // $scope.lat = 40.76;
                               // $scope.long = -74.18;

                                //titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                            }

                            if(data.FuelStationDetail[k].FillingStation[i].Feature.Property == 'http://www.w3.org/2003/01/geo/wgs84_pos#lat'){
                                // console.log("TITLE = " + data.FillingStationList[k].FillingStation[i].Feature.Literal )

                                latitude = data.FuelStationDetail[k].FillingStation[i].Feature.Literal;
                                console.log("LATITUDE: " +latitude );
                                //titles.push(data.FillingStationList[k].FillingStation[i].Feature.Literal);
                                var lat = latitude.split("E");

                                $scope.lat_target = lat[0] * 10;

                                //$scope.lat = latitude * 10;
                            }

                        }

                        //if (data[k].FillingStation[i].Feature.Resource)
                        //console.log(data[k].FillingStation[i].Feature.Property + obj.FillingStation[i].Feature.Resource);
                        i++;
                       // console.log("I" + i);
                    }
                    //console.log("K" + k);
                    k++;
                }

            })
            .error(function (data, status, headers, config) {
                // ...
                console.log("Error" + data);
            });


/*

        var cities = [
            {
                city : 'Toronto',
                desc : 'This is the best city in the world!',
                lat : 43.7000,
                long : -79.4000
            },
            {
                city : 'New York',
                desc : 'This city is aiiiiite!',
                lat : 40.6700,
                long : -73.9400
            },
            {
                city : 'Chicago',
                desc : 'This is the second best city in the world!',
                lat : 41.8819,
                long : -87.6278
            },
            {
                city : 'Los Angeles',
                desc : 'This city is live!',
                lat : 34.0500,
                long : -118.2500
            },
            {
                city : 'Las Vegas',
                desc : 'Sin City...\'nuff said!',
                lat : 36.0800,
                long : -115.1522
            }
        ];




        var mapOptions = {
            zoom: 4,
            center: new google.maps.LatLng(40.0000, -98.0000),
            mapTypeId: google.maps.MapTypeId.TERRAIN
        }

        $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);
        google.maps.event.trigger($scope.map,'resize');

        $scope.markers = [];

        var infoWindow = new google.maps.InfoWindow();

        var createMarker = function (info){

            var marker = new google.maps.Marker({
                map: $scope.map,
                position: new google.maps.LatLng(info.lat, info.long),
                title: info.city
            });
            marker.content = '<div class="infoWindowContent">' + info.desc + '</div>';

            google.maps.event.addListener(marker, 'click', function(){
                infoWindow.setContent('<h2>' + marker.title + '</h2>' + marker.content);
                infoWindow.open($scope.map, marker);
            });
            google.maps.event.trigger($scope.map,'resize');
            $scope.markers.push(marker);

        }

        for (var i = 0; i < cities.length; i++){
            createMarker(cities[i]);
        }

        $scope.openInfoWindow = function(e, selectedMarker){
            e.preventDefault();
            google.maps.event.trigger(selectedMarker, 'click');
        }

*/


        $scope.handleClick = function(){
            $scope.showWay = true;
            $scope.lat_dir_current = $scope.lat_current;
            $scope.long_dir_current = $scope.long_current

            $scope.lat_dir_target = $scope.lat_target ;
            $scope.long_dir_target = $scope.long_target;

        }


        function setData() {
            //$scope.longitude = longitude;
            //$scope.latitude = latitude;
            //$scope.lat = 40.76;
            //$scope.long = -74.18;




        }
        $timeout(setData, 1500);

    }

);

mobivocApp.controller('FillingStationInfoCtrl',
    function(){

    }

);