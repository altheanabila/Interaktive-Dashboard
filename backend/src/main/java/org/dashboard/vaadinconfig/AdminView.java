package org.dashboard.vaadinconfig;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.dashboard.model.Product;
import org.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("admin")
public class AdminView extends VerticalLayout {

  private final ProductService productService;
  private final Grid<Product> grid = new Grid<>(Product.class);
  private final TextField filterText = new TextField();

  @Autowired
  public AdminView(ProductService productService) {
    this.productService = productService;

    setSizeFull();


    filterText.setPlaceholder("Filter by name...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(e -> updateList());


    grid.setColumns("id", "name", "description", "price", "category", "stockQuantity");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.asSingleSelect().addValueChangeListener(event -> editProduct(event.getValue()));


    add(new H1("Product Management"), filterText, grid);

    updateList();
  }

  private void updateList() {
    if (filterText.getValue().isEmpty()) {
      grid.setItems(productService.getAllProducts());
    } else {
      grid.setItems(productService.searchProducts(filterText.getValue()));
    }
  }

  private void editProduct(Product product) {
    // Open edit dialog (you can implement this)
    if (product != null) {
      System.out.println("Editing product: " + product.getName());
      // Here you would open a dialog to edit the product
    }
  }
}