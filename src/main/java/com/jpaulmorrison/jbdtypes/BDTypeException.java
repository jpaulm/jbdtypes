package jbdtypes;


/**
 * Basic Data Type Exception class
 
 */
public class BDTypeException extends RuntimeException  {

	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";
	final static long serialVersionUID = 362498820763181265L;
/**
 * BDTypeException constructor with String.
 */
public BDTypeException(String s)  {
	super(s);
	
}
}
