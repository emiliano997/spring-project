package edu.coderhouse.example.controllers;

import edu.coderhouse.example.entities.Product;
import edu.coderhouse.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> findAll(@RequestParam(name = "name", defaultValue = "") String name, @RequestParam(name = "price", defaultValue = "") Double price) {

    if (name != null) {
      return ResponseEntity.ok(this.productService.findAll().stream().filter(product -> product.getName().contains(name)).toList());
    }

    if (price != null) {
      return ResponseEntity.ok(this.productService.findAll().stream().filter(product -> product.getPrice() == price).toList());
    }

    return ResponseEntity.ok(this.productService.findAll());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> save(@RequestBody Product product) {
    try {
      Product newProduct = this.productService.save(product);
      return ResponseEntity.created(URI.create("/products/" + newProduct.getId())).body(newProduct);
    } catch (Error error) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Optional<Product>> update(@PathVariable Long id,@RequestBody Product product) {
    Optional<Product> productOptional = this.productService.update(id, product);

    if (productOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(productOptional);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Optional<Product>> delete(@PathVariable Long id) {
    Optional<Product> productOptional = this.productService.delete(id);

    if (productOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(productOptional);
  }
}
