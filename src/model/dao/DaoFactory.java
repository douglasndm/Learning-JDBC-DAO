package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDAO CreateSellerDao() {
        return new SellerDaoJDBC();
    }
}
