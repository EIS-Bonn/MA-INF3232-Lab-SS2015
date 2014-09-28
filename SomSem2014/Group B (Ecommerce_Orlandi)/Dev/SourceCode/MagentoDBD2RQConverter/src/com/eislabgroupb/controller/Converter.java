package com.eislabgroupb.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.CodeSource;

import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.eislabgroupb.model.Conexion;
import com.eislabgroupb.view.AboutView;
import com.eislabgroupb.view.Vista;

public class Converter implements ActionListener, MenuListener{
	
	private String tables;
	
	private Vista vista;
	private Conexion conexion; 
	private Process proc;
	
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
		this.findAbsoluteLocation();
	}
	
	public void setStringTableDelaration(){
		boolean ban = false;
		
		if (vista.getJcboxProducts().isSelected()) {
			this.tables = this.tables + "catalog_category_entity,catalog_category_entity_datetime,catalog_category_entity_decimal,catalog_category_entity_int,catalog_category_entity_text,catalog_category_entity_varchar,catalog_category_product,catalog_category_product_index,core_url_rewrite,catalog_category_entity,core_store,eav_attribute,catalog_product_entity";
			ban = true;
		}
		if (vista.getJcboxReviews().isSelected()) {
			if (ban) {
				this.tables = this.tables + ",";
			}
			this.tables = this.tables + "review,review_detail,review_entity,review_entity_summary,review_status,review_store";
			ban = true;
		}
		if (vista.getJcboxUsers().isSelected()) {
			if (ban) {
				this.tables = this.tables + ",";
			}
			this.tables = this.tables + "";
			ban = true;
		}
	}
	
	public void findAbsoluteLocation(){
		String path = Converter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = "";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			//System.out.println(decodedPath);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//this.absolutePath = decodedPath+"lib";
		
		this.absolutePath = "/home/cristobal/Desktop/testjarjava/lib";
		try {
//			this.absolutePath = this.getJarContainingFolder(this.getClass()) + "/lib";
			System.out.println(absolutePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    	try {
    	   proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
    	}
    	catch (IOException e) {
    	   e.printStackTrace();
    	}
    	if (proc != null) {
    	   BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    	   PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
    	   out.println("cd "+this.absolutePath);
    	   out.println("bash generate-mapping -u " + conexion.getDbUser() + " -p " + conexion.getDbPassword() + " --tables "+this.tables+" -o mappingoutput.ttl jdbc:mysql:///" + conexion.getDbName());
    	   out.println("bash d2r-server --port "+ conexion.getPort() + " mappingoutput.ttl");
    	}
    	
	}
	
	public void closePort(){
		String cmd = "fuser -k -n tcp " + vista.getJtxt_DBPort().getText();
		Runtime run = Runtime.getRuntime() ;
		try {
			Process pr = run.exec(cmd) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getJarContainingFolder(Class aclass) throws Exception {
		CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

		File jarFile;

		if (codeSource.getLocation() != null) {
			jarFile = new File(codeSource.getLocation().toURI());
		}
		else {
			String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
			String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
			jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
			jarFile = new File(jarFilePath);
		}
		return jarFile.getParentFile().getAbsolutePath();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== vista.getJbtnGenerate()) {
			this.setStringTableDelaration();
			this.buildConexion();
			this.generateURL();
			this.publicURL = "http://localhost:" + conexion.getPort() + "/";
			vista.getJlblOutputURL().setText(this.publicURL);
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
			System.out.print("dasdasdasdad");
			AboutView av = new AboutView();
			av.setVisible(true);
			av.setLocationRelativeTo(null);
		}	
	}
}