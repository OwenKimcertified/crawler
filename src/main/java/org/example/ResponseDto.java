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
    private static int cnt = 0;
    private int id;
    private String product_name;
    private double origin_price;
    private double discount_price;
    private String rocket;
    private double rating;
    private String card_charge_discount_wow_only;
    private String coucash_payback_wow_only;
    private int page_num;

    public String logging() {
        return "ResponseDto{" +
                "id=" + ++cnt +
                ", product_name='" + product_name + '\'' +
                ", origin_price=" + origin_price +
                ", discount_price=" + discount_price +
                ", rocket='" + rocket + '\'' +
                ", rating=" + rating +
                ", card_charge_discount_wow_only='" + card_charge_discount_wow_only + '\'' +
                ", coucash_payback_wow_only='" + coucash_payback_wow_only + '\'' +
                ", page_num=" + page_num +
                '}';
    }
}