package databasefx;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB
{
    private static Connection conn = null;
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:dummyDB.db");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return conn;
    }
    
    private static Employee mapResultListToEmployee(ResultSet resultSet) {
        Employee employeeObject = new Employee();
        try {
            employeeObject.setId(resultSet.getInt("id"));
            employeeObject.setFirstName(resultSet.getString("firstName"));
            employeeObject.setMiddleName(resultSet.getString("middleName"));
            employeeObject.setLastName(resultSet.getString("lastName"));
            employeeObject.setEmail(resultSet.getString("email"));
            employeeObject.setPhone(resultSet.getString("phone"));
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employeeObject;

    }
    
    public static Employee getEmployee(int id) {
        Employee employeeObject = null;
        try {
            PreparedStatement preparedStatement;
            preparedStatement = getConnection().prepareStatement("SELECT * FROM Employee" + " WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employeeObject = mapResultListToEmployee(resultSet);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return employeeObject;

    }

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        try {
            Statement statement;
            statement = getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Employee");

            while (resultSet.next()) {
                Employee employeeObject = mapResultListToEmployee(resultSet);
                employeeArrayList.add(employeeObject);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return employeeArrayList;

    }

    public static boolean insertEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement;

            preparedStatement = getConnection().prepareStatement("INSERT INTO Employee(firstName, middleName, lastName, email, phone)"
                    + " VALUES(?, ?, ?, ?, ?)");

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getMiddleName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getPhone());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean deleteEmployee(int id) {
        try {
            PreparedStatement preparedStatement;

            preparedStatement = getConnection().prepareStatement("DELETE FROM Employee WHERE id=?");
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean updateEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement;

            preparedStatement = getConnection().prepareStatement("UPDATE Employee"
                    + " SET firstName=?, middleName=?, lastName=?, email=?, phone=?"
                    + " WHERE id=?");

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getMiddleName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getPhone());
            preparedStatement.setInt(6, employee.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
   
}
