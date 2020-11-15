package com.assessment.shopx.service;

import com.assessment.shopx.models.Product;
import com.assessment.shopx.models.PurchaseType;
import com.assessment.shopx.payload.request.ProductPriceRequest;
import com.assessment.shopx.payload.response.ProductPriceResponse;
import com.assessment.shopx.payload.response.ProductResponse;
import com.assessment.shopx.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    public void testListProducts() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(productRepository.findAll(pageRequest)).
                thenReturn(new PageImpl<>(getProducts(), pageRequest, pageRequest.getPageSize()));
        Page<ProductResponse> productResponses = productService.list(0,10);
        assertThat(productResponses.getContent().get(0).getId()).isEqualTo(getProducts().get(0).getId());
    }

    @Test
    public void testProductPriceList() {
        Optional<Product> productOptional = Optional.of(getProducts().get(0));
        when(productRepository.findById(getProducts().get(0).getId())).
                thenReturn(productOptional);
        List<ProductPriceResponse> priceResponseList =
                productService.calculatePriceList(getProducts().get(0).getId(),50);

        assertThat(priceResponseList.get(0).getCost()).isEqualTo(11.375);
        assertThat(priceResponseList.get(18).getCost()).isEqualTo(216.125);
        assertThat(priceResponseList.get(19).getCost()).isEqualTo(175);
        assertThat(priceResponseList.get(20).getCost()).isEqualTo(183.75);
        assertThat(priceResponseList.get(49).getCost()).isEqualTo(437.5);
    }

    @Test
    public void testCalculatePrice() {
        Optional<Product> productOptional = Optional.of(getProducts().get(0));
        when(productRepository.findById(getProducts().get(0).getId())).
                thenReturn(productOptional);
        ProductPriceRequest productPriceRequest = new ProductPriceRequest();

        productPriceRequest.setProductId(getProducts().get(0).getId());

        productPriceRequest.setPurchaseType(PurchaseType.UNIT);
        productPriceRequest.setQty(18);
        assertThat(productService.calculatePrice(productPriceRequest).getCost()).isEqualTo(204.75);

        productPriceRequest.setPurchaseType(PurchaseType.UNIT);
        productPriceRequest.setQty(20);
        assertThat(productService.calculatePrice(productPriceRequest).getCost()).isEqualTo(175);

        productPriceRequest.setPurchaseType(PurchaseType.CARTON);
        productPriceRequest.setQty(2);
        assertThat(productService.calculatePrice(productPriceRequest).getCost()).isEqualTo(350);

        productPriceRequest.setPurchaseType(PurchaseType.CARTON);
        productPriceRequest.setQty(3);
        assertThat(productService.calculatePrice(productPriceRequest).getCost()).isEqualTo(472.5);

        productPriceRequest.setPurchaseType(PurchaseType.CARTON);
        productPriceRequest.setQty(4);
        assertThat(productService.calculatePrice(productPriceRequest).getCost()).isEqualTo(630);
    }

    private List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L,"Penguin-ears",175,20));
        products.add(new Product(2L,"Horseshoe",825,5));
        return products;
    }


}

