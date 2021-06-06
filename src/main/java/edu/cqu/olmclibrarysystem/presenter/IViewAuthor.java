package edu.cqu.olmclibrarysystem.presenter;

import javafx.event.ActionEvent;

/**
 * IViewAuthor: View Interface for Author
 *
 * @author Tikaraj Ghising
 */
public interface IViewAuthor {

    public void getAllAuthors();

    public void addAuthor(ActionEvent event);

    public void addAuthorForm(ActionEvent event);
}
