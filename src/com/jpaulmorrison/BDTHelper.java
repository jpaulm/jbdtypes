package com.jpaulmorrison;

import java.math.*;

import java.util.*;


/**
 * This is a helper class supporting the other classes in this package
 * @author: Administrator
 */


   public class BDTHelper {
	   static final String copyright = 
		   "Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		   "distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		   "based on the Everything Development Company's Artistic License.  A document describing " +
		   "this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		   "THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";


	static final int EQ = 1;   // equal
	static final int LT = 2;   // less than
	static final int LE = 3;   // less than or equal
	static final int GT = 4;   // greater than
	static final int GE = 5;   // greater than or equal
	static final int NE = 6;   // not equal

	// Define a MathContext object providing 20 digits
	public static MathContext s_mathContext = new MathContext(20);

	static HashMap<String, String> s_countryTable = null;

	static {
		s_countryTable = new HashMap<String, String>();

	    s_countryTable.put("CA", null);
	    s_countryTable.put("US", null);

	    }

	static String s_defaultCountry = "CA";   //  Canada is default
	

	// The following tables will be loaded from a database 

	static HashMap<String, Currency> s_currencyTable = null;  // table of currencies

	static {

	    s_currencyTable = new HashMap<String, Currency>();

		   // Precision of -1 means undefined precision
	
	    AddCurrency("CAD", 2);   // Canadian dollars
	    	
	    AddCurrency("USD", 2);   // US dollars
	    
	    AddCurrency("GBP", 2);   // Great Britain Pounds
	    
	    AddCurrency("FRF", 2);   // French Francs
	    
	    AddCurrency("ITL", 0);   // Italian Lire 
	   
	    AddCurrency("BHD", 3);   // Bahraini Dinar
	   
		AddCurrency("XAU", -1);   // Euro Gold 
	    
	    } 


	static HashMap<String, IMarket> s_marketTable = new HashMap<String, IMarket>();  // table of markets

	

	// The following table will be loaded from a database - but not before DCUT3

	static HashMap<String, SimpleTimeZone> s_timeZoneTable = null;  // table of time zones

	static {

	    s_timeZoneTable = new HashMap<String, SimpleTimeZone>();

	    SimpleTimeZone tz;

	    tz = AddTimeZone("UTC", 0.0);    // UTC - same as GMT
	    tz = AddTimeZone("GMT", 0.0);    // UTC - same as GMT

	    tz = AddTimeZone("EST", -5.0);    // Eastern Standard Time
	    tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2*60*60*1000);
	    tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2*60*60*1000);
	    
	    tz = AddTimeZone("NST", -3.5);    // Newfoundland
	    tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2*60*60*1000);
	    tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2*60*60*1000);

	    tz = AddTimeZone("AST", -4.0);    // Atlantic Standard Time
	    tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2*60*60*1000);
	    tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2*60*60*1000);

	    tz = AddTimeZone("CST", -6.0);    // Central Standard Time
	    tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2*60*60*1000);
	    tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2*60*60*1000);

	    tz = AddTimeZone("SST", -6.0);    // Central Standard Time (Sask) - no DST

	    tz = AddTimeZone("MST", -7.0);    // Mountain Standard Time
	    tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2*60*60*1000);
	    tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2*60*60*1000);

	    tz = AddTimeZone("PST", -8.0);    // Pacific Standard Time
	    tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2*60*60*1000);
	    tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2*60*60*1000);
	    
	    AddTimeZone("JST", 9.0);  //  Japan standard time - no daylight savings time


	    }


/**
 *  Dummy constructor for BDTHelper - ensures no instance can be built
 */

	private BDTHelper() {}
/**
 * Add a currency to s_currencyTable (at initialization time)
 * @param s java.lang.String
 * @param precision int
 */
public static void AddCurrency(String s, int precision) {
	 Currency cur = new Currency(s, precision); 
	    s_currencyTable.put(s, cur);
	}
