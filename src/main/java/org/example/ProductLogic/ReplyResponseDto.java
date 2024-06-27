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
public class ReplyResponseDto {
    private String comment;
    private String key;

    public String logging_reply() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode product = mapper.createObjectNode();

        product.put("comment", comment);
        product.put("key", key);

        try {
            return mapper.writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
