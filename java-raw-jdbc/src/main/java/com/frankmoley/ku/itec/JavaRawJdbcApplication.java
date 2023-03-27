package com.frankmoley.ku.itec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

@SpringBootApplication
public class JavaRawJdbcApplication {

  public static void main(String[] args) {
    SpringApplication.run(JavaRawJdbcApplication.class, args);
  }

  @Bean
  public DataSource dataSource(){
    DataSourceBuilder builder = DataSourceBuilder.create();
    builder.url("jdbc:postgresql://localhost:5432/postgres");
    builder.driverClassName("org.postgresql.Driver");
    builder.username("postgres"); //change as appropriate
    builder.password("postgres"); //change as appropriate
    return builder.build();
  }

}
