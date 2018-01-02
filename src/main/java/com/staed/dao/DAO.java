package com.staed.dao;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

import oracle.jdbc.OracleDriver;

/**
 * A data access object (DAO) that handles connections and operations
 * on a SQL database for a specific object. The object can correspond
 * partially or complete with a table or multitude of tables.
 * @param <T> The object this instance manages
 */
public abstract class DAO<T> {
    static Connection conn;
    static ColumnNames names;

    public DAO() {
        DAO.conn = getConnection();
    }

    /**
     * Creates the Connection with the DB via JDBC
     */
    protected static Connection getConnection() {
        try {
            Properties prop = new Properties();
            InputStream in = DAO.class.getResourceAsStream("/connections.properties");
            prop.load(in);

            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
        	
            DriverManager.registerDriver(new OracleDriver());
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

    /**
     * Creates a PreparedStatement from a string query via a JDBC connection
     * @param String - The sql query
     * @return - The PreparedStatement
     */
    PreparedStatement prepareStatement(String sql) {
        try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Creates a PreparedStatement from a string query via a JDBC connection
     * @param String - The sql query
     * @return - The PreparedStatement
     */
    CallableStatement prepareCallable(String sql) {
        try {
			return conn.prepareCall(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Iterates through a ResultSet and adds the data to a List after
     * the raw data into a useful form.
     * @param PreparedStatement - The PreparedStatement to be executed
     * @return - A List of generics containing the transformed data
     */
    List<T> preparedIterator(PreparedStatement stmt) {
        return resultIterator(stmt);
    }

    /**
     * Executes the CallableStatement
     * @param CallableStatement
     * @return - Null
     */
    void callableIterator(CallableStatement stmt) {
        resultIterator(stmt);
    }

    /**
     * A generic iterator over the results of Statements, specifically
     * the PreparedStatement and CallableStatement classes, after execution
     * @param V - A Statement generic
     * @return - A generic list transformed data created upon execution of the
     * Statement generic
     */
    <V extends Statement> List<T> resultIterator(V stmt) {
        List<T> list = new ArrayList<>();

        try {
            Class<? extends Object> type = ((Object) stmt).getClass();
	        if (stmt.getClass() == CallableStatement.class) {
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

    /**
     * Defines how the raw data of a ResultSet is transformed
     * @param ResultSet - The ResultSet to extract data from
     */
    abstract T extractRow(ResultSet rs);

    /**
     * Defines how the generic object is transformed into an SQL INSERT query
     * @param T - The generic object to be transformed
     */
    abstract PreparedStatement prepareInsert(T t);
    
    /**
     * Executes a given SQL query after filling it in with the passed in list
     * of wrappers containing name, type, and value of fields.
     * @param String - The SQL query
     * @param List&lt;FieldValueWrapper&gt; - Contains representations of an
     * SQL table's columns and their values without being constrainted by the
     * different SQL data types possible
     * @return - The number of rows updated
     */
    int update(String sql, List<FieldValueWrapper> fields ) {
		PreparedStatement ps = prepareStatement(sql);
		int idx = 1;
		
		try {
			for (FieldValueWrapper field : fields) {
				prepareField(ps, idx++, field.get().getValue());
			}
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
    
    /**
     * Insert a field value into the PreparedStatement using the appropriate
     * set method from the PreparedStatement class
     * @param PreparedStatement - The Statement to be inserted into
     * @param int - The index where the value should be inserted
     * @param V - The value inserted represented by a generic
     */
	<V> void prepareField(PreparedStatement ps, int index, V param) throws SQLException {
		Class<? extends Object> type = ((Object) param).getClass();
		if (type == Integer.class) {
			ps.setInt(index, (Integer) param);
		} else if (type == String.class) {
			ps.setString(index, (String) param);
		} else if (type == Float.class) {
			ps.setFloat(index, (Float) param);
		} else if (type == Date.class) {
			ps.setDate(index, (Date) param);
		}
	}
}