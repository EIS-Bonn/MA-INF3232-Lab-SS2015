var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var logger = require('morgan');
require('buffer-concat');
var _ = require("underscore");

var busboy = require('connect-busboy'); //middleware for form/file upload
var app = express();

// Semantic web library
var rdfstore = require('rdfstore');

var http = require('http');
var request = require("request"); //the request module, for http requests
var fs = require('fs'); //Handle files
var glob = require("glob");
var filtered = 0;
/*--- application settings ---*/

/* 1. view engine setup -- optional; AngularJS is used */

/*  2. body parser setup */
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

/*  3. busboy setup  */
app.use(busboy());

/*  4. virtual directory setup */
//setup a virtual directory called /mocks which points to mocks folder
//this means that we can access the file like: localhost:4000/uploads/ontologyFile.owl
app.use('/uploads',express.static(path.join(__dirname, 'uploads')));

/*  5. logger setup */
app.use(logger('dev'));

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

var files = {
      hostname: 'localhost',
      port: app.get('port'),
      path: '/files',
      agent: false
}

//see if we have some files already, if not, set a default
var initFiles = function(cb) {

      http.get(files, function(response) {

         var pieces = [];

          response.on('data', function (piece){
            pieces.push(piece);
          });

          response.on('end', function() {

              var body = Buffer.concat(pieces);
              console.log('file body in init files', body.toString());

                _.map(body.toString())

            });
        })
}

//the parsed ontology using Java
var parsedOntology = '';

//options used in application
var options = {
      hostname: 'localhost',
      port: app.get('port'),
      ontologyNamespace: '',
      ontologyName: '',
      path: '',//'OWL/XML: family-owl-xml.owl ;  JSON-lD: person.json  TURTLE /mocks/ontologyFile.owl;  N3: blue.n3 ',
      dirPath: path.join(__dirname, 'uploads'),
      fileName: '',
      url: '',
      agent: false  // create a new agent just for this one request
};

/*--------------------------------------- ONTOLOGY ----------------------------------------------------*/

var Ontology = function() {
  this.ontologyName = "";
  this.triples = [];
  this.nodes = [];
  this.edges = [];
  this.filter = [];
  this.metrics = [];
  this.id = 0;
};



Ontology.prototype.addTriple = function(s, p, o) {
  var item = {subject: s, predicate: p, object: o};
  this.triples.push(item);
  return item;
};

Ontology.prototype.addNode = function(s, p, o) {
  var item = {subject: s, predicate: p, object: o};
  this.nodes.push(item);
  return item;
};

Ontology.prototype.addEdge = function(s, p, o, l, f, i) {
  var item = {subject: s, predicate: p, object: o, label: l, filter: f, interf: i};
  this.edges.push(item);
  return item;
};

Ontology.prototype.addFilter = function(from, to, label, arrows, filter) {
  var item = {from: from, to: to, label: label, arrows: "from", filter: filter};
  this.filter.push(item);
  return item;
};

Ontology.prototype.addMetric = function(metricName, metricValue) {
  var item = {name: metricName, value: metricValue};
  this.metrics.push(item);
  return item;
};

Ontology.prototype.resetMetric = function(){
  this.metrics = [];
}

Ontology.prototype.defaultContext = [
    {name: "rdf" , uri: "http://www.w3.org/1999/02/22-rdf-syntax-ns"},
    {name: "rdfs" , uri:  "http://www.w3.org/2000/01/rdf-schema"},
    {name: "owl" , uri:  "http://www.w3.org/2002/07/owl"},
    {name: "xsd" , uri:  "http://www.w3.org/2001/XMLSchema"},
    {name: "dcterms" , uri:  "http://purl.org/dc/terms/"},
    {name: "foaf" , uri:  "http://xmlns.com/foaf/0.1/"},
    {name: "cal" , uri:  "http://www.w3.org/2002/12/cal/ical"},
    {name: "vcard" , uri:  "http://www.w3.org/2006/vcard/ns "},
    {name: "geo" , uri:  "http://www.w3.org/2003/01/geo/wgs84_pos"},
    {name: "cc" , uri:  "http://creativecommons.org/ns"},
    {name: "sioc" , uri:  "http://rdfs.org/sioc/ns"},
    {name: "doap" , uri:  "http://usefulinc.com/ns/doap"},
    {name: "com" , uri:  "http://purl.org/commerce"},
    {name: "ps" , uri:  "http://purl.org/payswarm"},
    {name: "gr" , uri:  "http://purl.org/goodrelations/v1"},
    {name: "sig" , uri:  "http://purl.org/signature"},
    {name: "ccard" , uri:  "http://purl.org/commerce/creditcard"}];

