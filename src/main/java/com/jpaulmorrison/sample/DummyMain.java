package com.jpaulmorrison.sample;

import com.jpaulmorrison.jbdtypes.*;

public class DummyMain {

	public static void main(String[] args) throws Exception {

    Monetary cost = new Monetary("CAD40.00");
    Quantity noOfApples = new Quantity(8);
    MPrice mp = cost.deriveMPrice(noOfApples); 
    
    if (mp.gt(new MPrice("CAD1.75")))
    	System.out.println("Too expensive!");
    else
    	System.out.println("Price OK!");
    	
	}

}
