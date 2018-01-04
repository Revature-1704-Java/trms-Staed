package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.staed.beans.GradingFormat;

public class FormatDAO extends DAO<GradingFormat> {
	@Override
	GradingFormat extractRow(ResultSet rs) {
		try {
			int id = rs.getInt(names.formatId);
			String type = rs.getString(names.formatType);
			int cutoff = rs.getInt(names.cutoff);
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
		String sql = "SELECT " + names.formatId + " FROM GRADINGFORMAT WHERE " + names.formatType + " = ?";
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
