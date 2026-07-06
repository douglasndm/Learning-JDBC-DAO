package aplication;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDAO departmentDao = DaoFactory.CreateDepartmentDao();

        System.out.println("=== TEST 1: department findById ===");
        Department department = departmentDao.findById(3);
        System.out.println(department);

        System.out.println("\n=== TEST 2: department findAll ===");
        List<Department> departments = departmentDao.findAll();
        System.out.println(departments);

        System.out.println("\n=== TEST 3: department insert ===");
        Department newDepartment = new Department(null, "Mercearia");
        departmentDao.insert(newDepartment);
        System.out.println("Insert department successfully " + newDepartment.getId());

        System.out.println("\n=== TEST 5: department update ===");
        department = departmentDao.findById(1);
        System.out.println(department);
        department.setName("Departamento atualizado");
        departmentDao.update(department);
        System.out.println("Update department successfully " + department.getName());

        System.out.println("\n=== TEST 5: department delete ===");
        System.out.println("Enter an id for delete");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete department successfully " + id);
    }
}
