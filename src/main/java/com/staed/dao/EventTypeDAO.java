package com.staed.dao;

import com.staed.stores.EventType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;

public class EventTypeDAO extends DAO<SimpleEntry<String, Integer>>{
	EventType evt = new EventType();

	// This should only be called once during initialization of the program
	public HashMap<String, Integer> populateEventTypes() throws SQLException {
		HashMap<String, Integer> cur = new HashMap<>();
		PreparedStatement ps = prepareStatement("SELECT * FROM EVENTTYPE");
		List<SimpleEntry<String, Integer>> rs = resultIterator(ps);

		rs.forEach( elem -> {
			cur.put(elem.getKey(), elem.getValue());
		});
		return cur;
	}

	/**
	 * Extracts the key-value pair from the EVENTTYPE table
	 * @param ResultSet - A row in the table
	 * @return The key-value pair placed in a SimpleEntry<>
	 */
	@Override
	SimpleEntry<String, Integer> extractRow(ResultSet rs) throws SQLException {
		int id = rs.getInt("EVENTTYPEID");
		String name = rs.getString("EVENTTYPENAME");
		return new SimpleEntry<>(name, id);
	}

	/**
	 * This is not expected to be necessary and only returns null
	 * @param SimpleEntry&lt;String,Integer&gt;
	 */
	@Override
	PreparedStatement packageObj(SimpleEntry<String, Integer> t) throws SQLException {
		return null;
	}
}