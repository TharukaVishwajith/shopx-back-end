package com.assessment.shopx.service.impl;

import com.assessment.shopx.models.Product;
import com.assessment.shopx.payload.request.ProductPriceRequest;
import com.assessment.shopx.payload.response.ProductPriceResponse;
import com.assessment.shopx.payload.response.ProductResponse;
import com.assessment.shopx.repository.DiscountRepository;
import com.assessment.shopx.repository.ProductRepository;
import com.assessment.shopx.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ProductRepository productRepository;

    final
    DiscountRepository discountRepository;

    public ProductServiceImpl(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductResponse> list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> pageResult = productRepository.findAll(pageRequest);
        List<ProductResponse> products = pageResult
                .stream()
                .map(ProductResponse::new)
                .collect(toList());
        return new PageImpl<>(products, pageRequest, pageResult.getTotalElements());
    }

    @Override
    public double calculatePrice(ProductPriceRequest productPriceRequest) {
        Product product = productRepository.findById(productPriceRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Error: Product not found."));

        int units = 0;
        int cartons = 0;

        int qty = productPriceRequest.getQty();
        int noupc = product.getNoOfUnitsPerCarton();
        switch (productPriceRequest.getPurchaseType()){
            case UNIT:
                cartons = qty/noupc;
                if(cartons > 0){
                    units = qty % noupc;
                } else {
                    cartons = 0;
                    units = qty;
                }
//                return calculatePriceForUnits( product.getCartonCost(), qty, noupc);
                break;
            case CARTON:
                cartons = qty;
        }
        return calculatePrice(cartons, units, product.getCartonCost(), noupc);
    }

    private ProductPriceResponse calculatePriceForUnits(float cartonCost, int qty, int noupc){
        int cartons = qty/noupc;
        int units = 0;

        if(cartons > 0){
            units = qty % noupc;
        } else {
            cartons = 0;
            units = qty;
        }
        return  new ProductPriceResponse(qty, cartons, units,calculatePrice(cartons, units, cartonCost, noupc));
    }

    @Override
    public List<ProductPriceResponse> calculatePriceList(Long productId, int units) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Error: Product not found."));

        List<ProductPriceResponse> priceList = new ArrayList<>();
       for(int i = 0; i < units; i++){
           priceList.add(calculatePriceForUnits(product.getCartonCost(),i,product.getNoOfUnitsPerCarton()));
       }
       return priceList;
    }

    private double calculatePrice(int cartons, int units, double cartonCost, int noupc){
        double totalPrice = cartons * cartonCost + cartonCost/noupc * units;
        if (cartons == 0){
            totalPrice *= 1.3;
        }else if(cartons >= 3){
            totalPrice *= 0.9;
        }
        return totalPrice;
    }
}