var contextArray = _.map(Ontology.prototype.defaultContext, function (item) {
    return {
      name: item.name,
      uri: item.uri}
    });

var ont = new Ontology(); //initialize ontology

//creates edges, triples, nodes
function initOntology(){

  console.log('Loading ontology ... ');
  //start empty
  ont.ontologyName = "";
  ont.triples = [];
  ont.nodes = [];
  ont.edges = [];
  ont.filter = [];
  ont.metrics = [];
  parsedOntology = ''; //reset parsed ontology - only for URL

  function constructResponse(data) {

      var j = 0;
      _.each(data, function (item) {

          //console.log("item", item);
        var subject = extractID(item.subject.nominalValue);
        var predicate = extractID(item.predicate.nominalValue);
        var object = extractID(item.object.nominalValue);
        var interfaceName = item.object.interfaceName;
        var filter = "";
        var label = extractLabel(item.predicate.nominalValue, predicate);

        /*---------------------- ONTOLOGY NAME  --------------------*/
        if (subject!=="" && j == 0 ) { //extract this just once
          ont.ontologyName = extractOntologyName(item.subject.nominalValue);
          console.log("Extracting the ontology name", ont.ontologyName);
          options.ontologyName = ont.ontologyName;
          options.ontologyNamespace = extractOntologyNamespace(item.subject.nominalValue);
          j = 1;
        }

        /*---------------------- ONTOLOGY EDGES  --------------------*/
        ont.addEdge(subject, predicate, object, label, filter, interfaceName); //step 1 for edges

        /*---------------------- ONTOLOGY TRIPLES ------------------*/
        ont.addTriple(subject, predicate, object); //add triples

        /*---------------------- ONTOLOGY NODES ------------------*/
        ont.addNode(subject, predicate, object); //add nodes
      })

    console.log("Building ontology sync ... ");

  }
  //load the ontology either from file or from URL
  loadOntologySync(constructResponse);
};

//input: edges (subject, predicate, object, label, filter, interfaceName)
//adds filters in ontology
//adds metrics
//step 2 for edges: edges becomes filter
function initEdges(){
      //loop through each item of edges
     var triplesArray = _.map(ont.edges, function (item) {
        return { //construct the array of edges
            from: item.subject.trim(), //subject
            to: item.object.trim(), //object
            predicate: item.predicate.trim(), //predicate
            label: item.label.trim(), //label
            arrows:"from",
            filter: [],
            interf: item.interf
        };
    });
     //add filter
    var cl = 0, ind = 0 , lit = 0, bn = 0 , dp = 0 , op = 0;

    _.each(triplesArray, function (item) {
        if (item.predicate === "type")
        {
        if (item.to === 'ObjectProperty') {
          item.filter.push("object-property");
          op++;
        } else if (item.to === 'DatatypeProperty') {
          item.filter.push("data-property");
          dp++;
        } else if (item.to === 'Class') {
            if ( item.from.substring(0, 1) !== '_' ) {
              item.filter.push("class");
              cl++;
            }
        }  else if (item.to === 'NamedIndividual') {
            item.filter.push("individual");
            ind++;
            }
        }
        //LITERAL
        if (item.interf === "Literal")  {
            item.filter.push("literal");
            lit++;
        }

        //BLANK NODE
        if (item.interf === "BlankNode")  {
            item.filter.push("blanknode");
            bn++;
        }

        if (item.from.substring(0, 1) === "_" || item.from  !== "_")
            {
                item.filter.push("resource");
            }

    });

    //COUNTS, METRICS
    //if (parsedOntology !== ''){
        ont.resetMetric();
        ont.metrics=[];
        ont.addMetric("class", cl);
        ont.addMetric("individual", ind);
        ont.addMetric("blank", bn);
        ont.addMetric("literals", lit);
        ont.addMetric("dataprop", dp);
        ont.addMetric("objprop", op);
    //}

    console.log("Class COUNT", cl);
    console.log("Individual COUNT", ind);
    console.log("Blank node COUNT", bn);
    console.log("Literal COUNT", lit);
    console.log("Data property COUNT", dp);
    console.log("Object property COUNT", op);

    //reset filter
    ont.filter = []; //reset filter
    //filter
    console.log("------------------------------------------------------------------");
    console.log('filter content')
    console.log("------------------------------------------------------------------");
    
    //loop through triples and update the filter
    //this is the new edges

     //filter out duplicates
     /*
      var filteredTriples = _.filter(triplesArray, function (item, index) {
        for(index += 1; index < triplesArray.length; index += 1) {
          if (_.isEqual(item, triplesArray[index])) {
              return false;
          }
      }
        return true;
      });


    _.each(filteredTriples, function (item) {
        ont.addFilter(item.from, item.to, item.label, item.arrows, item.filter);
    })
  */
      _.each(triplesArray, function (item) {
        ont.addFilter(item.from, item.to, item.label, item.arrows, item.filter);
    })

    console.log("------------------------------------------------------------------");
    console.log('filter content after update')
    console.log("------------------------------------------------------------------");
    

}

