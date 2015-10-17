/*global angular */
(function () {
  'use strict';



/**
*******************************
****** Graph manipulation *****
*******************************
*/
angular.module('angularApp.controllers').controller('visualizeCtrl', ['$scope','$http', function($scope,$http) {
$scope.status = 0;//to hide divs


$http.get('/options').success(function (data) {
                $scope.ontName = data.ontologyName; //only get latest uploaded file
          });

  $http.get("http://localhost:4000/ontology/nodes").success(function(response) {
    $scope.allNodes = response;
    document.getElementById('nodeJson').innerHTML= JSON.stringify($scope.allNodes) ;
  });

  $http.get("http://localhost:4000/ontology/edges").success(function(response) {
    $scope.allEdges = response;
    document.getElementById('edgeJson').innerHTML= JSON.stringify($scope.allEdges) ;
  });
  /**
   * To fill nodes/edges array from json file
   */
$scope.visualizeme = function (){
  var nodesDiv = document.getElementById('nodeJson').innerHTML;
  var nodesObj= JSON.parse(nodesDiv);//convert to object
  $scope.nodes = new vis.DataSet(nodesObj);

  var edgesDiv = document.getElementById('edgeJson').innerHTML;
  var edgesObj= JSON.parse(edgesDiv);//convert to object
  $scope.edges = new vis.DataSet(edgesObj);

  //console.log(nodesObj);
  $scope.startVis();

  $scope.countClass = 0;
  $scope.individualCTR = 0;
  $scope.propertyCTR = 0;
  $scope.objpropertyCTR = 0;
  $scope.datapropertyCTR = 0;
  $scope.resourceCTR = 0;
  $scope.blankCTR = 0;
  $scope.literalCTR = 0;
  // alert(newCheckboxName);
  var edgesDiv = document.getElementById('edgeJson').innerHTML;
  var edgesObj= JSON.parse(edgesDiv);

  var class_visited =[];
  var individual_visited= [];
  var property_visited  =[];
  var objproperty_visited  = [];
  var dataproperty_visited  = [];
  var resource_visited  = [];
  var blank_visited  = [];
  var literal_visited  = [];

  edgesObj.forEach(function( data ) {
     data.filter.forEach(function(filterdata) {
        // if(filterdata==newCheckboxName){

           if(filterdata=="class"){
            // defualtcolor = classcolor;
            if(class_visited.indexOf(data.from) == -1)
            {
              $scope.countClass = $scope.countClass + 1;
              class_visited.push(data.from);
            }

           }

           if(filterdata=="individual"){
             //defualtcolor = individualcolor;
             if(individual_visited.indexOf(data.from) == -1)
             {
             $scope.individualCTR = $scope.individualCTR +1;
             individual_visited.push(data.from);
            }
           }

           if(filterdata=="property"){
            // defualtcolor = propertycolor;
            if(property_visited.indexOf(data.from) == -1)
            {
             $scope.propertyCTR = $scope.propertyCTR + 1;
             property_visited.push(data.from);
            }
           }
           if(filterdata=="object-property"){
            // defualtcolor = objPropcolor;
            if(objproperty_visited.indexOf(data.from) == -1)
            {
              $scope.objpropertyCTR = $scope.objpropertyCTR + 1;
              objproperty_visited.push(data.from);
            }
           }
           if(filterdata=="data-property"){
            // defualtcolor = dataPropcolor;
            if(dataproperty_visited.indexOf(data.from) == -1)
            {
             $scope.datapropertyCTR = $scope.datapropertyCTR + 1;
             dataproperty_visited.push(data.from);
            }
           }
           if(filterdata=="resource"){
             //defualtcolor = resourcecolor;
             if(resource_visited.indexOf(data.from) == -1)
             {
               $scope.resourceCTR = $scope.resourceCTR + 1;
              resource_visited.push(data.from);
            }
           }
           if(filterdata=="blanknode"){
             //defualtcolor = blankcolor;
             if(blank_visited.indexOf(data.from) == -1)
             {
             $scope.blankCTR = $scope.blankCTR + 1;
             blank_visited.push(data.from);
            }
           }

           if(filterdata=="literal"){
            // defualtcolor = literalcolor;
            if(literal_visited.indexOf(data.from) == -1)
            {
             $scope.literalCTR = $scope.literalCTR + 1;
             literal_visited.push(data.from);
            }
           }

           //$scope.nodes.update([{id:data.to, color:{background:defualtcolor}}]);
         //}
       });

    });

    //alert(class_visited);
}


  //$scope.nodeIds = [2, 3, 4, 5];
  $scope.shadowState = false;
  $scope.nodes;
  $scope.edgesArray;
  $scope.edges;
  $scope.network;
  $scope.nodesArray;
  $scope.data;
  $scope.showlable;


  // create an array with nodes
  //old//
 // $scope.nodesArray = [
 //   {"id":"Alice","label":"Alice"},
 //   {"id":"Bob","label":"Bob"},
 //   {"id":"Mary","label":"Mary"},
 //   {"id":"Female","label":"Female"},
 //   {"id":"hasChild","label":"hasChild"},
 //   {"id":"_:7","label":"_:7"},
 //   {"id":"_:17","label":"_:17"},
 //   {"id":"Person","label":"Person"},
 //   {"id":"Male","label":"Male"}
 //   ];


  // create an array with edges
  $scope.edgesArray =  [];


  $scope.nodes = new vis.DataSet($scope.nodesArray);
  $scope.edges = new vis.DataSet($scope.edgesArray);
  /**
   * calling vis lib & build graph
   */
  $scope.startVis = function() {
          // create a network
          var container = document.getElementById('visualization');
          $scope.data = {
              nodes: $scope.nodes,
              edges: $scope.edges
          };
        //  var options = {};
          var options = {
            interaction:{
              dragNodes:true,
              dragView: true,
              hideEdgesOnDrag: false,
              hideNodesOnDrag: false,
              hover: false,
              hoverConnectedEdges: true,
              keyboard: {
                enabled: false,
                speed: {x: 10, y: 10, zoom: 0.02},
                bindToWindow: true
              },
              multiselect: false,
              navigationButtons: false,
              selectable: true,
              selectConnectedEdges: true,
              tooltipDelay: 300,
              zoomView: true
            },


            manipulation: {
              enabled: true,
              initiallyActive: false,
              addNode: function (data, callback) {
                // filling in the popup DOM elements\
                document.getElementById('network-popUp').style.display = 'inline';
                document.getElementById('operation').innerHTML = "Add Node";
                document.getElementById('node-label').value = "";

              },
              editNode: function (data, callback) {
                // filling in the popup DOM elements
                document.getElementById('network-popUp').style.display = 'inline';
                document.getElementById('operation').innerHTML = "Edit Node";
                document.getElementById('node-label').value = data.label;
                document.getElementById('hidden-id').innerHTML = data.id;
                //document.getElementById('cancelButton').onclick = function() {cancelEdit()};//;
              },

              addEdge: function (data, callback) {
                if (data.from == data.to) {
                  var r = confirm("Do you want to connect the node to itself?");
                  if (r == true) {
                    callback(data);
                  }
                }
                else {
                  //alert(data.from);
                  $scope.edges.add([{from: data.from, to: data.to, arrows:'to'}]);
                  callback(data);
                }
              }
            }

          };

          $scope.network = new vis.Network(container, $scope.data, options);
      }

///
 $scope.cancelEdit = function () {
  var oprtType = document.getElementById('operation').innerHTML;
  document.getElementById('network-popUp').style.display = 'none';
  if(oprtType == "Edit Node"){
    $scope.startVis();
  }
 }

$scope.saveEdit = function (oprtType){
  var oprtType = document.getElementById('operation').innerHTML;
  document.getElementById('network-popUp').style.display = 'none';
  if(oprtType == "Add Node"){
    var newLabel = document.getElementById('node-label').value;
    var isExistId = $scope.nodes.get(newLabel);
    if(!isExistId){
      $scope.nodes.add([{id: newLabel, label: newLabel}]);
    }
    else{
      alert("This node is already exist!\n Please try again.");
    }

  }
  if(oprtType == "Edit Node"){
    var thisId = document.getElementById('hidden-id').innerHTML;
    var newLabel = document.getElementById('node-label').value;
    $scope.nodes.update([{id: thisId, label: newLabel}]);
  }
  $scope.startVis();
};

//change last node's color
$scope.changeNodeColor = function() {
   var ids_arr = $scope.nodes.getIds();
   var ids_len = ids_arr.length;
   var random_id = Math.floor((Math.random() * (ids_len-1)) + 0);
   //console.log(ids_len);
   var newColor = '#' + Math.floor((Math.random() * 255 * 255 * 255)).toString(16);
   var maxId = $scope.nodes.max("id");
   $scope.nodes.update([{id:ids_arr[random_id], color:{background:newColor}}]);
  $scope.startVis();
}

//to be used
$scope.showPopUp = function() {
  document.getElementById('network-popUp').style.display = 'inline';
};
//add node button (moved to inside the graph)
$scope.addNode = function() {
  alert(oprtType);
}



//////////////////////////
/////   HIGHLIGHTS  /////
/////////////////////////


$scope.highlights_vis = function (checkName){
  var classcolor ="#B168E3";
  var individualcolor = "#E37468";
  var propertycolor = "#9AE368";
  var resourcecolor = "#328066";
  var blankcolor = "#80324C";
  var literalcolor = "#FF0055";
  var objPropcolor = "#369C2D";
  var dataPropcolor = "#9C5B2D";
  var checkboxName = document.getElementById(checkName);

var defualtcolor = '#86b3fb';

var isPredicate = false;
var isResource = false;
var isIndivi = false;

  var newCheckboxName = checkName.replace("cb-", "");
  if (checkboxName.checked) {
      // alert(newCheckboxName);
      var edgesDiv = document.getElementById('edgeJson').innerHTML;
      var edgesObj= JSON.parse(edgesDiv);

      edgesObj.forEach(function( data ) {

         data.filter.forEach(function(filterdata) {
             if(filterdata==newCheckboxName){

               if(filterdata=="class"){
                 defualtcolor = classcolor;
                   //$scope.countClass = $scope.countClass + 1;
               }

               if(filterdata=="individual"){
                 defualtcolor = individualcolor;
                 var isIndivi = true;
$scope.nodes.update([{id:data.from, shape: 'circle'}]);

                // $scope.individualCTR = $scope.individualCTR +1;
               }

               if(filterdata=="property"){
                 defualtcolor = propertycolor;
                // $scope.propertyCTR = $scope.propertyCTR + 1;
                isPredicate = true;
               }
               if(filterdata=="object-property"){
                 defualtcolor = objPropcolor;
                 isPredicate = true;

                //  $scope.objpropertyCTR = $scope.objpropertyCTR + 1;


               }
               if(filterdata=="data-property"){
                 defualtcolor = dataPropcolor;
                 isPredicate = true;
                // $scope.datapropertyCTR = $scope.datapropertyCTR + 1;

               }
               if(filterdata=="resource"){
                 defualtcolor = resourcecolor;
                 isResource = true;
                 var thislablel = data.label;
                // alert(thislablel);
                 $scope.nodes.update([{id:data.from, shape: 'diamond'}]);
                 //$scope.resourceCTR = $scope.resourceCTR + 1;
               }
               if(filterdata=="blanknode"){
                 defualtcolor = blankcolor;
                 //$scope.blankCTR = $scope.blankCTR + 1;
               }

               if(filterdata=="literal"){
                 defualtcolor = literalcolor;
                 //$scope.literalCTR = $scope.literalCTR + 1;
               }
               //
               if(!isPredicate && !isResource && !isIndivi){
                  $scope.nodes.update([{id:data.from, color:{background:defualtcolor}}]);
              }


              if(isPredicate){
                 $scope.edges.edit([{color:{background:defualtcolor}}]);
               }
               if(isResource){
                // $scope.nodes.update([{id:data.from, shape: 'diamond'}]);

               }
             }
           });

        });

   } else {
       //alert("You didn't check it! Let me check it for you.");
       // alert(newCheckboxName);
       var edgesDiv = document.getElementById('edgeJson').innerHTML;
       var edgesObj= JSON.parse(edgesDiv);
       defualtcolor = '#86b3fb';
       edgesObj.forEach(function( data ) {

          data.filter.forEach(function(filterdata) {
              if(filterdata==newCheckboxName){
                  $scope.nodes.update([{id:data.from, color:{background:defualtcolor}}]);
                  if(filterdata=="resource"){
                    $scope.nodes.update([{id:data.from, shape: 'ellipse'}]);
                  }
                  if(filterdata=="individual"){
                    $scope.nodes.update([{id:data.from, shape: 'ellipse'}]);

                  }
              }
            });

         });

   }
   //alert($scope.countClass);
}


//////////////////////////
/////   METRICS  /////
/////////////////////////


$scope.metrics_vis = function (){
  //var checkboxName = document.getElementById(checkName);

  //var newCheckboxName = checkName.replace("cb-", "");
  //if (checkboxName.checked) {
      // alert(newCheckboxName);
      var edgesDiv1 = document.getElementById('edgeJson').innerHTML;
      var edgesObj1= JSON.parse(edgesDiv1);
      edgesObj1.forEach(function( data ) {

         data.filter.forEach(function(filterdata) {
             //if(filterdata==newCheckboxName){

               if(filterdata=="class"){
                // defualtcolor = classcolor;
               }

               if(filterdata=="individual"){
                // defualtcolor = individualcolor;
               }

               if(filterdata=="property"){
                 //defualtcolor = propertycolor;
               }
               if(filterdata=="object-property"){
                 //defualtcolor = objPropcolor;
               }
               if(filterdata=="data-property"){
                 //defualtcolor = dataPropcolor;
               }
               if(filterdata=="resource"){
                 //defualtcolor = resourcecolor;
               }
               if(filterdata=="blanknode"){
                 //defualtcolor = blankcolor;
               }

               if(filterdata=="literal"){
                 //defualtcolor = literalcolor;
               }

             //}
           });

        });

}



//$scope.highlights($scope.edgesArray);
//$scope.highlights = function($scope.edgesArray) {
  var classCTR = 0;
  var individualCTR = 0;
  var propertyCTR = 0;
  var resourceCTR = 0;
  var blankCTR = 0;
  var literalCTR = 0;


  $scope.edgesArray.forEach(function( data ) {

    if(data.filter=="class"){
      classCTR = classCTR + 1;
    }

    if(data.filter=="individual"){
      individualCTR = individualCTR + 1;

    }

    if(data.filter=="property"){
      propertyCTR = propertyCTR + 1;
    }

    if(data.filter=="resource"){
      resourceCTR = resourceCTR + 1;
    }
    if(data.filter=="blanknode"){
      blankCTR = blankCTR + 1;
    }

    if(data.filter=="literal"){
      literalCTR = literalCTR + 1;
    }

   });
   console.log(classCTR);




   $scope.startVis();



  }]);

  }());
