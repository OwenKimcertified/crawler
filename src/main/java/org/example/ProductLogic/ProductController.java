package org.example.ProductLogic;

import org.example.Kafka.KafkaProducer;
import org.example.ProductsRepository.CPNotebook;
import org.example.ProductsRepository.CPRefrigerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService service;
    @Autowired
    private KafkaProducer kafkaProducer;

    @PutMapping("/put/coupang/refrigerator")
    public ProductResponseDto putRefrigerator(@RequestBody ProductResponseDto responseDto) {
        CPRefrigerator refrigerator = new CPRefrigerator(responseDto.getProduct_name(),
                responseDto.getOrigin_price(),
                responseDto.getDiscount_price(),
                responseDto.getRocket(),
                responseDto.getRating(),
                responseDto.getCard_charge_discount_wow_only(),
                responseDto.getCoucash_payback_wow_only(),
                responseDto.getPage_num());
        service.saveRefrigerator(refrigerator);
        kafkaProducer.sendPutRefrigerator(responseDto);
        return new ProductResponseDto(); //
    }

    @PutMapping("/put/coupang/notebook")
    public ProductResponseDto putNotebook(@RequestBody ProductResponseDto responseDto) {
        CPNotebook notebook = new CPNotebook(responseDto.getProduct_name(),
                                                responseDto.getOrigin_price(),
                                                responseDto.getDiscount_price(),
                                                responseDto.getRocket(),
                                                responseDto.getRating(),
                                                responseDto.getCard_charge_discount_wow_only(),
                                                responseDto.getCoucash_payback_wow_only(),
                                                responseDto.getPage_num());
        service.saveNotebook(notebook);
        kafkaProducer.sendPutNotebook(responseDto);
        return new ProductResponseDto(); //
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
