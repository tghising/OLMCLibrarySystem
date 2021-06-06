package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.enums.UserRole;
import edu.cqu.olmclibrarysystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserPersister: Persister for User
 *
 * @author Tikaraj Ghising
 */
public class UserPersister {

    private Connection connection; // Connection object creation
    private PreparedStatement createUserTable;
    private PreparedStatement findAllUsers;
    private PreparedStatement findUserByEmail;
    private PreparedStatement insertUser;
    private PreparedStatement updateUserPasswordByUserId;
    private PreparedStatement deleteUserById;
    private PreparedStatement loginUserByEmailAndPassword;

    // constructor connect MySQL database, create database if not exist, create tables if not exist, insert records into tables
    public UserPersister() {
        try {
            this.connection = DBConnection.getConnection(); // database connection
            if (connection != null) {              

                insertUser = connection.prepareStatement("INSERT INTO user (full_name, email, phone_number, address, password, role) "
                        + "VALUES(?, ?, ?, ?, ?, ?)");
                deleteUserById = connection.prepareStatement("DELETE FROM user WHERE user_id = ?");
                findAllUsers = connection.prepareStatement("SELECT * FROM user");
                updateUserPasswordByUserId = connection.prepareStatement("UPDATE user SET password = ? WHERE user_id = ?");
                findUserByEmail = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
                loginUserByEmailAndPassword = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");

            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    // method to find User by Email and return User
    public User findUserByEmail(String email) {
        User user = new User();
        try {
            findUserByEmail.setString(1, email);
            ResultSet findResult = findUserByEmail.executeQuery();
            if (findResult.next()) {
                int userId = findResult.getInt("user_id");
                String fullName = findResult.getString("full_name");
                String userEmail = findResult.getString("email");
                String phoneNumber = findResult.getString("phone_number");
                String address = findResult.getString("address");
                String role = findResult.getString("role");

                user.setUserId(userId);
                user.setFullName(fullName);
                user.setEmail(userEmail);
                user.setAddress(address);
                user.setPhoneNumber(phoneNumber);
                user.setRole(UserRole.getType(role));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            ResultSet findResult = findAllUsers.executeQuery();
            while (findResult.next()) {
                int userId = findResult.getInt("user_id");
                String fullName = findResult.getString("full_name");
                String userEmail = findResult.getString("email");
                String phoneNumber = findResult.getString("phone_number");
                String address = findResult.getString("address");
                String role = findResult.getString("role");
                User user = new User();
                user.setUserId(userId);
                user.setFullName(fullName);
                user.setEmail(userEmail);
                user.setAddress(address);
                user.setPhoneNumber(phoneNumber);
                user.setRole(UserRole.getType(role));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return userList;
    }

    // method to check given email and password matched user
    public boolean isUserExist(String email, String password) {
        try {
            loginUserByEmailAndPassword.setString(1, email);
            loginUserByEmailAndPassword.setString(2, password);
            ResultSet loginResult = loginUserByEmailAndPassword.executeQuery();
            if (loginResult.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return false;
    }

    // method to register user
    public void updateUserPassword(int userId, String password) {
        try {
            updateUserPasswordByUserId.setString(1, password);
            updateUserPasswordByUserId.setInt(2, userId);
            updateUserPasswordByUserId.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    // method to register user
    public void registerUser(User user) {
        try {
            insertUser.setString(1, user.getFullName());
            insertUser.setString(2, user.getEmail());
            insertUser.setString(3, user.getPhoneNumber());
            insertUser.setString(4, user.getAddress());
            insertUser.setString(5, user.getPassword());
            insertUser.setString(6, user.getRole().toString());

            insertUser.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    // method to delete user
    public void deleteUser(int id) {
        try {
            deleteUserById.setInt(1, id);
            int deleteResult = deleteUserById.executeUpdate();
            if (deleteResult > 0) {
                System.out.println("The user has been deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
