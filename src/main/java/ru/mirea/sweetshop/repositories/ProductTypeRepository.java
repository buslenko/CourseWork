package ru.mirea.sweetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.sweetshop.models.ProductType;


@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Long deleteProductTypeById(Long id);
    ProductType findProductTypeById(Long id);

}
