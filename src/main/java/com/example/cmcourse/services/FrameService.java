package com.example.cmcourse.services;

import com.example.cmcourse.data.GraphicImageSettings;
import com.example.cmlabs.graphics.Frame;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public class FrameService {

  public Frame generateFrame(Map<String, Function<Double, Double>> namedFunctions, GraphicImageSettings graphicImageSettings) {
    int preferredWidth = graphicImageSettings.getPreferredWidth();
    int preferredHeight = graphicImageSettings.getPreferredHeight();
    int borderGap = graphicImageSettings.getBorderGap();
    int xStrokeCount = graphicImageSettings.getxStrokeCount();
    int yStrokeCount = graphicImageSettings.getyStrokeCount();
    double xMax = graphicImageSettings.getxMax();
    double yMax = graphicImageSettings.getyMax();

    return new Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount, yStrokeCount, xMax, yMax, namedFunctions);
  }
}
