package com.nightlyecommercebackend.specification;

import com.nightlyecommercebackend.entity.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Configuration
public class ProductSpecification {

    public Specification<Product> categoryIdEquals(Long categoryId){
         return (table, query, builder)->builder.equal(table.get("category").get("id"), categoryId);
    }

    public Specification<Product> isBetweenPrice(Double minPrice, Double maxPrice){
        return (table, query, builder) -> builder.between(table.get("price"),minPrice, maxPrice );
    }

}
