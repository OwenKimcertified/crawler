package org.example.ProductLogic;

//import org.example.ProductsRepository.OldVersion.CPNotebook;
//import org.example.ProductsRepository.OldVersion.CPRefrigerator;

import org.example.ProductsRepository.KurlyFrozenComment;
import org.example.Kafka.KafkaProducer;
import org.example.ProductsRepository.KurlyFrozen;
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

    @PostMapping("/post/kurly/frozen")
    public ProductResponseDto kulryfrozen(@RequestBody ProductResponseDto responseDto) {
        KurlyFrozen product_frozen = new KurlyFrozen(
                                                        responseDto.getName(),
                                                        responseDto.getDescribe(),
                                                        responseDto.getOrigin_price(),
                                                        responseDto.getDiscount_price(),
                                                        responseDto.getReply_count(),
                                                        responseDto.getLucifer(),
                                                        responseDto.getEndpoint());
        service.saveProductFrozen(product_frozen);
        kafkaProducer.sendProductFrozen(responseDto);
        return new ProductResponseDto();
    }

    @GetMapping("/get/kurly/frozen")
    public List<KurlyFrozen> getkurlyFrozen() {
        return service.getAllFrozen();
    }

    @PostMapping("/post/kurly/reply")
    public ReplyResponseDto KurlyFrozenComment(@RequestBody ReplyResponseDto replyResponseDto) {
        KurlyFrozen product = service.getProductByEndpoint(replyResponseDto.getKey());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        KurlyFrozenComment productReply = new KurlyFrozenComment(replyResponseDto.getComment(),
                                                                 replyResponseDto.getKey());
        service.saveComment(productReply);
        kafkaProducer.sendProductFrozenReply(replyResponseDto);
        return new ReplyResponseDto();
    }


//    @PutMapping("/put/coupang/refrigerator")
//    public ProductResponseDto putRefrigerator(@RequestBody ProductResponseDto responseDto) {
//        CPRefrigerator refrigerator = new CPRefrigerator(responseDto.getProduct_name(),
//                responseDto.getOrigin_price(),
//                responseDto.getDiscount_price(),
//                responseDto.getRocket(),
//                responseDto.getRating(),
//                responseDto.getCard_charge_discount_wow_only(),
//                responseDto.getCoucash_payback_wow_only(),
//                responseDto.getPage_num());
//        service.saveRefrigerator(refrigerator);
//        kafkaProducer.sendPutRefrigerator(responseDto);
//        return new ProductResponseDto(); //
//    }
//
//    @PutMapping("/put/coupang/notebook")
//    public ProductResponseDto putNotebook(@RequestBody ProductResponseDto responseDto) {
//        CPNotebook notebook = new CPNotebook(responseDto.getProduct_name(),
//                                                responseDto.getOrigin_price(),
//                                                responseDto.getDiscount_price(),
//                                                responseDto.getRocket(),
//                                                responseDto.getRating(),
//                                                responseDto.getCard_charge_discount_wow_only(),
//                                                responseDto.getCoucash_payback_wow_only(),
//                                                responseDto.getPage_num());
//        service.saveNotebook(notebook);
//        kafkaProducer.sendPutNotebook(responseDto);
//        return new ProductResponseDto(); //
//    }
//
//    @GetMapping("/get/coupang/refrigerator")
//    public List<CPRefrigerator> getRefrigerator() {
//
//        kafkaProducer.sendGetRefrigerator();
//        // 모든 제품 조회
//        return service.getAllRefrigerator();
//    }
//
//
//    @GetMapping("/get/coupang/notebook")
//    public List<CPNotebook> getNotebook() {
//
//        kafkaProducer.sendGetNotebook();
//        // 모든 제품 조회
//        return service.getAllNotebook();
//    }
}
