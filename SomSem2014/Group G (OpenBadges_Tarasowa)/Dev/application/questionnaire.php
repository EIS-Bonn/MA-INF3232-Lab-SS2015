<html>
    <head>
        <title> Questionnaire </title>
        <link href="buttons.css" type="text/css" rel="stylesheet">
        <style>
            body {
                align: center;
                color: #D0C6B1;
                background-color: #D0C6B1;
                font-family: tahoma;
            }
            .top-div{
                background-color: #555555;
                width:70%;
                min-height:100px;
                padding:15px;
                box-shadow: 10px 10px 5px #888888;
            }
            h2{
                color: #06A2CB;
                
            }
            .main-div{
                background-color: #EBB035;
                width:70%;
                padding:15px;
                box-shadow: 10px 10px 5px #888888;
                margin-top:20px;
            }
            .bottom-div{
                text-align: center;
                background-color: #218559;
                width:70%;
                padding:15px;
                box-shadow: 10px 10px 5px #888888;
                margin-top:20px;
            }
            .thank_you{
                text-align: right;                
                width:70%;
                box-shadow: 10px 10px 5px #888888;
                margin-top:100px; 
                background-image:url('kitty.gif');
                background-repeat:no-repeat;
                background-position:left center;
                min-height: 100px;
                padding: 15px;
            }
            
            .submit-div{
                text-align: center;
                background-color: #555555;
                width:70%;
                padding:15px;
                box-shadow: 10px 10px 5px #888888;
                margin-top:20px;
            }
            .inner-div{
                background-color: #555555;
                margin-top: 10px;
                text-align: justify;
                padding: 5px;
            }
            p{
                color: #06A2CB;
            }
            .options-row{
                width: 100%;
                text-align: left;
            }
            input{
                margin: 10px;
            }
            
            a{
                color: #EBB035;
            }
            a:hover{
                color: #D0C6B1;
            }
            textarea{
                margin:10px;
            }
            td{
                vertical-align:middle;
                color: #218559;
                text-align: center;
                min-height:100px;
            }
            .thank{
                 background-color:#D0C6B1;
                 width:513px;
            }
            
        </style>
    </head>
    <body>
        <script type="text/javascript">
            function openDiv(){
                var div = document.getElementById('example').style.display="block";
            }
        </script>
        <center>
            
<?
if ($_POST) :
    $link = mysqli_connect('localhost', 'quest', 'sw123', 'questionnaire');
    if (!$link) {
        die('Could not connect: ' . mysqli_connect_error());
    }
 
    $name_x = $_POST['name'];
    $name = htmlspecialchars($link->real_escape_string($name_x));
    $org_x = $_POST['organisation'];
    $organisation = htmlspecialchars($link->real_escape_string($org_x));
    $expertise = $_POST['expertise'];
    $impl = $_POST['impl'];
    $pres = $_POST['pres'];
    $incorp = $_POST['incorp'];
    $simplicity = $_POST['simplicity'];
    $integr = $_POST['integr'];
    $reuse = $_POST['reuse'];
    $qual_reus = $_POST['qual_reus'];
    $acc_reus_prop = $_POST['acc_reus_prop'];
    $acc_reus_prop2 = $_POST['acc_reus_prop2'];
    $acc_reus_class = $_POST['acc_reus_class'];
    $qual_new = $_POST['qual_new'];
    $open_x = $_POST['open'];
    $open = htmlspecialchars($link->real_escape_string($open_x));
    
    $values = "'','".$name."','".$organisation."','".$expertise."','".$impl."','".$pres."','".$incorp."','".$simplicity."','".$integr."','".$reuse."','".$qual_reus."','".$acc_reus_prop."','".$acc_reus_prop2."','".$acc_reus_class."','".$qual_new."','".$open."'";
 
    $query= "INSERT INTO answers VALUES (" . $values . ")";
    

    if (!mysqli_query($link, $query)){
        die('Error: ' . mysqli_error($link));
    }

?>
            <div class="thank_you">
                <table cellpadding="5px;" width="100%">
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td class="thank">
                            <div style="margin:25px;">
                                Thank you for your collaboration! Your opinion is extremely important to us.<br> 
                                Please share the link with any people who can help us to <span style="text-decoration: line-through">save the world</span> evaluate our work.
                            </div>
                       </td>
                    </tr>
                </table>
            </div>    
            

