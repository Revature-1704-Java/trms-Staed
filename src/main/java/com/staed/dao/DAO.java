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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class DAO<V> {
    /**
     * Creates a SQL connection and creates a PreparedStatement of the query
     * @param sql - The SQL query desired
     * @return A PreparedStatement of the query
     */
    PreparedStatement prepareStatement(String sql) throws SQLException {
        return prepare(sql, PreparedStatement.class);
    }

    /**
     * Creates a SQL connection and creates a CallableStatement of the query
     * @param sql - The SQL query desired
     * @return A CallableStatement of the query
     */
    CallableStatement prepareCallable(String sql) throws SQLException {
        return prepare(sql, CallableStatement.class);
    }

    /**
     * Create the SQL connection and return either a PreparedStatement
     * or CallableStatement of the SQL query
     * @param String - The SQL query desired
     * @param Class&lt;T&gt; - A PreparedStatement or CallableStatement class
     * @return Either a PreparedStatement or CallableStatement
     */
    <T extends Statement> T prepare(String sql, Class<T> type) throws SQLException {
        Connection conn = getConnection();

        if (type.isInstance(PreparedStatement.class))
            return (T) conn.prepareStatement(sql);
        else if (type.isInstance(CallableStatement.class))
            return (T) conn.prepareCall(sql);
        else
            return null;
    }

    /**
     * Given a SQL query, it will either process it as a PreparedStatement
     * or a CallableStatement. If the query is a PreparedStatement, this
     * will return a list<Obj> of the result set whose size is indeterminate.
     * If the query is a CallableStatement, it will be executed and return an
     * empty list<Obj>.
     * @param T - A type extending from java.sql.Statement, handled only if
     * it is a PreparedStatement or CallableStatement.
     * @return A List&lt;U&gt; and is either null or of indeterminate size.
     */
    <T extends Statement> List<V> resultIterator(T stmt) throws SQLException {
        List<V> list = new ArrayList<>();

        if (stmt.getClass().isInstance(CallableStatement.class)) {
            ((CallableStatement) stmt).execute();
        } else if (stmt.getClass().isInstance(PreparedStatement.class)) {
            ResultSet rs = ((PreparedStatement) stmt).executeQuery();
            while (rs.next()) {
                list.add(extractRow(rs));
            }
            rs.close();
        }
        stmt.close();

        return list;
    }

    abstract V extractRow(ResultSet rs) throws SQLException;

    abstract PreparedStatement packageObj(V t) throws SQLException;

    protected static Connection getConnection() {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream("connection.properties");
            prop.load(in);

            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}