package com.jpaulmorrison.jbdtypes;


import java.math.*;


/**
 * Immutable class of monetary amounts - must have a currency specified.
 * Note: this class can't extend BigDecimal as we don't want to pick up
 *   BigDecimal's methods! 
 
 */

   public class Monetary  implements IBDType {
	   static final String copyright = 
		   "Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		   "distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		   "based on the Everything Development Company's Artistic License.  A document describing " +
		   "this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		   "THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";


	BigDecimal m_value;  // value of Monetary object - package scope
	Currency m_currency;  // currency of Monetary object - package scope    
   
   
/**
 * Monetary constructor with no parameters - package restricted.
 */
  Monetary() {
	
}
/**
 * Monetary constructor with value initialized from BigDecimal
 */
 public Monetary(BigDecimal bd, Currency cur) {
	super();
	m_currency = cur;
	m_value = bd;
}
/**
 * Monetary constructor using currency + value in String, eg CAD25.74
 * @throws BDTypeException
 */
 public Monetary(String s) throws BDTypeException {
	super();
        if (s.length() < 4)
            throw new BDTypeException("Invalid Monetary String: " + s);
	String c = s.substring(0, 3);  // currency code
	String v = s.substring(3);     // rest of string
	
	m_currency = Currency.Get(c);
	m_value = new BigDecimal(v.toCharArray());
	
}
/**
 * Monetary constructor using value in String, eg 25.74, and separate
 *    String for Currency
 * @throws BDTypeException
 */

 
 public Monetary(String numdata, String cur) throws BDTypeException {
	super();

	m_currency = Currency.Get(cur);
	m_value = new BigDecimal(numdata);
}
   /**
   * add value of Monetary object to this Monetary object, creating another one
   * @return com.jpaulmorrison.jbdtypes.Monetary 
   * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
   * @throws BDTypeException
   */
   
   public Monetary add(Monetary y) throws BDTypeException {

	    if (this.m_currency != y.m_currency || y.m_currency == null)
	      throw new BDTypeException("Currency mismatch: " + 
		       this.getCurrAbbr() + ", " + y.getCurrAbbr());
	    Monetary m = new Monetary(BigDecimal.ZERO, this.m_currency);  
		m.m_value = this.m_value.add(y.m_value, BDTHelper.s_mathContext);
		return m;           
		}
/**
* Convert this Monetary amount using an Exchange Rate, giving another Monetary amount of
* the target currency type.
 * This will only work if the currency of the Monetary amount matches the source
 *  currency of the Exchange Rate
 * @return com.jpaulmorrison.jbdtypes.Monetary 
 * @param x com.jpaulmorrison.jbdtypes.ExchRate
 * @throws BDTypeException
 */
public Monetary convert(ExchRate x) throws BDTypeException {
	    if (this.m_currency != x.m_sourceCurrency || this.m_currency == null)
		        throw new BDTypeException("Currency mismatch: " + this.getCurrAbbr() + 
		        ", " + x.m_sourceCurrency.getAbbrev());
	    	
		Monetary m = new Monetary(BigDecimal.ZERO, x.m_targetCurrency);
		m.m_value = this.m_value.multiply(x.m_value, BDTHelper.s_mathContext);
		return m;      
}
   /**
   * Divide value of <code>this</code> Monetary object by Monetary object, creating BigDecimal
   * @return java.math BigDecimal 
   * @param y com.jpaulmorrison.jbdtypes.Monetary - second Monetary amount
   * @throws BDTypeException
   */
   
   public BigDecimal divide(Monetary y) throws BDTypeException {

	    if (this.m_currency != y.m_currency || y.m_currency == null)
	      throw new BDTypeException("Currency mismatch: " + 
		       this.getCurrAbbr() + ", " + y.getCurrAbbr());
	    BigDecimal bd = this.m_value.divide(y.m_value, BDTHelper.s_mathContext);
		return bd;           
		}

/**
 * Divide this Monetary amount by a BigDecimal quantity, creating a new Monetary
 * @return com.jpaulmorrison.jbdtypes.Monetary 
 * @param x java.math.BigDecimal
 * @throws com.jpaulmorrison.jbdtypes.BDTypeException
 */
public Monetary divide(Quantity x) throws BDTypeException {
	    if (m_currency == null)
	      throw new BDTypeException("Unknown currency");
		Monetary m = new Monetary(BigDecimal.ZERO, this.m_currency);
		m.m_value = this.m_value.divide(x.qty, BDTHelper.s_mathContext);
		return m;      
}
/**
 * Divide this Monetary amount by a BigDecimal quantity, creating an MPrice
 * @return com.jpaulmorrison.jbdtypes.MPrice
 * @param x java.math.BigDecimal
 * @throws com.jpaulmorrison.jbdtypes.BDTypeException
 */
public MPrice deriveMPrice(Quantity x) throws BDTypeException {
	    if (m_currency == null)
	      throw new BDTypeException("Unknown currency");
		MPrice m = new MPrice(BigDecimal.ZERO, this.m_currency);
		m.m_value = this.m_value.divide(x.qty, BDTHelper.s_mathContext);
		return m;      
}
/**
 * Compare to see if monetary amount is equal to this one
 * @return boolean
 * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
 * @throws BDTypeException
 */
public boolean eq(Monetary y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.EQ);
}
/**
 * Compare to see if this monetary amount is greater than or equal to specified one
 * @return boolean
 * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
 * @throws BDTypeException
 */
public boolean ge(Monetary y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.GE);
}
/**
 * Return the abbreviation for the Currency object.
 * @return java.lang.String
 */
String getCurrAbbr() {
	
	if (m_currency == null)
	    return "XXX";
	else    
	    return m_currency.getAbbrev();
}
/**
 * Get currency from Monetary value
 * @return com.jpaulmorrison.jbdtypes.Currency
 */