/*----------------------CONSTRUCTOR  --------------------*/

/**
 * Gets the ID from a URL
 * @example
 * // returns {"subject":"Cairo_B2","predicate":"type","object":"Gate","id":66},{"subject":"Cairo_B2","predicate":"type","object":"NamedIndividual","id":67}
 * extractID(http://www.semanticweb.org/eng.mahmoud/ontologies/2014/11/AirportOntology#Cairo_B2)
 * @example
 * // returns  {"subject":"http://www.semanticweb.org/eng.mahmoud/ontologies/2014/11/AirportOntology","predicate":"type","object":"Ontology","id":68}
 * extractID(http://www.semanticweb.org/eng.mahmoud/ontologies/2014/11/AirportOntology)
 * @param {string} uri - The URI of the entity
 * @returns {string} id - The ID of the entity or the URI if the entity does not exist or not defined
 */

function extractOntologyName(uri){

    var ontologyName = "";
    var startIndex = uri.lastIndexOf("/");
    var endIndex = uri.lastIndexOf("#");

    if (endIndex !== -1 && startIndex !== -1)
      ontologyName = uri.substring(startIndex + 1, endIndex);
    else
    {
      if (endIndex === -1)
      ontologyName = uri.substring(startIndex + 1, uri.length);
    }

    return ontologyName;
}

function extractOntologyNamespace(uri){

    var ontologyNamespace = "";    
    var endIndex = uri.lastIndexOf("/");
    var startIndex = 0;

    if (endIndex !== -1 )
      ontologyNamespace = uri.substring(startIndex , endIndex);
    
    console.log("Extracting the ontology namespace");
    return ontologyNamespace;
}



function extractID(uri)
{
    var item = uri.split('#')[1];

    if (!item)
    {
      var endIndex = uri.lastIndexOf("/");
        if (endIndex != -1)
          var ontologyName = uri.substring(endIndex+1, uri.length);
        else
          var ontologyName = uri;
    }
    return item || ontologyName;
}

function findPrefix(uri)
{
    //find by URI value
    var findPrefix = _.findWhere(contextArray, {uri: uri});
    //lookup in contextarray and get the shortname for URI
    if (_.isEmpty(findPrefix))
      return "";
    else
      return findPrefix.name;
}

function extractLabel(uri, p)
{
    var itemContext = uri.split('#')[0];    
    var item = uri.split('#')[1];    

    if ( findPrefix(itemContext) != "" )
    {
        if (item !== undefined)
        var label = findPrefix(itemContext)+":" + p;
    }
    else
    {
        if (item !== undefined)
            var label = ont.ontologyName+":" + p;
        else
            var label = p;
    }

    return  label ;
}

/**
 * Loads an ontology by reading local file or remote URL
 * @param {string} cb - The callback (a function that will store the result)
 */
