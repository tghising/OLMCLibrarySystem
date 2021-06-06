package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Donor;
import edu.cqu.olmclibrarysystem.model.DonorDonatedBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DonorPersister: Persister for Donor
 *
 * @author Tikaraj Ghising
 */
public class DonorPersister {

    private Connection connection; // Connection object creation
    private PreparedStatement insertDonor;
    private PreparedStatement insertBook;
    private PreparedStatement findDonorByDonorId;
    private PreparedStatement deleteBookFromDonorByBookId;
    private PreparedStatement findAllAuthors;
    private PreparedStatement insertDonorDonatedBook;
    private PreparedStatement findAllDonorDonatedBookByDonorId;
    private PreparedStatement findAllDonors;
    private PreparedStatement findAllBooks;
    private PreparedStatement findAuthorByAuthorId;
    private PreparedStatement findAllDonorDonatedBook;
    private PreparedStatement findDonorById;
    private PreparedStatement findBookByID;
    private BookPersister bookPersister;

    // constructor connect MySQL database, create database if not exist, create tables if not exist, insert records into tables and
    public DonorPersister() {
        this.bookPersister = new BookPersister();
        try {
            this.connection = DBConnection.getConnection(); // database connection
            if (connection != null) {                

                insertDonor = connection.prepareStatement("INSERT INTO donor (full_name, email, phone_number, address) "
                        + "VALUES(?, ?, ?, ?)");
                insertDonorDonatedBook = connection.prepareStatement("INSERT INTO donor_donated_book (donor_id, donated_book_id, quantity) "
                        + "VALUES(?, ?, ?)");
                findAllAuthors = connection.prepareStatement("SELECT * FROM author ");
                deleteBookFromDonorByBookId = connection.prepareStatement("DELETE FROM donor_donated_book WHERE donated_book_id = ?");
                findDonorByDonorId = connection.prepareStatement("SELECT * FROM donor WHERE donor_id = ?");
                findAllDonorDonatedBookByDonorId = connection.prepareStatement("SELECT * FROM donor_donated_book WHERE donor_id = ?");
                findAllDonors = connection.prepareStatement("SELECT * FROM donor");
                insertBook = connection.prepareStatement("INSERT INTO book (title, price, available, author_id) "
                        + "VALUES(?, ?, ?, ?)");
                findAllBooks = connection.prepareStatement("SELECT * FROM book");
                findAuthorByAuthorId = connection.prepareStatement("SELECT * FROM author WHERE author_id = ?");
                findAllDonorDonatedBook = connection.prepareStatement("SELECT * FROM donor_donated_book;");
                findDonorById = connection.prepareStatement("SELECT * FROM donor WHERE donor_id = ?");
                findBookByID = connection.prepareStatement("SELECT * FROM book WHERE book_id = ?");
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    public void addDonor(Donor donor) {
        try {
            insertDonor.setString(1, donor.getFullName());
            insertDonor.setString(2, donor.getEmail());
            insertDonor.setString(3, donor.getPhoneNumber());
            insertDonor.setString(4, donor.getAddress());

            insertDonor.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void deleteBookDonorByBookId(int id) {
        try {
            deleteBookFromDonorByBookId.setInt(1, id);
            deleteBookFromDonorByBookId.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void addDonorDonatedBook(DonorDonatedBook donatedBook) {
        try {
            insertDonorDonatedBook.setInt(1, donatedBook.getDonor().getDonorId());
            insertDonorDonatedBook.setInt(2, donatedBook.getBook().getBookId());
            insertDonorDonatedBook.setInt(3, donatedBook.getQuantity());
            insertDonorDonatedBook.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public List<DonorDonatedBook> findAllDonorDonatedBookByDonorId(int id) {
        List<DonorDonatedBook> donatedBooks = new ArrayList<DonorDonatedBook>();
        try {
            // find the donor details from database table
            Donor bookDonor = new Donor();
            findDonorByDonorId.setInt(1, id);
            ResultSet donorResult = findDonorByDonorId.executeQuery();
            while (donorResult.next()) {
                int donor_id = donorResult.getInt("donor_id");
                String fullName = donorResult.getString("full_name");
                String email = donorResult.getString("email");
                String phoneNumber = donorResult.getString("phone_number");
                String address = donorResult.getString("address");
                bookDonor.setDonorId(donor_id);
                bookDonor.setFullName(fullName);
                bookDonor.setEmail(email);
                bookDonor.setPhoneNumber(phoneNumber);
                bookDonor.setAddress(address);
            }

            if (bookDonor != null) {
                findAllDonorDonatedBookByDonorId.setInt(1, id);
                ResultSet result = findAllDonorDonatedBookByDonorId.executeQuery();

                while (result.next()) {
                    int donatedBookId = result.getInt("donated_book_id");
                    int quantity = result.getInt("quantity");
                    // find donor donated book
                    Book donatedBook = bookPersister.getBookByBookId(donatedBookId);
                    DonorDonatedBook donorDonatedBook = new DonorDonatedBook(donatedBook, bookDonor, quantity);
                    donatedBooks.add(donorDonatedBook);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return donatedBooks;
    }

    public List<Donor> findAllDonors() {
        List<Donor> allDonors = new ArrayList<>();

        try {
            ResultSet allDonorsResult = findAllDonors.executeQuery();

            while (allDonorsResult.next()) {
                int donor_id = allDonorsResult.getInt("donor_id");
                String fullName = allDonorsResult.getString("full_name");
                String email = allDonorsResult.getString("email");
                String phoneNumber = allDonorsResult.getString("phone_number");
                String address = allDonorsResult.getString("address");

                Donor donorEntry = new Donor();
                donorEntry.setDonorId(donor_id);
                donorEntry.setFullName(fullName);
                donorEntry.setEmail(email);
                donorEntry.setPhoneNumber(phoneNumber);
                donorEntry.setAddress(address);

                allDonors.add(donorEntry);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return allDonors;
    }

    public List<Author> findAllAuthors() {
        List<Author> authorList = new ArrayList<Author>();
        try {
            ResultSet findResult = findAllAuthors.executeQuery();
            while (findResult.next()) {
                int authorId = findResult.getInt("author_id");
                String fullName = findResult.getString("full_name");
                Author author = new Author();
                author.setAuthorId(authorId);
                author.setFullName(fullName);
                authorList.add(author);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return authorList;
    }

    public void addDonorDonatedBook(Book book) {
        try {
            String idd = book.getTitle().substring(0, 3);
            insertBook.setString(1, book.getTitle());
            insertBook.setDouble(2, book.getPrice());
            insertBook.setInt(3, book.getAvailable());
            insertBook.setInt(4, book.getAuthor().getAuthorId());

            insertBook.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public List<Book> findAllBooks() {
        List<Book> bookList = new ArrayList<Book>();
        try {
            ResultSet bookResult = findAllBooks.executeQuery();

            System.out.println("Books details reading from the database.");
            while (bookResult.next()) {
                int bookId = bookResult.getInt("book_id");
                String title = bookResult.getString("title");
                Double price = bookResult.getDouble("price");
                int available = bookResult.getInt("available");

                int authorId = bookResult.getInt("author_id");
                Author author = findAuthorByAuthorId(authorId);

                Book book = new Book(title, price, author, available);
                book.setBookId(bookId);
                bookList.add(book);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return bookList;
    }

    public Author findAuthorByAuthorId(int id) {
        Author author = new Author();
        try {
            findAuthorByAuthorId.setInt(1, id);
            ResultSet findResult = findAuthorByAuthorId.executeQuery();
            if (findResult.next()) {
                int authorId = findResult.getInt("author_id");
                String fullName = findResult.getString("full_name");

                author.setAuthorId(authorId);
                author.setFullName(fullName);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return author;
    }

    public List<DonorDonatedBook> findAllDonorDonatedBook() {
        List<DonorDonatedBook> donorDonatedBooks = new ArrayList<>();

        try {
            ResultSet donorDonatedBookResult = findAllDonorDonatedBook.executeQuery();

            while (donorDonatedBookResult.next()) {
                DonorDonatedBook donorDonatedBook = new DonorDonatedBook();
                donorDonatedBook.setDdbID(donorDonatedBookResult.getInt("ddb_id"));
                // finding the accompanying Donor using ID for the DonorDonatedBook object
                Integer donorID = donorDonatedBookResult.getInt("donor_id");
                findDonorById.setInt(1, donorID);

                ResultSet donorResult = findDonorById.executeQuery();

                while (donorResult.next()) {
                    Donor donorEntry = new Donor();

                    Integer donorIDEntry = donorResult.getInt("donor_id");
                    String donorFullNameEntry = donorResult.getString("full_name");
                    String donorEmailEntry = donorResult.getString("email");
                    String donorPhoneNumberEntry = donorResult.getString("phone_number");
                    String donorAddressEntry = donorResult.getString("address");

                    donorEntry.setDonorId(donorIDEntry);
                    donorEntry.setFullName(donorFullNameEntry);
                    donorEntry.setEmail(donorEmailEntry);
                    donorEntry.setPhoneNumber(donorPhoneNumberEntry);
                    donorEntry.setAddress(donorAddressEntry);

                    donorDonatedBook.setDonor(donorEntry);
                }

                // finding the accompanying Book using ID for DonorDonatedBook object
                Integer bookID = donorDonatedBookResult.getInt("donated_book_id");
                findBookByID.setInt(1, bookID);

                ResultSet bookResult = findBookByID.executeQuery();

                while (bookResult.next()) {
                    Book bookEntry = new Book();

                    Integer bookIDEntry = bookResult.getInt("book_id");
                    String bookTitleEntry = bookResult.getString("title");
                    Double bookPriceEntry = bookResult.getDouble("price");
                    Integer bookAvailableEntry = bookResult.getInt("available");
                    Author bookAuthorEntry = this.findAuthorByAuthorId(bookResult.getInt("author_id"));

                    bookEntry.setBookId(bookIDEntry);
                    bookEntry.setTitle(bookTitleEntry);
                    bookEntry.setPrice(bookPriceEntry);
                    bookEntry.setAvailable(bookAvailableEntry);
                    bookEntry.setAuthor(bookAuthorEntry);

                    donorDonatedBook.setBook(bookEntry);
                }

                // setting the quantity donated in DonorDonatedBook object
                Integer donatedQuantity = donorDonatedBookResult.getInt("quantity");

                donorDonatedBook.setQuantity(donatedQuantity);

                donorDonatedBooks.add(donorDonatedBook);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return donorDonatedBooks;
    }
}
