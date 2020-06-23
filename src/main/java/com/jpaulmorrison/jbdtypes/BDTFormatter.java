package com.jpaulmorrison.jbdtypes;

import java.math.*;

import java.util.TimeZone;
import java.text.SimpleDateFormat;


/**
 * Helper class for formatting Basic Data Types
 */
public class BDTFormatter {

	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	
/**
 * Create a numeric String from a string with optional surrounding blanks
 * @return java.lang.String
 * @param s java.lang.String - string to be analyzed
 */
  public static String EditIntegerPart(String s)  {
		    String targ = "";
	        int len = s.length(); 
	        char [] c = new char[len];        // build new char array 
	        s.getChars(0, len, c, 0);         // move string into char array
	        int i;

	        for (i = 0; i < len; i++) {
		        if (c[i] != ' ')
		            break;
	        	}
	  
	        int si = i;    
	               
			for (i = len - 1; i >= si; i--) {
				
				if (c[i] != ' ')
				    break; 
								
			    }
			targ = s.substring(si, i + 1);

			return targ;
			

}
/**
 * Create a numeric String from a string with possible embedded thousands delimiters or blanks
 * @return java.lang.String
 * @param s java.lang.String - string to be analyzed
 * @param td java.lang.String - thousands delimiter
 * @throws BDTypeException
 */
  public static String EditIntegerPart(String s, String td) throws BDTypeException {
		    String targ = "";
	        int len = s.length(); 
	        char [] c = new char[len];        // build new char array 
	        s.getChars(0, len, c, 0);         // move string into char array
	        int i;

	        for (i = 0; i < len; i++) {
		        if (c[i] != ' ')
		            break;
	        	}
	        if (td.equals("" + c[i]))
	            throw new BDTypeException("Invalid numeric value: " + s);

	        int si = i;    
	               
			for (i = len - 1; i >= si; i--) {
				
				if (c[i] == ' ' || td.equals("" + c[i])) {
					if (targ.length() % 3 != 0)
					    throw new BDTypeException("Invalid numeric value: " + s);   
							
				    }
				    
				
				else 
					targ = "" + c[i] + targ;
					
				
			    }

			return targ;
			

}
/**
 * Format a Monetary value using Cultural Preference variables
 * @return java.lang.String
 * @param  com.jpaulmorrison.jbdtypes.Monetary
 * @param  String td - thousands delimiter (may be null)
 * @param  String dd - decimal delimiter 
 * @param  String cs - currency separator (may be null)
 * @param  String bef - "B" if currency code is before value, "A" if after 
 */
public static String Format(Monetary m, String td, String dd, String cs, String bef) {

	String s = m.serialize();

	String cur = s.substring(0, 3);  // save currency
	s = s.substring(3);            // get rest
	
	int i = s.indexOf('.');

	String d = s.substring(i + 1);   // decimal part of currency
	String targ = "";

	while (true) {

		int j = i - 3;

		if (j < 0)
			j = 0;
			
		targ = s.substring(j, i) + targ;
	   
		
		if (j > 0)
			if (td != null)
			    targ = td + targ;
			else {}    
		else
		    break;	

		i = j;	
	    }

	targ = targ + dd + d;

	if (bef.equals("B"))     // test if currency code is before value 
	
	    if (cs == null)
	        targ = cur + targ;
	    else
	        targ = cur + cs + targ;
	else        

	    if (cs == null)
	        targ = targ + cur;
	    else
	        targ = targ + cs + cur;    

	return targ;
	
}
/**
 * Display an (non-Java.util) TimeStamp using specified masks and timestamp separator
 * @param ts com.jpaulmorrison.jbdtypes.TimeStamp
 * @param dm java.lang.String - date mask
 * @param tm java.lang.String - time mask
 * @param tss java.lang.String - timestamp separator
 * @param zone java.lang.String - time zone - may be alpha or +/-hh:mm,
 *   where zone specified is from BDTHelper list, not from java.util.TimeZone 		       		       
 * @return java.lang.String
 * @throws BDTypeException
 */
public static String Format(TimeStamp ts, String dm, String tm, String tss, 
	       String zone) throws BDTypeException {

		  
	  long millis = ts.getTime();
	  TimeStamp ts2 = new TimeStamp(millis);

	  if (zone.charAt(0) == '+' || zone.charAt(0) == '-') {
		  int hrs;  
	 	  int mins = 0;
	
		  if (zone.length() == 3)
		     hrs = Integer.parseInt(zone.substring(1));
		  else {    
	          if (zone.charAt(3) != ':' || zone.length() != 6) 
	              throw new BDTypeException("Invalid TimeStamp adjustment: " + zone);
	          hrs = Integer.parseInt(zone.substring(1,3));
		      mins = Integer.parseInt(zone.substring(4,6));
		      }
	     
		  if (zone.charAt(0) == '-') {
			  hrs = - hrs;
			  mins = - mins;
		      }
		 		     
		  millis += (3600 * hrs + 60 * mins) * 1000;
		  ts2.setTime(millis);
	  	  }
	  
	  else {
		  
	 	  TimeZone tz = TimeTz.GetTimeZone(zone);
	  	  if (tz == null) throw new BDTypeException("Time Zone not found: " + zone);
	  	  millis += tz.getRawOffset();
	  	  ts2.setTime(millis);
	  	  if (tz.inDaylightTime(ts2)) 
	          ts2.setTime(millis + 3600000);          // assume dst savings = 1 hr (1.1)
	          
	  	  }     

 	 
	  TimeZone zon2 = TimeZone.getTimeZone("UTC");  // java.util.zone

	  SimpleDateFormat formatter
		  = new SimpleDateFormat(dm);
	 
	  formatter.setTimeZone(zon2);
	
	  String s1 = formatter.format(ts2);
	  formatter = new SimpleDateFormat(tm);
		 
	  formatter.setTimeZone(zon2);
	
	  String s2 = formatter.format(ts2);

	  String s = s1 + tss + s2 + ' ' + zone;

	  return s;
	         
}
/**
 * Make a BigDecimal object from a string with possible embedded delimiters or blanks
 * @return java.math.BigDecimal
 * @param s java.lang.String
 * @param td java.lang.String - thousands delimiter (may be null)
 * @param dd java.lang.String - decimal delimiter
 * @throws BDTypeException
 */
  public static BigDecimal MakeDecimal (String s, String td, String dd) 
		  throws BDTypeException {
			String intPart;
			String decs = null;
		    int sp = s.indexOf(dd);
		    if (sp != -1) {
		        intPart = s.substring(0,sp);
		        decs = s.substring(sp + 1);
		    	}
		    else
		        intPart = s;

		    try {
			    if (td == null)
		           intPart = EditIntegerPart(intPart);
		        else    
		            intPart = EditIntegerPart(intPart, td);

		        if (decs == null)
		            decs = intPart;
		        else    
		            decs = intPart + '.' + EditIntegerPart(decs);  
		    	}
		    catch (BDTypeException e) {
			    throw new BDTypeException("Invalid numeric value: " + s);
		    	}
		        
			BigDecimal bd = null; 
			try { bd = new BigDecimal(decs);}
			catch (NumberFormatException e) {
				throw new BDTypeException("Invalid numeric value: " + s);
			    }

			return bd;
			

}
/**
 * Make a Monetary object from a string with possible embedded delimiters or blanks;
 *  currency abbreviation must also be provided.  
 * @return com.jpaulmorrison.jbdtypes.Monetary
 * @param s java.lang.String
 * @param td java.lang.String - thousands delimiter (may be null)
 * @param dd java.lang.String - decimal delimiter
 * @param cur java.lang.String - currency abbreviation
 * @throws BDTypeException
 */
  public static Monetary MakeMonetary(String s, String td, String dd, String cur) 
		  throws BDTypeException {
			
			Currency c = Currency.Get(cur);
			
		    BigDecimal bd = MakeDecimal (s, td, dd);
		    
			return new Monetary(bd, c);
			

}
/**
 * Make an MPrice object from a string with possible embedded delimiters or blanks;
 *  currency abbreviation must also be provided.  If a slash is present, the
 *  numerator immediately precedes the slash, and the denominator follows.
 * If no slash is present, there may be a decimal delimiter.
 * The portion before the numerator is the integer part.
 * @return com.jpaulmorrison.jbdtypes.MPrice
 * @param s java.lang.String
 * @param td java.lang.String - thousands delimiter (may be null)
 * @param dd java.lang.String - decimal delimiter
 * @param cur java.lang.String - currency abbreviation
 * @throws BDTypeException
 */
  public static MPrice MakeMPrice(String s, String td, String dd, String cur) 
		  throws BDTypeException {
			
			int parts[] = {0, 0, 0};
			Currency c = Currency.Get(cur);
			int sp;
			int ss = s.indexOf('/');
			if (ss != -1) {
				if (s.indexOf(dd) != -1)
				    throw new BDTypeException("Decimal delimiter and slash both present:" +
					    s);
				int sb;
				for (sb = ss - 1; sb >= 0; sb--) {
					if (s.charAt(sb) != ' ') break;
					}
				sp = s.lastIndexOf(' ', sb);

				// parts are: integer part, numerator, denominator
				parts[0] = Integer.parseInt(EditIntegerPart(s.substring(0, sp), td));
				parts[1] = Integer.parseInt(s.substring(sp + 1, sb + 1));
				parts[2] = Integer.parseInt(EditIntegerPart(s.substring(ss + 1)));
				// denominator must be a power of 2!
				BigDecimal bd2 = new BigDecimal(parts[1]);
				bd2 = bd2.divide(new BigDecimal(parts[2]), BDTHelper.s_mathContext);
											
				return new MPrice(parts, c); 
				
				}
			 
		    BigDecimal bd = MakeDecimal (s, td, dd);
		    
			return new MPrice(bd, c);
			

}
}
