package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Member;
import edu.cqu.olmclibrarysystem.model.MemberBorrowedBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MemberPersister: Persister for Member
 *
 * @author Tikaraj Ghising
 */
public class MemberPersister {

    private Connection connection; // Connection object creation
    private PreparedStatement insertMember;
    private PreparedStatement findAllMembers;
    private PreparedStatement findMemberByMemberId;
    private PreparedStatement deleteBookFromMemberByBookId;
    private PreparedStatement findAllMemberBorrowedBooks;
    private PreparedStatement findAllMemberBorrowedOverDueBooks;
    private PreparedStatement insertMemberBorrowedBook;
    private PreparedStatement updateReturnDateDonorDonatedBook;
    private PreparedStatement findAllMemberBorrowedBookByMemberId;
    private PreparedStatement findAllOverDueReturnsBorrowedBooks;
    private PreparedStatement updateBookStocksAfterIssue;
    

    private BookPersister bookPersister;
    private DatabaseUtility utility;

    // constructor connect MySQL database, create database if not exist, create tables if not exist, insert records into tables and
    public MemberPersister() {
        utility = new DatabaseUtility();
        bookPersister = new BookPersister();
        try {
            this.connection = DBConnection.getConnection(); // database connection
            if (connection != null) {   
                
                insertMember = connection.prepareStatement("INSERT INTO library_member (full_name, email, phone_number, address) "
                        + "VALUES(?, ?, ?, ?)");
                insertMemberBorrowedBook = connection.prepareStatement("INSERT INTO member_borrowed_book (member_id, borrowed_book_id, issue_date, due_date) "
                        + "VALUES(?, ?, ?, ?)");

                updateReturnDateDonorDonatedBook = connection.prepareStatement("UPDATE member_borrowed_book SET return_date = ? WHERE member_id = ? AND borrowed_book_id = ?");

                findAllMembers = connection.prepareStatement("SELECT * FROM library_member");
                findMemberByMemberId = connection.prepareStatement("SELECT * FROM library_member WHERE member_id = ?");
                findAllMemberBorrowedBookByMemberId = connection.prepareStatement("SELECT * FROM member_borrowed_book WHERE member_id = ?");
                deleteBookFromMemberByBookId = connection.prepareStatement("DELETE FROM member_borrowed_book WHERE borrowed_book_id = ?");
                findAllMemberBorrowedBooks = connection.prepareStatement("SELECT * FROM member_borrowed_book WHERE issue_date IS NOT NULL AND return_date IS NULL");
                findAllMemberBorrowedOverDueBooks = connection.prepareStatement("SELECT * FROM member_borrowed_book WHERE due_date < NOW() AND return_date IS NULL");
                findAllOverDueReturnsBorrowedBooks = connection.prepareStatement("SELECT * FROM member_borrowed_book WHERE return_date IS NOT NULL AND return_date > due_date");
                updateBookStocksAfterIssue = connection.prepareStatement("UPDATE book SET available = available - 1 WHERE book_id = ?");
                
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    public void addMember(Member member) {
        try {
            insertMember.setString(1, member.getFullName());
            insertMember.setString(2, member.getEmail());
            insertMember.setString(3, member.getPhoneNumber());
            insertMember.setString(4, member.getAddress());

            insertMember.executeUpdate();  // execute the prepared statement insert
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public List<Member> findAllMembers() {
        List<Member> memberList = new ArrayList<Member>();
        try {
            ResultSet memberResult = findAllMembers.executeQuery();
            while (memberResult.next()) {
                int memberId = memberResult.getInt("member_id");
                String fullName = memberResult.getString("full_name");
                String email = memberResult.getString("email");
                String phoneNumber = memberResult.getString("phone_number");
                String address = memberResult.getString("address");
                Member findMember = new Member(fullName, email, phoneNumber, address);
                findMember.setMemberId(memberId);
                memberList.add(findMember);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return memberList;
    }

    public void addMemberBorrowedBook(MemberBorrowedBook borrowedBook) {
        try {
            insertMemberBorrowedBook.setInt(1, borrowedBook.getBorrowerMember().getMemberId());
            insertMemberBorrowedBook.setInt(2, borrowedBook.getBorrowedBook().getBookId());
            insertMemberBorrowedBook.setDate(3, utility.convertToSQLDate(borrowedBook.getIssueDate()));
            insertMemberBorrowedBook.setDate(4, utility.convertToSQLDate(borrowedBook.getDueDate()));
            insertMemberBorrowedBook.executeUpdate();  // execute the prepared statement insert
            
            updateBookStocksAfterIssue.setInt(1, borrowedBook.getBorrowedBook().getBookId());
            updateBookStocksAfterIssue.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void deleteMemberBookByBookId(int id) {
        try {
            deleteBookFromMemberByBookId.setInt(1, id);
            int memberResult = deleteBookFromMemberByBookId.executeUpdate();
            if (memberResult > 0) {
                System.out.println("The book has been successfully deleted.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void returnMemberBorrowedBook(MemberBorrowedBook borrowedBook) {
        try {
            updateReturnDateDonorDonatedBook.setDate(1, utility.convertToSQLDate(borrowedBook.getReturnDate()));
            updateReturnDateDonorDonatedBook.setInt(2, borrowedBook.getBorrowerMember().getMemberId());
            updateReturnDateDonorDonatedBook.setInt(3, borrowedBook.getBorrowedBook().getBookId());
            updateReturnDateDonorDonatedBook.executeUpdate();  // execute the prepared statement insert
            
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public Member findMemberByMemberId(int id) {
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

    public List<MemberBorrowedBook> getAllMemberBorrowedOverDueBooks() {
        List<MemberBorrowedBook> borrowedBooksList = new ArrayList<MemberBorrowedBook>();
        try {
            ResultSet borrowedBookResult = findAllMemberBorrowedOverDueBooks.executeQuery();
            while (borrowedBookResult.next()) {
                int memberId = borrowedBookResult.getInt("member_id");
                int borrowedBookId = borrowedBookResult.getInt("borrowed_book_id");
                Date issueDate = borrowedBookResult.getDate("issue_date");
                Date dueDate = borrowedBookResult.getDate("due_date");
                Date returnDate = borrowedBookResult.getDate("return_date");
                System.out.println("returnDate = " + returnDate);
                System.out.println("member_id = " + memberId);

                Member borrowedMember = findMemberByMemberId(memberId);
                Book borrowedBook = bookPersister.getBookByBookId(borrowedBookId);
                System.out.println("borrowedBook = " + borrowedBook);

                MemberBorrowedBook memberBorrowedBook = new MemberBorrowedBook();
                memberBorrowedBook.setBorrowerMember(borrowedMember);
                memberBorrowedBook.setBorrowedBook(borrowedBook);
                memberBorrowedBook.setIssueDate(issueDate);
                memberBorrowedBook.setDueDate(dueDate);
                memberBorrowedBook.setReturnDate(returnDate);

                borrowedBooksList.add(memberBorrowedBook);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return borrowedBooksList;
    }

    public List<MemberBorrowedBook> getAllMemberBorrowedBooks() {
        List<MemberBorrowedBook> borrowedBooksList = new ArrayList<MemberBorrowedBook>();
        try {
            ResultSet borrowedBookResult = findAllMemberBorrowedBooks.executeQuery();
            while (borrowedBookResult.next()) {
                int memberId = borrowedBookResult.getInt("member_id");
                int borrowedBookId = borrowedBookResult.getInt("borrowed_book_id");
                Date issueDate = borrowedBookResult.getDate("issue_date");
                Date dueDate = borrowedBookResult.getDate("due_date");
                Date returnDate = borrowedBookResult.getDate("return_date");

                Member borrowedMember = findMemberByMemberId(memberId);
                Book borrowedBook = bookPersister.getBookByBookId(borrowedBookId);

                MemberBorrowedBook memberBorrowedBook = new MemberBorrowedBook();
                memberBorrowedBook.setBorrowerMember(borrowedMember);
                memberBorrowedBook.setBorrowedBook(borrowedBook);
                memberBorrowedBook.setIssueDate(issueDate);
                memberBorrowedBook.setDueDate(dueDate);
                memberBorrowedBook.setReturnDate(returnDate);

                borrowedBooksList.add(memberBorrowedBook);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return borrowedBooksList;
    }

    public List<MemberBorrowedBook> getAllMemberBorrowedBookByMemberId(int id) {
        List<MemberBorrowedBook> borrowedBooksList = new ArrayList<MemberBorrowedBook>();
        try {
            findAllMemberBorrowedBookByMemberId.setInt(1, id);

            ResultSet borrowedBookResult = findAllMemberBorrowedBookByMemberId.executeQuery();
            while (borrowedBookResult.next()) {
                int memberId = borrowedBookResult.getInt("member_id");
                int borrowedBookId = borrowedBookResult.getInt("borrowed_book_id");
                Date issueDate = borrowedBookResult.getDate("issue_date");
                Date dueDate = borrowedBookResult.getDate("due_date");
                Date returnDate = borrowedBookResult.getDate("return_date");

                Member borrowedMember = findMemberByMemberId(memberId);
                Book borrowedBook = bookPersister.getBookByBookId(borrowedBookId);

                MemberBorrowedBook memberBorrowedBook = new MemberBorrowedBook();
                memberBorrowedBook.setBorrowerMember(borrowedMember);
                memberBorrowedBook.setBorrowedBook(borrowedBook);
                memberBorrowedBook.setIssueDate(issueDate);
                memberBorrowedBook.setDueDate(dueDate);
                memberBorrowedBook.setReturnDate(returnDate);

                borrowedBooksList.add(memberBorrowedBook);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return borrowedBooksList;
    }

    public List<MemberBorrowedBook> getAllOverDueReturnsBorrowedBooks() {
        List<MemberBorrowedBook> borrowedBooksList = new ArrayList<MemberBorrowedBook>();
        try {
            ResultSet borrowedBookResult = findAllOverDueReturnsBorrowedBooks.executeQuery();
            while (borrowedBookResult.next()) {
                int memberId = borrowedBookResult.getInt("member_id");
                int borrowedBookId = borrowedBookResult.getInt("borrowed_book_id");
                Date issueDate = borrowedBookResult.getDate("issue_date");
                Date dueDate = borrowedBookResult.getDate("due_date");
                Date returnDate = borrowedBookResult.getDate("return_date");

                Member borrowedMember = findMemberByMemberId(memberId);
                Book borrowedBook = bookPersister.getBookByBookId(borrowedBookId);

                MemberBorrowedBook memberBorrowedBook = new MemberBorrowedBook();
                memberBorrowedBook.setBorrowerMember(borrowedMember);
                memberBorrowedBook.setBorrowedBook(borrowedBook);
                memberBorrowedBook.setIssueDate(issueDate);
                memberBorrowedBook.setDueDate(dueDate);
                memberBorrowedBook.setReturnDate(returnDate);

                borrowedBooksList.add(memberBorrowedBook);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return borrowedBooksList;
    }
}
