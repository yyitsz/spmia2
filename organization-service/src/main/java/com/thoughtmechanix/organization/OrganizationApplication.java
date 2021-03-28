package com.thoughtmechanix.organization;

import com.thoughtmechanix.common.utils.FeignClientUserContextInterceptor;
import com.thoughtmechanix.common.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages = {
        "com.thoughtmechanix.organization"
})
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class OrganizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
    }

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public FeignClientUserContextInterceptor feignClientUserContextInterceptor() {
        return new FeignClientUserContextInterceptor();
    }
}
