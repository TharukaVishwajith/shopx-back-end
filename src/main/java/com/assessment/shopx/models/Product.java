package com.assessment.shopx.models;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private float cartonCost;

    private int NoOfUnitsPerCarton;

    public Product(Long id, String name, float cartonCost, int noOfUnitsPerCarton) {
        this.id = id;
        this.name = name;
        this.cartonCost = cartonCost;
        NoOfUnitsPerCarton = noOfUnitsPerCarton;
    }

    public Product() {
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

    public int getNoOfUnitsPerCarton() {
        return NoOfUnitsPerCarton;
    }

    public void setNoOfUnitsPerCarton(int noOfUnitsPerCarton) {
        NoOfUnitsPerCarton = noOfUnitsPerCarton;
    }
}
