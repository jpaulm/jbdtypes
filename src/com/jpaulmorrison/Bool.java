package com.jpaulmorrison;

/**
 * Basic Data Type wrapping a boolean value
 * @author: Administrator
 */
public class Bool implements IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	boolean m_value = false;
	public static final Bool FALSE = new Bool("0");
	public static final Bool TRUE = new Bool("1");
/**
 * Bool constructor using Boolean value 
 */
public Bool(Boolean b) {
	m_value = b.booleanValue();
}
/**
 * Bool constructor using String value 
 */
public Bool(String s) {
	// aziz sept 26, 2000 added the "".equals(s) to be true
	m_value = "1".equals(s) || "".equals(s);    // also applied to bugfix
}
/**
 * Bool constructor using boolean value 
 */
public Bool(boolean b) {
	m_value = b;
}
/**
 * Return the value of the Bool object as a boolean
 * @return boolean
 */
public boolean booleanValue() {
	return m_value;
}

/**
 * Return boolean value of instance variable 
 * @return boolean
 */
public boolean isTrue() {
	return m_value;
}
/**
 * Returns true if and only if the argument is not null and is a Boolean object that 
 * contains the same boolean value as this object. 
 * @return boolean
 * @param o java.lang.Object
 */
public boolean equals(Object o) {
	
	if (!(o != null && o instanceof Bool))
	    return false;

	Bool b = (Bool) o;    
	return (m_value == b.booleanValue());    
	
}
/**
 * Convert object to String
 * @return java.lang.String
 */
public String serialize() {
	
	return (m_value ? "1" : "0");
	     
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
