package com.example.cmcourse.services;


import com.example.cmcourse.data.GraphicImageSettings;
import com.example.cmcourse.data.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.*;
import java.util.function.Function;

@Service
public class FunctionService {
  private final DerivativeService derivativeService;
  private final InterpolationService interpolationService;
  private final ScriptEngine engine;
  private final int xMax;

  @Autowired
  public FunctionService(DerivativeService derivativeService, InterpolationService interpolationService, GraphicImageSettings settings) {
    this.derivativeService = derivativeService;
    this.interpolationService = interpolationService;
    this.engine = new ScriptEngineManager().getEngineByName("groovy");
    this.xMax = settings.getxMax();
  }

  public Function<Double, Double> interpretFunction(String expression) {
    return x -> {
      try {
        Object res = engine.eval(expression, new SimpleBindings(Map.of("x", x)));
        if (res instanceof Double) return (Double) res;
        else return (double) ((int)res);
      } catch (ScriptException e) {
        throw new RuntimeException(e);
      }
    };
  }

  public Function<Double, Double> interpolateFunction(Function<Double, Double> function, int pointsCount) {
    List<Point> points = new ArrayList<>();
    double increment = ((double) 2 * xMax) / pointsCount;
    for (double i = -xMax; i < xMax; i += increment) {
      points.add(new Point(i, function.apply(i)));
    }
    return x -> interpolationService.newtonFormula(points, x);
  }

  public List<LinkedHashMap<String, Function<Double, Double>>> functionInterpolateFaultsMaps(Function<Double, Double> function, int interpolationPointsCount, double derivativeDelta) {
    List<LinkedHashMap<String, Function<Double, Double>>> mapList = new ArrayList<>();
    mapList.add(namedFunctions(function, derivativeDelta));
    mapList.add(namedInterpolationFunctions(function, interpolationPointsCount, derivativeDelta));
    mapList.add(namedFaultsFunctions(function, mapList.get(1).get("interpolateFunction"), derivativeDelta));
    return mapList;
  }

  public LinkedHashMap<String, Function<Double, Double>> namedFunctions(Function<Double, Double> function, double derivativeDelta) {
    LinkedHashMap<String, Function<Double, Double>> map = new LinkedHashMap<>();
    map.put("function",
      function
    );
    map.put("firstDerivativeFunction",
      derivativeService.firstDerivative(function, derivativeDelta)
    );
    map.put("secondDerivativeFunction",
      derivativeService.secondDerivative(function, derivativeDelta)
    );
    map.put("thirdDerivativeFunction",
      derivativeService.thirdDerivative(function, derivativeDelta)
    );
    map.put("fourthDerivativeFunction",
      derivativeService.fourthDerivative(function, derivativeDelta)
    );
    return map;
  }

  public LinkedHashMap<String, Function<Double, Double>> namedInterpolationFunctions(Function<Double, Double> function, int pointsCount, double derivativeDelta) {
    Function<Double, Double> interpolationFunction = interpolateFunction(function, pointsCount);
    LinkedHashMap<String, Function<Double, Double>> map = new LinkedHashMap<>();
    map.put("interpolateFunction",
      interpolationFunction
    );
    map.put("firstDerivativeInterpolationFunction",
      derivativeService.firstDerivative(interpolationFunction, derivativeDelta)
    );
    map.put("secondDerivativeInterpolationFunction",
      derivativeService.secondDerivative(interpolationFunction, derivativeDelta)
    );
    map.put("thirdDerivativeInterpolationFunction",
      derivativeService.thirdDerivative(interpolationFunction, derivativeDelta)
    );
    map.put("fourthDerivativeInterpolationFunction",
      derivativeService.fourthDerivative(interpolationFunction, derivativeDelta)
    );
    return map;
  }

  public LinkedHashMap<String, Function<Double, Double>> namedFaultsFunctions(Function<Double, Double> function, Function<Double, Double> interpolationFunction, double derivativeDelta) {
    Function<Double, Double> faultsFunction = x -> interpolationFunction.apply(x) - function.apply(x);

    LinkedHashMap<String, Function<Double, Double>> map = new LinkedHashMap<>();
    map.put("faultsFunction",
      faultsFunction
    );
    map.put("firstDerivativeFaults",
      (Double x) -> derivativeService.firstDerivative(interpolationFunction, derivativeDelta).apply(x)
      - derivativeService.firstDerivative(function, derivativeDelta).apply(x)
    );
    map.put("secondDerivativeFaults",
      (Double x) -> derivativeService.secondDerivative(interpolationFunction, derivativeDelta).apply(x)
      - derivativeService.secondDerivative(function, derivativeDelta).apply(x)
    );
    map.put("thirdDerivativeFaults",
      (Double x) -> derivativeService.thirdDerivative(interpolationFunction, derivativeDelta).apply(x)
      - derivativeService.thirdDerivative(function, derivativeDelta).apply(x)
    );
    map.put("fourthDerivativeFaults",
      (Double x) -> derivativeService.fourthDerivative(interpolationFunction, derivativeDelta).apply(x)
      - derivativeService.fourthDerivative(function, derivativeDelta).apply(x)
    );
    return map;
  }
}
