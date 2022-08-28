package org.example;

import java.sql.*;
import java.util.Scanner;

public class Service {
    private final Connection connection;

    public Service(Connection connection) {
        this.connection = connection;
    }

    public int menu() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        boolean check = true;
        while (check) {
            System.out.println("""
            Choose action
            1 - Show table
            2 - Show sum salary
            3 - Show the biggest order
            4 - Exit""");
            number = scanner.nextInt();
            scanner.nextLine();
            if ((number >= 1) && (number <= 4)) {
                check = false;
            } else
                System.out.println("Wrong action! Try again");
        }
        return number;
    }

    protected int sumSalary() throws SQLException {
        ResultSet set = executeQuery("select sum(Salary) from cooks");
        int salary = 0;
        if (set.next()) {
            salary = set.getInt(1);
        }
        return salary;
    }

    private ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void biggestOrder() throws SQLException {
        ResultSet set = executeQuery("select od.Count, m.NameOfDish from orders_dishes od join menu m on od.DishID=m.DishID  where Count = (SELECT max(Count) from orders_dishes)");
        while (set.next()) {
            System.out.println(set.getString(1) + " " + set.getString(2));
        }
    }

    protected void showTable() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int tableNumber = 0;
        boolean check = true;
        while (check) {
            System.out.println("Choose table to show \n1 Cooks - \n2 - Menu \n3 - Orders \n4 - Orders_Dishes");
            tableNumber = scanner.nextInt();
            if ((tableNumber >= 1) && (tableNumber <= 4)) {
                check = false;
                break;
            } else
                System.out.println("Wrong action! Try again");
        }
        switch (tableNumber) {
            case 1:
                ResultSet set = executeQuery("select * from cooks");
                while (set.next()) {
                    System.out.println(set.getString(1) + " " + set.getString(2) + " " + set.getString(3) + " "
                            + set.getString(4));
                }
                break;
            case 2:
                set = executeQuery("select * from menu");
                while (set.next()) {
                    System.out.println(set.getString(1) + " " + set.getString(2) + " " + set.getString(3) + " "
                            + set.getString(4) + " " + set.getString(5));
                }
                break;
            case 3:
                set = executeQuery("select * from orders");
                while (set.next()) {
                    System.out.println(set.getString(1) + " " + set.getString(2) + " " + set.getString(3));
                }
                break;
            case 4:
                set = executeQuery("select * from orders_dishes");
                while (set.next()) {
                    System.out.println(set.getString(1) + " " + set.getString(2) + " " + set.getString(3));
                }
                break;
        }
    }
}
