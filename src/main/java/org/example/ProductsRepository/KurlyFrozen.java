package org.example.ProductsRepository;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KURLY_PRODUCT_FROZEN")
public class KurlyFrozen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_describe")
    private String describe;

    @Column(name = "origin_price")
    private double origin_price;

    @Column(name = "discount_price")
    private double discount_price;

    @Column(name = "reply_count")
    private int reply_count;

    @Column(name = "lucifer")
    private String lucifer;

    @Column(name = "endpoint", unique = true)
    private String endpoint;

    @Builder
    public KurlyFrozen(String name, String describe,
                       double origin_price, double discount_price,
                       int reply_count, String lucifer, String endpoint)
    {
            this.name = name;
            this.describe = describe;
            this.origin_price = origin_price;
            this.discount_price = discount_price;
            this.reply_count = reply_count;
            this.lucifer = lucifer;
            this.endpoint = endpoint;
    }
}
