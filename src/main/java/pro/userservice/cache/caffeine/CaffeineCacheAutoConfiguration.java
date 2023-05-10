package pro.userservice.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Import(CacheAutoConfiguration.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CaffeineCacheAutoConfiguration {

  @Bean
  public Ticker ticker() {
    return Ticker.systemTicker();
  }

  @Bean("customKeyGenerator")
  public KeyGenerator keyGenerator() {
    return new CustomKeyGenerator();
  }

  @Bean("caffeine-cache-manager")
  @Primary
  public CacheManager cacheManager(final Ticker ticker, final CaffeineCacheProperties caffeineCacheProperties) {
    final List<CaffeineCache> caches = new ArrayList<>();
    caffeineCacheProperties
        .getProperties()
        .forEach(
            (k, v) -> {
              CaffeineCache cache = this.buildCache(v, ticker);
              caches.add(cache);
            });
    final SimpleCacheManager manager = new SimpleCacheManager();
    manager.setCaches(caches);
    return manager;
  }

  private CaffeineCache buildCache(final CacheBuilder cacheBuilder, final Ticker ticker) {
    return new CaffeineCache(
        cacheBuilder.getCacheName(),
        Caffeine.newBuilder()
            .expireAfterWrite(cacheBuilder.getExpiredTime())
            .maximumSize(cacheBuilder.getMaximumSize())
            .ticker(ticker)
            .build());
  }
}
