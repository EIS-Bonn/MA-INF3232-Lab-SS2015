package application.model.dao.tdb;

import application.model.dao.IRDFStatement;

public class RDFStatement implements IRDFStatement {

	String subject;
	String predicate;
	String object;
	
	public RDFStatement(String subject, String predicate, String object){
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	public String getSubjcet() {
		// TODO Auto-generated method stub
		return this.subject;
	}

	public String getObj() {
		// TODO Auto-generated method stub
		return this.object;
	}

	public String getPredicate() {
		// TODO Auto-generated method stub
		return this.predicate;
	}
}
