package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.presenter.UserPresenter;
import edu.cqu.olmclibrarysystem.util.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * LoginView: Login View of MVP
 *
 * @author Tikaraj Ghising - 12129239
 */
public class LoginView implements Initializable {

    @FXML
    private TextField mainLibrarianEmail;

    @FXML
    private PasswordField mainLibrarianPassword;

    @FXML
    private Label signInValidation;

    private UserPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.presenter = new UserPresenter();
    }

    @FXML
    public void signIn(ActionEvent event) {
        String email = mainLibrarianEmail.getText();
        String password = mainLibrarianPassword.getText();
        signInValidation.setText("");
        if (presenter.isUserExist(email, password)) {
            UserContext.getInstance().setLoggedUser(presenter.search(email)); // User Context setting here
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            signInValidation.setText("Email and Password must be a valid.");
        }
    }
}
