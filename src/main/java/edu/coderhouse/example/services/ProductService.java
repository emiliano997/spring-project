package edu.coderhouse.example.services;

import edu.coderhouse.example.entities.Product;
import edu.coderhouse.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public Product save(Product product) {
    return this.productRepository.save(product);
  }

  public List<Product> findAll() {
    return this.productRepository.findAll();
  }

  public Optional<Product> update(Long id, Product product) {
    Optional<Product> productOptional = this.productRepository.findById(id);

    if (productOptional.isEmpty()) {
      return Optional.empty();
    }

    productOptional.get().setName(product.getName());
    productOptional.get().setDescription(product.getDescription());
    productOptional.get().setPrice(product.getPrice());

    this.productRepository.save(productOptional.get());

    return productOptional;
  }

  public Optional<Product> delete(Long id) {
    Optional<Product> productOptional = this.productRepository.findById(id);

    if (productOptional.isEmpty()) {
      return Optional.empty();
    }

    this.productRepository.delete(productOptional.get());

    return productOptional;
  }
}
