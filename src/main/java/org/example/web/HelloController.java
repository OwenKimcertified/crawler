package org.example.web;


import org.example.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping; // HTTP Get 요청에 반응하는 API
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; // 컨트롤러를 json 으로 리턴
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class HelloController {
    //API
    @GetMapping("/hello") //
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, //
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount); }
}
