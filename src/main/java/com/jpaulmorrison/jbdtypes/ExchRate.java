package com.jpaulmorrison.jbdtypes;
import java.math.*;






/**
 * Immutable class describing an Exchange Price - must specify two currencies and a timestamp
 
 */

   final public class ExchRate  implements IBDType {
	   static final String copyright = 
		   "Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		   "distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		   "based on the Everything Development Company's Artistic License.  A document describing " +
		   "this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		   "THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";


	BigDecimal m_value;  //value of Exchange rate object - package scope

	Currency m_sourceCurrency;  // source currency - package scope
	Currency m_targetCurrency;  // target currency - package scope
	TimeStamp m_timeStamp;      // to millisecond - if more precision needed, use java.sql
	 
/**
 * Exchange Price constructor with value initialized from BigDecimal
 */
 public ExchRate(BigDecimal bd, Currency scur, Currency tcur, TimeStamp ts) {
	super();
	m_sourceCurrency = scur;
	m_targetCurrency = tcur;

	m_value = bd;
	m_timeStamp = ts;
	
}
/**
 * Exchange Price constructor using single String, containing
 *  value, source currency, target currency, optional timestamp, separated
 *  by semicolons.
 * @throws BDTypeException
 */
 public ExchRate(String s) throws BDTypeException {
	super();
	int sp = s.indexOf(BDT_DELIM);
	if (sp == -1) throw new BDTypeException("Invalid ExchRate String: " + s);
	m_value = new BigDecimal(s.substring(0, sp));
	String rest = s.substring(sp + 1);
	
	sp = rest.indexOf(BDT_DELIM);
	if (sp != 3) throw new BDTypeException("Invalid ExchRate String: " + s);
	String scur = rest.substring(0, 3);
	m_sourceCurrency = Currency.Get(scur);
	rest = rest.substring(4);

	if (rest.length() < 3) 
	    throw new BDTypeException("Invalid ExchRate String: " + s);

	String tcur = rest.substring(0, 3);
	m_targetCurrency = Currency.Get(tcur);  
	
	if (rest.length() > 3) { 
	
	    if (rest.charAt(3) != BDT_DELIM)
	        throw new BDTypeException("Invalid ExchRate String: " + s);
	    m_timeStamp = new TimeStamp(rest.substring(4));
		}

	if (m_sourceCurrency == null)
	    throw new BDTypeException("Unknown Source Currency: " + s);

	if (m_targetCurrency == null)
	    throw new BDTypeException("Unknown Target Currency: " + s);    
}
/**
 * Exchange Price constructor with value initialized from 4 Strings 
 *   (TimeStamp will use serialized form)
 * @throws BDTypeException
 */
 public ExchRate(String bd, String scur, String tcur, String ts) 
	 throws BDTypeException{
	
	m_sourceCurrency = Currency.Get(scur);
	m_targetCurrency = Currency.Get(tcur);

	m_value = new BigDecimal(bd);
	m_timeStamp = new TimeStamp(ts);

	if (m_sourceCurrency == null)
	    throw new BDTypeException("Unknown Source Currency: " + scur);

	if (m_targetCurrency == null)
	    throw new BDTypeException("Unknown Target Currency: " + tcur);    
	
}
/**
 * Convert a Monetary amount using this Exchange Rate, giving another Monetary amount of 
 * the target currency type
 * This will only work if the currency of the Monetary amount matches the source
 *  currency of the Exchange Rate
 * @return jbdtypes.Monetary
 * @param m jbdtypes.Monetary
 * @throws BDTypeException
 */
public Monetary convert(Monetary m) throws BDTypeException{
	return m.convert(this);
}
/**
 * Compare to see if this exchange rate is equal to specified one
 * @return boolean
 * @param y jbdtypes.ExchRate - second exchange rate
 * @throws BDTypeException
 */
public boolean eq(ExchRate y) throws BDTypeException{
		return BDTHelper.Comp(this, y, BDTHelper.EQ);
}
/**
 * Compare to see if this exchange rate is greater than or equal to specified one
 * @return boolean
 * @param y jbdtypes.ExchRate - second exchange rate
 * @throws BDTypeException
 */
public boolean ge(ExchRate y) throws BDTypeException{
		return BDTHelper.Comp(this, y, BDTHelper.GE);
}
/**
 * Get Source Currency
 * @return jbdtypes.Currency
 */
public Currency getSourceCurrency() {
	return m_sourceCurrency;
}
/**
 * Get Target Currency
 * @return jbdtypes.Currency
 */
public Currency getTargetCurrency() {
	return m_targetCurrency;
}
/**
 * Get TimeStamp
 * @return jbdtypes.TimeStamp
 */
public TimeStamp getTimeStamp() {
	return m_timeStamp;
}
/**
 * Get the value from an Exchange Rate
 * @return java.math.BigDecimal
 */
public BigDecimal getValue() {
	return m_value;
}
/**
 * Compare to see if this exchange rate is greater than specified one
 * @return boolean
 * @param y jbdtypes.ExchRate - second exchange rate
 * @throws BDTypeException
 */
public boolean gt(ExchRate y) throws BDTypeException{
		return BDTHelper.Comp(this, y, BDTHelper.GT);
}
/**
 * This method inverts an Exchange Rate - a new Exchange Rate object is created with
 *  the currencies interchanged, and with a new value which is the reciprocal of this one 
 * @return jbdtypes.ExchRate
 */
public ExchRate invert() {
	BigDecimal temp = BigDecimal.ONE.divide(m_value, BDTHelper.s_mathContext);
	return new ExchRate(temp, m_targetCurrency, m_sourceCurrency, m_timeStamp);
}
/**
 * Compare to see if this exchange rate is less than or equal to specified one
 * @return boolean
 * @param y jbdtypes.ExchRate - second exchange rate
 * @throws BDTypeException
 */
public boolean le(ExchRate y) throws BDTypeException{
		return BDTHelper.Comp(this, y, BDTHelper.LE);
}
/**
 * Compare to see if this exchange rate is less than specified one
 * @return boolean
 * @param y jbdtypes.ExchRate - second exchange rate
 * @throws BDTypeException
 */
public boolean lt(ExchRate y) throws BDTypeException{
		return BDTHelper.Comp(this, y, BDTHelper.LT);
}
/**
 * Compare to see if this exchange rate is not equal to specified one
 * @return boolean
 * @param y jbdtypes.ExchRate - second exchange rate
 * @throws BDTypeException
 */
public boolean ne(ExchRate y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.NE);
}
/**
 * This method combines this Exchange Rate with another Exchange Rate, resulting in
 * a new Exchange Rate object.  To construct this, the target currency of 
 * this Exchange Rate is first compared with the source currency of the parameter.  If
 * they match, a new Exchange Rate object is created whose target is the target of the 
 * parameter, and whose source is the source of this Exchange Rate.  The value will be the
 * product of the two exchange rates, and the timeStamp will be the older of the two.
 * If the targets of the two Exchange Rates are the same, the parameter Exchange Rate is
 * converted using the <code>invert()</code> method first.  Any other combinations are
 * rejected.
 * @return jbdtypes.ExchRate
 * @param x exchange rate
 * @throws BDTypeException
 */
