package com.assessment.shopx.payload.response;

import com.assessment.shopx.models.Product;

public class ProductResponse {
    private Long id;

    private String name;

    private float cartonCost;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.cartonCost = product.getCartonCost();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCartonCost() {
        return cartonCost;
    }

    public void setCartonCost(float cartonCost) {
        this.cartonCost = cartonCost;
    }
}
