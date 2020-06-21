package com.jpaulmorrison;


/**
 * This interface indicates that a class is a Basic Data Type
 * @author: Administrator
 */
public interface IBDType 
   
   {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	static final char BDT_DELIM = ';';

/**
 * This requires that all Basic Data Types implement a serialize method
 * @return java.lang.String
 */
String serialize();
}
