package com.example.cmcourse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CmCourseApplication {

  public static void main(String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(CmCourseApplication.class);
    builder.headless(false);
    builder.run(args);
  }
}
