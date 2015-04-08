package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Ильнар on 30.03.2015.
 */
public class Student {
    private String first_Name;
    private String lastName;
    private int passwHash;
    private String login;
    private String userToken;

    public Student(String first_Name, String lastName, int passwHash, String login, String userToken) {
        this.first_Name = first_Name;
        this.lastName = lastName;
        this.passwHash = passwHash;
        this.login = login;
        this.userToken = userToken;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPasswHash() {
        return passwHash;
    }

    public void setPasswHash(int passwHash) {
        this.passwHash = passwHash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
