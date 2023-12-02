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
    private String roadname;
    private String cityname;

    public User( String userId, String emailId, char[] password, String forename,
                          String surname, Integer housenumber, String postcode, String roadname, String cityname ) {
 
                            this.setUserId(userId);
        this.setEmailId(emailId);
        this.setForename(forename);
        this.setSurname(surname);
        this.setPassword (password);
        this.sethousenumber(housenumber);
        this.setPostcode(postcode);
        this.setRoadname(roadname);
        this.setCityname(cityname);
    }


    public User(String userId, String emailId, char[] password,String forename,
        String surname, List<Role> roles, Integer housenumber, String postcode, String roadname, String cityname) {

        this.setUserId(userId);
        this.setEmailId(emailId);
        this.roles = roles;
        this.setForename(forename);
        this.setSurname(surname);
        this.setPassword (password);
        this.sethousenumber(housenumber);
        this.setPostcode(postcode);
        this.setRoadname(roadname);
        this.setCityname(cityname);            

    }

    public User( String userId, String emailId, String forename,
                          String surname, Integer housenumber, String postcode, String roadname, String cityname ) {
 
        this.setUserId(userId);
        this.setEmailId(emailId);
        this.setForename(forename);
        this.setSurname(surname);
        this.sethousenumber(housenumber);
        this.setPostcode(postcode);
        this.setRoadname(roadname);
        this.setCityname(cityname);
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


    /**
     * Sets the user's email address.
     *
     * @param email The user's email address.
     */
    public void setEmailId(String emailId) {

        if (isValidemailId(emailId)) {
        this.emailId = emailId;
        } else {
            throw new IllegalArgumentException("emailId is not Valid.");
        }
    }


     // Private validation methods for each attribute
     private boolean isValidemailId(String emailId) {
        // Checking for duplicate in the model instead 
        return emailId != null && emailId.length() <= 100;     
        // return true;
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

    public void setPassword(char[] password) {

        if (isValidPassword(password)) {
        this.password = password;
        } else {
            throw new IllegalArgumentException("password is too long");
        }        
    }

    private boolean isValidPassword(char[]  password){
        return password != null && password.length <= 100;
    }

    public void setForename(String forename) {

        if (isValidforename(forename)) {
        this.forename = forename;
        } else {
            throw new IllegalArgumentException("forename is too long");
        }                
    }

    private boolean isValidforename(String forename){
        return forename != null && forename.length() <= 50;
    }    
    public void setSurname(String surname) {

        if (isValidsurname(surname)) {
        this.surname = surname;
        } else {
            throw new IllegalArgumentException("surname is too long");
        }              
    }

   private boolean isValidsurname(String surname){
        return surname != null && surname.length() <= 50;
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


    public void setHousenumber(Integer housenumber) {
        this.housenumber = housenumber;
    }


    public void setRoadname(String roadname) {
        this.roadname = roadname;
    }


    public void setCityname(String cityname) {
        this.cityname = cityname;
    }


    public Integer getHousenumber() {
        return housenumber;
    }


    public String getRoadname() {
        return roadname;
    }


    public String getCityname() {
        return cityname;
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
