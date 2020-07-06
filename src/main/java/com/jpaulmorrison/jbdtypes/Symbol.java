package jbdtypes;



/**
 * Symbol - composed of market, symbol and country codes - at least one of market and 
 *  country will usually be present - if both are missing, we use the default country
 
 */
public class Symbol implements IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";
	String m_market = "";
	String m_sym;
	String m_country = "";

	
/**
 * Symbol constructor using market;sym;country, where at least one of market and country 
 *  will usually be present - if both are missing, we use the default country
 * @throws BDTypeException
 */
public Symbol(String s) throws BDTypeException {
	
	super();
		
	int sp = s.indexOf(BDT_DELIM);
	if (sp == -1)
	    throw new BDTypeException("Invalid symbol string: " + s);
	else {
		m_market = s.substring(0, sp);
		if (sp > 0) {
		    try {Market.Get(m_market);}   // verify market is valid (added Oct. 3)
		    catch (BDTypeException e) {
			    throw new BDTypeException("Invalid market code in symbol: " + s);
		        }
		    }
		
		String s2 = s.substring(sp + 1);
		sp = s2.indexOf(BDT_DELIM);
	    if (sp == -1)
	        m_sym = s2;
	    else {
		    m_sym = s2.substring(0, sp);
		    m_country = s2.substring(sp + 1 );
		    if (!m_country.equals("") &&
			    !(BDTHelper.s_countryTable.containsKey(m_country)))
				throw new BDTypeException("Invalid country code in symbol: " + s);
		    }
	    }

        if (m_sym.equals(""))
            throw new BDTypeException("Missing symbol within Symbol string: " + s);

	if (m_market.equals("") && m_country.equals(""))
	    m_country = BDTHelper.s_defaultCountry;
}
/**
 * Get country String from object 
 */
public String getCountry() {

	return m_country;

}
/**
 * Get market String from object 
 */
public String getMarket() {

	return m_market;

}
/**
 * Get symbol String from object 
 */
public String getSym() {

	return m_sym;

}
/**
 * Convert object to String
 * @return java.lang.String
 */
public String serialize() {

	String s1 = "";

	if (!m_market.equals(""))
	    s1 = m_market;
	
	s1 = s1 + BDT_DELIM + m_sym; 
	
	if (!m_country.equals(""))
	    s1 = s1 + BDT_DELIM + m_country;

	return s1;    
	 
	    
	     
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
