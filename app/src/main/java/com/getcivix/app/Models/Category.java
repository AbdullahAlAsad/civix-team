package com.getcivix.app.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Category {

    public String categoryName;
    public Integer catergoryType;
    public String categoryTypeColor;

    public Category(String categoryName, Integer catergoryType, String categoryTypeColor) {
        this.categoryName = categoryName;
        this.catergoryType = catergoryType;
        this.categoryTypeColor = categoryTypeColor;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", catergoryType=" + catergoryType +
                ", categoryTypeColor='" + categoryTypeColor + '\'' +
                '}';
    }
}
