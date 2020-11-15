package com.assessment.shopx.repository;

import com.assessment.shopx.models.Discount;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount, Long> {
}
