package com.example.cmcourse.configs;

import com.example.cmcourse.data.GraphicImageSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public GraphicImageSettings frameSettings() {
    return new GraphicImageSettings.Builder()
      .setPreferredWidth(600)
      .setPreferredHeight(600)
      .setBorderGap(10)
      .setxMax(10)
      .setyMax(10)
      .setxStrokeCount(20)
      .setyStrokeCount(20)
      .build();
  }
}