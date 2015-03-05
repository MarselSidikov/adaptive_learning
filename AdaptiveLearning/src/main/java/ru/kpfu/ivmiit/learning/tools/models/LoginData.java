package ru.kpfu.ivmiit.learning.tools.models;

/**
 * @author Ilnar Ramazanov (Kazan Federal University)
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
}
