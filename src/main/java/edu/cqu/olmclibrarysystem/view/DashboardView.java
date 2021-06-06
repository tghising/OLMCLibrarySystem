package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.model.*;
import edu.cqu.olmclibrarysystem.presenter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DashboardView: Dashboard View of of Library
 *
 * @author Tikaraj Ghising - 12129239
 */
public class DashboardView implements Initializable, IViewDashboard {

    @FXML
    private AnchorPane overDueReturnsLayout;

    @FXML
    private AnchorPane dashboardLayout;

    @FXML
    private TableView<MemberBorrowedBookTableView> overdueBooksTable = new TableView<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Integer> dueBookId = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, String> dueBookTitle = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, String> dueBookMember = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Date> dueBookIssuedDate = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Date> dueBookDueDate = new TableColumn<>();

    @FXML
    private TableView<MemberBorrowedBookTableView> allIssuedBooksTable = new TableView<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Integer> issueBookId = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, String> issueBookTitle = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, String> issueBookMemberName = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Date> issueBookDueDate = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Date> issueBookIssueDate = new TableColumn<>();

    @FXML
    private TableView<MemberBorrowedBookTableView> overDueReturnBooksTable = new TableView<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Integer> overDueReturnBookId = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, String> overDueReturnBookTitle = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, String> overDueReturnBookMember = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Date> overDueReturnBookDueDate = new TableColumn<>();

    @FXML
    private TableColumn<MemberBorrowedBookTableView, Date> overDueReturnBookReturnDate = new TableColumn<>();

    private MemberPresenter memberPresenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.memberPresenter = new MemberPresenter();
        goToDashboard();

    }

    @FXML
    private void goToDashboard() {
        dashboardLayout.setVisible(true);
        overDueReturnsLayout.setVisible(false);
        getAllIssuedBooks();
        getAllOverDueBooks();
    }

    @Override
    public void getAllOverDueBooks() {
        dueBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        dueBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        dueBookMember.setCellValueFactory(new PropertyValueFactory<>("bookMemberName"));
        dueBookIssuedDate.setCellValueFactory(new PropertyValueFactory<>("bookIssuedDate"));
        dueBookDueDate.setCellValueFactory(new PropertyValueFactory<>("bookDueDate"));

        ObservableList<MemberBorrowedBookTableView> allOverDueBooks = FXCollections.observableArrayList(this.memberBorrowedBookMappingToTableView(this.memberPresenter.getAllOverDueBooks()));
        overdueBooksTable.getItems().clear();
        if (allOverDueBooks.isEmpty()) {
            overdueBooksTable.setPlaceholder(new Label("No records found!"));
        } else {
            overdueBooksTable.setItems(allOverDueBooks);
        }
    }

    @Override
    public void getAllOverDueReturnBooks() {
        dashboardLayout.setVisible(false);
        overDueReturnsLayout.setVisible(true);
        overDueReturnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        overDueReturnBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        overDueReturnBookMember.setCellValueFactory(new PropertyValueFactory<>("bookMemberName"));
        overDueReturnBookReturnDate.setCellValueFactory(new PropertyValueFactory<>("bookReturnDate"));
        overDueReturnBookDueDate.setCellValueFactory(new PropertyValueFactory<>("bookDueDate"));

        ObservableList<MemberBorrowedBookTableView> allOverDueBooks = FXCollections.observableArrayList(this.memberBorrowedBookMappingToTableView(this.memberPresenter.getAllOverDueReturnsBorrowedBooks()));
        overDueReturnBooksTable.getItems().clear();
        if (allOverDueBooks.isEmpty()) {
            overDueReturnBooksTable.setPlaceholder(new Label("No records found!"));
        } else {
            overDueReturnBooksTable.setItems(allOverDueBooks);
        }
    }

    private List<MemberBorrowedBookTableView> memberBorrowedBookMappingToTableView(List<MemberBorrowedBook> books) {
        List<MemberBorrowedBookTableView> bookTableViewList = new ArrayList<>();

        for (MemberBorrowedBook memberBook : books) {
            MemberBorrowedBookTableView bookTableView = new MemberBorrowedBookTableView();
            bookTableView.setBookId(memberBook.getBorrowedBook().getBookId());
            bookTableView.setBookTitle(memberBook.getBorrowedBook().getTitle());
            bookTableView.setBookAuthor(memberBook.getBorrowedBook().getAuthor().getFullName());
            bookTableView.setBookMemberName(memberBook.getBorrowerMember().getFullName());
            bookTableView.setBookIssuedDate(memberBook.getIssueDate());
            bookTableView.setBookDueDate(memberBook.getDueDate());
            bookTableView.setBookReturnDate(memberBook.getReturnDate());
            bookTableViewList.add(bookTableView);
        }
        return bookTableViewList;
    }

    @Override
    public void getAllIssuedBooks() {
        issueBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        issueBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        issueBookMemberName.setCellValueFactory(new PropertyValueFactory<>("bookMemberName"));
        issueBookIssueDate.setCellValueFactory(new PropertyValueFactory<>("bookIssuedDate"));
        issueBookDueDate.setCellValueFactory(new PropertyValueFactory<>("bookDueDate"));

        ObservableList<MemberBorrowedBookTableView> allOverDueBooks = FXCollections.observableArrayList(this.memberBorrowedBookMappingToTableView(this.memberPresenter.getAllIssuedBooks()));
        allIssuedBooksTable.getItems().clear();
        if (allOverDueBooks.isEmpty()) {
            allIssuedBooksTable.setPlaceholder(new Label("No records found!"));
        } else {
            allIssuedBooksTable.setItems(allOverDueBooks);
        }
    }

    protected class MemberBorrowedBookTableView {

        private Integer bookId;
        private String bookTitle;
        private String bookAuthor;
        private String bookMemberName;
        private Date bookIssuedDate;
        private Date bookDueDate;
        private Date bookReturnDate;

        public MemberBorrowedBookTableView() {
        }

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public String getBookAuthor() {
            return bookAuthor;
        }

        public void setBookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
        }

        public String getBookMemberName() {
            return bookMemberName;
        }

        public void setBookMemberName(String bookMemberName) {
            this.bookMemberName = bookMemberName;
        }

        public Date getBookDueDate() {
            return bookDueDate;
        }

        public void setBookDueDate(Date bookDueDate) {
            this.bookDueDate = bookDueDate;
        }

        public Date getBookIssuedDate() {
            return bookIssuedDate;
        }

        public void setBookIssuedDate(Date bookIssuedDate) {
            this.bookIssuedDate = bookIssuedDate;
        }

        public Date getBookReturnDate() {
            return bookReturnDate;
        }

        public void setBookReturnDate(Date bookReturnDate) {
            this.bookReturnDate = bookReturnDate;
        }
    }
}
