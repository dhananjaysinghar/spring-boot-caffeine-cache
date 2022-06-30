package net.learning.controller;

import lombok.extern.slf4j.Slf4j;
import net.learning.entity.Employee;
import net.learning.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        log.info("Received request to store data");
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> find(@PathVariable(value = "id") Integer id) {
        log.info("Received request for id {} ", id);
        return ResponseEntity.ok(employeeService.find(id));
    }

}
