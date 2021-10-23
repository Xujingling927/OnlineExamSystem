package com.bnuz.itc.onlineexamsystem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @RequestMapping("/hello")
    public static String hello(@RequestParam(name = "name") String name) {
        return name;
    }
}
