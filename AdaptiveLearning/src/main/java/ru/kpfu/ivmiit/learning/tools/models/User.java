package ru.kpfu.ivmiit.learning.tools.models;

public class User {
  String name;
  String lastname;
  ArrayList<Material> userMaterial;
  LoginData userLogin;
  public User(){
    userMaterial = new ArrayList<Material>();
  }
}
