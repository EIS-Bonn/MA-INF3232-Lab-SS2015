/**
 * 
 */
package de.unibonn.iai.eis.qaentlod.util;

/**
 * @author vm72i6p
 * 
 */
public class FooTest {
	public static void main(String[] args) throws Exception {
		
		Metrics met1 = new Metrics();
		met1.setName("AuthenticityDataset");
		met1.setValue("0,5");
		
		Metrics met2 = new Metrics();
		met2.setName("DigitalSignatures");
		met2.setValue("0,3");
		
		Metrics met3 = new Metrics();
		met3.setName("Free of Error");
		met3.setValue("0,0");
		
		Dimension dim1 = new Dimension();
		dim1.setName("Trust");
		dim1.getMetrics().add(met1);
		dim1.getMetrics().add(met2);
		
		Dimension dim2 = new Dimension();
		dim2.setName("Free of Error");
		dim2.getMetrics().add(met3);
		
		Results f1 = new Results();
		//f1.setName("bar");
		//f1.getDimensions().add(dim1);
		//f1.getDimensions().add(dim2);
		
		//ResultsHelper.write(f1, "foo.xml");

		//Results f2 = ResultsHelper.read("foo.xml");
		//System.out.println("Foo" + f2.getName());
		// the output : Foobar
	}
}