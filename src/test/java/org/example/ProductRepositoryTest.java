package org.example;

import org.example.Product;
import org.example.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanup() {
        productRepository.deleteAll();
    }

    @DisplayName("jpa test")
    @Transactional
    @Test
    public void saveProductTest() {
        // Given
        String product_name = "test";
        double price = 1234;


        // When
        productRepository.save(Product.builder().productName(product_name)
                                                .price(price).build());

        List<Product> productList = productRepository.findAll();

        // Then
        Product product = productList.get(0);
        assertThat(product.getProductName()).isEqualTo(product_name);
    }
}