package tenpo.diegosaez.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfiguration {

	@Bean
	Cache<String, String> tokenBlacklistCache(@Value("${cache.expiration.minutes}") Integer writeExpirationMinutes,	@Value("${cache.max.size}") Integer cacheMaxSize) {
		return Caffeine.newBuilder()
				.expireAfterWrite(writeExpirationMinutes, TimeUnit.MINUTES)
				.maximumSize(cacheMaxSize).build();
	}

}
