package edu.cqu.olmclibrarysystem.presenter;

import javafx.event.ActionEvent;

/**
 * IViewMember: View Interface for Member
 *
 * @author Tikaraj Ghising
 */
public interface IViewDonor {

    public void getAllDonors();

    public void addDonor(ActionEvent event);

    public void donateBook(ActionEvent event);

    public void getAllDonatedBooksByDonor();
}
