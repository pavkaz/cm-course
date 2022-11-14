package com.example.cmcourse.services;

import com.example.cmcourse.data.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class InterpolationServiceTest {
  private final InterpolationService interpolationService;

  @Autowired
  public InterpolationServiceTest(InterpolationService interpolationService) {
    this.interpolationService = interpolationService;
  }

  @BeforeAll
  static void setUpHeadlessMode() {
    System.setProperty("java.awt.headless", "false");
  }

  @Test
  void newtonFormula() {
    List<Point> points = new ArrayList<>();
    points.add(new Point(0, Math.sin(0)));
    points.add(new Point(Math.PI / 6, Math.sin(Math.PI / 6)));
    points.add(new Point(Math.PI / 3, Math.sin(Math.PI / 3)));
    points.add(new Point(Math.PI / 2, Math.sin(Math.PI/2)));
    points.add(new Point(2 * Math.PI / 3, Math.sin(2 * Math.PI / 3)));

    double result = interpolationService.newtonFormula(points, Math.PI / 9);
    Assertions.assertTrue(Math.abs(result - Math.sin(Math.PI / 9)) < 0.001);
  }
}