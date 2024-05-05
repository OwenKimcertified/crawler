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

    public void sendPutRefrigeratorMessage(ResponseDto responseDto) {
        String message = responseDto.toString();
        kafkaTemplate.send(TOPIC_PUT_REFRIGERATOR, message);
    }

    public void sendPutNotebookMessage(ResponseDto responseDto) {
        String message = responseDto.toString();
        kafkaTemplate.send(TOPIC_PUT_NOTEBOOK, message);
    }

    public void sendGetRefrigeratorMessage() {
        kafkaTemplate.send(TOPIC_GET_REFRIGERATOR, "Request for Refrigerator products");
    }

    public void sendGetNotebookMessage() {
        kafkaTemplate.send(TOPIC_GET_NOTEBOOK, "Request for Notebook products");
    }
}