public ExchRate propagate(ExchRate x) throws BDTypeException{
	TimeStamp date;
	if (this.m_timeStamp.before(x.m_timeStamp)) date = this.m_timeStamp;
	   else date = x.m_timeStamp;
	if (this.m_targetCurrency == x.m_sourceCurrency)
	    return new ExchRate(this.m_value.multiply(x.m_value, BDTHelper.s_mathContext), 
		    this.m_sourceCurrency, x.m_targetCurrency, date);
	   else 
		  if (this.m_targetCurrency == x.m_targetCurrency) {
	          ExchRate temp = x.invert();
	          return new ExchRate(this.m_value.multiply(temp.m_value,
		             BDTHelper.s_mathContext),
		          this.m_sourceCurrency, temp.m_targetCurrency, date);
			  }
		  else throw new BDTypeException("Currency mismatch - sources: " +
			  this.m_sourceCurrency.getAbbrev() + ", " + x.m_sourceCurrency.getAbbrev() +
			  "; targets: " +
			  this.m_targetCurrency.getAbbrev() + ", " + x.m_targetCurrency.getAbbrev());
	
}
/**
 * Generate a 'preference neutral' string from Exchange Rate.
 * The resulting String will be of the form:
 * value, semicolon, sourceCurrency, semicolon, targetCurrency, optionally followed by 
 *   semicolon and timestamp   
 * @return java.lang.String
 */
public String serialize() {
	
	m_value = m_value.divide(BigDecimal.ONE, BDTHelper.s_mathContext);
	String s1 = m_value.toString();
	s1 = s1 +  BDT_DELIM + m_sourceCurrency.m_abbrev + BDT_DELIM + m_targetCurrency.m_abbrev;
	if (m_timeStamp != null) s1 = s1 + BDT_DELIM + m_timeStamp.serialize();
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
