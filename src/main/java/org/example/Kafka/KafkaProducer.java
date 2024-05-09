package org.example.Kafka;
import org.example.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class KafkaProducer {

    private static final String TOPIC_PUT_REFRIGERATOR = "put_refrigerator_topic";
    private static final String TOPIC_PUT_NOTEBOOK = "put_notebook_topic";
    private static final String TOPIC_GET_REFRIGERATOR = "get_refrigerator_topic";
    private static final String TOPIC_GET_NOTEBOOK = "get_notebook_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPutRefrigerator(ResponseDto responseDto) {
        String message = responseDto.logging();
        kafkaTemplate.send(TOPIC_PUT_REFRIGERATOR, message);
    }

    public void sendPutNotebook(ResponseDto responseDto) {
        String message = responseDto.logging();
        kafkaTemplate.send(TOPIC_PUT_NOTEBOOK, message);
    }

    public void sendGetRefrigerator() {
        kafkaTemplate.send(TOPIC_GET_REFRIGERATOR, "Request for Refrigerator products");
    }

    public void sendGetNotebook() {
        kafkaTemplate.send(TOPIC_GET_NOTEBOOK, "Request for Notebook products");
    }
}