package jbdtypes;



/**
 * This interfaces includes Monetary Price and Percent Price.
  */
public interface IPrice  extends IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";

	
	 

/**
 * Determine if <code> this </code> IPrice is a multiple of the parameter IPrice.
 * @return boolean
 * @param p jbdtypes.IPrice
 */
public boolean isMultipleOf(IPrice p) ;
/**
 * Determine if <code> this </code> IPrice is positive (greater than zero)
 * @return boolean
 */
public boolean isPositive() ;
}
