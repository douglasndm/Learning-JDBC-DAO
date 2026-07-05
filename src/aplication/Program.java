package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
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

        System.out.println("\n=== TEST 3: seller findAll ===");
        sellers = sellerDao.findAll();
        System.out.println(sellers);

        System.out.println("\n=== TEST 4: seller insert ===");
        Seller newSeller = new Seller(null, "Greg", "greg@mail.com", new Date(), 4000.00, department);
        sellerDao.insert(newSeller);
        System.out.println("Insert seller successfully " + newSeller.getId());

        System.out.println("\n=== TEST 5: seller update ===");
        seller = sellerDao.findById(1);
        System.out.println(seller);
        seller.setName("Melissa");
        sellerDao.update(seller);
        System.out.println("Update seller successfully " + seller.getName());
    }
}
