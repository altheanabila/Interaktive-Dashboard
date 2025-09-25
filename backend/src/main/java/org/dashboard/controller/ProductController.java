package org.dashboard.controller;

import org.dashboard.model.Product;
import org.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "https://interaktive-dashboard-3p9w.vercel.app")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  // GET product by ID
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Optional<Product> product = productService.getProductById(id);
    return product.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  // CREATE new product
  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }



  // UPDATE existing product
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
    Optional<Product> product = productService.getProductById(id);

    if (product.isPresent()) {
      Product existingProduct = product.get();
      existingProduct.setName(productDetails.getName());
      existingProduct.setDescription(productDetails.getDescription());
      existingProduct.setPrice(productDetails.getPrice());
      existingProduct.setCategory(productDetails.getCategory());
      existingProduct.setStockQuantity(productDetails.getStockQuantity());

      Product updatedProduct = productService.saveProduct(existingProduct);
      return ResponseEntity.ok(updatedProduct);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // DELETE product
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    Optional<Product> product = productService.getProductById(id);

    if (product.isPresent()) {
      productService.deleteProduct(id);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
