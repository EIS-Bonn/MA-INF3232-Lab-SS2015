import org.jdom.*;

var g_nLoc = Context.getSelectedLanguage(); 
var xmlOutput = Context.createXMLOutputObject(Context.getSelectedFile(), "Root");
 
var oSelModels = ArisData.getSelectedModels();
for (var i = 0; i < oSelModels.length; i++) {
    var oModel = oSelModels[i];
    var xmlModel = writeXmlItem(xmlOutput, xmlOutput.getRootElement(), oModel, "Model");
     
    var oObjDefs = oModel.ObjDefList();
    for (var j = 0; j < oObjDefs.length; j++) {
        var oObjDef = oObjDefs[j];
        var xmlObjDef = writeXmlItem(xmlOutput, xmlModel, oObjDef, "ObjDef");
    }
     
    var oCxnDefs = oModel.CxnList();
    for (var j = 0; j < oCxnDefs.length; j++) {
        var oCxnDef =  oCxnDefs[j]
        var xmlCxnDef = writeXmlCxn(xmlOutput, xmlModel, oCxnDef, "CxnDef");
    }
}
xmlOutput.WriteReport();
 
/****************************************************************************/
 
function writeXmlItem(xmlOutput, xmlParent, oItem, sElement) {
    var xmlItem = xmlOutput.addElement(xmlParent, sElement);
    xmlItem.setAttribute("Name", oItem.Name(g_nLoc));
    xmlItem.setAttribute("TypeNum", oItem.TypeNum());
    xmlItem.setAttribute("ID", getAttrID(oItem));
    return xmlItem;
}
 
function writeXmlCxn(xmlOutput, xmlParent, oCxn, sElement) {
    var xmlCxn = xmlOutput.addElement(xmlParent, sElement);
    xmlCxn.setAttribute("SourceName", oCxn.SourceObjDef().Name(g_nLoc));
    xmlCxn.setAttribute("TargetName", oCxn.TargetObjDef().Name(g_nLoc));    
    xmlCxn.setAttribute("TypeNum", oCxn.TypeNum());
    xmlCxn.setAttribute("ID", getAttrID(oCxn));
    return xmlCxn;
}
 
function getAttrID(oItem) {
    return oItem.Attribute(Constants.AT_ID, g_nLoc).getValue();
}