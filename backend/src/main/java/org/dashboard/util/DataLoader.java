package org.dashboard.util;

import org.dashboard.model.Product;
import org.dashboard.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

  @Autowired
  private ProductRepo productRepo;

  @Autowired
  public DataLoader(ProductRepo productRepository) {
    this.productRepo = productRepository;
  }

  @Override
  public void run(org.springframework.boot.ApplicationArguments args) throws Exception {

    if (productRepo.count() == 0) {
      List<Product> sampleProducts = List.of(
              new Product(null, "Laptop", "A high-performance laptop for gaming and development", 1299.99, "Electronics", 25),
              new Product(null, "Desk Chair", "An ergonomic office chair for long work hours", 299.99, "Furniture", 15),
              new Product(null, "Java Programming Book", "Learn Java the easy way with this comprehensive guide", 39.99, "Books", 100),
              new Product(null, "Wireless Mouse", "A responsive wireless mouse with ergonomic design", 24.99, "Electronics", 50),
              new Product(null, "Coffee Mug", "A large ceramic mug for your morning coffee", 9.99, "Home & Kitchen", 200)
      );
      productRepo.saveAll(sampleProducts);
      System.out.println("Sample data loaded successfully!");
    }
  }
}
