package com.jpaulmorrison.jbdtypes;
import java.math.*;

import java.util.*;

/**
 * This class describes a Percentage Price.
  
 */
public class PCPrice extends Percent implements IPrice {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";
	final static long serialVersionUID = 362498820763181265L;
	
	String m_type;  // will contain one of above values

	static HashMap<String,String> s_types = null;

	static {
		s_types = new HashMap<String,String>();
	    s_types.put("PCT", null);              // percent
	    s_types.put("DSC", null);              // discount: 100 - value  
	    s_types.put("PRM", null);              // premium: 100 + value
	    }

public  PCPrice(BigDecimal bd, String t)  {
		super(bd.toPlainString());  
		m_type = t; 
	    }
/**
 * @throws BDTypeException
 */


public PCPrice(String s) throws BDTypeException {
	
		super(CalcPCPrice(s));  
		m_type = s.substring(0,3);  // get m_type
		if (!s_types.containsKey(m_type))
		    throw new BDTypeException("Invalid PCPrice code: " + m_type);
		
	    }
/**
 * Add this PC Price to another, giving a PC Price.
 * Types must match
 * @return com.jpaulmorrison.jbdtypes.PCPrice
 * @param x com.jpaulmorrison.jbdtypes.PCPrice
 * @throws BDTypeException
 */
public PCPrice add(PCPrice x) throws BDTypeException {
	String type = this.m_type;
	if (!this.m_type.equals(x.m_type)) 
	    type = "PCT";
	return new PCPrice(this.add(x, BDTHelper.s_mathContext), type);
}
/**
 * Calculate a char array containing decimal representation of PCPrice
 * e.g. "DSC3" (3 percent discount) will generate an array containing .97
 * @return String
 * @param s java.lang.String
 * @throws BDTypeException
 */
static String CalcPCPrice(String s) throws BDTypeException {

	String code = s.substring(0, 3);   // scan off 3-character prefix
	String v = s.substring(3);         // rest of value
	int len = v.length();
	if (v.substring(len - 1, len).equals("P"))
		v = v.substring(0, len - 1);
	
	BigDecimal temp = Percent.CalcPercent(v);
	
	if (code.equals("PRM")) 
	    temp = BigDecimal.ONE.add(temp, BDTHelper.s_mathContext);
	else
	    if (code.equals("DSC")) 
	        temp = BigDecimal.ONE.subtract(temp, BDTHelper.s_mathContext);
	    else
	        if (!code.equals("PCT"))
		        throw new BDTypeException("Invalid PCPrice code: " + code); 
	return temp.toPlainString();
}
/**
 * Get the value part of PCPrice, and return it as BigDecimal 
 * @return java.math.BigDecimal
 */
public BigDecimal getValue() {
	return (BigDecimal) this;
}
/**
 * Determine if <code> this </code> PCPrice is a multiple of the parameter IPrice -
 *  return true if it is.
 * @return boolean
 * @param p com.jpaulmorrison.jbdtypes.IPrice
 */
public boolean isMultipleOf(IPrice p) {
	if (!(p instanceof PCPrice))
	    throw new BDTypeException("Parameter not a PCPrice: " + p);
	PCPrice pc = (PCPrice) p;
	BigDecimal bd = this.remainder(pc, BDTHelper.s_mathContext);
	return 0 == bd.compareTo(BigDecimal.ZERO);

}
/**
 * Determine if <code> this </code> PCPrice is a multiple of the parameter PCPrice -
 *  return true if it is.
 * @return boolean
 * @param pc com.jpaulmorrison.jbdtypes.PCPrice
 */
public boolean isMultipleOf(PCPrice pc) {

	BigDecimal bd = this.remainder(pc, BDTHelper.s_mathContext);
	return 0 == bd.compareTo(BigDecimal.ZERO);

}
/**
 * Determine if <code> this </code> PCPrice is positive (greater than 0)
  * @return boolean
 */
public boolean isPositive() {
	return +1 == this.compareTo(BigDecimal.ZERO);

}
/**
 * Multiply a Monetary amount by this Percent Price, returning a Monetary amount
 * @return com.jpaulmorrison.jbdtypes.Monetary
 * @param x com.jpaulmorrison.jbdtypes.Monetary
 */
public Monetary multiply(Monetary x) {
	return new Monetary(x.m_value.multiply(this, BDTHelper.s_mathContext), x.m_currency);
}
/**
 * Multiply a Quantity by this Percent Price, returning a Percent Price
 * @return com.jpaulmorrison.jbdtypes.PCPrice
 * @param x com.jpaulmorrison.jbdtypes.Quantity
 */
public PCPrice multiply(Quantity x) {
	return this.multiply(x);
}
/**
 * Generate a 'preference neutral' string from Percent Price, showing
 *   3-character type abbreviation.
 * @return java.lang.String
 */
public String serialize() {
	BigDecimal temp = this;
	BigDecimal temp2 = BigDecimal.ONE;
	if (m_type.equals("PRM"))    // subtract 1 from value, and multiply by 100
	    temp = temp.subtract(temp2, BDTHelper.s_mathContext);
	else
	    if (m_type.equals("DSC"))   // subtract value from 1, and multiply by 100 
	        temp = temp2.subtract(temp, BDTHelper.s_mathContext);  
	temp = temp.multiply(BigDecimal.TEN, BDTHelper.s_mathContext);
	temp = temp.multiply(BigDecimal.TEN, BDTHelper.s_mathContext);
	temp = temp.divide(BigDecimal.ONE, BDTHelper.s_mathContext);
	String s1 = temp.toString();
		
	return  m_type + s1;
}
/**
 * Subtract this PC Price from another, giving a PC Price.
 * Types must match
 * @return com.jpaulmorrison.jbdtypes.PCPrice
 * @param x com.jpaulmorrison.jbdtypes.PCPrice
 * @throws BDTypeException
 */
public PCPrice subtract(PCPrice x) throws BDTypeException {
	String type = this.m_type;
	if (!this.m_type.equals(x.m_type)) 
	    type = "PCT";

	return new PCPrice(this.subtract(x, BDTHelper.s_mathContext), type);
}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
