package com.eis.greenenergy.help;

/**
 * An instance of this class represents an entry in the energy prediction list.<br />
 * This auxiliary class supports the customized appearance of the <code>ListView</code> in the application's detail view.
 * Each object of this class consists of the entry's label, the entry's position in the <code>ListView</code>, 
 * the percentage of green energy, and a boolean value which indicates whether this entry was selected, or not.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-08-23
 */
public class PredictionEntry {
	/**
	 * Label of an entry in the <code>ListView</code>.
	 */
	String name = null;
	
	/**
	 * Position of an entry in the <code>ListView</code>.
	 */
	int id = -1;
	
	/**
	 * Amount of predicted green energy related to <code>ListView</code> entry.
	 */
	double amount = -1;
	
	/**
	 * Indicates whether an entry in the prediction <code>ListView</code> was checked.
	 */
	boolean selected = false;

	/**
	 * The information given as parameters are assigned to the class variables (<code>name</code>, <code>selected</code>, <code>id</code>, <code>amount</code>).<br />
	 * The boolean value <code>selected</code> indicates whether the entry which is represented by an instance of this class was 
	 * selected in the <code>ListView</code>, or not.
	 * 
	 * @param	name		Label for this entry of the prediction list.
	 * @param	selected	<code>true</code> if this entry was selected in the <code>ListView</code> by the user;<br />
	 * 						<code>false</code> otherwise.
	 * @param	id			Position of this entry in the <code>ListView</code>.
	 * @param	amount		Percentage of green energy in relation to conventional energies.
	 */
	public PredictionEntry(String name, boolean selected, int id, double amount) {
		// Object's default constructor is called
		super();
		this.name = name;
		this.selected = selected;
		this.id = id;
		this.amount = amount;
	}
	
	/**
	 * Get method to access the id for this entry of the energy prediction list.
	 * 
	 * @return	Id of this entry, which represents the entry's position in the <code>ListView</code>.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get method to access the green energy amount for this entry of the energy prediction list.
	 * 
	 * @return	Percentage of green energy in relation to conventional energies, which is displayed in the <code>ListView</code>.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Get method to access the label for this entry of the energy prediction list.
	 * 
	 * @return	Name of this entry, which is then displayed in the detail view's <code>ListView</code>.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set method to edit the label for this entry of the energy prediction list.
	 * 
	 * @param	name	Label of this entry, which should be displayed in the detail view's <code>ListView</code>.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to access the state of this entry of the energy prediction list.
	 * 
	 * @return	Boolean value <code>selected</code>, which indicates if the user selected this entry in the <code>ListView</code>, or not.
	 */
	public boolean isSelected() {
		return selected;
	}
	 
	/**
	 * Set method to edit the state of this entry of the energy prediction list.<br />
	 * This method is called, when the user selected an entry in the <code>ListView</code>.
	 * 
	 * @param	selected	<code>true</code> if this entry was tapped in the <code>ListView</code>;<br />
	 * 						<code>false</code> otherwise.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	  
}