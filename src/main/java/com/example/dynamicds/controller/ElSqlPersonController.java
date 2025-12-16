package com.example.dynamicds.controller;

import com.example.dynamicds.entity.Person;
import com.example.dynamicds.service.ElSqlPersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elsql/person")
public class ElSqlPersonController {

    private final ElSqlPersonService service;

    public ElSqlPersonController(ElSqlPersonService service) {
        this.service = service;
    }

    @PostMapping
    public void create(@RequestParam String name) {
        service.create(name);
    }

    @GetMapping
    public List<Person> list() {
        return service.list();
    }

    @GetMapping("/search")
    public List<Person> search(@RequestParam String name) {
        return service.search(name);
    }
}
