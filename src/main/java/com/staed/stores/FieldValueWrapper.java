package com.staed.stores;

import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;

public class FieldValueWrapper {
	private SimpleEntry<String, Integer> number = null;
	private SimpleEntry<String, String> varchar = null;
	private SimpleEntry<String, Float> floating = null;
	private SimpleEntry<String, Date> date		= null;
	
	public FieldValueWrapper(String name, int val) {
		number = new SimpleEntry<String, Integer>(name, val);
	}
	
	public FieldValueWrapper(String name, String val) {
		varchar = new SimpleEntry<String, String>(name, val);
	}
	
	public FieldValueWrapper(String name, float val) {
		floating = new SimpleEntry<String, Float>(name, val);
	}
	
	public FieldValueWrapper(String name, Date val) {
		date = new SimpleEntry<String, Date>(name, val);
	}
	
	public SimpleEntry<String, ?> get() {
		if (number != null)
			return number;
		else if (varchar != null)
			return varchar;
		else if (floating != null)
			return floating;
		else if (date != null)
			return date;
		else
			return null;
	}
}
