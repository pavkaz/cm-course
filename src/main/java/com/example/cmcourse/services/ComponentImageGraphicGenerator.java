package com.example.cmcourse.services;

import com.example.cmcourse.data.GraphicImageSettings;
import com.example.cmlabs.graphics.functions.FunctionalGraph;
import com.example.cmlabs.graphics.functions.FunctionsLabel;
import com.example.cmlabs.graphics.functions.HatchedAxes;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ComponentImageGraphicGenerator {
  private final int width;
  private final int height;
  private final int borderGap = 10;
  private final int hatchedAxesStrokeSize = 12;
  private final int hatchedAxesXStrokeCount;
  private final int hatchedAxesYStrokeCount;
  private final double xMax;
  private final double yMax;
  private final int hatchedAxesFontSize = 12;

  public ComponentImageGraphicGenerator(GraphicImageSettings settings) {
    this.width = settings.getPreferredWidth();
    this.height = settings.getPreferredHeight();
    this.hatchedAxesXStrokeCount = settings.getxStrokeCount();
    this.hatchedAxesYStrokeCount = settings.getyStrokeCount();
    this.xMax = settings.getxMax();
    this.yMax = settings.getyMax();
  }

  public BufferedImage capture(Map<String, Function<Double, Double>> functionMap) {
    BufferedImage captureImage = generateBufferedImage();
    Graphics2D graphicsFromImage = getGraphicsFromImage(captureImage);
    drawGraphicIntoImage(graphicsFromImage, functionMap);
    return captureImage;
  }

  public BufferedImage capture(Function<Double, Double> function, String funcName) {
    BufferedImage captureImage = generateBufferedImage();
    Graphics2D graphicsFromImage = getGraphicsFromImage(captureImage);
    drawGraphicIntoImage(graphicsFromImage, function, funcName);
    return captureImage;
  }

  private void drawGraphicIntoImage(Graphics2D graphics, Map<String, Function<Double, Double>> functionMap) {
    HatchedAxes hatchedAxes = generateHatchedAxes(graphics);
    hatchedAxes.draw(hatchedAxesXStrokeCount, hatchedAxesYStrokeCount);

    FunctionalGraph graph = generateFunctionalGraph(graphics);
    Set<Map.Entry<String, Function<Double, Double>>> entries = functionMap.entrySet();

    entries.forEach(entry -> graph.draw(entry.getValue(), entry.getKey()));

    List<String> names = entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    applyFunctionsLabel(graphics, names);
  }

  private void drawGraphicIntoImage(Graphics2D graphics, Function<Double, Double> function, String funcName) {
    HatchedAxes hatchedAxes = generateHatchedAxes(graphics);
    hatchedAxes.draw(hatchedAxesXStrokeCount, hatchedAxesYStrokeCount);

    FunctionalGraph graph = generateFunctionalGraph(graphics);
    graph.draw(function, funcName);

    applyFunctionsLabel(graphics, List.of(funcName));
  }

  private Graphics2D getGraphicsFromImage(BufferedImage image) {
    Graphics2D graphics = image.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    graphics.setPaint(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
    return graphics;
  }

  private BufferedImage generateBufferedImage() {
    return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  }

  private FunctionalGraph generateFunctionalGraph(Graphics2D graphics2D) {
    return new FunctionalGraph(graphics2D, borderGap, xMax, yMax, width, height);
  }

  private HatchedAxes generateHatchedAxes(Graphics2D graphics2D) {
    return new HatchedAxes(graphics2D, borderGap, hatchedAxesStrokeSize, width, height, xMax, yMax, hatchedAxesFontSize);
  }

  private void applyFunctionsLabel(Graphics2D graphics, List<String> functionsName) {
    FunctionsLabel functionsLabel = new FunctionsLabel(graphics, borderGap);
    functionsLabel.draw(functionsName);
  }
}
