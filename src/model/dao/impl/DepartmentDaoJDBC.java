package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {
    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO department (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());

            boolean success = statement.execute();
            ResultSet results = statement.getGeneratedKeys();

            if(results.next()) {
                Integer id = results.getInt(1);
                department.setId(id);
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseStatement(statement);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());

            int affectedRows = statement.executeUpdate();

            if(affectedRows <= 0) {
                throw new DbException("No rows were affected");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM department WHERE id = ?");
            statement.setInt(1, id);

            Integer affectedRows = statement.executeUpdate();

            if(affectedRows <= 0) {
                throw new DbException("No rows were affected");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseStatement(statement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM department WHERE id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt(1));
                department.setName(resultSet.getString(2));

                return  department;
            }

            return null;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseResultSet(resultSet);
            DB.CloseStatement(statement);
        }
    }

    @Override
    public List<Department> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");

            List<Department> departments = new ArrayList<>();

            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt(1));
                department.setName(resultSet.getString(2));

                departments.add(department);
            }

            return departments;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.CloseResultSet(resultSet);
            DB.CloseStatement(statement);
        }
    }
}
