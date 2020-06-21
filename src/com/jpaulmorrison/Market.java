package com.jpaulmorrison;

import java.util.*;

/**
 * Market object - object is mutable, but only the holiday table for a market will be 
 *   changed on a regular basis (plus the closed indicators); open and close times will 
 *   only be changed at BDT Load time. 
 * @author: Administrator
 */
public class Market {

	
		static class MarketImpl implements IMarket {

	   /**
		 * Returns true if market is open for specified FIType  
		* @return boolean
		 */
	static final String copyright = 
				"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
				"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
				"based on the Everything Development Company's Artistic License.  A document describing " +
				"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
				"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";
	String m_code = null;     // this must be the Reuters code

	String m_timeZone = null;  // alpha TimeZone ID - can be used by TimeStamp methods)
	String m_openTime = "09:30";    // default values
	String m_closeTime = "16:00";   //     same
	String m_countryCode = null;
	String m_quoteCurrency = null;
	HashMap<String, List<?>> m_times = null;           // HashMap of open and close times - key is fiType

	HashMap<?, ?> m_holidays = null;                      // list of holidays - keyed on date
	HashMap<?, ?> m_replHolidays = new HashMap<String, String>();  // used to build new holiday list
	                                                          // not sure if values are Strings or Dates

	
	/**
	 * Returns true if market is open right now for specified FIType  
	 * @return boolean
	 */


	public boolean isOpen(String fiType) {

		TimeStamp ts = new TimeStamp();   //   set timestamp to right now!
		return isOpen(ts, fiType);

	    }

	
	
	/**
	 * Returns true if market is open at specified time and for specified FIType  
	 * @return boolean
	 */


	public boolean isOpen(TimeStamp ts, String fiType) {

	if (m_code.equals("M")) {
		System.err.println("Montreal Exchange forced closed for testing!");
		System.err.println("Remember to correct code later!");
		return false;
	    }

		String open = m_openTime;
		String close = m_closeTime;
		boolean closeInd = false;
		if (m_times != null) {
			List list = (List) m_times.get(fiType);
			closeInd = list.get(0).equals("1");
			open = (String) list.get(1);
			close = (String) list.get(2); 
		    }

		if (closeInd)
		    return false;
		
	
	try {String nowInTZ = ts.formatWithZone(m_timeZone);  // now in market time zone
	
		TimeZone tz = TimeTz.GetTimeZone(m_timeZone);  // get from BDTHelper table
		String date = nowInTZ.substring(0,8);

		Calendar cal = new GregorianCalendar(tz);  // needed to obtain day of week
		cal.setTime(ts);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    HashMap hm = getHolidays();
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY  ||
			    hm != null && null != hm.get(date))  //  or is date in list of holidays? 
		    return false;
		

		TimeStamp tsStart = new TimeStamp(date + 'T' + open + '!' + m_timeZone);
		TimeStamp tsEnd = new TimeStamp(date + 'T' + close + '!' + m_timeZone);
		
		if (ts.before(tsStart) || ts.after(tsEnd))
		    return false;
			
		}
	catch (BDTypeException ex) {
		System.err.println("Date error: " + ex); 
		}

	return true;
	

	
}

   /**
 * Returns true if market is open for specified Date and FI Type (partial dates
 *  will be treated as not open); times will not be checked at all
 * @return boolean
 */
public boolean isOpen(Date date, String fiType)  {
   
	if (m_code.equals("M")) {
		System.err.println("Montreal Exchange forced closed for testing!");
		System.err.println("Remember to correct code later!");
		return false;
	    }


		boolean closeInd = false;
		if (m_times != null) {
			List list = (List) m_times.get(fiType);
			closeInd = list.get(0).equals("1");
		    }

		if (closeInd)
		    return false;
		
	
	try {TimeStamp timeWithinDate = new TimeStamp(date + "T12:00!" + m_timeZone);   
		
		TimeZone tz = TimeTz.GetTimeZone(m_timeZone);  // get from BDTHelper table 
		Calendar cal = new GregorianCalendar(tz);  // needed to obtain day of week
		cal.setTime(timeWithinDate);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		HashMap hm = getHolidays();
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY  ||
			    hm != null && null != hm.get(date))  //  or is date in list of holidays? 
		    return false;
		    		
				
		}
	catch (BDTypeException ex) {
		System.err.println("Date error: " + ex); 
		}

	return true;
	

	
}



   /**
	* Get HashMap of open/close times
	*/ 

   public HashMap<String, List<?>> getTimes() {
	return m_times;
   }

	/**
	* Set HashMap with open/close times
	*/ 

   public void setTimes(HashMap<String, List<?>> hm) {
	m_times = hm;
   }	
   /**
	* Insert the method's description here.
	* Creation date: (9/20/00 2:49:20 PM)
	* @return java.lang.String
	*/
	 public String serialize() {

	String str = m_code + ';' + m_timeZone + ';' + m_countryCode + ';' + 
	        m_quoteCurrency + ';' + '{' ;
	Iterator<?> iter = m_times.keySet().iterator();  
	boolean first = true; 
	while (iter.hasNext()) {
		if (!first)
		    str = str + ';';
		first = false;    
		String key = (String) iter.next();
		List vec = (List) m_times.get(key);
		str = str + key + "={" + vec.get(0) + ';' + vec.get(1) + ';' + vec.get(2) + '}'; 
	    }
	str = str + "}{";

	iter = m_holidays.keySet().iterator();  
	first = true; 
	while (iter.hasNext()) {
		if (!first)
		    str = str + ';';
		first = false;    
		Date d = (Date) iter.next();
		str = str + d.serialize(); 
	    }
	str = str + '}';
	return str;
	
	}

	
	
	MarketImpl(String s1, String s2, String s3, String s4) {
		m_code = s1;
		m_timeZone = s2;
		m_countryCode = s3;
		m_quoteCurrency = s4;
	    }



	public String getTimeZone() {
		return m_timeZone;
	    }

	public synchronized HashMap getHolidays() {
		return m_holidays;
	    }

	public synchronized void setHolidays(HashMap hm) {
		m_holidays = hm;
	    }

	public HashMap<?, ?> getReplHolidays() {
		return m_replHolidays;
	    }

	public void setReplHolidays(HashMap hm) {
		m_replHolidays = hm;
	    }
 
	 }

/**
 * Insert the method's description here.
 * Creation date: (9/22/00 4:09:05 PM)
 * @return com.jpaulmorrison.IMarket
 * @param s1 java.lang.String
 * @param s2 java.lang.String
 * @param s3 java.lang.String
 * @param s4 java.lang.String
 */
static IMarket CreateMarket(String s1, String s2, String s3, String s4) {
	return new MarketImpl(s1, s2, s3, s4);
}
/**
* Return an IMarket object given the name - return null if string empty 
* @return com.jpaulmorrison.Market
* @param s java.lang.String
*/
public static IMarket Get(String s) throws BDTypeException {

	if (s.equals(""))
	    return null;
	else  {
		IMarket mkt = (IMarket) BDTHelper.s_marketTable.get(s);
		if (mkt == null) {
		    // System.err.println("Invalid market code: " + s);
		    throw new BDTypeException("Invalid market code: " + s);
		    }
		return mkt;    
	    }
}
}
