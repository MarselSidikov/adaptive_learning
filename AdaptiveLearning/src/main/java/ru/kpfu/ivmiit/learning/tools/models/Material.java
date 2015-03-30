package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * @author Ilnar Ramazanov (Kazan Federal University)
 */
public class Material {
    public List<String> getUrl() {
        return url;
    }

    public Material(List<String> url) {
        this.url = url;
    }

    private List<String> url;
  public void setMaterial(List<String> textOfMaterial){
    this.url = url;
  }
}
