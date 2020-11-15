package com.assessment.shopx.payload.request;

import com.assessment.shopx.models.PurchaseType;

import javax.validation.constraints.NotBlank;

public class ProductPriceRequest {
    @NotBlank
    private long productId;

    @NotBlank
    private PurchaseType purchaseType;

    @NotBlank
    private int qty;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
