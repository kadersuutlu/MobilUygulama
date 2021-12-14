package com.example.market.Model;

public class Product {
    private String productName;
    private String productImage;
    private String productPrice;

    public Product() {

    }
    public Product(String name, String image,String price) {
        productName = name;
        productImage = image;
        productPrice = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