/**
 * Add a Market to s_marketTable (at initialization time)
 * @param s1 java.lang.String  - code
 * @param s2 java.lang.String  - timezone
 * @param s3 java.lang.String  - country_code
 * @param s4 java.lang.String  - quote_currency
 */
public static void AddMarket(String s1, String s2, String s3, String s4) {
	 IMarket mkt = Market.CreateMarket(s1, s2, s3, s4);
	 mkt.setHolidays(null);	

	 s_marketTable.put(s1, mkt);
	}
/**
 * Add a Market to s_marketTable (at initialization time)
 * @param s1 java.lang.String  - code
 * @param s2 java.lang.String  - timezone
 * @param s3 java.lang.List  - vector of close indicator, open time, close time
 */
public static void AddMarketTimes(String s1, String s2, List<?> list) {
		
		IMarket mkt = Market.Get(s1);

		HashMap<String, List<?>> hm = null;

		hm = mkt.getTimes();
		if (hm == null) {
			hm = new HashMap<String, List<?>>();
			mkt.setTimes(hm);
		    }
		hm.put(s2, list);
			
	}
/**
 * Add a java.util.SimpleTimeZone
 * @param String - identifier of time zone
 * @param double - hours to be added to UTC to get local time (may be fractional)
 * @return SimpleTimeZone
 */
private static SimpleTimeZone AddTimeZone(String id, double off) {

	    int offset = (new Double(off * 3600000)).intValue();

		SimpleTimeZone tz =  new SimpleTimeZone(offset, id);

		s_timeZoneTable.put(id, tz);

		return tz;

	}
/**
 * Use value (BigDecimal) to calculate vulgar fraction, and set fields.
 * This logic uses the actual number of digits to the right of the
 *  decimal point.  Thus .50 will be expressed as 2/4, rather than 1/2.
 * @return int[]
 * @param bd java.math.BigDecimal
 * @throws BDTypeException
 */
public static int[] CalcVulgarFrac(BigDecimal bd) throws BDTypeException {

	int[] parts = {0,0,0};
	parts[0] = bd.intValue();
	BigDecimal temp = new BigDecimal(parts[0]);
	BigDecimal temp2 = bd.subtract(temp, BDTHelper.s_mathContext);
	String s = temp2.toPlainString();
	int len = s.length() - s.indexOf('.') - 1;  // determine no. of places of decimals
	BigDecimal two = new BigDecimal(2);
	BigDecimal pow = two.pow(len);                        // we now have correct power of 2
	parts[2] = pow.intValue();
	pow = pow.multiply(temp2, BDTHelper.s_mathContext);
	try {parts[1] = pow.intValueExact();}
	catch (ArithmeticException e){
		parts[2] = 0;    // reset denominator, so value will display as decimal
		throw new BDTypeException(
			"Fractional value not divisible by power of 2: " + bd); 
	    };
	return parts;
	
	}

/**
 * Generalized compare for exchange rates
 * @return boolean
 * @param x ExchRate - first exchange rate
 * @param y ExchRate - second exchange rate
 * @param op int - desired relationship (less than, equal, not equal, etc.)
 * @throws BDTypeException
 */
