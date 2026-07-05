package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDAO sellerDao = DaoFactory.CreateSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findByDepartment ===");
        Department department = new Department();
        department.setId(2);

        List<Seller> sellers = sellerDao.findByDepartment(department);
        System.out.println(sellers);
    }
}
