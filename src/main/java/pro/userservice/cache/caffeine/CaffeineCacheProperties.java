package pro.userservice.cache.caffeine;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.cache.memory")
public class CaffeineCacheProperties {

  private CacheType type;

  private Map<String, CacheBuilder> properties;

  public CacheType getType() {
    return type;
  }

  public void setType(CacheType type) {
    this.type = type;
  }

  public Map<String, CacheBuilder> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, CacheBuilder> properties) {
    this.properties = properties;
  }
}
