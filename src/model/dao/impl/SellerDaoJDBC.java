package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller department) {

    }

    @Override
    public void update(Seller department) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.name as depName FROM seller INNER JOIN department ON seller.departmentid = department.id WHERE seller.id = ?"
            );
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("departmentid"));
                department.setName(resultSet.getString("depName"));

                Seller seller = new Seller();
                seller.setId(resultSet.getInt("id"));
                seller.setName(resultSet.getString("name"));
                seller.setEmail(resultSet.getString("email"));
                seller.setBirthDate(resultSet.getDate("birthdate"));
                seller.setBaseSalary(resultSet.getDouble("basesalary"));
                seller.setDepartment(department);

                return seller;
            }

            return null;
        } catch (SQLException ex) {
            throw  new DbException(ex.getMessage());
        } finally {
            DB.CloseStatement(preparedStatement);
            DB.CloseResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