public Currency getCurrency() {
	return m_currency;
}
/**
 * Compare to see if this monetary amount is greater than specified one
 * @return boolean
 * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
 * @throws BDTypeException
 */
public boolean gt(Monetary y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.GT);
}
/**
 * Check if value is positive (> 0)
 * @return boolean
 */
  public boolean isPositive() {
		if (m_currency == null)
	      throw new BDTypeException("Unknown currency");
	  
	return +1 == m_value.compareTo(BigDecimal.ZERO);  
	    
}
/**
 * Compare to see if this monetary amount is less than or equal to specified one
 * @return boolean
 * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
 * @throws BDTypeException
 */
public boolean le(Monetary y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.LE);
}
/**
 * Compare to see if this monetary amount is less than specified one
 * @return boolean
 * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
 * @throws BDTypeException
 */
public boolean lt(Monetary y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.LT);
}
/**
 * Multiply this Monetary amount by a Percent Price, returning a Monetary amount 
 * @return com.jpaulmorrison.jbdtypes.Monetary
 * @param x com.jpaulmorrison.jbdtypes.PCPrice
 */

		
public Monetary multiply(PCPrice x) {
	
		return x.multiply(this);      
}

/**
 * Multiply this Monetary amount by a BigDecimal, giving another Monetary amount
 * @return com.jpaulmorrison.jbdtypes.Monetary 
 * @param x java.math.BigDecimal
 * @throws com.jpaulmorrison.jbdtypes.BDTypeException
 */
public Monetary multiply(Quantity x) throws BDTypeException {
	
	if (m_currency == null)
	      throw new BDTypeException("Unknown currency");	
	return new Monetary(this.m_value.multiply(x.qty, BDTHelper.s_mathContext), this.m_currency);
	   
}
/**
 * Compare to see if a monetary amount is unequal to this one
 * @return boolean
 * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
 * @throws BDTypeException
 */
public boolean ne(Monetary y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.NE);
}
/**
 * Method to adjust precision of Monetary amount to that specified in
 * Currency object, rounding as determined by active MathContext.
 * If precision is -1, value is unchanged.
 */
public void round() throws BDTypeException{

	if (m_currency == null)
	      throw new BDTypeException("Unknown currency");
	
	if (m_currency.m_precision >= 0) {
	    /* 
	    Old code:
	    int exp = m_currency.m_precision + 1;
	    BigDecimal power = BigDecimal.TEN.pow(exp, BDTHelper.s_mathContext);
	    BigDecimal prod = m_value.multiply(power, BDTHelper.s_mathContext);
	    long prod2 = prod.longValue();
	    BigDecimal prod3 = new BigDecimal(prod2);
	    RoundingMode rounding = BDTHelper.s_mathContext.getRoundingMode();
	    m_value = prod3.divide(power, m_currency.m_precision, rounding);
	    New code:
	    */
	    
	    int scale = m_value.scale();
	    int prec = m_value.precision();
	    int x = prec - scale + m_currency.m_precision;
	    MathContext mc1 = new MathContext(x, BDTHelper.s_mathContext.getRoundingMode());
	    m_value = m_value.round(mc1);
	    }
	
	
	}
/**
 * Generate a 'preference neutral' string from Monetary value.
 * Value will be preceded by Currency abbreviation 
 * @return java.lang.String
 */
public String serialize() {

	int precision = -1;
	Monetary m1 = new Monetary(m_value, m_currency);
	if (m_currency != null)
	    precision =  m_currency.m_precision;
	
	if (precision != -1) 
		m1.round();
	String s1 = m1.m_value.toPlainString();
	
	if (precision > 0) {
		int i = s1.indexOf('.');
		if (i == -1) {
			i = s1.length();
			s1 += ".";
		}
		s1 += "000000000000";
		s1 = s1.substring(0, i + precision + 1);
		
	}
	    
	return  getCurrAbbr() + s1;
}
/**
 * Create new Monetary with new currency - can only be done if currency is unknown (== null)
 * @param cur com.jpaulmorrison.jbdtypes.Currency
 * @return Monetary
 * @throws com.jpaulmorrison.jbdtypes.BDTypeException
 */
public Monetary setCurrency(Currency cur) throws BDTypeException{
	
	if (m_currency != null) 
	    throw new BDTypeException("Trying to change currency "  +
		      this.getCurrAbbr() + " to " + cur.getAbbrev());
	return new Monetary(m_value, cur);
	
	}
/**
 * Create new Monetary with new currency - can only be done if currency is unknown (== null)
 * @param s java.lang.String
 * @return Monetary
 * @throws com.jpaulmorrison.jbdtypes.BDTypeException
 */
public Monetary setCurrency(String s) throws BDTypeException {
	
	if (m_currency != null) 
	    throw new BDTypeException("Trying to change currency "  +
		      this.getCurrAbbr() + " to " + s);
	return new Monetary(m_value, Currency.Get(s));    
	
	
	}
	/**
   * subtract value of Monetary object from this Monetary object, creating another one
   * @return com.jpaulmorrison.jbdtypes.Monetary 
   * @param y com.jpaulmorrison.jbdtypes.Monetary - second monetary amount
   * @throws BDTypeException
   */
   
   public Monetary subtract(Monetary y) throws BDTypeException{

	    if (this.m_currency != y.m_currency || y.m_currency == null)
	      throw new BDTypeException("Currency mismatch: "  +
		      this.getCurrAbbr() + ", " + y.getCurrAbbr());
	    Monetary m = new Monetary(BigDecimal.ZERO, this.m_currency);  
		m.m_value = this.m_value.subtract(y.m_value, BDTHelper.s_mathContext);
		return m;           
		}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
