package pro.userservice.cache.multicache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheRedisCaffeineProperties.class)
public class CacheRedisCaffeineAutoConfiguration {

  private final CacheRedisCaffeineProperties cacheRedisCaffeineProperties;
  private final RedisTemplate<Object, Object> redisTemplateCaffeine;

  public CacheRedisCaffeineAutoConfiguration(
      CacheRedisCaffeineProperties cacheRedisCaffeineProperties,
      RedisTemplate<Object, Object> redisTemplateCaffeine) {
    this.cacheRedisCaffeineProperties = cacheRedisCaffeineProperties;
    this.redisTemplateCaffeine = redisTemplateCaffeine;
  }

  @Bean("multi-cache-manager")
  public RedisCaffeineCacheManager cacheManager() {
    return new RedisCaffeineCacheManager(cacheRedisCaffeineProperties, redisTemplateCaffeine);
  }

}
