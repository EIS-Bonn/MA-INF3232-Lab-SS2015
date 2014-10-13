import unittest
import converter

from xml.dom import minidom

from rdflib import Graph, URIRef, Literal, RDF
from rdflib.namespace import FOAF

def str_comp(str1, str2):
	for i in range(len(str1)):
			if str1[i] != str2[i]:
				return False
	return True
	
class ConverterTests(unittest.TestCase):
	def testOne(self):
	    self.graph = Graph()
	    self.obj_itemlist, self.cxn_itemlist, self.models_lists = converter.parse("test_file.xml")
	    self.failUnless(len(self.obj_itemlist)>0)
	    self.failUnless(len(self.cxn_itemlist)>0)
	    self.failUnless(len(self.models_lists)>0)
		
	def testTwo(self):
		self.graph = Graph()
		self.obj_itemlist, self.cxn_itemlist, self.models_lists = converter.parse("test_file.xml")
		for m in self.models_lists:
			model = m.attributes['Name'].value + "/"
		objects = converter.obj_parser(model, self.obj_itemlist, self.graph, self.cxn_itemlist)
		name1 = 'our_model: Login Business process/Enter credentials'
		type1 = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
		arg1 = "sepc: Function"		
		self.failUnless(str_comp(name1, objects[0][0]))
		self.failUnless(str_comp(type1, objects[0][1]))
		self.failUnless(str_comp(arg1, objects[0][2]))
		
		name2 = "our_model: Login Business process/Enter credentials"
		type2 = "sepc: hasOutput"
		arg2 = "our_model: Login Business process/Admin access"
		self.failUnless(str_comp(name2, objects[1][0]))
		self.failUnless(str_comp(type2, objects[1][1]))
		self.failUnless(str_comp(arg2, objects[1][2]))
		
		name3 = "our_model: Login Business process/Enter credentials"
		type3 = "sepc: hasInput"
		arg3 = "our_model: Login Business process/Enter credentials"
		self.failUnless(str_comp(name3, objects[2][0]))
		self.failUnless(str_comp(type3, objects[2][1]))
		self.failUnless(str_comp(arg3, objects[2][2]))

def main():
    unittest.main()

if __name__ == '__main__':
    main()