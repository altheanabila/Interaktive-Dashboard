package org.dashboard.util;

import org.dashboard.model.Product;
import org.dashboard.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

  @Autowired
  private ProductRepo productRepo;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // Only run in dev or if DB is empty
    if (productRepo.count() == 0) {
      List<Product> sampleProducts = List.of(
              new Product(null, "Laptop", "High-performance laptop", 1299.99, "Electronics", 25),
              new Product(null, "Desk Chair", "Ergonomic chair", 299.99, "Furniture", 15),
              new Product(null, "Java Book", "Learn Java easily", 39.99, "Books", 100),
              new Product(null, "Wireless Mouse", "Responsive wireless mouse", 24.99, "Electronics", 50),
              new Product(null, "Coffee Mug", "Large ceramic mug", 9.99, "Home & Kitchen", 200)
      );
      productRepo.saveAll(sampleProducts);
      System.out.println("Sample data loaded!");
    }
  }
}