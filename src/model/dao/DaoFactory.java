package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDAO CreateSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDAO CreateDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
