package com.staed;

import com.staed.dao.RequestDAO;

public class App {
    public static void main(String[] args) {
        RequestDAO dao = new RequestDAO();
        System.out.println(dao.getRequest(3));
    }
}
