package edu.cqu.olmclibrarysystem.model;
/**
 * Book: Model class for Book manipulation 
 *
 * @author Tikaraj Ghising - 12129239
 */
public class Book {

    private int bookId;
    private String title;
    private Double price;
    private Author author;
    private int available;

    public Book() {
    }

    public Book(String title, Double price, Author author, int available) {
        this.title = title;
        this.price = price;
        this.author = author;
        this.available = available;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
