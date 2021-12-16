package com.example.market.Model;

public class Category {
    private  String id, Name;
    private String Image;

    public Category() {

    }

    public Category(String id, String name, String image) {
        this.id = id;
        Name = name;
        Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
