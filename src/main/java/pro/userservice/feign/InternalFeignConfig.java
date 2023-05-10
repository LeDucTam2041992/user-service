package pro.userservice.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import pro.userservice.feign.interceptor.FeignErrorResponseDecoder;
import pro.userservice.feign.interceptor.InternalRequestInterceptor;

public class InternalFeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public FeignErrorResponseDecoder feignErrorResponseDecoder() {
        return new FeignErrorResponseDecoder();
    }

    @Bean
    public InternalRequestInterceptor internalRequestInterceptor() {
        return new InternalRequestInterceptor();
    }
}
