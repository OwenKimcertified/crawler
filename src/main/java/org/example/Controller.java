package org.example;

import org.example.Kafka.KafkaProducer;
import org.example.Products.CPNotebook;
import org.example.Products.CPRefrigerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class Controller {
    @Autowired
    private ProductService service;
    @Autowired
    private KafkaProducer kafkaProducer;

    @PutMapping("/put/coupang/refrigerator")
    public ResponseDto putRefrigerator(@RequestBody ResponseDto responseDto) {
        CPRefrigerator refrigerator = new CPRefrigerator(responseDto.getProduct_name(),
                responseDto.getOrigin_price(),
                responseDto.getDiscount_price(),
                responseDto.getRocket(),
                responseDto.getRating(),
                responseDto.getCard_charge_discount_wow_only(),
                responseDto.getCoucash_payback_wow_only());
        service.saveRefrigerator(refrigerator);
        kafkaProducer.sendPutRefrigerator(responseDto);
        return new ResponseDto(); //
    }

    @PutMapping("/put/coupang/notebook")
    public ResponseDto putNotebook(@RequestBody ResponseDto responseDto) {
        CPNotebook notebook = new CPNotebook(responseDto.getProduct_name(),
                                                responseDto.getOrigin_price(),
                                                responseDto.getDiscount_price(),
                                                responseDto.getRocket(),
                                                responseDto.getRating(),
                                                responseDto.getCard_charge_discount_wow_only(),
                                                responseDto.getCoucash_payback_wow_only());
        service.saveNotebook(notebook);
        kafkaProducer.sendPutNotebook(responseDto);
        return new ResponseDto(); //
    }

    @GetMapping("/get/coupang/refrigerator")
    public List<CPRefrigerator> getRefrigerator() {

        kafkaProducer.sendGetRefrigerator();
        // 모든 제품 조회
        return service.getAllRefrigerator();
    }


    @GetMapping("/get/coupang/notebook")
    public List<CPNotebook> getNotebook() {

        kafkaProducer.sendGetNotebook();
        // 모든 제품 조회
        return service.getAllNotebook();
    }
}
