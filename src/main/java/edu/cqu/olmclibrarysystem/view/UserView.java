package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.model.User;
import edu.cqu.olmclibrarysystem.enums.UserRole;
import edu.cqu.olmclibrarysystem.presenter.IViewUser;
import edu.cqu.olmclibrarysystem.presenter.UserPresenter;
import edu.cqu.olmclibrarysystem.util.HelperUtility;
import edu.cqu.olmclibrarysystem.util.UserContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * UserView: User View of MVP for OLMCLibrarySystem
 *
 * @author Tikaraj Ghising - 12129239
 */
public class UserView implements Initializable, IViewUser {

    @FXML
    private TableView<User> listLibrarianTableView = new TableView<>();

    @FXML
    private TableColumn<User, Integer> librarianId = new TableColumn<>();

    @FXML
    private TableColumn<User, String> librarianName = new TableColumn<>();

    @FXML
    private TableColumn<User, String> librarianEmail = new TableColumn<>();

    @FXML
    private TableColumn<User, String> librarianPhoneNumber = new TableColumn<>();

    @FXML
    private TableColumn<User, String> librarianAddress = new TableColumn<>();

    @FXML
    private TableColumn<User, String> librarianRole = new TableColumn<>();

    @FXML
    private AnchorPane addLibrarianForm;

    @FXML
    private AnchorPane listLibrarianLayout;

    @FXML
    private Button deleteLibrarianButton;

    @FXML
    private TextField newLibrarianName;

    @FXML
    private TextField newLibrarianEmail;

    @FXML
    private PasswordField newLibrarianPassword;

    @FXML
    private PasswordField newLibrarianConfirmPassword;

    @FXML
    private TextField newLibrarianPhoneNumber;

    @FXML
    private TextArea newLibrarianAddress;

    @FXML
    private AnchorPane updateLibrarianDetails;

    @FXML
    private Label oneLibrarianDetails;

    @FXML
    private Label oneLibrarianPasswordLabel;

    @FXML
    private Button updateListLibrarianButton;

    @FXML
    private Button updateLibrarianButton;

    @FXML
    private Label oneLibrarianConfirmPasswordLabel;

    @FXML
    private TextField oneLibrarianName;

    @FXML
    private TextField oneLibrarianEmail;

    @FXML
    private TextField oneLibrarianPhoneNumber;

    @FXML
    private TextArea oneLibrarianAddress;

    @FXML
    private PasswordField oneLibrarianPassword;

    @FXML
    private PasswordField oneLibrarianConfirmPassword;

    @FXML
    private ComboBox<UserRole> newLibrarianRole;

    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"; // password regex
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"; // email regex
    private static final String FULL_NAME_REGEX = "^[A-Z][a-zA-Z]{2,}(?: [A-Z][a-zA-Z]*){0,2}$"; // full name regex
    private static final String PHONE_REGEX = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"; // 10 digits phone number regex

    private User loggedInUser;
    private UserPresenter presenter;
    private User selectedUser;
    private HelperUtility helperUtility;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.presenter = new UserPresenter();
        this.selectedUser = new User();
        this.helperUtility = new HelperUtility();
        this.loggedInUser = UserContext.getInstance().getLoggedUser();
        if (loggedInUser.getRole().equals(UserRole.MAIN_LIBRARIAN)) {
            this.listLibrarianLayout.setVisible(true);
        } else {
            this.listLibrarianLayout.setVisible(false);
        }

