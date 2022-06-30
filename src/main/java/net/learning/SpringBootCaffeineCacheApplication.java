package net.learning;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@SpringBootApplication
public class SpringBootCaffeineCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCaffeineCacheApplication.class, args);
	}


	@Bean
	public CacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		caffeineCacheManager.setCacheNames(List.of("EMPLOYEE_CACHE"));
		return caffeineCacheManager;
	}
	@Bean
	public Caffeine caffeineConfig() {
		return Caffeine.newBuilder()
				.expireAfterWrite(1, TimeUnit.MINUTES)
				.recordStats();
	}


}
