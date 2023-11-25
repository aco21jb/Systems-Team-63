package com.sheffield.trainStore.model;

import java.util.List;

/**
 * The CurrentUser class represents the user that has logged in to this system.
 */
public class CurrentUser {
    private String userId;
    private String email;
    private List<Role> roles;

    /**
     * Constructor for creating a User object.
     *
     * @param userId   The unique identifier for the user.
     * @param email    The email address of the user.
     * @param username The username chosen by the user.
     * @param roles     The role assigned to the user.
     */
    // public CurrentUser(String userId, String email, String username, List<Role> roles) {
    public CurrentUser(String userId, String email, List<Role> roles) {

        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return The user's unique identifier.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userId The user's unique identifier.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The user's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

   
    /**
     * Gets the list of roles associated with the user.
     *
     * @return The list of roles.
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the list of roles associated with the user.
     *
     * @param roles The list of roles to be set.
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Overrides the toString method to provide a string representation of the User object.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", role='" + roles.toString() + '\'' +
                '}';
    }
}