package ru.kpfu.ivmiit.learning.tools.models;

import java.util.ArrayList;

/**
 * @author Ilnar Ramazanov (Kazan Federal University)
 */
public class Student {
  private String name;
  private String lastname;
  private ArrayList<Lesson> userLesson;
  private LoginData userLogin;
  public void setName(String name){
    this.name = name;
  }
  public void setLastName(String lastname){
    this.lastname = lastname;
  }
  public void setMaterial(ArrayList<Lesson> userLesson){
    this.userLesson = new ArrayList<Lesson>();
    this.userLesson = userLesson;
  }
  public void setUserLogin(String login, String password){
    userLogin = new LoginData();
    userLogin.setLogin(login);
    userLogin.setPassword(password);
  }
  public String getName(){
   return name; 
  }
  public String lastname(){
    return lastname;
  }
  public ArrayList<Lesson> getMaterial(){
    return userLesson;
  }
  public LoginData getLoginData(){
    return userLogin;
  }
  public Student(){
    userLesson = new ArrayList<Lesson>();
    userLogin = new LoginData();
  }
}
