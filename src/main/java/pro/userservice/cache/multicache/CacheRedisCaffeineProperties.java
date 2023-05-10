package pro.userservice.cache.multicache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ConfigurationProperties(prefix = "spring.cache.multi")
@Getter
@Setter
public class CacheRedisCaffeineProperties {

  private Set<String> cacheNames = new HashSet<>();

  /** Whether to store an empty value, the default is true to prevent cache penetration */
  private boolean cacheNullValues = true;

  /**
   * Whether to dynamically create the implementation of Cache based on cacheName, the default is
   * true
   */
  private boolean dynamic = true;

  /** The prefix of the cache key */
  private String cachePrefix;

  private Redis redis = new Redis();

  private Caffeine caffeine = new Caffeine();

  @Getter
  @Setter
  public static class Redis {

    /** Global expiration time, in milliseconds, default does not expire */
    private Duration defaultExpiration = Duration.ofDays(1);

    /**
     * The expiration time of each cacheName, in milliseconds, with a higher priority than
     * defaultExpiration
     */
    private Map<String, Duration> expires = new HashMap<>();

    /** The topic name to notify other nodes when the cache is updated */
    private String topic = "cache:redis:caffeine:topic";
  }

  @Getter
  @Setter
  public static class Caffeine {

    /** Expiration time after access, in milliseconds */
    private Duration expireAfterAccess;

    /** Expiration time after writing, in milliseconds */
    private Duration expireAfterWrite;

    /** Refresh time after writing, in milliseconds */
    private Duration refreshAfterWrite;

    /** Initialize size */
    private int initialCapacity;

    /**
     * The maximum number of cached objects. When this number is exceeded, the previously placed
     * cache will be invalidated
     */
    private long maximumSize;
  }
}
