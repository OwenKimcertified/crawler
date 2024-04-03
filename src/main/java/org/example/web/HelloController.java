package org.example.web;


import org.springframework.web.bind.annotation.GetMapping; // HTTP Get 요청에 반응하는 API
import org.springframework.web.bind.annotation.RestController; // 컨트롤러를 json 으로 리턴
@RestController
public class HelloController {
    //API
    @GetMapping("/hello") //
    public String hello() {
        return "hello";
    }

}
