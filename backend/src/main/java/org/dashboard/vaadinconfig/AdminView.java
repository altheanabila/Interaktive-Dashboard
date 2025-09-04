package org.dashboard.vaadinconfig;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.dashboard.model.Product;
import org.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("admin")
public class AdminView extends VerticalLayout {
  private final ProductService productService;
  private final Grid<Product> grid = new Grid<>(Product.class);

  @Autowired
  public AdminView(ProductService productService) {
    this.productService = productService;
    configureGrid();
    add(grid);
    updateList();
  }

  private void configureGrid() {
    grid.setColumns("id", "name", "description", "price", "category", "stockQuantity");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
  }

  private void updateList() {
    grid.setItems(productService.getAllProducts());
  }

}
