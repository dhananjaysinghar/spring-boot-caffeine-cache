package net.learning.service;

import lombok.extern.slf4j.Slf4j;
import net.learning.entity.Employee;
import net.learning.repo.EmployeeRepository;
import net.learning.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CacheUtils cacheUtils;

    public Employee save(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee find(Integer id) {
        return cacheUtils.writeBackToCache(String.valueOf(id), () -> {
            log.info("Employee data fetched from database:: " + id);
            Optional<Employee> empById = employeeRepository.findById(id);
            if (empById.isPresent()) {
                return empById.get();
            }
            throw new RuntimeException("User Id not available");
        });
    }

}
