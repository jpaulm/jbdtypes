package jbdtypes;

import java.util.*;

/**
 * Financial Instrument ID
 
 */
public class InstrumentId implements IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	String m_schema;
	String m_fiId;

	static HashMap<String,String> s_schemaTable = null;
	
	static {
		s_schemaTable = new HashMap<String,String>();

	    s_schemaTable.put("1", null);
	    s_schemaTable.put("2", null);
	    s_schemaTable.put("3", null);
	    s_schemaTable.put("4", null);
	    s_schemaTable.put("5", null);
	    s_schemaTable.put("6", null);
	    s_schemaTable.put("7", null);
	    s_schemaTable.put("111", null);
	    s_schemaTable.put("112", null);
	    s_schemaTable.put("113", null);
		s_schemaTable.put("114", null);  // I2 function - had to be added to bugfix1
		s_schemaTable.put("999", null);  //                  do.

	    }
	    


/**
 * InstrumentId constructor using schema; fiID.
 * @throws BDTypeException
 */
public InstrumentId(String s) throws BDTypeException{
	super();

	int sp = s.indexOf(BDT_DELIM);
	if (sp <= 0)
	    throw new BDTypeException("Invalid InstrumentId: " + s);
	else {
		m_schema = s.substring(0, sp);
		if (!(s_schemaTable.containsKey(m_schema)))
				throw new BDTypeException("Invalid schema code in Instrument Id: " + s);
		
		m_fiId = s.substring(sp + 1);
	    } 
}
/**
 * Get FI ID
 * @return java.lang.String
 */
public String getFiId() {
	return m_fiId;
}
/**
 * Get Schema
 * @return java.lang.String
 */
public String getSchema() {
	return m_schema;
}
/**
 * Convert object to String
 * @return java.lang.String
 */
public String serialize() {
	
	return m_schema + BDT_DELIM + m_fiId;     
	    
	     
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
