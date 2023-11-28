package com.sheffield.trainStore.model;

import java.math.BigDecimal;
import java.util.List;

import javax.management.relation.Role;

// PENDING
// Implement validatiom
// check all the get and set method

public class User {

    private String userId;
    private String emailId;
    private char[] password;
    private String forename;
    private String surname;

    private List<Role> roles;
    private Integer housenumber;
    private String postcode;


      public User( String userId, String emailId, char[] password, String forename,
        String surname, Integer housenumber, String postcode) {

        this.userId = userId;
        this.emailId = emailId;
        // this.roles = roles;

        this.forename = forename;
        this.surname = surname;
        this.password = password;

        this.housenumber = housenumber;
        this.postcode = postcode;

    }


    public User(String userId, String emailId, char[] password,String forename,
        String surname, List<Role> roles, Integer housenumber, String postcode) {

        this.userId = userId;
        this.emailId = emailId;
        this.roles = roles;

        this.forename = forename;
        this.surname = surname;
        this.password = password;

        this.housenumber = housenumber;
        this.postcode = postcode;

    }



    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char[] getPassword() {
        return password;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
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
    public String getEmailId() {
        return emailId;
    }


    public void sethousenumber(Integer housenumber) {
        this.housenumber = housenumber;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getHouseNumber() {
        return housenumber;
    }

    public String getPostcode() {
        return postcode;
    }



    /**
     * Sets the user's email address.
     *
     * @param email The user's email address.
     */
    public void setEmailId(String emailId) {

        if (isValidemailId(emailId)) {
        this.emailId = emailId;
        } else {
            throw new IllegalArgumentException("emailId is already exists.");
        }
    }


     // Private validation methods for each attribute
     private boolean isValidemailId(String emailId) {
        // need to check for duplicate emailid
        
        return true;
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
                ", email='" + emailId + '\'' +
                ", role='" + roles.toString() + '\'' +
                '}';
    }

}
