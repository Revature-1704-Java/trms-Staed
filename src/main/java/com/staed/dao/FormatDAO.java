package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.staed.beans.GradingFormat;
import com.staed.stores.ColumnNames;

public class FormatDAO extends DAO<GradingFormat> {
	@Override
	GradingFormat extractRow(ResultSet rs) {
		try {
			int id = rs.getInt(ColumnNames.FORMATID);
			String type = rs.getString(ColumnNames.FORMATTYPE);
			int cutoff = rs.getInt(ColumnNames.CUTOFF);
			return new GradingFormat(id, type, cutoff);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	PreparedStatement prepareInsert(GradingFormat format) {
		String sql = "INSERT INTO GRADINGFORMAT (TYPE, CUTOFF) VALUES (?,?)";
		PreparedStatement ps = prepareStatement(sql);
		
		try {
			ps.setString(1, format.getType());
			ps.setInt(2, format.getCutoff());
			return ps;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public int idFromName(String name) {
		String sql = "SELECT " + ColumnNames.FORMATID + " FROM GRADINGFORMAT WHERE " + ColumnNames.FORMATTYPE + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		
		try {
			ps.setString(1, name);
			List<GradingFormat> fmtIter = preparedIterator(ps);
			if (fmtIter.isEmpty())
				return 0;
			else
				return fmtIter.get(0).getId();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
