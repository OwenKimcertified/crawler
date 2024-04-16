package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class T_Service {

    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        // Product 엔티티를 데이터베이스에 저장
        productRepository.save(product);
        System.out.println("Data saved to MySQL database: " + product);
    }

    public List<Product> getAllProducts() {
        // 데이터베이스에 저장된 모든 제품 조회
        return productRepository.findAll();
    }
}