package edu.cqu.olmclibrarysystem.presenter;

import javafx.event.ActionEvent;

/**
 * IViewLibrarian: View Interface for Librarian
 *
 * @author Tikaraj Ghising
 */
public interface IViewUser {

    public void getAllLibrarians();

    public void registerLibrarian(ActionEvent event);

    public void registerLibrarianForm(ActionEvent event);

    public void updateLibrarianPassword(ActionEvent event);

    public void updateLibrarianForm(ActionEvent event);

}
