package com.fl.dol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DolWebappMiddlewareApplication {

    public static void main(String[] args) {

        SpringApplication.run(DolWebappMiddlewareApplication.class, args);
    }

    @RequestMapping("/")
    String sayHello() {
        return "Hello World!";
    }

    @GetMapping("/getone")
    public @ResponseBody ResponseEntity<String> get() {
        return new ResponseEntity<String>("GET Response from dol-webapp-middleware", HttpStatus.OK);
    }

}
