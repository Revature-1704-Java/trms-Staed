package com.staed.beans;

import java.util.ArrayList;
import java.util.List;
import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

public class GradingFormat implements Bean {
	private int id;
	private String type;
	private int cutoff;
	
	public GradingFormat(int id, String type, int cutoff) {
		this.id = id;
		this.type = type;
		this.cutoff = cutoff;
	}

	@Override
	public List<FieldValueWrapper> toFieldValueWrappers() {
		List<FieldValueWrapper> list = new ArrayList<>();
		list.add(new FieldValueWrapper(ColumnNames.FORMATID, id));
		list.add(new FieldValueWrapper(ColumnNames.FORMATTYPE, type));
		list.add(new FieldValueWrapper(ColumnNames.CUTOFF, cutoff));
		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCutoff() {
		return cutoff;
	}

	public void setCutoff(int cutoff) {
		this.cutoff = cutoff;
	}
}
