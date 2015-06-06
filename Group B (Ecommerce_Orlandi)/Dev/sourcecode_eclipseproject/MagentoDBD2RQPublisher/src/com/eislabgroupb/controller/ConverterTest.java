package com.eislabgroupb.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eislabgroupb.util.Validator;
import com.eislabgroupb.view.Vista;

public class ConverterTest {

	@Test
	public void testFindAbsoluteLocation() {
		Converter cv = new Converter(new Vista());
		assertEquals("/home/cristobal/Desktop/testjarjava/lib", cv.findAbsoluteLocation());
	}

}
