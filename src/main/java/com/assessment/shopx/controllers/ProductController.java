package com.assessment.shopx.controllers;

import com.assessment.shopx.payload.request.ProductPriceRequest;
import com.assessment.shopx.payload.response.ProductPriceResponse;
import com.assessment.shopx.payload.response.ProductResponse;
import com.assessment.shopx.service.ProductService;
import org.apache.catalina.loader.ResourceEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public Page<ProductResponse> list(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        return productService.list(page,size);
    }

    @PostMapping("/calculate-price")
    public ResponseEntity<ProductPriceResponse> calculateProductPrice(@RequestBody ProductPriceRequest productPriceRequest) {
        return ResponseEntity.ok(productService.calculatePrice(productPriceRequest));
    }

    @GetMapping("/calculate-price-list")
    public ResponseEntity<List<ProductPriceResponse>> calculatePriceList(@RequestParam("productId") Long productId,
                                                           @RequestParam(name = "units", defaultValue = "50") int units) {
        return ResponseEntity.ok(productService.calculatePriceList(productId, units));
    }
}
