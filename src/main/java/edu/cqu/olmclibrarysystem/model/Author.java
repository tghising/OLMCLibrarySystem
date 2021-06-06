package edu.cqu.olmclibrarysystem.model;

/**
 * Author: Model class for Author registration
 *
 * @author Tikaraj Ghising - 12129239
 */
public class Author {

    private int authorId;
    private String fullName;

    // default constructor
    public Author() {
    }

    // parameterized constructor
    public Author(String fullName) {
        this.fullName = fullName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
