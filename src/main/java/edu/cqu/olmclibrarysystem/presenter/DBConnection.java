package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.enums.UserRole;
import edu.cqu.olmclibrarysystem.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ConnectionToDB: Class for DB creation/connection
 *
 * @author Tikaraj Ghising - 12129239
 */
public class DBConnection {

    private Connection connection;
    private PreparedStatement createUserTable;
    private PreparedStatement createAuthorTable;
    private PreparedStatement createBookTable;
    private PreparedStatement createDonorTable;
    private PreparedStatement createDonorDonatedBookTable;
    private PreparedStatement createMemberTable;
    private PreparedStatement createMemberBorrowedBookTable;
    private UserPresenter userPresenter;

    // static method return mysql connection
    public static Connection getConnection() {
        final String MYSQL_URL = "jdbc:mysql://localhost:3306/";
        final String DATABASE_NAME = "OLMCLibrarySystem";
        final String DATABASE_URL = MYSQL_URL + DATABASE_NAME;
        final String USER_NAME = "root";
        final String USER_PASSWORD = "root";
        PreparedStatement createDatabase;
        Connection conn = null;  // Connection object creation

        try {
            // MySQL connection without specified database name
            conn = DriverManager.getConnection(MYSQL_URL, USER_NAME, USER_PASSWORD);
            if (conn != null) {
                System.out.println("MySQL database has been connected .........");
                createDatabase = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME); // database creation if not exist
                int result = createDatabase.executeUpdate();
                if (result > 0) {
                    System.out.println(DATABASE_NAME + " database has been created successfully IF NOT EXISTS.");
                }
            }

            // database connection or selection with the specified database name
            conn = DriverManager.getConnection(DATABASE_URL, USER_NAME, USER_PASSWORD);
            if (conn != null) {
                System.out.println(DATABASE_NAME + " database has been connecting successfully");
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println("SQLException : " + e.getMessage());
        }
        return conn;
    }

    public void createTables() {
        try {
            this.connection = getConnection(); // database connection
            if (connection != null) {
                // user table creation
                createUserTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS user ("
                        + "user_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "full_name VARCHAR(100) NOT NULL,"
                        + "email VARCHAR(100) NOT NULL UNIQUE,"
                        + "phone_number VARCHAR(10) NOT NULL,"
                        + "address VARCHAR(100) NOT NULL,"
                        + "password VARCHAR(45) NOT NULL,"
                        + "role VARCHAR(20) NOT NULL,"
                        + "PRIMARY KEY (user_id) )");

                int userResult = createUserTable.executeUpdate();

                if (userResult > 0) {
                    System.out.println("User table has been created successfully.");
                    createUserTable = connection.prepareStatement("ALTER TABLE user AUTO_INCREMENT = 10001");
                    createUserTable.executeUpdate();
                }

                // author table creation
                createAuthorTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS author ("
                        + "author_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "full_name VARCHAR(100) NOT NULL,"
                        + "PRIMARY KEY (author_id) )");

                int authorResult = createAuthorTable.executeUpdate();
                if (authorResult > 0) {
                    System.out.println("Author table has been created successfully.");
                    createAuthorTable = connection.prepareStatement("ALTER TABLE author AUTO_INCREMENT = 20001");
                    createAuthorTable.executeUpdate();
                }

                // book table creation
                createBookTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS book ("
                        + "book_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "title VARCHAR(100) NOT NULL,"
                        + "price DOUBLE NOT NULL,"
                        + "available INT NOT NULL,"
                        + "author_id INT NOT NULL,"
                        + "PRIMARY KEY (book_id),"
                        + "CONSTRAINT author_id FOREIGN KEY (author_id) REFERENCES author (author_id) )");

                int bookResult = createBookTable.executeUpdate();
                if (bookResult > 0) {
                    System.out.println("Author table has been created successfully.");
                    createBookTable = connection.prepareStatement("ALTER TABLE book AUTO_INCREMENT = 30001");
                    createBookTable.executeUpdate();
                }

                // donor table creation
                createDonorTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS donor ("
                        + "donor_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "full_name VARCHAR(100) NOT NULL,"
                        + "email VARCHAR(50) DEFAULT NULL,"
                        + "phone_number VARCHAR(100) NOT NULL,"
                        + "address VARCHAR(50) DEFAULT NULL,"
                        + "PRIMARY KEY (donor_id) )");

                int donorResult = createDonorTable.executeUpdate();
                if (donorResult > 0) {
                    System.out.println("Donor table has been created successfully.");
                    createDonorTable = connection.prepareStatement("ALTER TABLE donor AUTO_INCREMENT = 50001");
                    createDonorTable.executeUpdate();
                }

                // donor_donated_book table creation
                createDonorDonatedBookTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS donor_donated_book ("
                        + "ddb_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "donor_id int NOT NULL,"
                        + "donated_book_id int NOT NULL,"
                        + "quantity int NOT NULL,"
                        + "PRIMARY KEY (ddb_id),"
                        + "FOREIGN KEY (donor_id) REFERENCES donor (donor_id),"
                        + "FOREIGN KEY (donated_book_id) REFERENCES book (book_id) )");

                int donorDonatedBookResult = createDonorDonatedBookTable.executeUpdate();
                if (donorDonatedBookResult > 0) {
                    System.out.println("donor_donated_book table has been created successfully.");
                }

                // member table creation
                createMemberTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS library_member ("
                        + "member_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "full_name VARCHAR(100) NOT NULL,"
                        + "email VARCHAR(50) DEFAULT NULL,"
                        + "phone_number VARCHAR(100) NOT NULL,"
                        + "address VARCHAR(50) DEFAULT NULL,"
                        + "PRIMARY KEY (member_id) )");

                int memberResult = createMemberTable.executeUpdate();
                if (memberResult > 0) {
                    System.out.println("Member table has been created successfully.");
                    createMemberTable = connection.prepareStatement("ALTER TABLE library_member AUTO_INCREMENT = 40001");
                    createMemberTable.executeUpdate();
                }

                // member_borrowed_book table creation
                createMemberBorrowedBookTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS member_borrowed_book ("
                        + "mbb_id int NOT NULL AUTO_INCREMENT UNIQUE,"
                        + "member_id int NOT NULL,"
                        + "borrowed_book_id int NOT NULL,"
                        + "issue_date DATE NOT NULL,"
                        + "due_date DATE NOT NULL,"
                        + "return_date DATE DEFAULT NULL,"
                        + "PRIMARY KEY (mbb_id),"
                        + "FOREIGN KEY (member_id) REFERENCES library_member (member_id),"
                        + "FOREIGN KEY (borrowed_book_id) REFERENCES book (book_id) )");

                int memberBorrowedBookResult = createMemberBorrowedBookTable.executeUpdate();
                if (memberBorrowedBookResult > 0) {
                    System.out.println("member_borrowed_book table has been created successfully.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    // main librarian initialization
    public void initializeMainLibrarian() {
        this.userPresenter = new UserPresenter();
        User mainLibrarian = new User("Admin", "admin@gmail.com", "0415317726", "Victoria");
        mainLibrarian.setPassword("admin12345");
        mainLibrarian.setRole(UserRole.MAIN_LIBRARIAN);
        
        this.userPresenter.add(mainLibrarian);
    }
}
