package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe", "root", "01042004");
        Service service = new Service(connection);
        boolean check = true;
        while (check) {
            switch (service.menu()) {
                case 1 -> service.showTable();
                case 2 -> {
                    int salary = service.sumSalary();
                    System.out.println("Sum =" + salary);
                }
                case 3 -> service.biggestOrder();
                case 4 -> check = false;
            }
        }
    }
}