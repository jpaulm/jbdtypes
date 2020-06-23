package com.jpaulmorrison.jbdtypes;
import java.math.*;

/**
 * This class describes Percent amounts
 * 
 
 */
public class Percent extends BigDecimal implements IBDType {
	static final String copyright = "Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, "
			+ "distribute, or make derivative works under the terms of the Clarified Artistic License, "
			+ "based on the Everything Development Company's Artistic License.  A document describing "
			+ "this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. "
			+ "THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	final static long serialVersionUID = 362498820763181265L;

	int m_precision = 15; // will be held, but not set

	Percent(char[] c) {
		super(c);
	}
	public Percent(BigDecimal bd) {
		super(bd.toPlainString());
	}
	public Percent(String s) {
		super((CalcPercent(s)).toPlainString());
	}
	/**
	 * Calculate a BigDecimal value for a Percent e.g. "3" (3 percent) will
	 * generate an array containing .03
	 * 
	 * @return java.math.BigDecimal
	 * @param s
	 *            java.lang.String
	 */
	static BigDecimal CalcPercent(String s) {

		BigDecimal temp = new BigDecimal("100");
		BigDecimal temp2 = new BigDecimal(s);
		return temp2.divide(temp, BDTHelper.s_mathContext); // we now have .03

	}

	/**
	 * Check if quantity is positive (>0)
	 * 
	 * @return boolean
	 */
	public boolean isPositive() {
		return +1 == this.compareTo(BigDecimal.ZERO);
	}
	/**
	 * Compare to see if <code> this </code> Percent amount is equal to
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpaulmorrison.jbdtypes.Percent - second Percent
	 */
	public boolean eq(Percent y) {
		return BDTHelper.Comp(this, y, BDTHelper.EQ);
	}
	/**
	 * Compare to see if <code> this </code> Percent amount is greater than or
	 * equal to parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpaulmorrison.jbdtypes.Percent - second Percent
	 */
	public boolean ge(Percent y) {
		return BDTHelper.Comp(this, y, BDTHelper.GE);
	}
	/**
	 * Get the value part of Percent, and return it as BigDecimal
	 * 
	 * @return java.math.BigDecimal
	 */
	public BigDecimal getValue() {
		return (BigDecimal) this;
	}
	/**
	 * Compare to see if <code> this </code> Percent amount is greater than
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpaulmorrison.jbdtypes.Percent - second Percent
	 */
	public boolean gt(Percent y) {
		return BDTHelper.Comp(this, y, BDTHelper.GT);
	}
	/**
	 * Compare to see if <code> this </code> Percent amount is less or equal to
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpaulmorrison.jbdtypes.Percent - second Percent
	 */
	public boolean le(Percent y) {
		return BDTHelper.Comp(this, y, BDTHelper.LE);
	}
	/**
	 * Compare to see if <code> this </code> Percent amount is less than
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpaulmorrison.jbdtypes.Percent - second Percent
	 */
	public boolean lt(Percent y) {
		return BDTHelper.Comp(this, y, BDTHelper.LT);
	}
	/**
	 * Compare to see if <code> this </code> Percent amount is not equal to
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpaulmorrison.jbdtypes.Percent - second Percent
	 */
	public boolean ne(Percent y) {
		return BDTHelper.Comp(this, y, BDTHelper.NE);
	}
	/**
	 * Generate a 'preference neutral' string from Percent value.
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {
		BigDecimal temp = (BigDecimal) this;
		temp = temp.multiply(BigDecimal.TEN, BDTHelper.s_mathContext);
		temp = temp.multiply(BigDecimal.TEN, BDTHelper.s_mathContext);
		temp = temp.divide(BigDecimal.ONE, BDTHelper.s_mathContext);
		return temp.toString();

	}
	/**
	 * Create a String from this object
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		return serialize();
	}
}
