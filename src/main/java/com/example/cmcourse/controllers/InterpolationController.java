package com.example.cmcourse.controllers;

import com.example.cmcourse.data.EncodedGraphicImagesPack;
import com.example.cmcourse.data.GraphicImageSettings;
import com.example.cmcourse.services.ComponentImageGraphicGenerator;
import com.example.cmcourse.services.FrameService;
import com.example.cmcourse.services.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.function.Function;

@Controller
public class InterpolationController {
  private final FunctionService functionService;
  private final ComponentImageGraphicGenerator componentImageGraphicGenerator;
  private final FrameService frameService;
  private final GraphicImageSettings graphicImageSettings;

  @Autowired
  public InterpolationController(FunctionService functionService, ComponentImageGraphicGenerator componentImageGraphicGenerator, FrameService frameService, GraphicImageSettings graphicImageSettings) {
    this.functionService = functionService;
    this.componentImageGraphicGenerator = componentImageGraphicGenerator;
    this.frameService = frameService;
    this.graphicImageSettings = graphicImageSettings;
  }

  @GetMapping("/interpolation/faults")
  public String faultsPage(Model model) {
    model.addAttribute("tableSizes", List.of(4, 5, 6, 10, 20, 40, 60, 80));
    String defaultExpression = "Math.exp(Math.cos(x)) * (Math.sin(x) + 2) / (Math.pow(x, 2) + 4 - Math.cos(2 * x))";
    model.addAttribute("function", defaultExpression);
    return "faults";
  }

  @PostMapping("/interpolation/faults/results")
  public String faults(@RequestParam("function") String function, @RequestParam("tableSize") Integer tableSize, Model model) throws IOException {
    if (function == null || function.equals("")) {
      function = "Math.exp(Math.cos(x)) * (Math.sin(x) + 2) / (Math.pow(x, 2) + 4 - Math.cos(2 * x))";
    }
    Function<Double, Double> interpretedFunction = functionService.interpretFunction(function);
    double derivativeDelta = 2 * (double) graphicImageSettings.getxMax() / graphicImageSettings.getPreferredWidth();
    List<LinkedHashMap<String, Function<Double, Double>>> mapList = functionService.functionInterpolateFaultsMaps(interpretedFunction, tableSize, derivativeDelta);
    saveToModel(mapList, model);
    return "faults-results";
  }

  private void saveToModel(List<LinkedHashMap<String, Function<Double, Double>>> mapList, Model dstModel) throws IOException {
    List<EncodedGraphicImagesPack> packs = new ArrayList<>();
    var namedFunctions = new ArrayList<>(mapList.get(0).entrySet());
    var namedInterpolationFunctions = new ArrayList<>(mapList.get(1).entrySet());
    var namedFaultsFunctions = new ArrayList<>(mapList.get(2).entrySet());
    for (int i = 0; i < namedFunctions.size(); i++) {
      Map.Entry<String, Function<Double, Double>> funcE = namedFunctions.get(i);
      Map.Entry<String, Function<Double, Double>> interpolateE = namedInterpolationFunctions.get(i);
      Map.Entry<String, Function<Double, Double>> faultsE = namedFaultsFunctions.get(i);
      EncodedGraphicImagesPack encodedGraphicImagesPack = new EncodedGraphicImagesPack(
        imageToBase64(componentImageGraphicGenerator.capture(funcE.getValue(), funcE.getKey())),
        imageToBase64(componentImageGraphicGenerator.capture(interpolateE.getValue(), interpolateE.getKey())),
        imageToBase64(componentImageGraphicGenerator.capture(faultsE.getValue(), faultsE.getKey()))
      );
      packs.add(encodedGraphicImagesPack);
    }
    dstModel.addAttribute("encodedGraphicImagesPacks", packs);
  }

  private String imageToBase64(BufferedImage bufferedImage) throws IOException {
    try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(bufferedImage, "png", baos);
      byte[] imageBytes = baos.toByteArray();
      byte[] encodedBytes = Base64.getEncoder().encode(imageBytes);
      return "data:image/png;base64," + new String(encodedBytes);
    }
  }

}

