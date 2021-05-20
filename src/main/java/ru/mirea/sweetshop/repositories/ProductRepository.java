package ru.mirea.sweetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.sweetshop.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByProductTypeId(Long productTypeId);
    Product findProductById(Long id);
    void deleteProductById(Long id);
}
