package com.jpaulmorrison.jbdtypes;

import java.math.*;




/**
 * This class describes a Monetary Price.
 * If denominator is zero, the three additional integer fields are 
 * ignored;  if not zero, the result of calculating 
 *    integer + numerator / denominator must equal Monetary value.
 * Therefore this is a "skewed 2-state" object
  
 */
public class MPrice extends Monetary implements IPrice {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	int m_integerValue;  // integer value of price
	int m_vFNumerator;   // vulgar fraction numerator
	int m_vFDenominator = 0; // vulgar fraction denominator
	 

/**
 * MPrice constructor with value initialized from int[3] array and Currency object 
 */
 public MPrice(int[] parts, Currency cur) {
	 
	super(MakeDecFromParts(parts), cur);
	m_integerValue = parts[0];
	m_vFNumerator = parts[1];
	m_vFDenominator = parts[2];
}
/**
 * MPrice constructor with value initialized from BigDecimal value and Currency object 
 */
 public MPrice(BigDecimal bd, Currency cur) {
	super(bd, cur);
	m_vFDenominator = 0;  // indicate that this is not in vulgar fraction format
	
}
/**
 * MPrice constructor with value initialized from BigDecimal value, Currency object, and
 *  boolean indicating that it is to be held as a vulgar fraction
 * @throws BDTypeException
 */
 public MPrice(BigDecimal bd, Currency cur, boolean holdAsVulgarFrac) 
		  throws BDTypeException{
			  
	super(bd, cur);
	m_vFDenominator = 0;
	if (holdAsVulgarFrac)
	    try {
		    int[] parts = BDTHelper.CalcVulgarFrac(bd);
		    m_integerValue = parts[0];
		    m_vFNumerator = parts[1];
		    m_vFDenominator = parts[2];
		    }
	    catch (BDTypeException e)
	        {throw new BDTypeException("Cannot be held as vulgar fraction: " + 
		        this.serialize());
	        }
	
}
/**
 * Monetary Price constructor using currency + value in String.
 * The first 3 characters of String will be currency code.
 * Following that, we either have a normal decimal value, or
 *   a price in fractional form, e.g. 23 17/32 (23 + 17 / 32).
 * It may also have a blank or 'V' appended on the end.
 * @throws BDTypeException
 */
 public MPrice(String s) throws BDTypeException {

        if (s.length() < 4)
            throw new BDTypeException("Invalid MPrice String: " + s);
	String c = s.substring(0,3);
	int parts[];

	try{
	
	    m_currency = Currency.Get(c);

		String v = s.substring(3);  // get value part of string

		if (v.indexOf('/') != -1) {
		    parts = GetVFParts(v);	// scan off vulgar fraction parts
		    m_integerValue = parts[0];
		    m_vFNumerator = parts[1];
		    m_vFDenominator = parts[2];

		    BigDecimal temp = new BigDecimal(m_vFNumerator);
	        BigDecimal temp2 = new BigDecimal(m_vFDenominator);
	        BigDecimal temp3 = new BigDecimal(m_integerValue);
	        temp = temp.divide(temp2, BDTHelper.s_mathContext);
	        m_value = temp3.add(temp, BDTHelper.s_mathContext);
		    }
	    else  {
		    m_vFDenominator = 0;
		    boolean vulgar = false;
		    int len = v.length();
			if (v.substring(len - 1, len).equals(" ")) 				
				v = v.substring(0, len - 1);
			else if (v.substring(len - 1, len).equals("V")) {				
				v = v.substring(0, len - 1);
				vulgar = true;
			    }
	        m_value = new BigDecimal(v);
	        
	        if (vulgar) {
	            parts = BDTHelper.CalcVulgarFrac(m_value);
	            m_integerValue = parts[0];
		        m_vFNumerator = parts[1];
		        m_vFDenominator = parts[2];
	            }
	        }
	    }catch(Exception e){
		    throw new BDTypeException(e.getMessage());
	        }

	}
/**
 * MPrice constructor with value initialized from 2 Strings - value (vulgar or decimal),
 *   and currency abbreviation.
 * The value will either be a normal decimal value, or
 *   a price in fractional form, e.g. 23 17/32 (23 + 17 / 32).
 * @throws BDTypeException
 */
 public MPrice(String v, String cur) throws BDTypeException {
	 
	int[] parts;
	m_currency = Currency.Get(cur);
	if (v.indexOf('/') != -1) {
		parts = GetVFParts(v);	// scan off vulgar fraction parts
		m_integerValue = parts[0];
		m_vFNumerator = parts[1];
		m_vFDenominator = parts[2];
		
		BigDecimal temp = new BigDecimal(m_vFNumerator);
	    BigDecimal temp2 = new BigDecimal(m_vFDenominator);
	    BigDecimal temp3 = new BigDecimal(m_integerValue);
	    temp = temp.divide(temp2, BDTHelper.s_mathContext);
	    m_value = temp3.add(temp, BDTHelper.s_mathContext);
		}
	else {
	    m_vFDenominator = 0;
		boolean vulgar = false;
		int len = v.length();
		if (v.substring(len - 1, len).equals(" ")) 				
			v = v.substring(0, len - 1);
		else if (v.substring(len - 1, len).equals("V")) {				
			v = v.substring(0, len - 1);
			vulgar = true;
		    }
	    m_value = new BigDecimal(v);
	        
	    if (vulgar) {
	        parts = BDTHelper.CalcVulgarFrac(m_value);
	        m_integerValue = parts[0];
		    m_vFNumerator = parts[1];
		    m_vFDenominator = parts[2];
	        }
	    }
	
	
	
}
/**
 * Add this Monetary Price to another, giving a Monetary Price.
 * Currencies must match
 * @return jbdtypes.MPrice
 * @param x jbdtypes.MPrice
 * @throws BDTypeException
 */
public MPrice add(MPrice x) throws BDTypeException {
	if (this.m_currency != x.m_currency || x.m_currency == null) 
	    throw new BDTypeException("Currency mismatch: "  +
		    this.getCurrAbbr() + ", " + x.getCurrAbbr()); 
	return new MPrice(this.m_value.add(x.m_value, BDTHelper.s_mathContext), 
		        this.m_currency);
}
/**
 * Divide this MPrice amount by a BigDecimal quantity, creating a new MPrice
 * @return jbdtypes.MPrice
 * @param x java.math.BigDecimal
 * @throws BDTypeException
 */
public MPrice divide(Quantity x) throws BDTypeException {
	    if (m_currency == null)
	        throw new BDTypeException("Unknown currency");
		MPrice m = new MPrice(BigDecimal.ZERO, this.m_currency);
		m.m_value = this.m_value.divide(x.qty, BDTHelper.s_mathContext);
		return m;      
}
/**
 * Scan off the parts of a vulgar fraction ('tight' format) - returns parts 
 *  in int array (integer part, numerator, denominator).  Only called if slash present.
 * @return int[]
 * @param String
 * @throws BDTypeException
 */
		 static int[] GetVFParts(String s) throws BDTypeException {
			int parts[] = {0, 0, 0};  
	   
			int sb = s.indexOf(' ');
			int ss = s.indexOf('/');

			if (sb == -1 || ss == -1 || sb > ss)
			    throw new BDTypeException("Invalid vulgar fraction: " + s);
			
			parts[0] = Integer.parseInt(s.substring(0,sb));			// integer part
			parts[1] = Integer.parseInt(s.substring(sb + 1, ss));	// numerator		
			parts[2] = Integer.parseInt(s.substring(ss + 1));   	// denominator		

			if (parts[2] == 0 || parts[1] >= parts[2])   
			    throw new BDTypeException("Invalid vulgar fraction: " + s); 

			return parts;
			
}
/**
 * Determine if <code> this </code> MPrice is a multiple of the parameter IPrice -
 *  return true if it is. 
 * @return boolean
 * @param p jbdtypes.IPrice
  */
public boolean isMultipleOf(IPrice p) {
	
	if (!(p instanceof MPrice))
	    throw new BDTypeException("Parameter not an MPrice: " + p);
	MPrice pc = (MPrice) p;    
	return isMultipleOf(pc);
}
/**
 * Determine if <code> this </code> MPrice is a multiple of the parameter MPrice -
 *  return true if it is. 
 * @return boolean
 * @param pc price 
 */
public boolean isMultipleOf(MPrice pc) {
	
	if (this.m_currency != pc.m_currency || this.m_currency == null)
	      throw new BDTypeException("Currency mismatch: " + 
		       this.getCurrAbbr() + ", " + pc.getCurrAbbr());    
	BigDecimal bd = this.m_value.remainder(pc.m_value, BDTHelper.s_mathContext);   
	return 0 == bd.compareTo(BigDecimal.ZERO);  
	    
}
/**
 * Compare to see if monetary price is equal to this one
 * @return boolean
 * @param y jbdtypes.MPrice - second monetary price
 * @throws BDTypeException
 */
public boolean eq(MPrice y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.EQ);
}
/**
 * Compare to see if monetary price is grater than this one
 * @return boolean
 * @param y jbdtypes.MPrice - second monetary price
 * @throws BDTypeException
 */
