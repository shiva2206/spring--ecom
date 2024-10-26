package com.shiva.Ecommerce_spring.Repository;

import com.shiva.Ecommerce_spring.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
