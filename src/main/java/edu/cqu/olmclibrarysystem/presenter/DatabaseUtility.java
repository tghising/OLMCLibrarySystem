package edu.cqu.olmclibrarysystem.presenter;

/**
 * DatabaseUtility: Helper Class for data base utility
 *
 * @author Tikaraj Ghising - 12129239
 */
public class DatabaseUtility {

    // method to parse Java Util Date into SQL date
    public java.sql.Date convertToSQLDate(java.util.Date date) {
        java.sql.Date newDate = null;
        if (date != null) {
            newDate = new java.sql.Date(date.getTime());
        }
        return newDate;
    }
}
