package ru.kpfu.ivmiit.learning.tools.models;

import java.util.List;

/**
 * @author Ilnar Ramazanov (Kazan Federal University)
 */
public class Material {

    private List<String> url;

    public List<String> getUrl() {

        return url;
    }

    public void setMaterial(List<String> url){
        this.url = url;
    }

    public Material(List<String> url) {

        this.url = url;
    }
}
