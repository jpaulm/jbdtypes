package com.jpaulmorrison.jbdtypes;

import java.util.*;

/**
 * Insert the type's description here.
 * Creation date: (9/20/00 2:35:47 PM)
 *  */
 

public interface IMarket extends IBDType{   

	
 static final String copyright = 
				"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
				"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
				"based on the Everything Development Company's Artistic License.  A document describing " +
				"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
				"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";		
 
 /**
  * Insert the method's description here.
  * Creation date: (9/26/00 4:55:31 PM)
  * @return com.sun.java.util.collections.HashMap
  */
HashMap<?, ?> getHolidays();
/**
 * Insert the method's description here.
 * Creation date: (9/28/00 9:43:08 AM)
 * @return com.sun.java.util.collections.HashMap
 */
HashMap<?,?> getReplHolidays();
/**
 * Insert the method's description here.
 * Creation date: (9/25/00 10:25:18 AM)
 * @return com.sun.java.util.collections.HashMap
 */
HashMap <String, List<?>> getTimes();
/**
 * Insert the method's description here.
 * Creation date: (9/25/00 4:36:09 PM)
 * @return java.lang.String
 */
String getTimeZone();
/**
 * Returns true if market is open for specified date and FI Type 
 * @return boolean
 */
public boolean isOpen(Date date, String fiType);
/**
 * Returns true if market is open for specified time and FI Type 
 * @return boolean
 */
public boolean isOpen(TimeStamp ts, String fiType);
/**
 * Returns true if market is open right now for specified FIType  
 * @return boolean
 */
public boolean isOpen(String fiType);
/**
 * Insert the method's description here.
 * Creation date: (9/26/00 4:52:54 PM)
 * @param hm com.sun.java.util.collections.HashMap
 */
void setHolidays(HashMap<?, ?> hm);
/**
 * Insert the method's description here.
 * Creation date: (9/26/00 4:52:54 PM)
 * @param hm com.sun.java.util.collections.HashMap
 */
void setReplHolidays(HashMap<?, ?> hm);
/**
 * Insert the method's description here.
 * Creation date: (9/25/00 10:25:57 AM)
 * @param hm com.sun.java.util.collections.HashMap
 */
void setTimes(HashMap<String, List<?>> hm);
}
