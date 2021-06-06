package edu.cqu.olmclibrarysystem.presenter;

import javafx.event.ActionEvent;

/**
 * IViewDashboard: View Interface for dashboard
 *
 * @author Tikaraj Ghising
 */
public interface IViewDashboard {

    public void getAllOverDueBooks();

    public void getAllIssuedBooks();

    public void getAllOverDueReturnBooks();
}
