package org.example;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "coupang_notebook")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "rating")
    private double rating;
    @Builder
    public Product(String productName, double price, double rating) {

        this.productName = productName;
        this.price = price;
        this.rating = rating;
    }
}