function loadOntologySync(cb)
{      
      rdfstore.create(function(err, store) {        
 
            if (options.path === '' && options.url === '')
                console.log("Upload , select or choose an URL for an ontology first!");                
            else
            {
              //are we loading a remote or local file?

              /*---------------------- load remote ontology from URI  --------------------*/  
              if (options.url !== '' && options.url !== 'URI')
              {
                console.log("Parsing ontology from URL", options.url);               

                store.load('remote' , options.url , "graph", function(err, results)
                {
                     store.execute("SELECT * WHERE { ?s ?p ?o } LIMIT 10", function(success, results){
                              console.log(success, results);
                     });       

                   
                          store.graph("graph", function(err, graph) {

                              if (err)      
                              
                                  console.log("There was an error loading the graph", err);
                                  
                              
                              else
                                {
                                    var triples = graph.toArray();
                                    console.log("Constructing triples sync... ");
                                    console.log("There are ", triples.length, "triples");
                                    console.log(triples);

                                    if (triples.length !== 0)                                      
                                            cb(triples); //return the triples in callback (cb = callback)                                
                                          
                                }
                            }); //graph
                        
                })
              }//-load ontology from URI


              /*---------------------- load local ontology  ------------------------------*/

              if (options.fileName !== '' )
              {
                  var syncPath = __dirname + options.path;      //local but not enough
                  var re = new RegExp('/', 'g');
                  syncPath = syncPath.replace(re, '\\'); //update path

                  //set full path from filesystem of the ontology
                  var ontology = fs.readFileSync(__dirname + options.path).toString();

                           store.load("text/turtle" , ontology, "graph", function(err, results) {
                              if (err)
                                console.log("There was an error loading the store", err);
                              else
                              {
                                store.graph("graph", function(err, graph) {

                                   if (err)
                                   {
                                      console.log("There was an error loading the graph", err);
                                   }
                                   else
                                   {
                                      var triples = graph.toArray();
                                      console.log("Constructing triples sync... ");
                                      console.log("There are ", triples.length, "triples");
                                      //console.log(triples);

                                      if (triples.length !== 0)
                                      {
                                        cb(triples); //return the triples in callback (cb = callback)
                                        alreadyLoaded = 1;    //flag that ontology was loaded
                                      }
                                  }

                                }); //graph
                             }
                          });

                          store.close(); //done
              } //-load ontology from local file
            }
 
      }) //-create

} //-loadOntologySync

//REST endpoint on /ontology/nodes
app.get('/ontology/nodes', function(req, res)
{

     var triplesArray = _.map(ont.nodes, function (item) {
                    return {
                             id: item.subject.trim(),
                             label: item.subject.trim()
                        };
                    });

     var objectsArray = _.map(ont.nodes, function (item) {
                        return {
                             id: item.object.trim(),
                             label: item.object.trim()
                        };
                    });

      var triplesAndObjects = _.union(triplesArray, objectsArray);

      //filter out duplicates
      var filteredTriplesAndObjects = _.filter(triplesAndObjects, function (item, index) {
        for(index += 1; index < triplesAndObjects.length; index += 1) {
          if (_.isEqual(item, triplesAndObjects[index])) {
              return false;
          }
      }
        return true;
      });

    res.json(filteredTriplesAndObjects); //REST endpoint of JSON triples and objects

});

//REST endpoint on /ontology/edges
app.get('/ontology/edges', function(req, res) {
   console.log("Getting edges");
    res.json(ont.filter);
});

//REST endpoint on /ontology/metrics with the metrics from RDFSTORE
app.get('/metrics', function(req, res) {   

   console.log("Getting metrics (RDFSTORE)");
   res.json(ont.metrics);  //constructed data of triples, nodes, edges

});


//REST endpoint on /ontology/graph with the triples loaded
app.get('/ontology/graph', function(req, res) {
    res.json(ont);  //constructed data of triples, nodes, edges
});

//REST endpoint on /ontology
//raw data as generated by library
app.get('/ontology', function(req, res) {

    function constructResponse(data) {
      res.json(data);
    }

    loadOntologySync(constructResponse);
});

/*------------------------------ UPLOAD ONTOLOGY -----------------------------------*/
app.post('/upload' , function (req, res) {
        //busboy handles multi-part file upload
        console.log('Post received to upload ... ');

        var fstream;

        req.pipe(req.busboy);

        req.busboy.on('file', function (fieldname, file, filename) {
            //only update if the filename was change or exists
            if (filename!== undefined || filename !=='') {
                  //reset url
                  options.url = '';
                  console.log("Uploading the file " + filename);
                  console.log("before", options);
                  options.path = '/uploads/'  + filename;
                  options.fileName = filename; //update file name

                  console.log("Updating options ... ", options);
                  fstream = fs.createWriteStream(__dirname + '/uploads/' + filename); //path where the file will be uploaded
                  file.pipe(fstream);
                  fstream.on('close', function () {
                      console.log("Upload finished successfully ! Uploaded " + filename);                               
                  });
            }

        });
});


/*------------------------------- FILES -----------------------------------------*/
//REST endpoint on /files
// sort files in last accessed order (by datetime)
app.get('/files', function(req, res) {

    var dir = options.dirPath;
    console.log("Getting list of ontologies from ...", dir);

    var files = fs.readdirSync(dir)
    files.sort(function(a, b) {
               return fs.statSync(dir + "//" + a).mtime.getTime() -
                      fs.statSync(dir + "//" + b).mtime.getTime();
           });

    files = files.reverse();
    console.log("These are the available ontologies", files);
    res.json(files);
});

