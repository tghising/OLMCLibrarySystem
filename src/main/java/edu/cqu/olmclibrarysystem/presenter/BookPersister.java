package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.enums.UserRole;
import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Member;
import edu.cqu.olmclibrarysystem.util.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BookPersister: Persister for Book
 *
 * @author Tikaraj Ghising - 12129239 | Frederick Apostol - 12129780 || Patrick
 * Angelo Ferrer Amable - 12136440 || Patel Utsavkumar - 12126577
 */
public class BookPersister {

    private Connection connection; // Connection object creation
    private PreparedStatement findAuthorByAuthorId;
    private PreparedStatement findAllAuthors;
    private PreparedStatement insertAuthor;

    private PreparedStatement insertBook;
    private PreparedStatement deleteBookById;
    private PreparedStatement findAllBooksByAuthorId;
    private PreparedStatement findAllBooks;
    private PreparedStatement findAllBooksByBookId;
    private PreparedStatement findAllBooksByTitle;
    private PreparedStatement findAllBooksByAuthor;
    private PreparedStatement findAuthorByFullname;
    private PreparedStatement findMemberIDsWithIssuedBooks;
    private PreparedStatement findMemberByMemberId;
    private PreparedStatement findBookIDsIssuedToMember;
    private PreparedStatement updateReturnDate;
    private PreparedStatement updateBookStocksAfterReturn;
    private DatabaseUtility utility;

