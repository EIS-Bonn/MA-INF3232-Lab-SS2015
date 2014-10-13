from xml.dom import minidom

from rdflib import Graph, URIRef, Literal, RDF
from rdflib.namespace import FOAF

import sys 

def skip_w(str):
	return str.replace(' ','-')

elements_id = {'Event' : 18, 'Function' : 22, 'OrganisationalUnit' : 43, 'LogicalConnector' : 50, 'ITSystemOrApplication' : 6, 'ErrorEvent' : 159, 'Role' : 78}

def main():
	if len(sys.argv) > 1:
		xmldoc = minidom.parse(sys.argv[1]) 
		obj_itemlist = xmldoc.getElementsByTagName('ObjDef') 
		cxn_itemlist = xmldoc.getElementsByTagName('CxnDef')
		models_lists = xmldoc.getElementsByTagName('Model')
	
	graph = Graph()
	n = "http://example.org/ARIS_MODELS/web-shop/"
	ontology = "http://www.ip-super.org/ontologies/process/sepc/v1.1.1#"
	ontology2 = "http://www.ip-super.org/ontologies/process/bpmo/v2.0.1#"
	ontology3 = "http://www.ip-super.org/ontologies/process/upo/v2.0.1#"
	
	model_prefix = "our_model"
	sepc_prefix = "sepc"
	bpmo_prefix = "bpmo"
	upo_prefix = "upo"
	graph.bind(model_prefix, URIRef(n))
	graph.bind(sepc_prefix, URIRef(ontology))
	graph.bind(bpmo_prefix, URIRef(ontology2))
	graph.bind(upo_prefix, URIRef(ontology3))
	for m in models_lists:
		model = m.attributes['Name'].value + "/"
		graph.add((URIRef(skip_w(n + model)), RDF.type, FOAF.model))
		for o in obj_itemlist:
			obj = o.attributes['Name'].value
			if o.attributes['TypeNum'].value == '18':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(sepc_prefix+": Event")))
				indices = [i for i, c in enumerate(cxn_itemlist) if c.attributes['SourceName'].value == obj]
				for i in indices:
					graph.add(((Literal(model_prefix+": "+model + obj)), Literal(sepc_prefix+": hasOutgoingFlow"), Literal(model_prefix+": "+ model + cxn_itemlist[i].attributes['TargetName'].value)))
				indices = [i for i, c in enumerate(cxn_itemlist) if c.attributes['SourceName'].value == obj]
				for i in indices:
					graph.add(((Literal(model_prefix+": "+model + obj)), Literal(sepc_prefix+": hasIncomingFlow"), Literal(model_prefix+": " + model + cxn_itemlist[i].attributes['SourceName'].value)))
			if o.attributes['TypeNum'].value == '22':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(sepc_prefix+": Function")))
				indices = [i for i, c in enumerate(cxn_itemlist) if c.attributes['SourceName'].value == obj]
				for i in indices:
					graph.add(((Literal(model_prefix+": "+model + obj)), Literal(sepc_prefix+": hasOutput"), Literal(model_prefix+": "+ model + cxn_itemlist[i].attributes['TargetName'].value)))
				indices = [i for i, c in enumerate(cxn_itemlist) if c.attributes['SourceName'].value == obj]
				for i in indices:
					graph.add(((Literal(model_prefix+": "+model + obj)), Literal(sepc_prefix+": hasInput"), Literal(model_prefix+": " + model + cxn_itemlist[i].attributes['SourceName'].value)))
			if o.attributes['TypeNum'].value == '43':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(sepc_prefix+": OrganisationalUnit")))
			if o.attributes['TypeNum'].value == '50':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(sepc_prefix+": LogicalConnector")))
				indices = [i for i, c in enumerate(cxn_itemlist) if c.attributes['SourceName'].value == obj]
				for i in indices:
					graph.add(((Literal(model_prefix+": "+model + obj)), Literal(sepc_prefix+": hasOutgoingFlow"), Literal(model_prefix+": " + model + cxn_itemlist[i].attributes['TargetName'].value)))
				indices = [i for i, c in enumerate(cxn_itemlist) if c.attributes['SourceName'].value == obj]
				for i in indices:
					graph.add(((Literal(model_prefix+": "+model + obj)), Literal(sepc_prefix+": hasIncomingFlow"), Literal(model_prefix+": " + model + cxn_itemlist[i].attributes['SourceName'].value)))
			if o.attributes['TypeNum'].value == '159':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(bpmo_prefix+": ErrorEvent")))
			if o.attributes['TypeNum'].value == '6':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(sepc_prefix+": ITSystemOrApplication")))
			if o.attributes['TypeNum'].value == '78':
				graph.add(((Literal(model_prefix+": "+model + obj)), RDF.type, Literal(upo_prefix+": Role")))
		for c in cxn_itemlist:
			start = Literal(c.attributes['SourceName'].value)
			end = Literal(c.attributes['TargetName'].value)
			graph.add(((Literal(model_prefix+": "+model + "from_" +start + "_to_" + end)), RDF.type, Literal(sepc_prefix+": Arc")))
			graph.add(((Literal(model_prefix+": "+model + "from_" +start + "_to_" + end)), Literal(sepc_prefix+": hasSource"), Literal(model_prefix+": "+ model + cxn_itemlist[i].attributes['SourceName'].value)))
			graph.add(((Literal(model_prefix+": "+model + "from_" +start + "_to_" + end)), Literal(sepc_prefix+": hasTarget"), Literal(model_prefix+": " + cxn_itemlist[i].attributes['TargetName'].value)))
	
	graph.serialize(destination='output.txt', format='n3')
	
if __name__ == "__main__":
    main()
