package com.thoughtmechanix.licenses;

import com.thoughtmechanix.common.utils.FeignClientUserContextInterceptor;
import com.thoughtmechanix.common.utils.UserContextFilter;
import com.thoughtmechanix.common.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

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
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        interceptors.add(new UserContextInterceptor());
        template.setInterceptors(interceptors);

        return template;
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
