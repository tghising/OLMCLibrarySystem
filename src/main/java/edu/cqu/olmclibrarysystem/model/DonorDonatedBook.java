package edu.cqu.olmclibrarysystem.model;

import java.util.Date;

/**
 * DonorDonatedBook: Model class for Donor Donated Book registration
 *
 * @author Tikaraj Ghising - 12129239
 */
public class DonorDonatedBook {
    
    int ddbID;
    private Donor donor;
    private Book donatedBook;
    private int quantity;
    private Date donatedDate = new Date();

    public DonorDonatedBook() {
    }

    public DonorDonatedBook(Book book, Donor donor, int quantity) {
        this.donatedBook = book;
        this.donor = donor;
        this.quantity = quantity;
    }

    public int getDdbID() {
        return ddbID;
    }

    public void setDdbID(int ddbID) {
        this.ddbID = ddbID;
    }

    public Book getDonatedBook() {
        return donatedBook;
    }

    public void setDonatedBook(Book donatedBook) {
        this.donatedBook = donatedBook;
    }
    
    

    public Book getBook() {
        return donatedBook;
    }

    public void setBook(Book book) {
        this.donatedBook = book;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDonatedDate() {
        return donatedDate;
    }

    public void setDonatedDate(Date donatedDate) {
        this.donatedDate = donatedDate;
    }
}

