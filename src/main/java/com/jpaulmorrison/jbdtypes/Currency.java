package com.jpaulmorrison.jbdtypes;




/**
 * This class describes a currency type, for example Canadian Dollars.
 * The abbreviation is the ISO code for the currency.
 * <code>precision</code> is the number of decimal places that must be entered or displayed,
 * eg 2 for dollars or pounds, 0 for lire or pesetas, 3 for certain Middle Eastern
 * currencies.  -1 means undefined precision (ie retain results of calculations).
 * <code>currencyTable</code> is a table of currencies, built at class load time -
 * if it has to be changed, change this class definition.  Later this table will be loaded from 
 * a database.
 
 */
 public class Currency implements IBDType {
	 static final String copyright = 
		 "Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		 "distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		 "based on the Everything Development Company's Artistic License.  A document describing " +
		 "this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		 "THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";


	String m_abbrev = "CAD";                // currency abbreviation 
	int m_precision = 2;                    // no. of decimal places

 
/**
 * Currency constructor, taking abbreviation parameter only (must be valid).
 * @throws BDTypeException
 */
public Currency(String abbr) throws BDTypeException {
	super();
	m_abbrev = abbr;
	m_precision = (Currency.Get(abbr)).getPrecision();

}
/**
 * Currency constructor, taking 2 parameters: abbreviation and precision - this is needed to
 *  build currency table. 
 */
 public Currency(String abbr, int prec) {
	super();
	m_abbrev = abbr;
	m_precision = prec;

}
/** Get information about Currency specified by ISO code
* @param c String - ISO code
* @return com.jpaulmorrison.jbdtypes.Currency
* @throws BDTypeException
*/

public static Currency Get (String c) throws BDTypeException {
	    Currency cur = null;
	    if (!c.equals("XXX")) {
	        cur = (Currency) BDTHelper.s_currencyTable.get(c);
	        if (cur == null)
	            throw new BDTypeException("Currency code not valid: " + cur);
	        }    
	    return cur;
	 }
/**
 * Get abbreviation for Currency (ISO code)
 * @return java.lang.String
 */
public String getAbbrev() {
	return m_abbrev;
}
/**
 * Get precision for Currency 
 * @return int
 */
public int getPrecision() {
	return m_precision;
}
/** Generate a String representation of Currency 
 * @return java.lang.String
 */
public String serialize() {
	return m_abbrev;
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}  
