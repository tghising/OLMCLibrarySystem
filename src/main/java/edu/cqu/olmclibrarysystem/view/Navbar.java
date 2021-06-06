package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.model.User;
import edu.cqu.olmclibrarysystem.enums.UserRole;
import edu.cqu.olmclibrarysystem.util.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Navbar: View for navbar of OLMCLibrarySystem
 *
 * @author Tikaraj Ghising - 12129239
 */
public class Navbar implements Initializable {

    @FXML
    private Label loggedInUser;

    @FXML
    private Button librariansButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loggedInUser.setText(UserContext.getInstance().getLoggedUser().getFullName()); // setting user name
        if (UserContext.getInstance().getLoggedUser().getRole().equals(UserRole.MAIN_LIBRARIAN)) {
            this.librariansButton.setVisible(true);
        } else {
            this.librariansButton.setVisible(false);
        }
    }

    // method to goto Dashboard
    @FXML
    public void goToDashBoard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void gotoAuthors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Authors.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
//            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void goToBooks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Books.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
//            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void goToMembers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Members.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
//            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void gotToDonors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Donors.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
//            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void gotToLibrarians(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Librarians.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // method to signout the user
    @FXML
    public void signOut(ActionEvent event) {
        UserContext.getInstance().setLoggedUser(new User());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignInLibrarian.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            System.out.println("FXML not found: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
