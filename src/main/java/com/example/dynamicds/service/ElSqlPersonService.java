package com.example.dynamicds.service;

import com.example.dynamicds.entity.Person;
import com.example.dynamicds.jdbc.ElSqlPersonRepository;
import com.example.dynamicds.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElSqlPersonService {

    private final ElSqlPersonRepository sqlRepo;
    private final PersonRepository repository;

    @Transactional
    public void create(String name) {
        repository.save(new Person(name));
       // sqlRepo.insert(name);
    }

    @Transactional(readOnly = true)
    public List<Person> list() {
        return sqlRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> search(String name) {
        return sqlRepo.findByName(name);
    }
}
