package net.learning.utils;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import net.learning.entity.Employee;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class CacheUtils {

    private Cache<String, Employee> cache;
    private final CacheManager cacheManager;

    public CacheUtils(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.cache = (Cache<String, Employee>) Objects.requireNonNull(this.cacheManager.getCache("EMPLOYEE_CACHE")).getNativeCache();
    }

    public Employee writeBackToCache(String cacheKey, Supplier<Employee> supplier) {
        Employee employee = cache.getIfPresent(cacheKey);
        log.info("employee id exist in cache: {} ", employee != null);
        return Optional.ofNullable(employee).orElseGet(writeBack(cacheKey, supplier));
    }

    private Supplier<Employee> writeBack(String key, Supplier<Employee> supplier) {
        return () -> {
            Employee employee = supplier.get();
            log.info("employee stored in cache");
            Optional.ofNullable(employee).ifPresent(value -> cache.put(key, value));
            return employee;
        };
    }
}
