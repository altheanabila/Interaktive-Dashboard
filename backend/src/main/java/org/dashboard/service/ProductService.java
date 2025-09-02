package org.dashboard.service;

import org.dashboard.model.Product;
import org.dashboard.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepo productRepo;

  public List<Product> getAllProducts(){
    return productRepo.findAll();
  }

  public Optional<Product> getProductById(Long id){
    return productRepo.findById(id);
  }

  public Product saveProduct(Product product){
    return productRepo.save(product);
  }

  public void deleteProduct(Long id){
    productRepo.deleteById(id);
  }

  public List<Product> getProductsByCategory(String category) {
    return productRepo.findByCategory(category);
  }
}
