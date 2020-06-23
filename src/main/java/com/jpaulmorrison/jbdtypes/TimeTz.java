package com.jpaulmorrison.jbdtypes;

import java.util.TimeZone;




/**
 * TimeTz is mostly needed in conjunction with dates, so it is held as a String
 *  
 
 */
public class TimeTz implements IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";


	   String m_ttz;

/**
 * TimeTz constructor with String.
 * Format is same as time and adjustment part of TimeStamp string.
 */
public TimeTz(String s) {
	m_ttz = s;
}
/**
 * Build a TimeStamp using specified Date object
 * @return com.jpaulmorrison.jbdtypes.TimeStamp
 * @param d com.jpaulmorrison.jbdtypes.Date
 * @throws BDTypeException
 */
TimeStamp buildTimeStamp(Date d) throws BDTypeException {
	
	return new TimeStamp(d.serialize() + 'T' + m_ttz);
	
}
/**
 * Find a TimeZone (alpha only) in static TimeZone table
 * @return java.util.SimpleTimeZone
 * @param s java.lang.String
 */
public static TimeZone GetTimeZone(String s) {
	return (TimeZone) BDTHelper.s_timeZoneTable.get(s);
}
/**
 * Display a TimeTz object 
 * @return java.lang.String
 */
public String serialize() {

	    return m_ttz;    
	  
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
