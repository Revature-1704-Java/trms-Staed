package com.staed;

import java.sql.SQLException;

import com.staed.dao.RequestDAO;

public class App {
    public static void main(String[] args) {
        RequestDAO dao = new RequestDAO();
        try {
            System.out.println(dao.getRequest(3));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
