package edu.cqu.olmclibrarysystem.presenter;

import javafx.event.ActionEvent;

/**
 * IViewBook: View Interface for Books
 *
 * @author Tikaraj Ghising
 */
public interface IViewBook {

    public void getAllBooks();

    public void addBook(ActionEvent event);

    public void issueBook(ActionEvent event);

    public void returnBook(ActionEvent event);

    public void issueBookForm(ActionEvent event);

    public void searchBook(ActionEvent event);

    public void deleteBook(ActionEvent event);

    public void addBookForm(ActionEvent event);
}
