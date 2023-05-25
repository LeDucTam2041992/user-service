package pro.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheService {

    @CacheEvict(cacheNames = "user", allEntries = true, cacheManager = "multi-cache-manager")
    public void cleanCacheUser() {
        log.info("clearCache user success!");
    }

    @CacheEvict(cacheNames = "location", allEntries = true, cacheManager = "multi-cache-manager")
    public void cleanCacheLocation() {
        log.info("clearCache location success!");
    }
}
