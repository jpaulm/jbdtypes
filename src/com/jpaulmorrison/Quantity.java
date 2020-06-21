package com.jpaulmorrison;


import java.math.*;


/**
 * Quantity class - should not pick up BigDecimal's methods
 * @author: Administrator
 */

   public class Quantity {
	   static final String copyright = 
		   "Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		   "distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		   "based on the Everything Development Company's Artistic License.  A document describing " +
		   "this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		   "THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";
	   final static long serialVersionUID = 362498820763181265L;
	   
	   BigDecimal qty;
	   
  public Quantity(BigDecimal val) {
	  qty = val;
	  }
  
    
/**
 * Compare to see if quantity is equal to this one
 * @return boolean
 * @param y com.jpaulmorrison.Quantity - second Quantity
 */
public boolean eq(Quantity y){
		return qty.compareTo(y.qty) == 0;
}
/**
 * Compare to see if this quantity is greater than or equal to specified one
 * @return boolean
 * @param y com.jpaulmorrison.Quantity - second quantity
  */
public boolean ge(Quantity y) {
	return !(qty.compareTo(y.qty) == -1);
}


/**
 * Compare to see if this quantity is greater than specified one
 * @return boolean
 * @param y com.jpaulmorrison.Quantity - second quantity
 */
public boolean gt(Quantity y) throws BDTypeException {
	return qty.compareTo(y.qty) == +1;
}
/**
 * Check if value is positive (> 0)
 * @return boolean
 */
  public boolean isPositive() {			  
	return +1 == qty.compareTo(BigDecimal.ZERO);  
	    
}
/**
 * Compare to see if this quantity is less than or equal to specified one
 * @return boolean
 * @param y com.jpaulmorrison.Quantity - second quantity
 */
public boolean le(Quantity y){
	return !(qty.compareTo(y.qty) == +1);
}
/**
 * Compare to see if this quantity is less than specified one
 * @return boolean
 * @param y com.jpaulmorrison.Quantity - second quantity
 */
public boolean lt(Quantity y)  {
	return qty.compareTo(y.qty) == -1;
}
/**
 * Multiply this quantity by a MPrice, returning a Monetary 
 * @return com.jpaulmorrison.Monetary
 * @param x com.jpaulmorrison.MPrice
 */

public Monetary multiply(MPrice mpr) {
	
		return mpr.multiply(this);      
}


/**
 * Compare to see if a quantity is unequal to this one
 * @return boolean
 * @param y com.jpaulmorrison.Quantity - second quantity
 */
public boolean ne(Quantity y) throws BDTypeException {
	return qty.compareTo(y.qty) != 0;
}

/**
 * Generate a 'preference neutral' string from Quantity value.
 * Value will be preceded by Currency abbreviation 
 * @return java.lang.String
 */
public String serialize() {

	return toString();
		
	}
/**
 * add value of Quantity object to this Quantity object, creating another one
 * @return com.jpaulmorrison.Quantity 
 * @param y com.jpaulmorrison.Quantity - second quantity
 */
 
 public Quantity add(Quantity y){

	    return new Quantity(qty.add(y.qty));
		      
		}

	/**
   * subtract value of Quantity object from this Quantity object, creating another one
   * @return com.jpaulmorrison.Quantity 
   * @param y com.jpaulmorrison.Quantity - second quantity
   */
   
   public Quantity subtract(Quantity y){

	   return new Quantity(qty.subtract(y.qty));
		      
		}

}
