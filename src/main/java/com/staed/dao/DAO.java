package com.staed.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public abstract class DAO<T> {
    static Connection conn;

    public DAO() {
        DAO.conn = getConnection();
    }

    protected static Connection getConnection() {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream("connection.properties");
            prop.load(in);

            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void finalize() {
        close();
    }

    PreparedStatement prepareStatement(String sql) {
        try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    CallableStatement prepareCallable(String sql) {
        try {
			return conn.prepareCall(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    List<T> preparedIterator(PreparedStatement stmt) {
        return resultIterator(stmt, PreparedStatement.class);
    }

    List<T> callableIterator(CallableStatement stmt) {
        return resultIterator(stmt, CallableStatement.class);
    }

    <V extends Statement> List<T> resultIterator(V stmt, Class<V> type) {
        List<T> list = new ArrayList<>();

        try {
	        if (type == CallableStatement.class) {
	            ((CallableStatement) stmt).execute();
	        } else if (type == PreparedStatement.class) {
	            ResultSet rs = ((PreparedStatement) stmt).executeQuery();
	            while (rs.next()) {
	                list.add(extractRow(rs));
	            }
	            rs.close();
	        }
	        stmt.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }

        return list;
    }

    abstract T extractRow(ResultSet rs);

    abstract PreparedStatement prepareInsert(T t);
}