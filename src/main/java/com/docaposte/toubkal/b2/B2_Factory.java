package com.docaposte.toubkal.b2;

public class B2_Factory {
	
	public IB2 getControl(String controlType) {
		if (controlType == null) {
			return null;
		}
		
		switch (controlType) {
		case "CP":
				return new CP();
		case "PH":
			return new PH();

		default:
			break;
		}
		return null;
	}

}
