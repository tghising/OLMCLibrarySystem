package edu.cqu.olmclibrarysystem.enums;

/**
 * UserRole: enum for classify user role
 *
 * @author Tikaraj Ghising - 12129239
 */
public enum UserRole {
    MAIN_LIBRARIAN("Main Librarian"),
    LIBRARIAN("Librarian");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public static UserRole getType(String roleType) {
        if (roleType != null) {
            for (UserRole userRole : UserRole.values()) {
                if (roleType.equalsIgnoreCase(userRole.role)) {
                    return userRole;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