public boolean gt(MPrice y) throws BDTypeException {
		return BDTHelper.Comp(this, y, BDTHelper.EQ);
}
/**
 * Make BigDecimal object from 3 integer parts (as in vulgar fraction)
 * @return java.math.BigDecimal
 * @param parts int[]
 */
static BigDecimal MakeDecFromParts(int[] parts) {
	
		BigDecimal temp = new BigDecimal(parts[1]);
	    BigDecimal temp2 = new BigDecimal(parts[2]);
	    BigDecimal temp3 = new BigDecimal(parts[0]);
	    temp = temp.divide(temp2, BDTHelper.s_mathContext);
	    return temp3.add(temp, BDTHelper.s_mathContext);
}

/**
 * Multiply this MPrice amount by a BigDecimal quantity, creating a new MPrice
 * @return jbdtypes.MPrice
 * @param x java.math.BigDecimal
 * @throws BDTypeException
 */
public MPrice multiply(Quantity x) throws BDTypeException{
		if (m_currency == null)
	      throw new BDTypeException("Unknown currency");
		MPrice m = new MPrice(BigDecimal.ZERO, this.m_currency);
		m.m_value = this.m_value.multiply(x.qty, BDTHelper.s_mathContext);
		return m;      
}

/**
 * Extend this MPrice amount by a BigDecimal quantity, creating a new Monetary
 * @return jbdtypes.Monetary
 * @param x java.math.BigDecimal
 * @throws BDTypeException
 */