        // initializing the combobox items for User Role
        List<UserRole> userRoles = Arrays.asList(UserRole.values());
        newLibrarianRole.getItems().addAll(userRoles);
        getAllLibrarians();

    }

    @FXML
    void deleteLibrarian(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to delete?");

        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            presenter.delete(selectedUser.getUserId());
            getAllLibrarians();
        }

    }

    private void hideAllComponents() {
        listLibrarianLayout.setVisible(false);
        addLibrarianForm.setVisible(false);
        updateLibrarianDetails.setVisible(false);
        updateListLibrarianButton.setDisable(true);
        this.deleteLibrarianButton.setDisable(true);
    }

    private void clearLibrarianForm() {
        newLibrarianName.clear();
        newLibrarianEmail.clear();
        newLibrarianPassword.clear();
        newLibrarianConfirmPassword.clear();
        newLibrarianPhoneNumber.clear();
        newLibrarianAddress.clear();
        newLibrarianRole.getSelectionModel().clearSelection();

    }

    @Override
    @FXML
    public void getAllLibrarians() {
        hideAllComponents();
        listLibrarianLayout.setVisible(true);

        librarianId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        librarianName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        librarianEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        librarianPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        librarianAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        librarianRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        ObservableList<User> donorsObservableList = FXCollections.observableArrayList(this.presenter.getAllUsers());
        listLibrarianTableView.getItems().clear();
        listLibrarianTableView.setItems(donorsObservableList);
    }

    @FXML
    void enableUpdateDelete(MouseEvent event) {
        User user = listLibrarianTableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            updateListLibrarianButton.setDisable(false);
            if (!user.getEmail().equals(loggedInUser.getEmail())) {
                deleteLibrarianButton.setDisable(false);
            } else {
                deleteLibrarianButton.setDisable(true);
            }
            this.selectedUser = user;
        } else {
            updateListLibrarianButton.setDisable(true);
            deleteLibrarianButton.setDisable(true);
            this.selectedUser = new User();
        }
    }

    @Override
    public void registerLibrarianForm(ActionEvent event) {
        hideAllComponents();
        clearLibrarianForm();
        addLibrarianForm.setVisible(true);
    }

    @Override
    public void updateLibrarianPassword(ActionEvent event) {
        String password = oneLibrarianPassword.getText();
        String updateConfirmPassword = oneLibrarianConfirmPassword.getText();
        String validationTitle = "Librarian Password update";
        if (password.isEmpty() || !password.matches(PASSWORD_REGEX)) {
            oneLibrarianPassword.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Password must contain the following:\n \u2022 A number\n \u2022 An uppercase char\n \u2022 At least 8 characters length.");
            return;
        } else if (!password.equals(updateConfirmPassword) && !updateConfirmPassword.matches(PASSWORD_REGEX)) {
            oneLibrarianConfirmPassword.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Confirm password must be matched with Password.");
            return;
        } else {
            boolean isConfirm = helperUtility.confirmValidation("Update Confirmation", null, "Are you sure want to update ?");
            if (isConfirm) {
                this.presenter.updatePassword(selectedUser.getUserId(), password);
                getAllLibrarians();
                this.deleteLibrarianButton.setDisable(false);
                this.updateListLibrarianButton.setDisable(false);

                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Librarian Password has been updated successfully.");
                confirmationAlert.showAndWait();
            }
        }
    }

    @Override
    @FXML
    public void registerLibrarian(ActionEvent event) {
        if (loggedInUser.getRole().equals(UserRole.MAIN_LIBRARIAN)) {
            String librarianName = newLibrarianName.getText();
            String librarianEmail = newLibrarianEmail.getText();
            String librarianPassword = newLibrarianPassword.getText();
            String librarianConfirmPassword = newLibrarianConfirmPassword.getText();
            String librarianPhoneNumber = newLibrarianPhoneNumber.getText();
            String librarianAddress = newLibrarianAddress.getText();

            String validationTitle = "Librarian Registrartion";
            if (librarianName.isEmpty() || !librarianName.matches(FULL_NAME_REGEX)) {
                newLibrarianName.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Name must be non-numeric and start with \nUppercase letter and at least 3 characters.");
                //return;
            } else if (librarianEmail.isEmpty() || !librarianEmail.matches(EMAIL_REGEX)) {
                newLibrarianEmail.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Please enter a valid email address.");
                //return;
            } else if (newLibrarianRole.getSelectionModel().isEmpty() || !librarianEmail.matches(EMAIL_REGEX)) {
                newLibrarianRole.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Please select User Type.");
                //return;
            } else if (librarianPassword.isEmpty() || !librarianPassword.matches(PASSWORD_REGEX)) {
                newLibrarianPassword.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Password must contain the following:\n \u2022 A number\n \u2022 An uppercase char\n \u2022 At least 8 characters length.");
                //return;
            } else if (!librarianPassword.equals(librarianConfirmPassword) && !librarianConfirmPassword.matches(PASSWORD_REGEX)) {
                newLibrarianConfirmPassword.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Confirm password must be matched with Password.");
                //return;
            } else if (librarianPhoneNumber.isEmpty() || !librarianPhoneNumber.matches(PHONE_REGEX)) {
                newLibrarianPhoneNumber.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid 10 digits phone number.");
                //return;
            } else if (librarianAddress.isEmpty()) {
                newLibrarianAddress.requestFocus();
                helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid address.");
                //return;
            } else {
                boolean isConfirm = helperUtility.confirmValidation("Add New Librarian", null, "Are you sure want to add ?");
                if (isConfirm) {
                    String librarianRole = newLibrarianRole.getValue().toString();
                    User user = new User(librarianName, librarianEmail, librarianPassword, librarianPhoneNumber, librarianAddress);
                    user.setRole(UserRole.getType(librarianRole));
                    this.presenter.add(user);
                    Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("New Librarian has been successfully added.");
                    confirmationAlert.showAndWait();

                    getAllLibrarians();
                    this.deleteLibrarianButton.setDisable(false);
                    this.updateListLibrarianButton.setDisable(false);
                    clearLibrarianForm();
                } else {
                    //return;
                    clearLibrarianForm();
                }
            }
        }
    }

    @Override
    public void updateLibrarianForm(ActionEvent event) {
        hideAllComponents();
        //TODO value display to User Details
        if (loggedInUser.getRole().equals(UserRole.MAIN_LIBRARIAN)) {
            updateLibrarianDetails.setVisible(true);
            oneLibrarianName.setText(selectedUser.getFullName());
            oneLibrarianEmail.setText(selectedUser.getEmail());
            oneLibrarianPhoneNumber.setText(selectedUser.getPhoneNumber());
            oneLibrarianAddress.setText(selectedUser.getAddress());
        }
    }
}
