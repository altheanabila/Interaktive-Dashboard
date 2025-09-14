package org.dashboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Double price;
  private String category;
  private Integer stockQuantity;


  public void setName(String name) { this.name = name; }
  public void setDescription(String description) { this.description = description; }
  public void setPrice(Double price) { this.price = price; }
  public void setCategory(String category) { this.category = category; }
  public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }


}
