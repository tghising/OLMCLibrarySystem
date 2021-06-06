package edu.cqu.olmclibrarysystem.presenter;

import javafx.event.ActionEvent;

/**
 * IViewMember: View Interface for Member
 *
 * @author Tikaraj Ghising
 */
public interface IViewMember {

    public void getAllMembers();

    public void addMember(ActionEvent event);
    
}
