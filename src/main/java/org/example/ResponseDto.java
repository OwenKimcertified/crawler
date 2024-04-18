package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int id;
    private String product_name;
    private double origin_price;
    private double discount_price;
    private String rocket;
    private double rating;
    private String card_charge_discount_wow_only;
    private String coucash_payback_wow_only;
}