//REST endpoint on /options
app.get('/options', function(req, res) {
        res.json(options);   //returns raw options
});

//REST endpoint on /options
app.post('/options', function(request, response) {

      console.log('Post received to change settings ... ');
      console.log("Old settings :( ", options);
        //local upload
        if (request.body.fileName) {
            options.path = '/uploads' + "/" + request.body.fileName;
            options.fileName = request.body.fileName;
            //reset url
            options.url = '';
        }

        //URL upload
        if (request.body.url) {
            options.url =  request.body.url;
            //reset all the other values
            options.path = "";
            options.fileName = "";
        }

      console.log("NEW settings :) ", options);
      //when options are changed, the ontology should be reinitialized
      console.log("Init ontology from options");
      initOntology();//initialize the ontology from options      
      initEdges();
      console.log("--------------------------------------------");
      response.send(request.body);    // echo the result back

});


/*------------------------------- PARSER -----------------------------------------*/

//REST endpoint on /parser from OWL2VOWL
app.get('/parser', function(req, res) {


    //if the ontology was parsed before, don't parse it again
     if (parsedOntology === '')
     {

         //parse ontology using JAVA
         var url = 'http://' + options.hostname + ':' + app.get('port') + '/options/';

              request({
                    url: url,
                    json: true
                }, function (error, response, body) {

                    if (!error && response.statusCode === 200) {

                      //the common part
                      var exec = require('child_process').exec, child;
                      //define the current working directory - so the files will be generated here
                      var javaOptions = { encoding: 'utf8',
                                    timeout: 0,
                                    maxBuffer: 200*1024,
                                    killSignal: 'SIGTERM',
                                    cwd:  __dirname + '/cached/',
                                    env: null 
                      }

                      //the different part
                      var javaString = '';

                      //remote URI  
                      if (body.url !=='')
                      {
                          console.log('Request to parse an url usin VOWL ... ', body.url);
                          javaString = __dirname + '/components/owl2vowl.jar' + ' -iri ' + '"'+body.url+'"'+'  -echo';                          
                      }
                      else 
                      //local file
                      {
                          var getFileName = javaOptions.cwd + body.fileName // Print the json response
                          var getPath = body.path;
                          
                          var syncPath = __dirname + getPath;
                          console.log('Request to parse a local file using VOWL... ', body.fileName);
                          console.log('Getting directory name ... ', __dirname);  
                          console.log('Calling final java path', 'java -jar ' +  __dirname + '/components/owl2vowl.jar' + ' -file ' + syncPath );                          
                          console.log("Parsing this ontology .. ", syncPath); //path to the latest uploaded ontology
                          javaString =   __dirname + '/components/owl2vowl.jar' + ' -file ' + syncPath
                      }

                      console.log('The JAVA command will run. Make sure you have JAVA installed!');
                      console.log(javaString);

                      
                      child = exec( 'java -jar ' + javaString , javaOptions, 
                              function (error, stdout, stderr){                      

                                if(error !== null)
                                {
                                  console.log('There was an error parsing the ontology in VOWL2OWL ! ' + error); 
                                  initEdges();              
                                }
                                else
                                {

                                  console.log('Succes parsing the ontology using VOWL2OWL');                                  
                                  //if there is a URL to be parsed
                                  if (body.url !=="")
                                  {
                                    console.log("Parsing an URL with VOWL");
                                    parsedOntology = JSON.parse(stdout);
                                    initEdges();                                                                                                            
                                    res.json(parsedOntology);                                     
                                  }                                  
                                  else
                                  {
                                    console.log("Parsing local file with VOWL");

                                      parsedOntology = JSON.parse(fs.readFileSync(getFileName.split(".")[0]+".json", 'utf8'));
                                      
                                      fs.readdirSync( javaOptions.cwd ).forEach(function(fileName) {

                                            console.log("Deleting the intermediate ontology" , fileName);
                                            if (path.extname(fileName) === ".json") {
                                                fs.unlinkSync(javaOptions.cwd + fileName);
                                            }
                                        }); 
                                        initEdges();
                                        res.json(parsedOntology);                                     
                                  }
                                }

                            }); //-child      
                    }
                });
     }
     else
     {

          console.log("Loading cached ontology ... ");
          res.json(parsedOntology);  //create json response on /parser
    }
});//-app.get

module.exports = app;
