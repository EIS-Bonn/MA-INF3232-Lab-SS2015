package com.eislabgroupb.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidatorTest {

	@Test
	public void testIsNumber() {
		String str = "12331d";
		assertEquals(false, Validator.isNumber(str));
	}

}
