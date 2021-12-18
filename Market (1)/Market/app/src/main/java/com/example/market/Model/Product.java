package com.example.market.Model;

public class Product {
    private String productName;
    private String productImage;
    private String productPrice;
    private String categoryID;
    private String productID;

    public Product() {

    }
    public Product(String name, String image,String price,String id, String productID) {
        productName = name;
        productImage = image;
        productPrice = price;
        categoryID=id;
        this.productID = productID;
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

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
