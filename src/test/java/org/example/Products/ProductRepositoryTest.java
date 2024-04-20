package org.example.Products;

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
    private CPRefrigeratorRepository productRepository;

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
        double origin_price = 1234;
        double discount_price = 222;
        String rocket = "True";
        double rating = 4.0;
        String card_charge_discount_wow_only = "20% 할인";
        String coucash_payback_wow_only = "12700원 적립";

        // When
        productRepository.save(CPRefrigerator.builder().product_name(product_name)
                .origin_price(origin_price)
                .discount_price(discount_price)
                .rocket(rocket)
                .rating(rating)
                .card_charge_discount_wow_only(card_charge_discount_wow_only)
                .coucash_payback_wow_only(coucash_payback_wow_only).build());

        List<CPRefrigerator> productList = productRepository.findAll();

        // Then
        CPRefrigerator product = productList.get(0);
        assertThat(product.getProduct_name()).isEqualTo(product_name);
    }
}