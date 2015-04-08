package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * Created by Ильнар on 30.03.2015.
 */
public class Student {
    private String first_Name;
    private String last_Name;
    private int passw_hash;
    private String login;
    private String user_token;
    private List<String> current_urls;

    public Student(String first_Name, String last_Name, int passw_hash, String login, String user_token) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.passw_hash = passw_hash;
        this.login = login;
        this.user_token = user_token;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public int getPassw_hash() {
        return passw_hash;
    }

    public void setPassw_hash(int passw_hash) {
        this.passw_hash = passw_hash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

}
