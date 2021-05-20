package ru.mirea.sweetshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.sweetshop.models.Product;
import ru.mirea.sweetshop.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product getProductById(Long id){
        return productRepository.findProductById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getAllProductByTypeId(Long id){
        return productRepository.findAllByProductTypeId(id);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteProductById(id);

    }
}
