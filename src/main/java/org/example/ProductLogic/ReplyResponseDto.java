package org.example.ProductLogic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {
    private String comment;
    private String key;

    public String logging_reply() {
        return "productDTO{" +
                ", product_comment='" + comment + '\'' +
                ", product_key='" + key + '\'' +
                '}';
    }
}
