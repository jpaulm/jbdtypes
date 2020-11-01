package com.jpaulmorrison.jbdtypes;


import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Define (non-Java.util) Date class 
 
 */
public class Date  implements IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	
	String m_date;

   
/** 
* Constructor for date with no parameter - use current date in this locale - dangerous!
*/

public Date()   {
	    
	    Calendar cal
		   = new GregorianCalendar();

		java.util.Date date = cal.getTime();   
		
		TimeStamp ts = new TimeStamp(date.getTime());
		m_date = ts.serialize().substring(0,8); 
	 	  
		    
	    }
/** 
* Constructor for date with date string (yyyymmdd); optional calendar and era
*   are being ignored for now
*/

public Date(String s)   {
	    super();
	    m_date = BuildDate(s);
	    }
/** 
* Constructor for jbdtypes Date using java.sql.Date
*/

public Date(java.sql.Date dt)   {
	   long millis = dt.getTime();
	   TimeStamp ts = new TimeStamp(millis);	
	   
	   m_date = BuildDate(ts.serialize().substring(0,8));
	   }
/**
 * Return true if <code>this</code> date is after specified date
 * @return boolean
 * @param d jbdtypes.Date
 */
public boolean after(Date d) {
	return Integer.parseInt(this.m_date) > Integer.parseInt(d.m_date);
}
/**
 * Return true if <code>this</code> date is before specified date
 * @return boolean
 * @param d jbdtypes.Date
 */
public boolean before(Date d) {
	return Integer.parseInt(this.m_date) < Integer.parseInt(d.m_date);
}
/**
 * Strip off calendar and era (if any)
 * @return java.lang.String
 * @param s java.lang.String
 */
 static String BuildDate(String s) {
	int sp = s.indexOf(BDT_DELIM);
	if (sp == -1)
	    return s;
	else
	    return s.substring(0,sp); 
}
/**
 * Build a TimeStamp using specified TimeTz object
 * @return jbdtypes.TimeStamp
 * @param t jbdtypes.TimeTz
 * @throws BDTypeException
 */
public TimeStamp buildTimeStamp(TimeTz t) throws BDTypeException {
	
	return new TimeStamp(m_date + 'T' + t.serialize());
}
/**
 * Convert this Date to a java.sql.Date 
 * @return java.sql.Timestamp
 */
public java.sql.Date convertToSQL() {

	TimeStamp ts = new TimeStamp(m_date + "T00:00");
	return new java.sql.Date(ts.getTime());
	
	
}
/**
 * Return true if <code>this</code> date is same as specified date
 * @return boolean
 * @param d jbdtypes.Date
 */
public boolean equals(Date d) {
	return Integer.parseInt(this.m_date) == Integer.parseInt(d.m_date);
}
/**
 * Display a (non-Java.util) jbdtypes Date object as an 8-byte string
 * @return java.lang.String
 */
public String serialize() {

	   return m_date;
 
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
