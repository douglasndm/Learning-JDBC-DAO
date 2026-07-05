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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDAO {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller (name, email, birthdate, basesalary, departmentid) VALUES (?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if(resultSet.next()) {
                    int id = resultSet.getInt(1);
                    seller.setId(id);
                }

                DB.CloseResultSet(resultSet);
            } else {
                throw new DbException("Seller not inserted");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseStatement(preparedStatement);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE seller SET name = ?, email = ?, birthdate = ?, basesalary = ?, departmentid = ? WHERE id = ?"
            );
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            preparedStatement.setInt(6, seller.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseStatement(preparedStatement);
        }
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
                Department department = instantiateDepartment(resultSet);

                Seller seller = instantiateSeller(resultSet, department);

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

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("id"));
        seller.setName(resultSet.getString("name"));
        seller.setEmail(resultSet.getString("email"));
        seller.setBirthDate(resultSet.getDate("birthdate"));
        seller.setBaseSalary(resultSet.getDouble("basesalary"));
        seller.setDepartment(department);

        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("departmentid"));
        department.setName(resultSet.getString("depName"));

        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.name as DepName FROM seller INNER JOIN department ON seller.departmentid = department.id ORDER BY name"
            );

            resultSet = preparedStatement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departments = new HashMap<>();

            while (resultSet.next()) {
                Department dep = departments.get(resultSet.getInt("departmentid"));

                if(dep == null) {
                    dep = instantiateDepartment(resultSet);

                    departments.put(resultSet.getInt("departmentid"), dep);
                }

                Seller seller = instantiateSeller(resultSet, dep);
                sellers.add(seller);
            }

            return sellers;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseResultSet(resultSet);
            DB.CloseStatement(preparedStatement);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.name as DepName FROM seller INNER JOIN department ON seller.departmentid = department.id WHERE departmentid = ? ORDER BY name"
            );
            preparedStatement.setInt(1, department.getId());

            resultSet = preparedStatement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departments = new HashMap<>();

            while (resultSet.next()) {
                Department dep = departments.get(resultSet.getInt("departmentid"));

                if(dep == null) {
                    dep = instantiateDepartment(resultSet);

                    departments.put(resultSet.getInt("departmentid"), dep);
                }

                Seller seller = instantiateSeller(resultSet, dep);
                sellers.add(seller);
            }

            return sellers;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseResultSet(resultSet);
            DB.CloseStatement(preparedStatement);
        }
    }
}
