package com.sheffield.trainStore.model;

/**
 * The Role enum represents the roles that users can have in the application.
 * Each role has a corresponding name.
 */
public enum Role {
    MANAGER("Manager"),
    STAFF("Staff"),
    CUSTOMER("Customer");

    // role ENUM('Manager', 'Staff', 'Customer') NOT NULL,

    // Instance variable to hold the role name
    private final String roleName;

    /**
     * Constructor for the Role enum.
     *
     * @param roleName The name associated with the role.
     */
    Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the name associated with the role.
     *
     * @return The role name.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Converts a string to a UserRole enum constant.
     *
     * @param roleName The name of the role to convert.
     * @return The corresponding UserRole enum constant.
     * @throws IllegalArgumentException if no enum constant with the given roleName is found.
     */
    public static Role fromString(String roleName) {
        for (Role role : Role.values()) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant with roleName: " + roleName);
    }
}