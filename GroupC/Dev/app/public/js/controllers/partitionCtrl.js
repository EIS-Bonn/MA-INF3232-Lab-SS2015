/*global angular */
(function () {
  'use strict';

  angular.module('angularApp.controllers')
    .controller('partitionCtrl', ['$scope','$http', '$timeout',
	      function ($scope,$http, $timeout) {


          // $scope.content = 'file content for example';
          // var blob = new Blob([ $scope.content ], { type : 'text/plain' });
          // $scope.url = window.URL.createObjectURL(blob);//(window.URL || window.webkitURL).createObjectURL( blob );


          $scope.status = 0;//to hide divs
          $scope.partitioning_nodes = [];


          $http.get('/options').success(function (data) {
                          $scope.ontName = data.ontologyName; //only get latest uploaded file
                    });

        $http.get("/options").success(function(data) {
          $scope.nameSpace = data.ontologyNamespace;
          document.getElementById('nameSpace').innerHTML= $scope.nameSpace ;
        });

          /**
           * Tabs funcs
           */
            $scope.graphs = [];


            $scope.selectedRange = 0;
            $scope.counter = $scope.selectedRange;
            $scope.addTab = function(){
                $scope.counter++;
                $scope.graphs.push({id:$scope.counter,content:""});
                $scope.selectedGraph = 0;//$scope.graphs.length - 1;
            }
            $scope.deleteTab = function(index){
                //$scope.graphs.splice(index,1);
                $scope.counter--;
                $scope.graphs.pop();
            }
            $scope.selectedGraph = 0;
            $scope.selectTab = function(index){
                $scope.selectedGraph = index;
            }
            $scope.changeTab = function(){

              console.log("changin tab");
              var requestNo = $scope.selectedRange;
              var tabNo = $scope.graphs.length;
              var diffNo = 0;
              if(tabNo>requestNo){
                diffNo = tabNo - requestNo;
                 for(var i=0;i<diffNo;i++) {
                   //var tabId = tabNo - i - 1;
                   $scope.deleteTab(i);
                 }
              }
              if(requestNo>tabNo){
                diffNo = requestNo - tabNo;
                for(var i=0;i<diffNo;i++) {
                  $scope.addTab();
                }
              }


            }//end $scope.changeTab




          $http.get("http://localhost:4000/ontology/nodes").success(function(response) {
            $scope.allNodes = response;
            document.getElementById('nodeJson').innerHTML= JSON.stringify($scope.allNodes) ;
          });

        //TEST
        // var nodes_array = [
        //  {"id":"Alice","label":"Alice"},
        //   {"id":"Bob","label":"Bob"},
        //   {"id":"Mary","label":"Mary"},
        //    {"id":"Female","label":"Female"},
        // // //  {"id":"hasChild","label":"hasChild"},
        // // //  {"id":"_:7","label":"_:7"},
        // // //  {"id":"_:17","label":"_:17"},
        //    {"id":"Class","label":"Class"},
        // {"id":"Person","label":"Person"},
        //    {"id":"Male","label":"Male"}
        //    ];
        //    document.getElementById('nodeJson').innerHTML= JSON.stringify(nodes_array);



          $http.get("http://localhost:4000/ontology/edges").success(function(response) {
            $scope.allEdges = response;
            document.getElementById('edgeJson').innerHTML= JSON.stringify($scope.allEdges) ;
          });


          $scope.graph_partitioning = function (){ //if buildTabs=0 create tabs otherwise just call clusters numbers


          $scope.loading = true;

          $timeout(function() {                      
                    console.log('timeout fired');
                        $scope.loading = false;   
                          }, 3000);   
    

            var nodesDiv = document.getElementById('nodeJson').innerHTML;
            var nodesObj= JSON.parse(nodesDiv);//convert to object
            var edgesDiv = document.getElementById('edgeJson').innerHTML;
            var edgesObj= JSON.parse(edgesDiv);//convert to object

            var weight_matrix = [];// to assign weights to the relationships(edges)
            var graph_linkedList = [];// linked list graph
            var nrwd_matrix = [];// The neighborhood random walk distance matrix nrwd_matrix
            var un_normalized_weight = [];
            var nrwd_classes = [];
            var nrwd_pairs = [];
            var nrwd_pairs_val = [];




           var rel_weights = [
             { relationship: "equivalentClass", weight: 20},
             { relationship: "subClassOf", weight: 10},
             { relationship: "subPropertyOf", weight: 10},
             { relationship: "domain", weight: 5},
             { relationship: "range", weight: 5},
             { relationship: "comment", weight: 0.2},
             { relationship: "seeAlso", weight: 0.2},
             { relationship: "isDefinedBy", weight: 0.2},
             { relationship: "label", weight: 0.2},
             { relationship: "equivalentProperty", weight: 20},
             { relationship: "type", weight: 10},
             { relationship: "unionOf", weight: 10},
             { relationship: "intersectionOf", weight: 10},
             { relationship: "disjointWith", weight: 0}, // 0-10
             { relationship: "complementOf", weight: 10},
             { relationship: "inverseOf", weight: 20},
             { relationship: "FunctionalProperty", weight: 5},
             { relationship: "InverseFunctionalProperty", weight: 5},
             { relationship: "SymmetricProperty", weight: 3},
             { relationship: "TransitiveProperty", weight: 2}
             // Other relations: 1
           ];

           ////////////
           //get all concepts
           ////////////
           $scope.classesNodes = [];
           edgesObj.forEach(function( data ) {
              data.filter.forEach(function(filterdata) {
                 // if(filterdata==newCheckboxName){
                    if(filterdata=="class"){
                      var class_ele = [data.from];
                     if( $scope.classesNodes.indexOf(class_ele[0]) == -1)
                     {
                        $scope.classesNodes.push(class_ele);
                        //console.log(class_ele);
                     }
                    }

                });

             });

             //heeeeeere

            console.log($scope.classesNodes.length);

           ////////////
           //loop edges
           ////////////
           edgesObj.forEach(function( data ) {
             //from edges
              var dataFrom = data.to;//vice versa
              var dataTo = data.from;//vice versa
              var data_label = data.label;

              var set_weight = 1;// weight of other relations

              //remove prefix of the relationship
              var data_label = data_label.replace("rdf:", "");
              var data_label = data_label.replace("rdfs:", "");
              var data_label = data_label.replace("owl:", "");

              for( var i=0; i<rel_weights.length; i++){
                if(data_label == rel_weights[i].relationship){
                  set_weight = rel_weights[i].weight;
                  break;//break the loop
                }
              }
              //construct weight matrix
             weight_matrix.push({ node_From: dataFrom, node_To: dataTo, norm_weight: set_weight, r_weight: set_weight } );//,
             un_normalized_weight.push({ node_From: dataFrom, node_To: dataTo, norm_weight: set_weight } );

            });

           // console.log(weight_matrix[0].node_From + " " + weight_matrix[0].node_To + " " + weight_matrix[0].norm_weight);

            //////////////
            // 1: normalize weights
            // 2: build grapgh linked list
            // 3: get non class node
            ////////////
            $scope.noneClassesNodes = [];
            nodesObj.forEach(function( data ) {
              var sum_outgoing_weights = 0 ;
              var normalized_weight;
              var vertex_rel = data.id;
              // linked list graph
              var parent = [vertex_rel];
              var childs = [];

              if( $scope.classesNodes.indexOf(data.id) == -1){
                $scope.noneClassesNodes.push([data.id]);
              }


              //add up all outgoing weights for current vertex
              for( var i=0; i<weight_matrix.length; i++){
                if(vertex_rel == weight_matrix[i].node_From){
                 sum_outgoing_weights = sum_outgoing_weights + weight_matrix[i].norm_weight;
                 childs.push(weight_matrix[i].node_To);
                }
              }
              parent.push(childs);
            //console.log(vertex_rel + " " + sum_outgoing_weights);

              //Replace weight with the normalized weight
              for( var i=0; i<weight_matrix.length; i++){
                if(vertex_rel == weight_matrix[i].node_From){
                  normalized_weight = weight_matrix[i].norm_weight/sum_outgoing_weights;
                  weight_matrix[i].norm_weight = normalized_weight;
                }
              }

              graph_linkedList.push(parent);// linked list graph

             });
           //console.log(weight_matrix[9].node_From + " " + weight_matrix[9].node_To + " " + weight_matrix[9].norm_weight);

          //  console.log($scope.classesNodes);
          //  console.log($scope.noneClassesNodes);


           //////////////
           //////////////
           // build The neighborhood random walk distance matrix nrwd_matrix
           ////////////
           /////////////
           var c = 0.2; //Restart probability where (0-1)


           for( var i=0; i<nodesObj.length; i++){
             for(var j=0; j<nodesObj.length; j++){

               if(nodesObj[i].id != nodesObj[j].id){
                 var path_i_j = [];
                 path_i_j = get_path( nodesObj[i].id, nodesObj[j].id, graph_linkedList);
                 var sum_nrwd_distance = 0;
                 var nrwd_distance = 0;
                 if(path_i_j.length>0){
                   for( var step=0; step<(path_i_j.length-1); step++){


                     for( var w=0; w<weight_matrix.length; w++){
                       if(path_i_j[step] == weight_matrix[w].node_From && path_i_j[step+1] == weight_matrix[w].node_To ){
                         //nrwd_distance = weight_matrix[w].norm_weight * c * Math.pow((1-c), (path_i_j.length-1)); // add up NRWD Distance
                         nrwd_distance = Math.pow(weight_matrix[w].norm_weight, step) * c * Math.pow((1-c), step);
                         //console.log("weight_matrix[w].norm_weight * c * Math.pow((1-c), (path_i_j.length-1))")
                         //console.log(nrwd_distance + "=" + weight_matrix[w].norm_weight +"*"+ c + "*"+ " Math.pow((1-"+c+"), ("+(path_i_j.length-1)+"))");
                         sum_nrwd_distance = sum_nrwd_distance + nrwd_distance;
                       }
                     }// end for of weight
                     //console.log("sum_nrwd_distance:"+sum_nrwd_distance)
                   }

                 }
                 nrwd_matrix.push({node_From: nodesObj[i].id, node_To:nodesObj[j].id, trans_Prob: sum_nrwd_distance});
                 var concat_pair = nodesObj[i].id + "<>" +nodesObj[j].id; //concatenate nodes
                 nrwd_pairs.push(concat_pair);
                 nrwd_pairs_val.push(sum_nrwd_distance);
                 // build another matrix for only classes
                 if($scope.classesNodes.indexOf(nodesObj[i].id)>-1 && $scope.classesNodes.indexOf(nodesObj[j].id)>-1 ){
                   nrwd_classes.push({node_From: nodesObj[i].id, node_To:nodesObj[j].id, trans_Prob: sum_nrwd_distance});
                 }

               } // end if

             } // end for j

               //console.log(nodesObj[i].id);
           }//end for i

           //console.log(nrwd_matrix);




             //console.log(class_visited.length);


             function get_path(source, target, graph_linkedList){
                 var queue_path = [source];//add source as the first element in the array
                 var visited = [source];
                 var start_node = source;
                 var final_path = [source];

                 while (queue_path.length){
                   start_node = queue_path[0];
                   queue_path.pop();
                   var index_LL =  -1;
                   for ( var i=0; i<graph_linkedList.length; i++){
                     if( start_node == graph_linkedList[i][0] ){
                       index_LL = i;
                     }
                   }
                   if ( index_LL > -1){
                       var childs_no = graph_linkedList[index_LL][1].length;
                         if(childs_no > 0){
                           if(graph_linkedList[index_LL][1].indexOf(target)> -1 ){
                             //queue_path.push(target);
                             visited.push(target);
                             final_path.push(target);
                             break;
                           }
                           else{
                             var current_child = "";
                             for ( var j=0; j<childs_no; j++){
                               if (visited.indexOf(graph_linkedList[index_LL][1][j]) < 0 ){ // check if the current node is not vistied
                                 var current_child = graph_linkedList[index_LL][1][j];
                               }//end if
                             }//end for
                             if(current_child.length>0){
                               visited.push(current_child);
                               queue_path.push(current_child);
                               final_path.push(current_child);
                             }
                             else{
                               final_path.pop();
                               queue_path.push(final_path[final_path.length-1]);//return to the parent to check other childs
                             }
                           }//end else
                         }//end if
                         else{
                           final_path.pop();
                           queue_path.push(final_path[final_path.length-1]);//return to the parent to check other childs
                         }
                   }//end if
                 }//end while

                 return final_path;
             }



           //////////////
           // agglomerative agorithm
           ////////////
           $scope.agglomerative_algo = function (){

               var final_modules = [];
               var all_vertex = [];
              var no_nodes = 0;////nodesObj.length;//
               if($scope.classesNodes.length > 20 || nodesObj.length>20){
                 all_vertex = $scope.classesNodes;
                 no_nodes = $scope.classesNodes.length;

               }else{
                  nodesObj.forEach(function(data) {
                    //var modulzrize_me = [];// to make each ele ass array
                    all_vertex.push([data.label]);
                  });
                  no_nodes = nodesObj.length;//
               }//[];//


               var modules_init = [];
               var modules_updated = [];
               var new_module_found = [];
               var best_partioning = [];
               best_partioning = all_vertex.slice(0);;
               for(var n=0; n<(all_vertex.length-1); n++){
                 var x = no_nodes - n;
                 //console.log(x);
                  modules_init = [];
                  modules_init = best_partioning.slice(0);
                  if(best_partioning.length == $scope.selectedRange){//
                    final_modules = best_partioning;// fill return of the function
                    break;
                    // for(var k=0; k<best_partioning.length; k++){
                    // console.log(x+"-"+best_partioning[k]);
                    // }
                  }
                  else{
                      final_modules = best_partioning;// fill return of the function
                  }

                  // for(var k=0; k<best_partioning.length; k++){
                  //   console.log(x+"-"+best_partioning[k]);
                  // }

                  var maxS = -2;
                  for(var i=0; i< x-1; i++){
                   for(var j=i+1; j < x; j++){
                    //console.log(i+","+j);
                     var combined_module = [];
                     var first_part = [];
                     var second_part = [];
                     var new_modules = [];

                     first_part = modules_init[i];
                     second_part = modules_init[j];
                     combined_module = first_part.concat(second_part);
                     //combined_module.push(first_part);
                     //combined_module.push(second_part);
                     modules_updated = [];
                     modules_updated = modules_init.slice(0);

                    for(var p=0; p<(modules_updated.length); p++){
                      if(modules_updated[p] != modules_init[i]){
                        if( modules_updated[p] != modules_init[j] ){
                          new_modules.push(modules_updated[p]);
                        }
                      }
                    }
                    new_modules.push(combined_module);
                    var avg_scores =  func_scores(nrwd_classes,new_modules);//new_modules//avg_modules_scores
                    //console.log(avg_scores);
                    if(avg_scores > maxS){
                      //console.log(avg_scores);
                       maxS = avg_scores;
                       modules_updated = new_modules.slice(0);
                       //modules_updated.push(combined_module);
                       new_module_found = new_modules.slice(0);
                       //modules_updated.push(combined_module);
                     }// end if

                   }// end for loop

                 }//end for loop
               //  break;
                   best_partioning = new_module_found;

               }//end for

               //console.log("->"+maxS);


               for(var part=0;part<final_modules.length; part++){
                 var getPartEle = [];
                 for(var ele=0; ele<final_modules[part].length; ele++){
                   getPartEle.push({id:final_modules[part][ele],label:final_modules[part][ele]});
                 }
                 $scope.partitioning_nodes.push(getPartEle);
                 console.log(getPartEle);
               }

               return final_modules;
           }//end Function

            // for(var ha=0; ha<nrwd_matrix.length; ha++){
            //    console.log(nrwd_matrix[ha].node_From+","+nrwd_matrix[ha].node_To+":"+nrwd_matrix[ha].trans_Prob+" - "+nrwd_pairs[ha]+":"+nrwd_pairs_val[ha]);
            //  }
            //  console.log("----------------------------------");


           var pr_modules = [];
           pr_modules = $scope.agglomerative_algo();

          //  console.log($scope.partitioning_nodes.length);
          //  console.log($scope.selectedRange );


           if(pr_modules.length != $scope.selectedRange ){
             //$scope.status = $scope.status + 1;
             alert("The minimum of partitioning is: " + pr_modules.length);
           }
             $scope.visualizeme();


          function func_scores(nrwd_matrix,other_modules){
            var s_i = 0; //s(i)
            var m_score = 0;
            var avg_m_scores;
            //sum
            var sum_m_score = 0;

            //console.log(other_modules);


            for(var m=0; m < other_modules.length; m++){
              if(other_modules[m].length == 1){
                m_score = 0;
              }
              else{
                //console.log(other_modules[m]);
                var sum_si = 0;
                for(var mv=0; mv<other_modules[m].length; mv++){
                  s_i = compute_ai(other_modules[m][mv],other_modules[m],other_modules,m);
                  //console.log(other_modules[m][mv]+":"+other_modules[m]);
                  sum_si = sum_si + s_i;
                }
                m_score = sum_si / other_modules[m].length;

              }

              sum_m_score = sum_m_score + m_score;
              //console.log(other_modules[m]+":"+other_modules[m].length);
            }

            avg_m_scores = sum_m_score / other_modules.length;
            //console.log("sum_m_score:"+sum_m_score);

            return avg_m_scores;

          }//End func_scores

          function compute_ai(element,ele_module, all_modules, moduleNo){
            var avg_ele_ai = 0;
            var sum_ele_ai = 0;
            var avg_ele_bi = 0;
            var sum_ele_bi = 0;
            var highest_avg_ele_bi = 0;
            for(var ele=0; ele<ele_module.length; ele++){
              if(ele_module[ele] != element){
                //console.log(element+"<>"+ele_module[ele]);
                var indx = nrwd_pairs.indexOf(element+"<>"+ele_module[ele]);
                var indx_rev = nrwd_pairs.indexOf(ele_module[ele]+"<>"+element);
                var pair_clossness = 0; // to compute clossness between pair vice versa
                 if(nrwd_pairs_val[indx] > 0 & nrwd_pairs_val[indx_rev] > 0){
                   pair_clossness = (nrwd_pairs_val[indx] + nrwd_pairs_val[indx_rev])/2;
                 }
                 else{
                   pair_clossness = nrwd_pairs_val[indx] + nrwd_pairs_val[indx_rev];
                 }
                //pair_clossness = nrwd_pairs_val[indx];//to be del
                sum_ele_ai = sum_ele_ai + pair_clossness;
              }
            }
            ///////////////value of a(i) ////////////////
            avg_ele_ai = sum_ele_ai/(ele_module.length-1);
            /////////////////////////////////////////////

            for(var ele=0; ele<all_modules.length; ele++){
              if(ele != moduleNo){//do not check the module of the repective ele.

                if(all_modules[ele].length == 1){
                  var indx = nrwd_pairs.indexOf(element+"<>"+all_modules[ele]);
                  var indx_rev = nrwd_pairs.indexOf(all_modules[ele]+"<>"+element);
                  var pair_clossness = 0; // to compute clossness between pair vice versa
                   if(nrwd_pairs_val[indx] > 0 & nrwd_pairs_val[indx_rev] > 0){
                     pair_clossness = (nrwd_pairs_val[indx] + nrwd_pairs_val[indx_rev])/2;
                   }
                   else{
                     pair_clossness = nrwd_pairs_val[indx] + nrwd_pairs_val[indx_rev];
                   }
                  //pair_clossness = nrwd_pairs_val[indx];//to be del
                   if(pair_clossness > highest_avg_ele_bi){
                     highest_avg_ele_bi = pair_clossness; ///////////////value of b(i) ////////////////
                   }

                }
                else{//if module has more than ele
                  for(var v=0; v<all_modules[ele].length; v++){
                    var indx = nrwd_pairs.indexOf(element+"<>"+all_modules[ele][v]);
                    var indx_rev = nrwd_pairs.indexOf(all_modules[ele][v]+"<>"+element);
                    var pair_clossness = 0; // to compute clossness between pair vice versa
                     if(nrwd_pairs_val[indx] > 0 & nrwd_pairs_val[indx_rev] > 0){
                       pair_clossness = (nrwd_pairs_val[indx] + nrwd_pairs_val[indx_rev])/2;
                     }
                     else{
                       pair_clossness = nrwd_pairs_val[indx] + nrwd_pairs_val[indx_rev];
                     }
                     //pair_clossness = nrwd_pairs_val[indx];//to be del
                     sum_ele_bi = sum_ele_bi + pair_clossness;

                  }//end for
                  avg_ele_bi = sum_ele_bi / all_modules[ele].length;
                  if(avg_ele_bi > highest_avg_ele_bi){
                    highest_avg_ele_bi = avg_ele_bi;   ///////////////value of b(i) ////////////////
                  }

                }//end else

              }// end if

            }//end for to check other modules
            ///////////////compute of s(i):  a(i)-b(i) / max(a(i),b(i)) ////////////////
            var max_ai_bi = highest_avg_ele_bi;
            if(max_ai_bi < avg_ele_ai){
              max_ai_bi = avg_ele_ai;
            }

            var s_i = (avg_ele_ai - highest_avg_ele_bi) / max_ai_bi;
            //console.log(s_i);
            return s_i;
          }//end compute_ai






          }// End $scope.graph_partitioning();

          ///////////////////////////////////////////////
          //////////////to process nodes and edges then visualize the gragh
          ///////////////////////////////////////////////
          $scope.visualizeme = function (){
            var nodesDiv = document.getElementById('nodeJson').innerHTML;
            var nodesObj= JSON.parse(nodesDiv);//convert to object
            //$scope.nodes = new vis.DataSet(nodesObj);

            var edgesDiv = document.getElementById('edgeJson').innerHTML;
            var edgesObj= JSON.parse(edgesDiv);//convert to object
            $scope.edges = new vis.DataSet(edgesObj);

            if($scope.selectedRange == 1){
              $scope.startVis(nodesObj,1);
            }
            else{
              for(var i=0; i<$scope.partitioning_nodes.length; i++){
                $scope.startVis($scope.partitioning_nodes[i],i+1);
              }
            }




            //$scope.graph_partitioning()

          }

          $scope.shadowState = false;
          $scope.nodes;
          $scope.edgesArray;
          $scope.edges;
          $scope.network;
          $scope.nodesArray;
          $scope.data;
          $scope.showlable;

          // $scope.nodesArray = [
          //  {"id":"Alice","label":"Alice"},
          //   {"id":"Bob","label":"Bob"},
          //   {"id":"Mary","label":"Mary"},
          //    {"id":"Female","label":"Female"},
          // // //  {"id":"hasChild","label":"hasChild"},
          // // //  {"id":"_:7","label":"_:7"},
          // // //  {"id":"_:17","label":"_:17"},
          //    {"id":"Class","label":"Class"},
          // {"id":"Person","label":"Person"},
          //    {"id":"Male","label":"Male"}
          //    ];

          $scope.nodes = new vis.DataSet($scope.nodesArray);
          $scope.edges = new vis.DataSet($scope.edgesArray);

          /**
           * calling vis lib & build graph
           */
          $scope.startVis = function(partitionNodes, partitionId) {
                  // create a network
                  $scope.nodes = new vis.DataSet(partitionNodes);


                  $scope.content = '" family-ontology#http://www.semanticweb.org/eislab/ontologies/2015/2 rdf:type#http://www.semanticweb.org/eislab/ontologies/2015/2 Ontology#http://www.semanticweb.org/eislab/ontologies/2015/2';
                  var blob = new Blob([ $scope.content ], { type : 'text/plain' });
                  $scope.url = window.URL.createObjectURL(blob);//(window.URL || window.webkitURL).createObjectURL( blob );


                  //$scope.nodes = new vis.DataSet(partitionNodes);
                  var container = document.getElementById('vis-'+partitionId);//;document.getElementById('visualization');
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

              //  alert(nodesGet);


		}]).config(['$compileProvider',
    function ($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
}]);//function ($scope,$http)


}());



/********************************
 **********   JQuery  ***********
 ********************************/
//  function avg_modules_scores (nrwd_matrix, other_modules){
//
//    //console.log(other_modules);
//    var sum_all_scores = 0;
//    var score_module = 0
//    //var all_modules_arr = other_modules.slice(0);
//    var showme = "";
//    for(var xm=0; xm < other_modules.length; xm++){
//      showme = showme+other_modules[xm]+"|";
//    }
// //  console.log(showme);
//
//    for(var m=0; m < other_modules.length; m++){//other_modules
//
//      var sum_silhouette = 0;
//      //compute a(i)
//      //console.log("current module: "+other_modules[m].length+"@"+other_modules[m]);
//      if(other_modules[m].length>1){
//          for ( var i =0; i< other_modules[m].length; i++){//other_modules[m]
//            var silhouette_coefficient_i; // [ s(i) = a(i)-b(i) / max(a(i),b(i)) ]
//            var a_vertex = 0; // a(i): the average similarity of (i)􏰅􏰅 to all other nodes within the same module
//            var b_vertex = 0; // b(i): the highest average similarity of (i)􏰅 to nodes of other modules
//            var max_a_b = 0; // max(a(i),b(i))
//            var sil_in_module = 0; // sum of similarity for i to all nodes in the module
//            var a_vertex_other = 0;
//
//          //compute a(i)
//           for(var j=0; j<nrwd_matrix.length; j++){
//             var isZero = 0;
//
//             if(other_modules[m][i] == nrwd_matrix[j].node_From){//other_modules[m][i]
//               var to_node = nrwd_matrix[j].node_To;
//
//               if(other_modules[m].indexOf(to_node)>-1){
//                 //isZero = nrwd_matrix[j].trans_Prob;
//                 sil_in_module = sil_in_module + nrwd_matrix[j].trans_Prob;
//               }
//             }
//
//             if(other_modules[m][i] == nrwd_matrix[j].node_To){
//               var From_node = nrwd_matrix[j].node_From;
//               if(other_modules[m].indexOf(From_node)>-1){
//                 //isZero = nrwd_matrix[j].trans_Prob;
//                 sil_in_module = sil_in_module + nrwd_matrix[j].trans_Prob;
//               }
//             }
//           }//end for to compute a(i)
//
//           a_vertex = sil_in_module;
//           if(other_modules[m].length>1){
//             a_vertex = sil_in_module / (other_modules[m].length-1);// a(i) is computed
//           }
//           if(other_modules[m].length == 1){
//             a_vertex = 0;// a(i) is computed
//           }
//          //console.log("a("+other_modules[m][i]+"):"+a_vertex);
//
//           //compute b(i)
//           //console.log(all_modules_arr.length);
//           for(var y=0; y<(other_modules.length-1); y++){
//             //check a(i) in the module other_modules[y]
//             var sil_other_module = 0;
//             if(other_modules[y].length>1){
//               for(var z=0; z<other_modules[y].length; z++){
//                 for(var x=0; x<nrwd_matrix.length; x++){
//                   if(other_modules[m][i] == nrwd_matrix[x].node_From && other_modules[y][z] == nrwd_matrix[x].node_To){
//                     sil_other_module = sil_other_module + nrwd_matrix[x].trans_Prob;
//                     //console.log("other_modules[y][z]:"+other_modules[m][i]+"-"+ other_modules[y][z] + "-" + sil_other_module);
//                   }
//                   if(other_modules[m][i] == nrwd_matrix[x].node_To && other_modules[y][z] == nrwd_matrix[x].node_From){
//                     sil_other_module = sil_other_module + nrwd_matrix[x].trans_Prob;
//                     //console.log("other_modules[y][z]:"+other_modules[m][i]+"-"+ other_modules[y][z] + "-" + sil_other_module);
//                   }
//                 }//end for
//               }//end for other module
//
//             }//end if greater
//             else{
//
//             //  for(var z=0; z<other_modules[y].length; z++){
//                 for(var x=0; x<nrwd_matrix.length; x++){
//                   console.log("other_modules[m][i]"+other_modules[m][i] )
//                   if(other_modules[m][i] == nrwd_matrix[x].node_From && other_modules[y] == nrwd_matrix[x].node_To){
//                     sil_other_module = sil_other_module + nrwd_matrix[x].trans_Prob;
//                     //console.log("other_modules[y][z]:"+other_modules[m][i]+"-"+ other_modules[y][z] + "-" + sil_other_module);
//                   }
//                   if(other_modules[m][i] == nrwd_matrix[x].node_To && other_modules[y] == nrwd_matrix[x].node_From){
//                     sil_other_module = sil_other_module + nrwd_matrix[x].trans_Prob;
//                     //console.log("other_modules[y][z]:"+other_modules[m][i]+"-"+ other_modules[y][z] + "-" + sil_other_module);
//                   }
//                 }//end for
//             //  }//end for other module
//
//             }
//
//
//
//
//             a_vertex_other = sil_other_module / (other_modules[y].length);
//             if (a_vertex_other > b_vertex){
//               b_vertex = a_vertex_other;
//             }
//            //console.log("b("+other_modules[m][i]+"):" + b_vertex);
//           }//end for to compute b(i)
//
//
//           max_a_b = b_vertex;
//           if( a_vertex > b_vertex){
//             max_a_b = a_vertex;
//           }
//           if(max_a_b==0){silhouette_coefficient_i = -1}// instead of showing NaN
//           else{
//             silhouette_coefficient_i = (a_vertex - b_vertex) / max_a_b; // #
//           }
//           //console.log("s("+other_modules[m][i]+"):" + silhouette_coefficient_i);
//           sum_silhouette += silhouette_coefficient_i;
//
//
//
//
//      }//end for loop
//        //  console.log(other_modules[m].length);
//
//        score_module = sum_silhouette / other_modules[m].length;// score for module m
//  }//end greater than one
//
//    //score_module = sum_silhouette / other_modules[m].length;// score for module m
//    //console.log("score("+other_modules[m]+"):" + score_module);
//    sum_all_scores = sum_all_scores + score_module;
//    }// end for for all modules
//    var avg_all_scores = sum_all_scores / other_modules.length;; // average of scores of all modules
//    console.log("avg_all_scores:"+avg_all_scores);
//    //console.log(avg_all_scores);
//    return avg_all_scores;
//  }// end function
