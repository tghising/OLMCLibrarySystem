package edu.cqu.olmclibrarysystem.util;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * HelperUtility: Helper Utility for HTS
 *
 * @author Tikaraj Ghising
 */
public class HelperUtility {
//    String regexISBN10 = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";

    // method to disable future date picker
    public void disableFutureDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
    }

    // method to disable past date picker
    public void disablePastDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });
    }

    // method to convert Local Date to Java Util Date
    public Date getDateFromLocalDate(LocalDate date) {
        Date newDate = null;
        if (date != null) {
            newDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return newDate;
    }

    // method to calculate after one month date
    public Date expireDateAfterMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        Date expirationDate = calendar.getTime();
        return expirationDate;
    }

    // method to calculate after Six month date
    public Date expireDateAfterSixMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 6);
        Date expirationDate = calendar.getTime();
        return expirationDate;
    }

    public void inputErrorValidation(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean confirmValidation(String title, String header, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == buttonTypeOk;
        }
    }
