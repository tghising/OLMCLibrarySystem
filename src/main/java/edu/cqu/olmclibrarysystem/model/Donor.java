package edu.cqu.olmclibrarysystem.model;

/**
 * Donor: Model class for Donor manipulation 
 *
 * @author Tikaraj Ghising - 12129239
 */

public class Donor {
    private int donorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    // default constructor
    public Donor() {
    }

    // parameterized constructor


    public Donor(String fullName, String email, String phoneNumber, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
