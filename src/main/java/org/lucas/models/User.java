package org.lucas.models;

/*enum UserRole{
    PATIENT, DOCTOR
}*/

import org.lucas.util.ObjectBase;

import java.util.Objects;

/**
 * Abstract user class, provides the very basic information required for users
 */
public abstract class User implements ObjectBase {
    /**
     * User's unique id.
     */
    private String id;
    /**
     * User's login/username (not real name)
     */
    private String loginName;
    /**
     * User's real name (full name)
     */
    private String name;
    /**
     * Plaintext Password, may be replaced with a password hash in the future.
     */
    private String password;
    /**
     * The user's email address
     */
    private String email;
    /**
     * The user's specified gender.
     */
    private String gender;
    /**
     * The user's phone number
     */
    private String phoneNumber;

    public User(String id, String loginName, String name, String password, String email, String gender, String phoneNumber) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return The user's unique id.
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return The user's login/username, different from their actual name.
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     *
     * @return The user's real name (full name)
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's real name (full name)
     * @param name The user's real name (full name)
     */
    public void setName(String name) {this.name = name; }

    /**
     * Compares against the provided password and checks if it is the same.
     * @param password The provided password
     * @return true/false if the provided password is the same/not same.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     *
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address
     * @param email The user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return The user's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender
     * @param gender the gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // override methods
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(loginName, user.loginName) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginName, name, password, email, gender);
    }
}

