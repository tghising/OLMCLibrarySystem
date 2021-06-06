package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.enums.UserRole;
import edu.cqu.olmclibrarysystem.model.*;
import edu.cqu.olmclibrarysystem.presenter.BookPresenter;
import edu.cqu.olmclibrarysystem.presenter.IViewBook;
import edu.cqu.olmclibrarysystem.presenter.MemberPresenter;
import edu.cqu.olmclibrarysystem.util.HelperUtility;
import edu.cqu.olmclibrarysystem.util.UserContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * BookView: Book View of MVP
 *
 * @author Tikaraj Ghising - 12129239
 */
public class BookView implements Initializable, IViewBook {

    @FXML
    private AnchorPane searchBookLayout;

    @FXML
    private TableView<Book> listBookTableView = new TableView<>();

    @FXML
    private TableColumn<Book, Integer> bookId = new TableColumn<>();

    @FXML
    private TableColumn<Book, String> bookTitle = new TableColumn<>();

    @FXML
    private TableColumn<Book, Double> bookPrice = new TableColumn<>();

    @FXML
    private TableColumn<Book, String> bookAuthor = new TableColumn<>();

    @FXML
    private TableColumn<Book, Integer> bookAvailable = new TableColumn<>();

    @FXML
    private TextField searchBookKey;

    @FXML
    private ComboBox<String> searchByTitleOrAuthor = new ComboBox();

    @FXML
    private ComboBox<Member> issueMemberList;

    @FXML
    private ComboBox<Book> issueBookList;

    @FXML
    private Button deleteBookButton;

    @FXML
    private AnchorPane addBookLayout;

    @FXML
    private TextField newBookTitle;

    @FXML
    private TextField newBookPrice;

    @FXML
    private ComboBox<Author> newBookAuthor;

    @FXML
    private TextField newBookStocks;

    @FXML
    private AnchorPane issueBookLayout;

    @FXML
    private DatePicker bookDueDate;

    @FXML
    private AnchorPane returnBookLayout;

    @FXML
    private ComboBox<Member> returnMemberList = new ComboBox<>();

    @FXML
    private ComboBox<Book> allIssuedBooksToMember = new ComboBox<>();

    private static final String DECIMAL_REGEX = "^([0-9]+\\.?[0-9]*|[0-9]*\\.[0-9]+)$";

    private BookPresenter bookPresenter;
    private MemberPresenter memberPresenter;
    //private HelperUtility helperUtility;

    private User loggedInUser;
    private HelperUtility helper;
    private Book selectedBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bookPresenter = new BookPresenter();
        this.memberPresenter = new MemberPresenter();
        this.selectedBook = new Book();
        this.loggedInUser = UserContext.getInstance().getLoggedUser();
        this.helper = new HelperUtility();

        ObservableList<String> searchByOptions = FXCollections.observableArrayList("All", "Title", "Author");
        searchByTitleOrAuthor.setItems(searchByOptions);

        // disable past dates picker
        helper.disablePastDatePicker(bookDueDate);

