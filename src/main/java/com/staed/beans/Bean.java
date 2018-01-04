package com.staed.beans;

import java.util.List;

import com.staed.stores.FieldValueWrapper;

public interface Bean {
	abstract List<FieldValueWrapper> toFieldValueWrappers();
}