    // constructor connect MySQL database, create database if not exist, create tables if not exist, insert records into tables and
    public BookPersister() {
        utility = new DatabaseUtility();
        try {
            this.connection = DBConnection.getConnection(); // database connection
            if (connection != null) {

                insertAuthor = connection.prepareStatement("INSERT INTO author (full_name) VALUES(?) ");

                findAllAuthors = connection.prepareStatement("SELECT * FROM author ");
                findAuthorByAuthorId = connection.prepareStatement("SELECT * FROM author WHERE author_id = ?");

                insertBook = connection.prepareStatement("INSERT INTO book (title, price, available, author_id) "
                        + "VALUES(?, ?, ?, ?)");
                deleteBookById = connection.prepareStatement("DELETE FROM book WHERE book_id = ?");
                findAllBooks = connection.prepareStatement("SELECT * FROM book");
                findAllBooksByAuthorId = connection.prepareStatement("SELECT * FROM book WHERE author_id = ?");
                findAllBooksByBookId = connection.prepareStatement("SELECT * FROM book WHERE book_id = ?");
                findAllBooksByTitle = connection.prepareStatement("SELECT * FROM book  WHERE title = ?");
                findAuthorByFullname = connection.prepareStatement("SELECT * FROM author WHERE full_name = ?");
                findAllBooksByAuthor = connection.prepareStatement("SELECT * FROM book WHERE author_id = ?");
                findMemberIDsWithIssuedBooks = connection.prepareStatement("SELECT DISTINCT member_id FROM member_borrowed_book WHERE return_date IS NULL");
                findMemberByMemberId = connection.prepareStatement("SELECT * FROM library_member WHERE member_id = ?");
                findBookIDsIssuedToMember = connection.prepareStatement("SELECT borrowed_book_id FROM member_borrowed_book WHERE member_id = ? AND return_date IS NULL");
                updateReturnDate = connection.prepareStatement("UPDATE member_borrowed_book SET return_date = ? WHERE member_id = ? AND borrowed_book_id = ?");
                updateBookStocksAfterReturn = connection.prepareStatement("UPDATE book SET available = available + 1 WHERE book_id = ?");
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    // overloading method to add book to database table
    public void add(Book book) {
        try {
            insertBook.setString(1, book.getTitle());
            insertBook.setDouble(2, book.getPrice());
            insertBook.setInt(3, book.getAvailable());
            insertBook.setInt(4, book.getAuthor().getAuthorId());

            insertBook.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    // method to find Author by Author id
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
    // method to find Author by Author id

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

    // method to register author
    public void addAuthor(Author author) {
        try {
            insertAuthor.setString(1, author.getFullName());

            insertAuthor.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

    }

    // overloading method to fetch all books of given user from database table
    public List<Book> getAllByAuthorId(int id) {
        List<Book> books = new ArrayList<Book>();
        try {
            findAllBooksByAuthorId.setInt(1, id);
            ResultSet bookResult = findAllBooksByAuthorId.executeQuery();

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
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return books;
    }

    public Book getBookByBookId(int id) {
        Book book = new Book();
        try {
            findAllBooksByBookId.setInt(1, id);
            ResultSet bookResult = findAllBooksByBookId.executeQuery();

            System.out.println("Books details reading from the database.");
            while (bookResult.next()) {
                int bookId = bookResult.getInt("book_id");
                String title = bookResult.getString("title");
                Double price = bookResult.getDouble("price");
                int available = bookResult.getInt("available");

                int authorId = bookResult.getInt("author_id");
                Author author = findAuthorByAuthorId(authorId);

                book = new Book(title, price, author, available);
                book.setBookId(bookId);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return book;
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

    public String deleteBook(int id) {
        String deleteInfo = "";
        try {
            if (UserContext.getInstance().getLoggedUser().getRole().equals(UserRole.MAIN_LIBRARIAN)) {
                deleteBookById.setInt(1, id);
                int bookResult = deleteBookById.executeUpdate();

                if (bookResult > 0) {
                    deleteInfo = "The book has been deleted successfully.";
                } else {
                    deleteInfo = "The book has been associated with donor or borrowed by member.";
                }
            } else {
                System.out.println("You haven't right access to delete book.");
            }
        } catch (SQLException e) {
            deleteInfo = "The book has been associated with donor or borrowed by member.";
            System.out.println("The book has been associated with donor or borrowed by member: " + e.getMessage());
        }
        return deleteInfo;
    }

    public List<Book> searchBooksByTitle(String bookTitle) {
        List<Book> searchResultList = new ArrayList();

        try {
            findAllBooksByTitle.setString(1, bookTitle);

            ResultSet findAllBooksByTitleResultSet = findAllBooksByTitle.executeQuery();

            while (findAllBooksByTitleResultSet.next()) {
                Book searchResultEntry = new Book();

                Integer bookIdEntry = findAllBooksByTitleResultSet.getInt("book_id");
                String bookTitleEntry = findAllBooksByTitleResultSet.getString("title");
                Double booktPriceEntry = findAllBooksByTitleResultSet.getDouble("price");
                Integer bookAvailableEntry = findAllBooksByTitleResultSet.getInt("available");

                Integer bookAuthorID = findAllBooksByTitleResultSet.getInt("author_id");
                Author bookAuthorEntry = findAuthorByAuthorId(bookAuthorID);

                searchResultEntry.setBookId(bookIdEntry);
                searchResultEntry.setTitle(bookTitleEntry);
                searchResultEntry.setPrice(booktPriceEntry);
                searchResultEntry.setAvailable(bookAvailableEntry);
                searchResultEntry.setAuthor(bookAuthorEntry);

                searchResultList.add(searchResultEntry);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return searchResultList;
    }

    public List<Book> searchBooksByAuthor(String bookAuthor) {
        List<Book> searchResultList = new ArrayList();

        try {
            Integer authorID = findAuthorIdByName(bookAuthor);

            findAllBooksByAuthor.setInt(1, authorID);

            ResultSet searchBooksByAuthorResultSet = findAllBooksByAuthor.executeQuery();

            while (searchBooksByAuthorResultSet.next()) {
                Book searchResultEntry = new Book();

                Integer bookIdEntry = searchBooksByAuthorResultSet.getInt("book_id");
                String bookTitleEntry = searchBooksByAuthorResultSet.getString("title");
                Double booktPriceEntry = searchBooksByAuthorResultSet.getDouble("price");
                Integer bookAvailableEntry = searchBooksByAuthorResultSet.getInt("available");

                Integer bookAuthorID = searchBooksByAuthorResultSet.getInt("author_id");
                Author bookAuthorEntry = findAuthorByAuthorId(bookAuthorID);

                searchResultEntry.setBookId(bookIdEntry);
                searchResultEntry.setTitle(bookTitleEntry);
                searchResultEntry.setPrice(booktPriceEntry);
                searchResultEntry.setAvailable(bookAvailableEntry);
                searchResultEntry.setAuthor(bookAuthorEntry);

                searchResultList.add(searchResultEntry);

            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return searchResultList;
    }

    private Integer findAuthorIdByName(String bookAuthor) {
        Integer searchResult = null;
        try {
            findAuthorByFullname.setString(1, bookAuthor);
            ResultSet searchAuthorID = findAuthorByFullname.executeQuery();

            if (searchAuthorID.first()) {
                searchResult = searchAuthorID.getInt("author_id");
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return searchResult;
    }

    public List<Member> findAllMembersWithIssuedBooks() {
        List<Member> membersWithIssuedBooks = new ArrayList<>();

        try {

            ResultSet memberIDsWithIssuedBooks = findMemberIDsWithIssuedBooks.executeQuery();

            while (memberIDsWithIssuedBooks.next()) {
                Integer memberID = memberIDsWithIssuedBooks.getInt("member_id");

                Member member = this.findMemberByMemberId(memberID);

                membersWithIssuedBooks.add(member);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return membersWithIssuedBooks;
    }

    private Member findMemberByMemberId(int id) {
        Member findMember = new Member();
        try {
            findMemberByMemberId.setInt(1, id);
            ResultSet memberResult = findMemberByMemberId.executeQuery();
            while (memberResult.next()) {
                int memberId = memberResult.getInt("member_id");
                String fullName = memberResult.getString("full_name");
                String email = memberResult.getString("email");
                String phoneNumber = memberResult.getString("phone_number");
                String address = memberResult.getString("address");
                findMember = new Member(fullName, email, phoneNumber, address);
                findMember.setMemberId(memberId);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return findMember;
    }

    public List<Book> findBooksIssuedToMember(Integer selectedMemberID) {
        List<Book> booksIssuedToMember = new ArrayList<>();

        try {
            findBookIDsIssuedToMember.setInt(1, selectedMemberID);

            ResultSet bookIDsIssuedToMemberResultSet = findBookIDsIssuedToMember.executeQuery();

            while (bookIDsIssuedToMemberResultSet.next()) {
                Integer bookID = bookIDsIssuedToMemberResultSet.getInt("borrowed_book_id");

                Book book = getBookByBookId(bookID);

                booksIssuedToMember.add(book);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return booksIssuedToMember;
    }

    public void returnBook(Date currentDate, Member selectedMember, Book selectedBook) {
        try {
            updateReturnDate.setDate(1, utility.convertToSQLDate(currentDate));
            updateReturnDate.setInt(2, selectedMember.getMemberId());
            updateReturnDate.setInt(3, selectedBook.getBookId());

            updateReturnDate.executeUpdate();

            updateBookStocksAfterReturn.setInt(1, selectedBook.getBookId());
            updateBookStocksAfterReturn.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
