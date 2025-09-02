package org.dashboard.repository;

import org.dashboard.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {

  List<Product> findByCategory(String category);
}
