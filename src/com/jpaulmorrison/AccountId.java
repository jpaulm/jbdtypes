package com.jpaulmorrison;

/**
 * Immutable class describing Account ID
 * @author: Administrator
 */
public class AccountId implements IBDType {
	static final String copyright = 
		"Copyright 1999, 2000, 2001, 2002, J. Paul Morrison.  At your option, you may copy, " +
		"distribute, or make derivative works under the terms of the Clarified Artistic License, " +
		"based on the Everything Development Company's Artistic License.  A document describing " +
		"this License may be found at http://www.jpaulmorrison.com/fbp/artistic2.htm. " +
		"THERE IS NO WARRANTY; USE THIS PRODUCT AT YOUR OWN RISK.";
	int m_hashcode;
	String m_identifier;
	String m_firm;     // null if absent
	String m_system;   // null if absent
/**
 * AccountId constructor using string: identifier;firm;system
 *  where firm and system are optional
 */
 public AccountId(String s) {
	super();

	int sp = s.indexOf(BDT_DELIM);
	if (sp == -1)
	    m_identifier = s;
	else {
		m_identifier = s.substring(0, sp);
		String rest = s.substring(sp + 1);
		if (rest.length() > 0) {
	        sp = rest.indexOf(BDT_DELIM);
	        if (sp == -1)
	            m_firm = rest;
	        else {
		        if (sp > 0)
		            m_firm = rest.substring(0, sp);
		        if (sp + 1 < rest.length())
		            m_system = rest.substring(sp + 1);
	            }
		    }
	    }
	m_hashcode = this.serialize().hashCode();

}
public boolean equals(Object obj)
{
	if (obj != null)
	{
		if (AccountId.class.isInstance(obj))
		{
            if(((AccountId)obj).getFirm()==null){
                if(m_firm!=null)
                    return false;
            }else if( !((AccountId)obj).getFirm().equals(m_firm) )
                return false;

            if(((AccountId)obj).getIdentifier()==null){
                if(m_identifier!=null)
                    return false;
            }else if( !((AccountId)obj).getIdentifier().equals(m_identifier) )
                return false;

            if(((AccountId)obj).getSystem()==null){
                if(m_system!=null)
                    return false;
            }else if( !((AccountId)obj).getSystem().equals(m_system) )
                return false;
                
    		return true;
		}
	}
	return false;
}
/**
 * Get firm
 * @return java.lang.String
 */
public String getFirm() {
	return m_firm;
}
/**
 * Get identifier
 * @return java.lang.String
 */
public String getIdentifier() {
	return m_identifier;
}
/**
 * Get system
 * @return java.lang.String
 */
public String getSystem() {
	return m_system;
}
public int hashCode()
{
	return m_hashcode;
}
/**
 * Convert object to String
 * @return java.lang.String
 */
public String serialize() {
	String s = m_identifier;
	if (m_firm != null || m_system != null)
	    s = s + BDT_DELIM;
	if (m_firm != null)
	    s = s + m_firm;
	if (m_system != null)
	    s = s + BDT_DELIM + m_system;
	return s;


}
/**
 * Create a String from this object
 * @return java.lang.String
 */
public String toString() {
	return serialize();
}
}
