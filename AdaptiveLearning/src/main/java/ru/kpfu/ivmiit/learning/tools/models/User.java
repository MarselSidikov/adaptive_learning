package ru.kpfu.ivmiit.learning.tools.models;

import java.util.ArrayList;

/**
 * @author Ilnar Ramazanov (Kazan Federal University)
 */
public class User {
  private String name;
  private String lastname;
  private ArrayList<Material> userMaterial;
  private LoginData userLogin;
  public void setName(String name){
    this.name = name;
  }
  public void setLastName(String lastname){
    this.lastname = lastname;
  }
  public void setMaterial(ArrayList<Material> userMaterial){
    this.userMaterial = userMaterial;
  }
  public void setUserLogin(String login, String password){
    userLogin = new LoginData();
    userLogin.setLogin(login);
    userLogin.setPassword(password);
  }
  public String getName(){
   return name; 
  }
  public String getLastname(){
    return lastname;
  }
  public ArrayList<Material> getMaterial(){
    return userMaterial;
  }
  public LoginData getLoginData(){
    return userLogin;
  }
  public User(){
    userMaterial = new ArrayList<Material>();
    userLogin = new LoginData();
  }
}
