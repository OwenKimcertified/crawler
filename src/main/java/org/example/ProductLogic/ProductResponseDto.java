package org.example.ProductLogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private static int cnt = 0;
    private String name;
    private String describe;
    private double origin_price;
    private double discount_price;
    private int reply_count;
    private String lucifer;
    private String endpoint;

    public String logging_product() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode product = mapper.createObjectNode();

        product.put("id", ++cnt);
        product.put("product_name", name);
        product.put("product_describe", describe);
        product.put("origin_price", (int) origin_price);
        product.put("discount_price", (int) discount_price);
        product.put("reply_count", reply_count);
        product.put("lucifer", lucifer);
        product.put("endpoint", endpoint);

        try {
            return mapper.writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return "{ None }";
        }
    }
}
//public class ProductResponseDto {
//    private static int cnt = 0;
//    private int id;
//    private String product_name;
//    private double origin_price;
//    private double discount_price;
//    private String rocket;
//    private double rating;
//    private String card_charge_discount_wow_only;
//    private String coucash_payback_wow_only;
//    private int page_num;
//
//    public String logging() {
//        return "ResponseDto{" +
//                "id=" + ++cnt +
//                ", product_name='" + product_name + '\'' +
//                ", origin_price=" + origin_price +
//                ", discount_price=" + discount_price +
//                ", rocket='" + rocket + '\'' +
//                ", rating=" + rating +
//                ", card_charge_discount_wow_only='" + card_charge_discount_wow_only + '\'' +
//                ", coucash_payback_wow_only='" + coucash_payback_wow_only + '\'' +
//                ", page_num=" + page_num +
//                '}';
//    }
//}