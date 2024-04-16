package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class Controller {
    @Autowired
    private T_Service service;

    @PutMapping("/put/coupang/notebook")
    public ResponseDto putProduct(@RequestBody ResponseDto responseDto) {
        Product product = new Product(responseDto.getProductName(), responseDto.getPrice());
        service.saveProduct(product);
        return new ResponseDto(); //
    }

    @GetMapping("/get/coupang/notebook")
    public List<Product> getProduct() {

        // 모든 제품 조회
        return service.getAllProducts();
    }
}
