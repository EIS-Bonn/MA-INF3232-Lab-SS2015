package com.eislabgroupb.main;

import com.eislabgroupb.controller.Converter;
import com.eislabgroupb.view.Vista;

public class MainTest {
	public static void main(String[] args) {
		Vista vista = new Vista();
		Converter conv = new Converter(vista);
		vista.setVisible(true);
		vista.pack();
	}
}
