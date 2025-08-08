package com.snowzhou.messaging.controllers;

import com.snowzhou.messaging.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class SuperHelloController {

    /*@GetMapping("/superHello/{name}")
    public String greetA(@PathVariable String name) {
        return "Hello, " + name + "!";
    }*/

    @GetMapping("/superHello/{name}")
    public String greetA(@PathVariable String name,
                        @RequestParam(required = false) String ssxxx) {

        return "Hello " + name + "!";
    }

    @GetMapping("/superHello")
    public String greetB(@ModelAttribute User user) {

        return user.toString();
    }

}