static boolean Comp(ExchRate x, ExchRate y, int op) throws BDTypeException{
	    if (x.m_sourceCurrency != y.m_sourceCurrency)
	        throw new BDTypeException("Source currency mismatch: " +
	        x.m_sourceCurrency.getAbbrev() + ", " + y.m_sourceCurrency.getAbbrev());
		if (x.m_targetCurrency != y.m_targetCurrency)
	        throw new BDTypeException("Target currency mismatch: " +
		    x.m_targetCurrency.getAbbrev() + ", " + y.m_targetCurrency.getAbbrev());
	    switch (op) {
		 case EQ: return (0 == x.m_value.compareTo(y.m_value));
		 case NE: return (0 != x.m_value.compareTo(y.m_value));
		 case LT: return (-1 == x.m_value.compareTo(y.m_value));
		 case LE: return (1 != x.m_value.compareTo(y.m_value));
		 case GT: return (1 == x.m_value.compareTo(y.m_value));
		 case GE: return (-1 != x.m_value.compareTo(y.m_value));
		 default: throw new BDTypeException("Unknown compare operator: " + op);
		 }

}
/**
 * Generalized compare for monetary amounts
 * @return boolean
 * @param x Monetary - first monetary amount
 * @param y Monetary - second monetary amount
 * @param op int - desired relationship (less than, equal, not equal, etc.)
 * @throws BDTypeException
 */
  static boolean Comp(Monetary x, Monetary y, int op) throws BDTypeException{
		if (x.m_currency != y.m_currency)
	      throw new BDTypeException("Currency mismatch: " +
		      x.getCurrAbbr() + ", " + y.getCurrAbbr());
	    switch (op) {
		 case EQ: return (0 == x.m_value.compareTo(y.m_value));
		 case NE: return (0 != x.m_value.compareTo(y.m_value));
		 case LT: return (-1 == x.m_value.compareTo(y.m_value));
		 case LE: return (1 != x.m_value.compareTo(y.m_value));
		 case GT: return (1 == x.m_value.compareTo(y.m_value));
		 case GE: return (-1 != x.m_value.compareTo(y.m_value));
		 default: throw new BDTypeException("Unknown compare operator: " + op);
		 }
  }  
/**
 * Generalized compare for Percents
 * @return boolean
 * @param x Percent - first Percent
 * @param y Percent - second Percent
 * @param op int - desired relationship (less than, equal, not equal, etc.)
 * @throws BDTypeException
 */
static boolean Comp(Percent x, Percent y, int op) throws BDTypeException{

	    switch (op) {
		 case EQ: return (0 == x.compareTo(y ));
		 case NE: return (0 != x.compareTo(y ));
		 case LT: return (-1 == x.compareTo(y ));
		 case LE: return (1 != x.compareTo(y));
		 case GT: return (1 == x.compareTo(y ));
		 case GE: return (-1 != x.compareTo(y ));
		 default: throw new BDTypeException("Unknown compare operator: " + op);
		 }

}

/**
  * Generalized compare for BigDecimal
 * @return boolean
 * @param x java.math.BigDecimal - first BigDecimal
 * @param y java.math.BigDecimal - second BigDecimal
 * @param op int - desired relationship (less than, equal, not equal, etc.)
 * @throws BDTypeException
 */

 static boolean Comp(BigDecimal x, BigDecimal y, int op) throws BDTypeException{
	    switch (op) {
		 case EQ: return (0 == x.compareTo(y));
		 case NE: return (0 != x.compareTo(y));
		 case LT: return (-1 == x.compareTo(y));
		 case LE: return (1 != x.compareTo(y));
		 case GT: return (1 == x.compareTo(y));
		 case GE: return (-1 != x.compareTo(y));
		 default: throw new BDTypeException("Unknown compare operator: " + op);
		 }

}
/**
 * Create an IPrice (MPrice or PCPrice) from a string containing 
 *   code/currency, value and 'P', 'V' or ' ' - all concatenated together.
 * If final character is absent, decide based on first 3 chars (code/currency).  
 * @return IPrice
 * @param s java.lang.String
 * @throws BDTypeException
 */
public static IPrice CreatePrice(String s) throws BDTypeException {
	String c = s.substring(0, 3);
	String v = s.substring(3);
	int len = v.length();
	
	String last = v.substring(len - 1, len);
	
	if (last.equals("P") || !last.equals(" ") && !last.equals("V") && 
		     PCPrice.s_types.containsKey(c)) 
	    return new PCPrice(c + v);
	else
	    return new MPrice(c + v); 


	
}
/**
 * Insert the method's description here.
 * Creation date: (9/27/00 6:02:51 PM)
 * @return com.sun.java.util.collections.HashMap
 */
public static HashMap GetMarkets() {
	return s_marketTable;
}
}
