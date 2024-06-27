package org.example.Kafka;
import org.example.ProductLogic.ProductResponseDto;
import org.example.ProductLogic.ReplyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class KafkaProducer {
    // pass
//    private static final String TOPIC_PUT_REFRIGERATOR = "put_refrigerator_topic";
//    private static final String TOPIC_PUT_NOTEBOOK = "put_notebook_topic";
//    private static final String TOPIC_GET_REFRIGERATOR = "get_refrigerator_topic";
//    private static final String TOPIC_GET_NOTEBOOK = "get_notebook_topic";

    // 컬리
    private static final String post_kurly_frozen = "post_kurly_product_frozen";
    private static final String post_kurly_frozen_reply = "post_kurly_product_frozen_reply";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendProductFrozen(ProductResponseDto responseDto) {
        String message = responseDto.logging_product();
        kafkaTemplate.send(post_kurly_frozen, message);
    }

    public void sendProductFrozenReply(ReplyResponseDto replyResponseDto) {
        String message = replyResponseDto.logging_reply();
        kafkaTemplate.send(post_kurly_frozen_reply, message);
    }
}
    // pass
//    public void sendPutRefrigerator(ProductResponseDto responseDto) {
//        String message = responseDto.logging();
//        kafkaTemplate.send(TOPIC_PUT_REFRIGERATOR, message);
//    }
//
//    public void sendPutNotebook(ProductResponseDto responseDto) {
//        String message = responseDto.logging();
//        kafkaTemplate.send(TOPIC_PUT_NOTEBOOK, message);
//    }
//
//    public void sendGetRefrigerator() {
//        kafkaTemplate.send(TOPIC_GET_REFRIGERATOR, "Request for Refrigerator products");
//    }
//
//    public void sendGetNotebook() {
//        kafkaTemplate.send(TOPIC_GET_NOTEBOOK, "Request for Notebook products");
//    }
