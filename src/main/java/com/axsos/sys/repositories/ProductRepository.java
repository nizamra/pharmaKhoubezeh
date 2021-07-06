package com.axsos.sys.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axsos.sys.models.Category;
import com.axsos.sys.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findAll();
	List<Product> findBySymptomContaining(String search);
	List<Product> findByCategory(Category category);
	List<Product> findAllByOwnerOfProduct(Long pharmaId);

}
