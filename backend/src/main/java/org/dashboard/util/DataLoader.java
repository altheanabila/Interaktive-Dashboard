package org.dashboard.util;

import org.dashboard.model.Product;
import org.dashboard.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;

public class DataLoader implements ApplicationRunner {

  @Autowired
  private ProductRepo productRepo;

  @Override
  public void run(org.springframework.boot.ApplicationArguments args) throws Exception {

    if (productRepo.count() == 0) {
      productRepo.save(new Product(0L, "Laptop", "A high-performance laptop", 1299.99, "Electronics", 25));
      productRepo.save(new Product(1L, "Desk Chair", "An ergonomic office chair", 299.99, "Home & Office", 15));
      productRepo.save(new Product(2L, "Java Programming Book", "Learn Java the easy way", 39.99, "Books", 100));
    }
  }
}
