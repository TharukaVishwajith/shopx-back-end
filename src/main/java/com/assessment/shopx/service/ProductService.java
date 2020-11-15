package com.assessment.shopx.service;

import com.assessment.shopx.models.Product;
import com.assessment.shopx.payload.request.ProductPriceRequest;
import com.assessment.shopx.payload.response.ProductPriceResponse;
import com.assessment.shopx.payload.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    Page<ProductResponse> list(int page, int size);

    ProductPriceResponse calculatePrice(ProductPriceRequest productPriceRequest);

    List<ProductPriceResponse> calculatePriceList(Long productId, int units);
}
