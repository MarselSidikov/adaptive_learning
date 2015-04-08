package ru.kpfu.ivmiit.learning.tools.models;

/**
 * Created by Ильнар on 08.04.2015.
 */
public class LoginData {
    private String login;
    private String password;
    public void setLogin(String login){
        this.login = login;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }

    public LoginData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginData() {
    }
}