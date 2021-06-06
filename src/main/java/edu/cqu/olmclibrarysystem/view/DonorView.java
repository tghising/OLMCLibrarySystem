package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.model.Author;
import edu.cqu.olmclibrarysystem.model.Book;
import edu.cqu.olmclibrarysystem.model.Donor;
import edu.cqu.olmclibrarysystem.model.DonorDonatedBook;
import edu.cqu.olmclibrarysystem.util.UserContext;
import javafx.fxml.Initializable;
import edu.cqu.olmclibrarysystem.model.User;
import edu.cqu.olmclibrarysystem.presenter.DonorPresenter;
import edu.cqu.olmclibrarysystem.presenter.IViewDonor;
import edu.cqu.olmclibrarysystem.util.HelperUtility;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * DonorView: Donor View of MVP
 *
 * @author Tikaraj Ghising - 12129239
 */
public class DonorView implements Initializable, IViewDonor {

    private DonorPresenter donorPresenter;

    private User loggedInUser;

    @FXML
    private AnchorPane addDonorLayout = new AnchorPane();

    @FXML
    private AnchorPane showDonorsLayout = new AnchorPane();

    @FXML
    private AnchorPane donateBookLayout = new AnchorPane();

    @FXML
    private TextField newDonorFullname = new TextField();

    @FXML
    private TextField newDonorEmailAddress = new TextField();

    @FXML
    private TextField newDonorPhoneNumber = new TextField();

    @FXML
    private TextArea newDonorAddress = new TextArea();

    @FXML
    private TableView<Donor> donorsListTable = new TableView<>();

    @FXML
    private TableColumn<Donor, Integer> donorsIdColumn = new TableColumn<>();

    @FXML
    private TableColumn<Donor, String> donorsFullNameColumn = new TableColumn<>();

    @FXML
    private TableColumn<Donor, String> donorsEmailAddressColumn = new TableColumn<>();

    @FXML
    private TableColumn<Donor, String> donorsPhoneNumberColumn = new TableColumn<>();

    @FXML
    private TableColumn<Donor, String> donorsAddressColumn = new TableColumn<>();

    @FXML
    private ComboBox<Donor> donorList = new ComboBox<>();

    @FXML
    private TextField donateBookTitle = new TextField();

    @FXML
    private TextField donateBookPrice = new TextField();

    @FXML
    private ComboBox<Author> donateBookAuthor = new ComboBox<>();

    @FXML
    private TextField donateBookQuantity = new TextField();

    @FXML
    private Button addDonorSaveButton = new Button();

    @FXML
    private TableView<DonorDonatedBooksValues> donatedBooksList = new TableView<>();

    @FXML
    private TableColumn<DonorDonatedBooksValues, Integer> bookIdColumn = new TableColumn<>();

    @FXML
    private TableColumn<DonorDonatedBooksValues, String> bookTitleColumn = new TableColumn<>();

    @FXML
    private TableColumn<DonorDonatedBooksValues, Double> bookPriceColumn = new TableColumn<>();

    @FXML
    private TableColumn<DonorDonatedBooksValues, String> bookAuthorColumn = new TableColumn<>();

    @FXML
    private TableColumn<DonorDonatedBooksValues, Integer> qualityDonatedColumn = new TableColumn<>();

    @FXML
    private TableColumn<DonorDonatedBooksValues, String> donorNamerColumn = new TableColumn<>();

    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"; // password regex
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"; // email regex
    private static final String FULL_NAME_REGEX = "^[A-Z][a-zA-Z]{2,}(?: [A-Z][a-zA-Z]*){0,2}$"; // full name regex
    private static final String PHONE_REGEX = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"; // 10 digits phone number regex
    private static final String NUMERIC_REGEX = "\\d+";

    private HelperUtility helperUtility;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.donorPresenter = new DonorPresenter();
        this.helperUtility = new HelperUtility();
        this.loggedInUser = UserContext.getInstance().getLoggedUser();

        donorsIdColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        donorsFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        donorsEmailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        donorsPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        donorsAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        ObservableList<Donor> donorsObservableList = FXCollections.observableArrayList(this.donorPresenter.getAllDonors());
        donorsListTable.getItems().clear();
        if (donorsObservableList.isEmpty()) {
            donorsListTable.setPlaceholder(new Label("No records found!"));
        } else {
            donorsListTable.setItems(donorsObservableList);
        }

