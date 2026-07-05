package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {
        SellerDAO sellerDao = DaoFactory.CreateSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}
