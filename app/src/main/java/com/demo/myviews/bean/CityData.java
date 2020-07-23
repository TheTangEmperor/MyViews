package com.demo.myviews.bean;

public class CityData {

    private int layoutType;
    private String name;
    private String letter;


    public CityData() {
    }

    public CityData(int layoutType, String name, String letter) {
        this.layoutType = layoutType;
        this.name = name;
        this.letter = letter;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