public Monetary extend(Quantity x) throws BDTypeException{
		if (m_currency == null)
	      throw new BDTypeException("Unknown currency");
		Monetary m = new Monetary(BigDecimal.ZERO, this.m_currency);
		m.m_value = this.m_value.multiply(x.qty, BDTHelper.s_mathContext);
		return m;      
}
/**
 * Generate a 'preference neutral' string from Monetary Price.
 * Value will be preceded by Currency abbreviation.
 * If denominator is non-zero, price will be displayed in fractional 
 *   format (xxVyy/zz)
 * @return java.lang.String
 */
public String serialize() {
	m_value = m_value.divide(BigDecimal.ONE, BDTHelper.s_mathContext);
	String s1;
	if (m_vFDenominator == 0)
	    s1 = m_value.toString(); //MPrice ignores currency precision
	else {
		s1 = Integer.toString(m_integerValue);
		String s2 = Integer.toString(m_vFNumerator);
		if (s1.charAt(0) == '-' && s2.charAt(0) == '-')
		    s2 = s2.substring(1);
	    s1 = s1 + ' ' + s2 + '/' + Integer.toString(m_vFDenominator);
	    }    
	  
	return  getCurrAbbr() + s1;
}
/**
 * Create new MPrice with new currency - can only be done if currency is unknown (== null)
 * @param cur jbdtypes.Currency
 * @return MPrice
 * @throws jbdtypes.BDTypeException
 */
public MPrice setPriceCurrency(Currency cur) throws BDTypeException{
	
	if (m_currency != null) 
	    throw new BDTypeException("Trying to change currency "  +
		      this.getCurrAbbr() + " to " + cur.getAbbrev());
	return new MPrice(m_value, cur);
	
	}
/**
 * Create new MPrice with new currency - can only be done if currency is unknown (== null)
 * @param s java.lang.String
 * @return MPrice
 * @throws jbdtypes.BDTypeException
 */
public MPrice setPriceCurrency(String s) throws BDTypeException {
	
	if (m_currency != null) 
	    throw new BDTypeException("Trying to change currency "  +
		      this.getCurrAbbr() + " to " + s);
	return new MPrice(m_value, Currency.Get(s));    
	
	
	}
/**
 * Subtract a Monetary Price from this one, giving a Monetary Price.
 * Currencies must match
 * @return jbdtypes.MPrice
 * @param x jbdtypes.MPrice
 * @throws BDTypeException
 */
public MPrice subtract(MPrice x) throws BDTypeException {
	if (this.m_currency != x.m_currency || x.m_currency == null) 
	    throw new BDTypeException("Currency mismatch: "  +
		    this.getCurrAbbr() + ", " + x.getCurrAbbr()); 
	return new MPrice(this.m_value.subtract(x.m_value, BDTHelper.s_mathContext), 
		        this.m_currency);
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
