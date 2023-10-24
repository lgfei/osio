package com.lgfei.osio.file.biz.controller;

import com.lgfei.osio.starter.core.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private ExampleService exampleService;

    public ExampleController(ExampleService exampleService){
        this.exampleService = exampleService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(){
        try {
            String result = exampleService.doSomeThing();
            return ResponseEntity.ok(result);
        } catch (UnknownHostException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
