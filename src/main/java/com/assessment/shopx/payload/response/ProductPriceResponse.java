package com.assessment.shopx.payload.response;

public class ProductPriceResponse {
    int onlyUnits;
    int noOfCartons;
    int noOfUnits;
    double cost;

    public ProductPriceResponse() {
    }

    public ProductPriceResponse(int onlyUnits, int noOfCartons, int noOfUnits, double cost) {
        this.onlyUnits = onlyUnits;
        this.noOfCartons = noOfCartons;
        this.noOfUnits = noOfUnits;
        this.cost = cost;
    }

    public int getOnlyUnits() {
        return onlyUnits;
    }

    public void setOnlyUnits(int onlyUnits) {
        this.onlyUnits = onlyUnits;
    }

    public int getNoOfCartons() {
        return noOfCartons;
    }

    public void setNoOfCartons(int noOfCartons) {
        this.noOfCartons = noOfCartons;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
