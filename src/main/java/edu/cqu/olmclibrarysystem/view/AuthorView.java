package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.presenter.BookPresenter;
import edu.cqu.olmclibrarysystem.presenter.IViewAuthor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * AuthorView: Author View of MVP
 *
 * @author Tikaraj Ghising - 12129239
 */
public class AuthorView implements Initializable, IViewAuthor {

    @FXML
    private AnchorPane listsAuthorLayout;

    @FXML
    private TableView<Author> listAuthorTableView;

    @FXML
    private TableColumn<Author, Integer> authorId;

    @FXML
    private TableColumn<Author, String> authorName;

    @FXML
    private AnchorPane addAuthorForm;

    @FXML
    private TextField newAuthorFullName;

    private BookPresenter bookPresenter;
    private static final String FULL_NAME_REGEX = "^[A-Z][a-zA-Z]{2,}(?: [A-Z][a-zA-Z]*){0,2}$"; // full name regex

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bookPresenter = new BookPresenter();
        getAllAuthors();
    }

    @Override
    public void getAllAuthors() {
        this.listsAuthorLayout.setVisible(true);
        this.addAuthorForm.setVisible(false);

        authorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        authorName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        ObservableList<Author> donorsObservableList = FXCollections.observableArrayList(this.bookPresenter.getAllAuthors());
        listAuthorTableView.getItems().clear();
        listAuthorTableView.setItems(donorsObservableList);
    }

    @Override
    public void addAuthor(ActionEvent event) {
        String name = newAuthorFullName.getText();
        if (name.isEmpty() || !name.matches(FULL_NAME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Full Name");
            alert.setHeaderText(null);
            alert.setContentText("Full name must be non-numeric and start with uppercase \nletter and at least 3 characters.");
            alert.showAndWait();
            newAuthorFullName.requestFocus();
            return;
        } else {
            bookPresenter.addAuthor(new Author(name));
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("New Author has been successfully added.");
            confirmationAlert.showAndWait();
            getAllAuthors();
            this.listsAuthorLayout.setVisible(true);
            this.addAuthorForm.setVisible(false);
            clearAuthorForm();
        }
    }

    private void clearAuthorForm() {
        newAuthorFullName.clear();
    }

    @Override
    public void addAuthorForm(ActionEvent event) {
        this.listsAuthorLayout.setVisible(false);
        this.addAuthorForm.setVisible(true);
        clearAuthorForm();
    }
}
