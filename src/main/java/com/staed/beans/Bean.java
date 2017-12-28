package com.staed.beans;

import java.util.List;

import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

public abstract class Bean {
	static ColumnNames names;
	
	abstract List<FieldValueWrapper> toFieldValueWrappers();
}
