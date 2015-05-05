package com.eislabgroupb.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;

import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.eislabgroupb.main.MainTest;
import com.eislabgroupb.model.Conexion;
import com.eislabgroupb.util.FileManager;
import com.eislabgroupb.util.Validator;
import com.eislabgroupb.view.AboutView;
import com.eislabgroupb.view.Vista;


public class Converter implements ActionListener, MenuListener{
	
	private String tables;
	
	private Vista vista;
	private Conexion conexion; 
	private Process proc;
	private FileManager fileManager = new FileManager();
	
	private String absolutePath;
	private String publicURL;
	
	public Converter(Vista vista) {
		super();
		this.vista = vista;
		this.vista.setLocationRelativeTo(null);
		this.vista.getJbtnGenerate().addActionListener(this);
		this.vista.getJbtnCancel().addActionListener(this);
		this.vista.getJbtnClosePort().addActionListener(this);
		this.vista.getJbtnOpenURL().addActionListener(this);
		this.vista.getJmenuItemAbout().addMenuListener(this);
		this.vista.getJmenuItemExit().addActionListener(this);
		this.vista.getJmenuItemOpen().addActionListener(this);
		this.vista.getJmenuItemSave().addActionListener(this);
		this.absolutePath = findAbsoluteLocation();
		System.out.println(absolutePath);
	}
	
	public void setStringTableDelaration(){
		boolean ban = false;
		this.tables = "";
		
		if (vista.getJcboxProducts().isSelected()) {
			this.tables = this.tables + "catalog_category_entity_varchar,catalog_product_entity_varchar";		
			ban = true;
		}
		if (vista.getJcboxReviews().isSelected()) {
			if (ban) {
				this.tables = this.tables + ",";
			}
			this.tables = this.tables + ",review_detail,review_status";
			ban = true;
		}
		if (vista.getJcboxUsers().isSelected()) {
			if (ban) {
				this.tables = this.tables + ",";
			}
			this.tables = this.tables + "customer_address_entity,customer_address_entity_varchar,customer_entity,customer_entity_varchar";
			ban = true;
		}
	}
	
	public String findAbsoluteLocation(){
		File f = new File(System.getProperty("java.class.path"));
		File dir = f.getAbsoluteFile().getParentFile();
		return dir.toString()+"/lib";
	}
	
	public void buildConexion(){	
		conexion = new Conexion();
		conexion.setDbName(vista.getJtxt_DBName().getText());
		conexion.setDbUser(vista.getJtxt_DBUser().getText());
		conexion.setDbPassword(vista.getJtxt_DBPassword().getText());
		conexion.setPort(vista.getJtxt_DBPort().getText());
	}
	
	public void generateURL(){
		File wd = new File("/bin");
    	System.out.println(wd);
    	String status = "";
    	try {
    		proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
	    	if (proc != null) {
	    	   BufferedReader in = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
	    	   PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
	    	   out.println("cd "+this.absolutePath);
	    	   out.println("bash generate-mapping -u " + conexion.getDbUser() + " -p " + conexion.getDbPassword() + " --tables "+this.tables+" -o mappingoutput.ttl jdbc:mysql:///" + conexion.getDbName());
	    	   out.println("bash d2r-server --port "+ conexion.getPort() + " mappingoutput.ttl");
	    	   status = in.readLine();
	    	   System.out.println(status);
	    	   this.publicURL = "http://localhost:" + conexion.getPort() + "/";
	    	   this.checkSuccessfulGeneration(status);
	    	}
    	} catch (IOException e) {
     	   e.printStackTrace();
		}
	}
	
	public void checkSuccessfulGeneration(String status){
		if (status.contains("Unknown database")) {
			JOptionPane.showMessageDialog(vista, "ERROR! Invalid Database name.");
		}else
		if (status.contains("Access denied for user")) {
			JOptionPane.showMessageDialog(vista, "ERROR! Invalid DB Username or Password.");
		}else
		if (status.contains("Address already in use")) {
			JOptionPane.showMessageDialog(vista, "Success! The specified port is already in use, try to close the port before using it.");
			Conexion.openURL(this.publicURL);
		}else 
		if(status.contains("No such file")){
			JOptionPane.showMessageDialog(vista, "ERROR! Please, verify that the folder '/lib' is in the same directory of magentodbpublisher.jar");
		}else {
			JOptionPane.showMessageDialog(vista, "Data published successfully!");
			Conexion.openURL(this.publicURL);
		}
	}
	
	public void closePort(){
		String cmd = "fuser -k -n tcp " + vista.getJtxt_DBPort().getText();
		Runtime run = Runtime.getRuntime() ;
		try {
			run.exec(cmd) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== vista.getJbtnGenerate()) {
			if (Validator.isNumber(vista.getJtxt_DBPort().getText())) {
				this.setStringTableDelaration();
				this.buildConexion();
				this.generateURL();
				vista.getJlblOutputURL().setText(this.publicURL);
				System.out.println(absolutePath);
			}else {
				JOptionPane.showMessageDialog(vista, "Error! Port input not valid!");
			}
		}
		if (e.getSource()== vista.getJbtnClosePort()) {
			this.closePort();
		}
		if (e.getSource()== vista.getJbtnOpenURL()) {
			if (this.publicURL != null) {
				Conexion.openURL(this.publicURL);
			}else {
				JOptionPane.showMessageDialog(vista, "You must publish the data first!");
			}
		}

		if (e.getSource() == vista.getJmenuItemExit()) {
			if (proc != null) {
				proc.destroy();
			}
			System.exit(0);
		}
		if (e.getSource() == vista.getJmenuItemSave()) {
			FileManager fm = new FileManager();
			fm.saveFile(vista);
		}
		if (e.getSource() == vista.getJmenuItemOpen()) {
			FileManager fm = new FileManager();
			fm.openFile(vista);
		}
		if (e.getSource()== vista.getJbtnCancel()) {
			if (proc != null) {
				proc.destroy();
			}
			System.exit(0);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent e) {
		if (e.getSource() == vista.getJmenuItemAbout()) {
			AboutView av = new AboutView();
			av.setVisible(true);
			av.setLocationRelativeTo(null);
		}	
	}
}