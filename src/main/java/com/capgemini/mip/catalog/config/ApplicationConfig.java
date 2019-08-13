package com.capgemini.mip.catalog.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public DozerBeanMapper beanMapper() {
    return new DozerBeanMapper();
  }


}