        // list all books
        getAllBooks();
    }

    @Override
    @FXML
    public void getAllBooks() {
        hideAllLayouts();
        searchBookLayout.setVisible(true);
        if (loggedInUser.getRole().equals(UserRole.MAIN_LIBRARIAN)) {
            deleteBookButton.setVisible(true);
        }
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));

        ObservableList<Book> donorsObservableList = FXCollections.observableArrayList(this.bookPresenter.getAllBooks());
        listBookTableView.getItems().clear();
        if (donorsObservableList.isEmpty()) {
            listBookTableView.setPlaceholder(new Label("No records found!"));
        } else {
            listBookTableView.setItems(donorsObservableList);
        }
    }

    private void clearNewBookDetails() {
        newBookTitle.clear();
        newBookPrice.clear();
        newBookStocks.clear();
        newBookAuthor.setValue(null);
    }

    private boolean addBookValidation() {
        boolean boolValue = false;
        String validationTitle = "Input validation";

        if (newBookTitle.getText().isEmpty()) {
            newBookTitle.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please enter a valid book title.");
            boolValue = true;

        } else if (newBookPrice.getText().isEmpty() || !newBookPrice.getText().matches(DECIMAL_REGEX)) {
            newBookPrice.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please enter a valid price.");
            boolValue = true;

        } else if (newBookAuthor.getValue() == null) {
            newBookAuthor.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please select a book author.");
            boolValue = true;

        } else if (newBookStocks.getText().isEmpty() || !newBookStocks.getText().matches(DECIMAL_REGEX)) {
            newBookStocks.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please enter a valid stock quantity.");
            boolValue = true;

        }
        return boolValue;
    }

    private boolean issueBookValidation() {
        boolean boolValue = false;
        String validationTitle = "Input validation";

        if (issueMemberList.getValue() == null) {
            issueMemberList.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please select member.");
            boolValue = true;

        } else if (issueBookList.getValue() == null) {
            issueBookList.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please select book.");
            boolValue = true;

        } else if (bookDueDate.getValue() == null) {
            bookDueDate.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please select valid date.");
            boolValue = true;

        }
        return boolValue;
    }

    @Override
    @FXML
    public void addBook(ActionEvent event) {
        String bookTitle = newBookTitle.getText();
        String bookPrice = newBookPrice.getText();
        String bookStocks = newBookStocks.getText();

        if (!addBookValidation()) {
            Double price = Double.parseDouble(bookPrice);
            Author author = newBookAuthor.getSelectionModel().getSelectedItem();

            this.bookPresenter.addBook(new Book(bookTitle, price, author, Integer.parseInt(bookStocks)));
            clearNewBookDetails();
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("New Book has been successfully added.");
            confirmationAlert.showAndWait();
            getAllBooks();
        }
    }

    @Override
    @FXML
    public void issueBook(ActionEvent event) {
        Member issueMember = issueMemberList.getSelectionModel().getSelectedItem();
        Book issueBook = issueBookList.getSelectionModel().getSelectedItem();
        LocalDate dueDate = bookDueDate.getValue();

        if (!issueBookValidation()) {
            this.memberPresenter.addMemberBorrowedBook(new MemberBorrowedBook(issueMember, issueBook, new Date(), helper.getDateFromLocalDate(dueDate)));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Book " + issueBook.getTitle() + " has been issued to " + issueMember.getFullName() + " successfully.");
            alert.showAndWait();
            //TODO pull all
            getAllBooks();
        }
    }

    @Override
    public void returnBook(ActionEvent event) {
        try {
            Member selectedMember = returnMemberList.getValue();
            Book selectedBook = allIssuedBooksToMember.getValue();

            if (!returnBookValidation(selectedMember, selectedBook)) {
                this.bookPresenter.returnBook(new Date(), selectedMember, selectedBook);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Book " + selectedBook.getTitle() + " hass been returned from " + selectedMember.getFullName() + " successfully.");
                alert.showAndWait();
                getAllBooks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearComboBoxes() {
        returnMemberList.setValue(null);
        allIssuedBooksToMember.setValue(null);
    }

    private boolean returnBookValidation(Member selectedMember, Book selectedBook) {
        boolean boolValue = false;
        String validationTitle = "Input validation";
        if (selectedMember == null) {
            returnMemberList.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please select member.");
            boolValue = true;
        } else if (selectedBook == null) {
            allIssuedBooksToMember.requestFocus();
            helper.inputErrorValidation(validationTitle, null, "Please select book.");
            boolValue = true;
        }
        return boolValue;

    }

    @FXML
    void enableBookDelete(MouseEvent event) {
        Book book = listBookTableView.getSelectionModel().getSelectedItem();
        if (book != null) {
            deleteBookButton.setDisable(false);
            this.selectedBook = book;
        } else {
            deleteBookButton.setDisable(true);
            this.selectedBook = new Book();
        }
    }

    @Override
    @FXML
    public void issueBookForm(ActionEvent event) {
        hideAllLayouts();
        issueBookLayout.setVisible(true);
        issueBookList.setPromptText("Select Book");
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList(bookPresenter.getAllBooks());
        issueBookList.setItems(bookObservableList);

        issueMemberList.setPromptText("Select Member");
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList(memberPresenter.getAllMembers());
        issueMemberList.setItems(memberObservableList);
    }

    @Override
    @FXML
    public void searchBook(ActionEvent event) {

        String keyword = searchBookKey.getText();
        String searchCriteria = searchByTitleOrAuthor.getValue();
        deleteBookButton.setDisable(true);
        this.selectedBook = new Book();

        if (searchByTitleOrAuthor.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search By Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select Search by option.");
            alert.showAndWait();
            return;
        } else if (keyword.isEmpty() && !searchByTitleOrAuthor.getSelectionModel().getSelectedItem().equalsIgnoreCase("all")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Keyword");
            alert.setHeaderText(null);
            alert.setContentText("Please enter keyword for search.");
            alert.showAndWait();
            return;
        } else {
            if (searchCriteria.equalsIgnoreCase("all")) {
                searchBookKey.setText("");
                ObservableList<Book> donorsObservableList = FXCollections.observableArrayList(this.bookPresenter.getAllBooks());
                listBookTableView.getItems().clear();
                listBookTableView.setItems(donorsObservableList);
            } else if (searchCriteria.equalsIgnoreCase("title")) {
                ObservableList<Book> donorsObservableList = FXCollections.observableArrayList(this.bookPresenter.findBooksByTitle(keyword));
                listBookTableView.getItems().clear();
                listBookTableView.setItems(donorsObservableList);
            } else if (searchCriteria.equalsIgnoreCase("author")) {
                ObservableList<Book> donorsObservableList = FXCollections.observableArrayList(this.bookPresenter.findBooksByAuthor(keyword));
                listBookTableView.getItems().clear();
                listBookTableView.setItems(donorsObservableList);
            }
        }

    }

    @Override
    public void deleteBook(ActionEvent event) {
        if (loggedInUser.getRole().equals(UserRole.MAIN_LIBRARIAN)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Book Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to delete book ?");

            ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == buttonTypeOk) {
                String deleteInfo = bookPresenter.deleteBook(selectedBook.getBookId());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText(deleteInfo);
                alert.show();
                getAllBooks();
            } else {
                return;
            }
        }
    }

    @Override
    @FXML
    public void addBookForm(ActionEvent event) {
        hideAllLayouts();
        addBookLayout.setVisible(true);

        newBookAuthor.setPromptText("Select Author");
        ObservableList<Author> list = FXCollections.observableArrayList(bookPresenter.getAllAuthors());
        newBookAuthor.setItems(list);
        clearNewBookDetails();
    }

    public void returnBookForm() {
        hideAllLayouts();
        returnBookLayout.setVisible(true);

        returnMemberList.setPromptText("Select Member");
        allIssuedBooksToMember.setPromptText("Select Book");
        ObservableList<Member> membersWithIssuedBooksList = FXCollections.observableArrayList(bookPresenter.getMembersWithIssuedBooks());
        returnMemberList.setItems(membersWithIssuedBooksList);
    }

    @FXML
    public void changeComboBoxValues() {
        Member selectedMember = returnMemberList.getValue();
        Integer selectedMemberID = selectedMember.getMemberId();
        ObservableList<Book> booksIssuedToMember = FXCollections.observableArrayList(bookPresenter.getBooksIssuedToMember(selectedMemberID));
        allIssuedBooksToMember.setItems(booksIssuedToMember);
    }

    // method to hide all components
    private void hideAllLayouts() {
        searchBookLayout.setVisible(false);
        returnBookLayout.setVisible(false);
        addBookLayout.setVisible(false);
        issueBookLayout.setVisible(false);
        deleteBookButton.setVisible(false);
        deleteBookButton.setDisable(true);
    }
}
