package pro.userservice.cache.caffeine;

import lombok.Data;

import java.time.Duration;

@Data
public class CacheBuilder {
  private String cacheName;
  private Duration expiredTime;
  private int maximumSize;
}
