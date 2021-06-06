package edu.cqu.olmclibrarysystem.model;

import java.util.Date;

/**
 * MemberBorrowedBook: Model class for Member Borrowed Book manipulation
 *
 * @author Tikaraj Ghising - 12129239
 */
public class MemberBorrowedBook {

    int mbbID;
    private Member borrower;
    private Book borrowedBook;
    private Date issueDate = new Date();
    private Date dueDate;
    private Date returnDate;

    public MemberBorrowedBook() {
    }

    public MemberBorrowedBook(Member member, Book book, Date issueDate, Date dueDate, Date returnDate) {
        this.borrower = member;
        this.borrowedBook = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public MemberBorrowedBook(Member member, Book book, Date issueDate, Date dueDate) {
        this.borrower = member;
        this.borrowedBook = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getMbbID() {
        return mbbID;
    }

    public void setMbbID(int mbbID) {
        this.mbbID = mbbID;
    }
    
    

    public Member getBorrowerMember() {
        return borrower;
    }

    public void setBorrowerMember(Member member) {
        this.borrower = member;
    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(Book borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
