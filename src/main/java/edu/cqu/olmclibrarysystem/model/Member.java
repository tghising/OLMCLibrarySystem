package edu.cqu.olmclibrarysystem.model;

/**
 * Member: Model class for Borrowers manipulation
 *
 * @author Tikaraj Ghising - 12129239
 */
public class Member {
    private int memberId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    // default constructor
    public Member() {
    }

    // parameterized constructor
    public Member(String name, String email, String phoneNumber, String address) {
        this.fullName = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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
        return fullName;
    }
}
