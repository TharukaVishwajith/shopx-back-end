package com.assessment.shopx.repository;

import com.assessment.shopx.models.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
