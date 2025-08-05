package com.team1.hrbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HrbankApplication {

  public static void main(String[] args) {
    SpringApplication.run(HrbankApplication.class, args);
  }
  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
    return factory -> factory.addConnectorCustomizers(connector -> {
      connector.setURIEncoding("UTF-8");
    });
  }

}
