package org.example.ProductsRepository;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "coupang_refrigerator")
public class CPRefrigerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "origin_price")
    private double origin_price;

    @Column(name = "discount_price")
    private double discount_price;

    @Column(name = "rocket")
    private String rocket;

    @Column(name = "rating")
    private double rating;

    @Column(name = "card_charge_discount_wow_only")
    private String card_charge_discount_wow_only;

    @Column(name = "coucash_payback_wow_only")
    private String coucash_payback_wow_only;

    @Column(name = "page_num")
    private int page_num;

    @Builder
    public CPRefrigerator(String product_name, double origin_price, double discount_price,
                          String rocket, double rating,
                          String card_charge_discount_wow_only,
                          String coucash_payback_wow_only, int page_num)
    {

        this.product_name = product_name;
        this.origin_price = origin_price;
        this.discount_price = discount_price;
        this.rocket = rocket;
        this.rating = rating;
        this.card_charge_discount_wow_only = card_charge_discount_wow_only;
        this.coucash_payback_wow_only = coucash_payback_wow_only;
        this.page_num = page_num;
    }
}