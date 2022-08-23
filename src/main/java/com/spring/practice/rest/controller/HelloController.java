package com.spring.practice.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Controller
 * 
 * @Controller 는 Spring MVC 에서 Controller 역할을 하는 클래스를 지칭한다.
 * 매핑된 요청이 들어왔을 때, Model 객체를 통해서 데이터를 획득하고 이에 대한 View 를 반환한다.
 * 
 * @Controller 는 @Component 어노테이션을 가지고 있기 때문에 자동으로 bean 으로 등록된다.
 * @Controller 아래에서 @RequestMapping 을 사용할 수 있는데, 이를 통해 사용자의 요청과 연결되는 엔드 포인트가 된다.
 * 
 * [Spring 동작 순서]
 * 1. 사용자가 URI 형식으로 웹 서비스에 요청을 보낸다.
 * 2. DispatcherServlet 은 HandlerMapping 을 통해 요청을 처리할 메서드 (Controller) 를 찾는다.
 * 3. HandlerAdapter 를 통해 HandlerMapping 에서 찾은 Controller 에게 처리를 요청한다.
 * 4. Contoller 는 요청을 처리한 후에 HandlerAdapter 에게 결과를 반환한다.
 * 5. HandlerAdapter 가 전달받은 결과를 ModelAndView 객체로 DispatcherServlet 에게 전달한다.
 * 6. DispatcherServlet 은 전달받은 ViewResolver 를 통해 ModelAndView 객체와 매핑되는 view 를 찾는다..
 * 7. ViewResolver 처리 결과가 포함된 View 를 DispatcherServlet 에 전달한다.
 * 8. DispatcherServlet 에서 최종 응답 결과를 사용자에게 전달한다.
 */
@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping("home")
    public String getHome() {
        return "index.html";
    }

    @GetMapping("hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("hello");
    }

    /**
     * Error 발생
     */
    // @GetMapping("test")
    // public Map<Object, Object> getTest() {
    //     Map<Object, Object> map = new HashMap<>();
    //     map.put("content", "hello");
    //     return  map;
    // }
}