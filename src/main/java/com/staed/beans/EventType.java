package com.staed.beans;

import java.util.ArrayList;
import java.util.List;

import com.staed.stores.FieldValueWrapper;

public class EventType extends Bean {
	private int id;
	private String name;
	private int compensation;
	
	public EventType(int id, String name, int compensation) {
		this.id = id;
		this.name = name;
		this.compensation = compensation;
	}

	@Override
	List<FieldValueWrapper> toFieldValueWrappers() {
		List<FieldValueWrapper> list = new ArrayList<>();
		list.add(new FieldValueWrapper(names.eventTypeId, id));
		list.add(new FieldValueWrapper(names.eventTypeName, name));
		list.add(new FieldValueWrapper(names.compensation, compensation));
		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCompensation() {
		return compensation;
	}

	public void setCompensation(int compensation) {
		this.compensation = compensation;
	}
}
