package com.eislabgroupb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import javax.swing.JFileChooser;

import com.eislabgroupb.controller.Converter;
import com.eislabgroupb.model.Conexion;
import com.eislabgroupb.view.Vista;

public class FileManager {
	
	public void saveFile(Vista vista){
		try {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println(file.getAbsolutePath());
				PrintWriter writer;
		
				writer = new PrintWriter(file, "UTF-8");
		
				writer.print(vista.getJtxt_DBName().getText()+";"+vista.getJtxt_DBUser().getText()+";"+vista.getJtxt_DBPassword().getText()+";"+vista.getJtxt_DBPort().getText());
				writer.close();
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void openFile(Vista vista){
		JFileChooser fileChooser = new JFileChooser();
		try {
			if (fileChooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				BufferedReader br = new BufferedReader(new FileReader(file));
				String[] line = br.readLine().split(";");
				vista.getJtxt_DBName().setText(line[0]);
				vista.getJtxt_DBUser().setText(line[1]);
				vista.getJtxt_DBPassword().setText(line[2]);
				vista.getJtxt_DBPort().setText(line[3]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
