package com.example.abc;

public class Product {
    private String title,shortdesc;
    private String image,url;

    public Product(String title, String shortdesc, String image,String url) {
        this.title = title;
        this.url=url;
        this.shortdesc = shortdesc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getImage() {
        return image;
    }
    public String getUrl() {
        return url;
    }

}
