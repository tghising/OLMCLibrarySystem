package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.model.Member;
import edu.cqu.olmclibrarysystem.presenter.IViewMember;
import edu.cqu.olmclibrarysystem.presenter.MemberPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import edu.cqu.olmclibrarysystem.util.HelperUtility;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MemberView implements Initializable, IViewMember {

    @FXML
    private TextField newMemberFullname;

    @FXML
    private TextField newMemberEmailAddress;

    @FXML
    private TextField newMemberPhoneNumber;

    @FXML
    private TextArea newMemberAddress;

    @FXML
    private TableView<Member> membersListTable = new TableView<>();

    @FXML
    private TableColumn<Member, Integer> membersIdColumn = new TableColumn<>();

    @FXML
    private TableColumn<Member, String> membersFullNameColumn = new TableColumn<>();

    @FXML
    private TableColumn<Member, String> membersEmailAddressColumn = new TableColumn<>();

    @FXML
    private TableColumn<Member, String> membersPhoneNumberColumn = new TableColumn<>();

    @FXML
    private TableColumn<Member, String> membersAddressColumn = new TableColumn<>();
    
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"; // email regex
    private static final String FULL_NAME_REGEX = "^[A-Z][a-zA-Z]{2,}(?: [A-Z][a-zA-Z]*){0,2}$"; // full name regex
    private static final String PHONE_REGEX = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"; // 10 digits phone number regex

    private MemberPresenter memberPresenter;
    private HelperUtility helperUtility;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.memberPresenter = new MemberPresenter();
        this.helperUtility = new HelperUtility();
        getAllMembers();
        resetAddMemberForm();
        
    }

    @Override
    public void getAllMembers() {
        membersIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        membersFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        membersEmailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        membersPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        membersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        ObservableList<Member> membersObservableList = FXCollections.observableArrayList(this.memberPresenter.getAllMembers());
        membersListTable.getItems().clear();
        if (membersObservableList.isEmpty()) {
            membersListTable.setPlaceholder(new Label("No records found!"));
        } else {
            membersListTable.setItems(membersObservableList);
        }
    }

    @Override
    @FXML
    public void addMember(ActionEvent event) {
        String fullName = newMemberFullname.getText();
        String emailAddress = newMemberEmailAddress.getText();
        String phoneNumber = newMemberPhoneNumber.getText();
        String address = newMemberAddress.getText();

        if (!addMemberValidation()) {
            Member newMember = new Member(fullName, emailAddress, phoneNumber, address);
            this.memberPresenter.add(newMember);

            this.resetAddMemberForm();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Member " + newMember.getFullName() + " has been successfully added");
            alert.showAndWait();
            getAllMembers();
            resetAddMemberForm();
        } 
        
    }

    @FXML
    public void resetAddMemberForm() {
        newMemberFullname.clear();
        newMemberEmailAddress.clear();
        newMemberPhoneNumber.clear();
        newMemberAddress.clear();
    }

    private boolean addMemberValidation() {
        boolean boolValue = false;
        String validationTitle = "Input validation";  
        
        if (newMemberFullname.getText().isEmpty() || !newMemberFullname.getText().matches(FULL_NAME_REGEX)) {
            newMemberFullname.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Name must be non-numeric and start with \nUppercase letter and at least 3 characters.");
            boolValue = true;
            
        } else if (newMemberEmailAddress.getText().isEmpty() || !newMemberEmailAddress.getText().matches(EMAIL_REGEX)) {
            newMemberEmailAddress.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter a valid email address.");
            boolValue = true;       
            
        } else if (newMemberPhoneNumber.getText().isEmpty() || !newMemberPhoneNumber.getText().matches(PHONE_REGEX)) {
            newMemberPhoneNumber.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid 10 digits phone number.");
            boolValue = true;   
        
        } else if (newMemberAddress.getText().isEmpty()) {
            newMemberPhoneNumber.requestFocus();
            helperUtility.inputErrorValidation(validationTitle, null, "Please enter valid address.");
            boolValue = true;   
            
        }
        return boolValue;
    }
}
