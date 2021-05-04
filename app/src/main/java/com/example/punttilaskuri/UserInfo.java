package com.example.punttilaskuri;

import java.util.ArrayList;
/**
 * This class characterises UserData
 * @author Sami Miettinen
 * @version 04.05.2021
 */
public class UserInfo {
    private String name;
    private String age;
    private String weight;
    private String height;

    public UserInfo(ArrayList<String> data){
       this.name = data.get(0);
       this.age = data.get(1);
       this.weight = data.get(2);
       this.height = data.get(3);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