        donorList.setItems(donorsObservableList);

        ObservableList<Author> authorsObservableList = FXCollections.observableArrayList(this.donorPresenter.getAllAuthors());
        donateBookAuthor.setItems(authorsObservableList);

        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        qualityDonatedColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDonated"));
        donorNamerColumn.setCellValueFactory(new PropertyValueFactory<>("donor"));

        ObservableList<DonorDonatedBooksValues> allDonatedBooks = FXCollections.observableArrayList(this.convertAllDonatedBooks(this.donorPresenter.getAllDonatedBooks()));
        donatedBooksList.getItems().clear();
        if (donorsObservableList.isEmpty()) {
            donatedBooksList.setPlaceholder(new Label("No records found!"));
        } else {
            donatedBooksList.setItems(allDonatedBooks);
        }
        showDonorsBookLayout();
    }

    private List<DonorDonatedBooksValues> convertAllDonatedBooks(List<DonorDonatedBook> donatedBooks) {
        List<DonorDonatedBooksValues> convertedValues = new ArrayList<>();

        for (DonorDonatedBook entry : donatedBooks) {
            DonorDonatedBooksValues convertedValueEntry = new DonorDonatedBooksValues();

            convertedValueEntry.setBookID(entry.getBook().getBookId());
            convertedValueEntry.setTitle(entry.getBook().getTitle());
            convertedValueEntry.setPrice(entry.getBook().getPrice());
            convertedValueEntry.setAuthor(entry.getBook().getAuthor().getFullName());
            convertedValueEntry.setQuantityDonated(entry.getQuantity());
            convertedValueEntry.setDonor(entry.getDonor().getFullName());

            convertedValues.add(convertedValueEntry);
        }

        return convertedValues;
    }

    @Override
    public void getAllDonors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDonor(ActionEvent event) {
        String donorFullname = newDonorFullname.getText();
        String donorEmailAddress = newDonorEmailAddress.getText();
        String donorPhoneNumber = newDonorPhoneNumber.getText();
        String donorAddress = newDonorAddress.getText();

        if (!addDonorValidation()) {
            Donor newDonor = new Donor(donorFullname, donorEmailAddress, donorPhoneNumber, donorAddress);
            this.donorPresenter.add(newDonor);

            ObservableList<Donor> donorsObservableList = FXCollections.observableArrayList(this.donorPresenter.getAllDonors());
            donorsListTable.getItems().clear();
            donorsListTable.setItems(donorsObservableList);

            donorList.setItems(donorsObservableList);

            this.showDonorsBookLayout();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Donor " + newDonor.getFullName() + " has been added successfully.");
            alert.showAndWait();

            clearAddDonorForm();
        }
    }

    private boolean addDonorValidation() {
        boolean boolValue = false;
        String validationTitle = "Donor registrartion";

        if (newDonorFullname.getText().isEmpty() || !newDonorFullname.getText().matches(FULL_NAME_REGEX)) {
            newDonorFullname.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Name must be non-numeric and start with \nUppercase letter and at least 3 characters.");
            boolValue = true;

        } else if (newDonorEmailAddress.getText().isEmpty() || !newDonorEmailAddress.getText().matches(EMAIL_REGEX)) {
            newDonorEmailAddress.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter a valid email address.");
            boolValue = true;

        } else if (newDonorPhoneNumber.getText().isEmpty() || !newDonorPhoneNumber.getText().matches(PHONE_REGEX)) {
            newDonorPhoneNumber.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid 10 digits phone number.");
            boolValue = true;

        } else if (newDonorAddress.getText().isEmpty()) {
            newDonorAddress.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid address.");
            boolValue = true;

        }

        return boolValue;
    }

    private void clearAddDonorForm() {
        newDonorFullname.clear();
        newDonorEmailAddress.clear();
        newDonorPhoneNumber.clear();
        newDonorAddress.clear();
    }

    private boolean addDonateBooksValidation() {
        boolean boolValue = false;
        String validationTitle = "Input validation";

        if (donorList.getSelectionModel().isEmpty()) {
            donorList.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please select Donor");
            boolValue = true;
        } else if (donateBookTitle.getText().isEmpty()) {
            donateBookTitle.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid book title");
            boolValue = true;

        } else if (donateBookPrice.getText().isEmpty() || !donateBookPrice.getText().matches(NUMERIC_REGEX)) {
            donateBookPrice.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid price");
            boolValue = true;

        } else if (donateBookAuthor.getValue() == null) {
            donateBookAuthor.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please select author");
            boolValue = true;

        } else if (donateBookQuantity.getText().isEmpty() || !donateBookQuantity.getText().matches(NUMERIC_REGEX)) {
            donateBookQuantity.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid quantity");
            boolValue = true;
        }

        return boolValue;
    }

    @FXML
    public void showNewDonorLayout(ActionEvent event) {
        showDonorsLayout.setVisible(false);
        addDonorLayout.setVisible(true);
        clearAddDonorForm();
    }

    @FXML
    public void showDonateBookLayout(ActionEvent event) {
        ObservableList<Author> authorsObservableList = FXCollections.observableArrayList(this.donorPresenter.getAllAuthors());
        donateBookAuthor.setItems(authorsObservableList);

        showDonorsLayout.setVisible(false);
        donateBookLayout.setVisible(true);
        resetDonateBookForm();
    }

    @FXML
    public void showDonorsBookLayout() {
        donateBookLayout.setVisible(false);
        addDonorLayout.setVisible(false);
        showDonorsLayout.setVisible(true);
    }

    @Override
    public void donateBook(ActionEvent event) {
        Donor selectedDonor = donorList.getValue();
        String newDonatedBookTitle = donateBookTitle.getText();
        String newDonatedBookPrice = donateBookPrice.getText();
        Author newDonatedBookAuthor = donateBookAuthor.getValue();
        String newDonatedBookQuantity = donateBookQuantity.getText();

        if (!addDonateBooksValidation()) {
            Book newDonatedBook = new Book(newDonatedBookTitle, Double.parseDouble(newDonatedBookPrice), newDonatedBookAuthor, Integer.parseInt(newDonatedBookQuantity));
            this.donorPresenter.addDonatedBook(newDonatedBook);

            List<Book> allBooks = this.donorPresenter.getAllBooks();
            Book insertedBook = allBooks.get(allBooks.size() - 1);

            DonorDonatedBook donorDonatedBook = new DonorDonatedBook(insertedBook, selectedDonor, Integer.parseInt(newDonatedBookQuantity));
            this.donorPresenter.donateBook(donorDonatedBook);
            
            ObservableList<DonorDonatedBooksValues> allDonatedBooks = FXCollections.observableArrayList(this.convertAllDonatedBooks(this.donorPresenter.getAllDonatedBooks()));
            donatedBooksList.getItems().clear();
            donatedBooksList.setItems(allDonatedBooks);
            this.showDonorsBookLayout();
            this.resetDonateBookForm();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Donor " + selectedDonor.getFullName() + " has been donated Book " + insertedBook.getTitle() + " successfully");
            alert.showAndWait();
            resetDonateBookForm();
        }
    }

    private void resetDonateBookForm() {
        donorList.setValue(null);
        donorList.setPromptText("Select Donor");
        donateBookTitle.clear();
        donateBookPrice.clear();
        donateBookAuthor.setValue(null);
        donateBookAuthor.setPromptText("Select Author");
        donateBookQuantity.clear();
    }

    @Override
    public void getAllDonatedBooksByDonor() {

    }

    protected class DonorDonatedBooksValues {

        private Integer bookID;
        private String title;
        private Double price;
        private String author;
        private Integer quantityDonated;
        private String donor;

        public DonorDonatedBooksValues() {
        }

        public Integer getBookID() {
            return bookID;
        }

        public void setBookID(Integer bookID) {
            this.bookID = bookID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Integer getQuantityDonated() {
            return quantityDonated;
        }

        public void setQuantityDonated(Integer quantityDonated) {
            this.quantityDonated = quantityDonated;
        }

        public String getDonor() {
            return donor;
        }

        public void setDonor(String donor) {
            this.donor = donor;
        }
    }
}
