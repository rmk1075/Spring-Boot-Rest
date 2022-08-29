package com.spring.practice.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController
 * 
 * @RestController 는 간단하게 @Controller 와 @ResponseBody 를 더한 어노테이션이다.
 * 매핑된 요청이 들어왔을 때, 응답으로 객체를 Response Body 에 포함시켜 Serialize 하여 전달한다.
 * 
 * 
 * @ResponseBody
 * 
 * Spring MVC 의 Controller 가 결과값을 view 로 변환하여 전달하는 것이 아니라 데이터로 전달하는 경우에 사용하는 어노테이션이다.
 * @ResponseBody 어노테이션을 붙인 함수는 결과값을 JSON 형태로 response body 에 포함시켜서 반환한다.
 * 
 * 
 * [RestController 의 요청 처리 순서]
 * 
 * 1. Client 가 URI 형식으로 웹 서비스에 요청을 보낸다.
 * 2. DispatcherServlet 은 HandlerMapping 을 통해 요청을 처리할 메서드 (Controller) 를 찾는다.
 * 3. HandlerAdapter 를 통해 HandlerMapping 에서 찾은 Controller 에게 처리를 요청한다.
 * 4. Contoller 는 요청을 처리한 후에 HandlerAdapter 에게 결과를 반환한다. 이때 @Controller 처럼 view 이름을 반환하지 않고 결과 객체를 ResponseEntity 로 감싸서 전달한다.
 * 5. HandlerAdapter 는 ViewResolver 를 거치지 않고 HttpMessageConverter 를 통하여 ResponseEntity 를 Http Response 의 Body 에 추가하여 결과를 변환한다.
 * 6. HandlerAdapter 가 변환된 Http Response 를 DispatcherServlet 에게 전달한다.
 * 7. DispatcherServlet 은 전달받은 값을 HTTP Response 로 Client 에게 전달한다.
 */
@RestController
@RequestMapping("/rest")
public class RestHelloController {

    @GetMapping("home")
    public String getHome() {
        return "index.html";
    }

    @GetMapping("hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("test")
    public Map<Object, Object> getTest() {
        Map<Object, Object> map = new HashMap<>();
        map.put("content", "hello");
        return  map;
    }
}