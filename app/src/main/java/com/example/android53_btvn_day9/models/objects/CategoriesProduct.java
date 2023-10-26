package com.example.android53_btvn_day9.models.objects;

import java.util.List;

public class CategoriesProduct {
    private String nameTitle;
    private List<Product> listProduct;

    public String getNameTitle() {
        return nameTitle;
    }

    public CategoriesProduct() {
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public CategoriesProduct(String nameTitle, List<Product> listProduct) {
        this.nameTitle = nameTitle;
        this.listProduct = listProduct;
    }
}
