package com.thoughtmechanix.licenses;

import com.thoughtmechanix.common.utils.FeignClientUserContextInterceptor;
import com.thoughtmechanix.common.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {
        "com.thoughtmechanix.licenses"
})
@EnableDiscoveryClient
@EnableFeignClients
//@EnableEurekaClient
public class LicensesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LicensesServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public FeignClientUserContextInterceptor feignClientUserContextInterceptor() {
        return new FeignClientUserContextInterceptor();
    }
     /*
     @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> resilience4JCircuitBreakerFactoryCustomizer() {
        return resilience4JCircuitBreakerFactory ->
        {
            Resilience4jBulkheadProvider provider = resilience4JCircuitBreakerFactory.getBulkheadProvider();
            provider.configureDefault(s -> {
                ThreadPoolBulkheadConfig threadPoolBulkheadConfig = ThreadPoolBulkheadConfig.ofDefaults();
                threadPoolBulkheadConfig.getContextPropagator().add(new UserContextPropagator());
                return new Resilience4jBulkheadConfigurationBuilder()
                        .bulkheadConfig(BulkheadConfig.ofDefaults())
                        .threadPoolBulkheadConfig(threadPoolBulkheadConfig)
                        .build();
            });
        };
    }

    @Bean
    public Customizer<Resilience4jBulkheadProvider> bulkheadProviderCustomizer() {
        return resilience4jBulkheadProvider -> {
            resilience4jBulkheadProvider.configureDefault(s -> {
                ThreadPoolBulkheadConfig threadPoolBulkheadConfig = ThreadPoolBulkheadConfig.ofDefaults();
                threadPoolBulkheadConfig.getContextPropagator().add(new UserContextPropagator());
                return new Resilience4jBulkheadConfigurationBuilder()
                        .bulkheadConfig(BulkheadConfig.ofDefaults())
                        .threadPoolBulkheadConfig(threadPoolBulkheadConfig)
                        .build();
            });

        };
    }*/
}