<?

    mysqli_close($link);

else :
?>
            <div class="top-div">
                <h2>Questionnaire</h2>
                <h3>for collecting opinions about methods of improving the quality of <i>relational data to RDF</i> mapping</h3>
            </div>
            <form name="main-form" method="post" action="questionnaire.php">
                <div class="main-div">                
                    <div class="inner-div">
                        <p>The following information will be used to thank you for your collaboration in the aknowledgement section. 
                            If you do not want to be explicitly named in the paper, please leave fields of the section empty. </p> 
                        <div class="options-row">
                            <label for="name">Your full name (incl. title)</label>
                            <input type="text" name="name">
                            <label for="organisation">Your institute/organisation</label>
                            <input type="text" name="organisation">
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>Please provide your level of expertise in the "Relational data to RDF" domain, this will help us to leverage the results.
                            Your contribution is important for us and will be acknowledged in the paper regardless of your expertise. </p> 
                        <div class="options-row">
                            <input type="radio" name="expertise" value="1">Novice 
                            <input type="radio" name="expertise" value="2">Low experience
                            <input type="radio" name="expertise" value="3">Moderate experience
                            <input type="radio" name="expertise" value="4" checked>Expert
                        </div>
                    </div>
                </div> 
                
                <div class="bottom-div">
                    <div class="inner-div">
                        <p>Both ETL and on-demand data access must be implemented</p>
                        <div class="options-row">
                            <input type="radio" name="impl" value="1">Absolutely disagree 
                            <input type="radio" name="impl" value="2">Disagree
                            <input type="radio" name="impl" value="3" checked>Does not influence
                            <input type="radio" name="impl" value="4">Agree
                            <input type="radio" name="impl" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>In case of automated mapping, the direct mapping requirements must be satisfied; in case of manual mapping, it must be represented in R2RML. </p>
                        <div class="options-row">
                            <input type="radio" name="pres" value="1">Absolutely disagree 
                            <input type="radio" name="pres" value="2">Disagree
                            <input type="radio" name="pres" value="3" checked>Does not influence
                            <input type="radio" name="pres" value="4">Agree
                            <input type="radio" name="pres" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>Explicitly related data from the database should be implicitly incorporated into the mapping.</p>
                        <div class="options-row">
                            <input type="radio" name="incorp" value="1">Absolutely disagree 
                            <input type="radio" name="incorp" value="2">Disagree
                            <input type="radio" name="incorp" value="3" checked>Does not influence
                            <input type="radio" name="incorp" value="4">Agree
                            <input type="radio" name="incorp" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>The <u>results</u> of frequently used complicated queries should be integrated into the dataset on the stage of mapping creation.</p>
                        <div class="options-row">
                            <input type="radio" name="simplicity" value="1">Absolutely disagree 
                            <input type="radio" name="simplicity" value="2">Disagree
                            <input type="radio" name="simplicity" value="3" checked>Does not influence
                            <input type="radio" name="simplicity" value="4">Agree
                            <input type="radio" name="simplicity" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>Integration of external datasets (when possible) improves the quality of mapping</p>
                        <div class="options-row">
                            <input type="radio" name="integr" value="1">Absolutely disagree 
                            <input type="radio" name="integr" value="2">Disagree
                            <input type="radio" name="integr" value="3" checked>Does not influence
                            <input type="radio" name="integr" value="4">Agree
                            <input type="radio" name="integr" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>The number of reused classes and properties (in comparison with newly defined ones) should be maximized</p>
                        <div class="options-row">
                            <input type="radio" name="reuse" value="1">Absolutely disagree 
                            <input type="radio" name="reuse" value="2">Disagree
                            <input type="radio" name="reuse" value="3" checked>Does not influence
                            <input type="radio" name="reuse" value="4">Agree
                            <input type="radio" name="reuse" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>When choosing classes and properties for reuse, the most frequently used ones (e.g. according to <a href="http://lov.okfn.org">LOV</a> metrics) should be chosen</p>
                        <div class="options-row">
                            <input type="radio" name="qual_reus" value="1">Absolutely disagree 
                            <input type="radio" name="qual_reus" value="2">Disagree
                            <input type="radio" name="qual_reus" value="3" checked>Does not influence
                            <input type="radio" name="qual_reus" value="4">Agree
                            <input type="radio" name="qual_reus" value="5">Absolutely agree
                        </div>
                    </div>
                     <div class="inner-div">
                        <p>The properties chosen for reuse should be available, dereferencable and have unambiguous meaning in the application domain.</p>
                        <div class="options-row">
                            <input type="radio" name="acc_reus_prop" value="1">Absolutely disagree 
                            <input type="radio" name="acc_reus_prop" value="2">Disagree
                            <input type="radio" name="acc_reus_prop" value="3" checked>Does not influence
                            <input type="radio" name="acc_reus_prop" value="4">Agree
                            <input type="radio" name="acc_reus_prop" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>When choosing the properties for reuse, <i>rdfs:domain</i>, <i>rdfs:range</i> and <i>rdfs:comment</i> must be taken into account.</p>
                        <div class="options-row">
                            <input type="radio" name="acc_reus_prop2" value="1">Absolutely disagree 
                            <input type="radio" name="acc_reus_prop2" value="2">Disagree
                            <input type="radio" name="acc_reus_prop2" value="3" checked>Does not influence
                            <input type="radio" name="acc_reus_prop2" value="4">Agree
                            <input type="radio" name="acc_reus_prop2" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>The classes chosen for reuse must be semantically compatible with the domain model. <a style="cursor:pointer; color: #EBB035; text-decoration: underline" onclick="openDiv()">See example</a></p>
                        <div id="example" style="display:none">
                            Let us assume that we need to link a slide presentation and its CSS style. The property <i>oa:styledBy</i> is chosen for the linkage.
                            The property is taken from the <a href="http://www.w3.org/ns/oa">Open Annotation Data Model ontology</a> and its domain class is <i>oa:Annotation</i>
                            
                            <table cellpadding='5px' style="margin: 5px 5px;" align="center" color="#D0C6B1" border='1px' border-color='#D0C6B1'>
                                <tr><td color="#D0C6B1"><b>Semantically incompatible</b></td><td color="#D0C6B1"><b>Possible solution</b></td></tr>
                                <tr><td style="text-align:left; color: #D0C6B1" >
                                    sw:Presentation rdfs:subClassOf oa:Annotation<br>
                                    sw:Style rdfs:subClassOf oa:CssStyle<br>
                                    http:\\sw#Presentation1 oa:styledBy http:\\sw#Style1<br>
                                </td><td style="text-align:left; color: #D0C6B1" >
                                    sw:styledBy rdfs:domain sw:Presentation<br>
                                    sw:styledBy rdfs:range sw:Style<br>
                                    sw:styledBy rdfs:seeAlso oa:styledBy<br>
                                    sw:Style rdfs:subClassOf oa:CssStyle
                                </td></tr>
                            </table>
                            Here, the classes <i>sw:Presentation</i> and <i>oa:Annotation</i> are not semantically compatible.
                        </div>
                        
                        <div class="options-row">
                            <input type="radio" name="acc_reus_class" value="1">Absolutely disagree 
                            <input type="radio" name="acc_reus_class" value="2">Disagree
                            <input type="radio" name="acc_reus_class" value="3" checked>Does not influence
                            <input type="radio" name="acc_reus_class" value="4">Agree
                            <input type="radio" name="acc_reus_class" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>When defining new classes and properties <i>rdfs:domain</i>, <i>rdfs:range</i> and <i>rdfs:comment</i> must be specified</p>
                        <div class="options-row">
                            <input type="radio" name="qual_new" value="1">Absolutely disagree 
                            <input type="radio" name="qual_new" value="2">Disagree
                            <input type="radio" name="qual_new" value="3" checked>Does not influence
                            <input type="radio" name="qual_new" value="4">Agree
                            <input type="radio" name="qual_new" value="5">Absolutely agree
                        </div>
                    </div>
                    <div class="inner-div">
                        <p>What other practices whould you suggest to improve the quality of mapping? (please, try to be concise)</p>
                        <div class="options-row">
                            <textarea name="open" rows="5" cols="50"></textarea>
                        </div>
                    </div>
                    
                    <button class="btn btn-4 btn-4a icon-arrow-right" onclick="this.submit()">Submit</button>
                    
                </div>                
            </form>

  
<? endif; ?>

         </center>
    </body>
</html>