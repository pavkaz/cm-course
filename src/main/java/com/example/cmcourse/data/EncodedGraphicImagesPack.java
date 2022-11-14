package com.example.cmcourse.data;

public class EncodedGraphicImagesPack {
  private String func;
  private String interpolate;
  private String faults;

  public EncodedGraphicImagesPack(String func, String interpolate, String faults) {
    this.func = func;
    this.interpolate = interpolate;
    this.faults = faults;
  }

  public String getFunc() {
    return func;
  }

  public String getInterpolate() {
    return interpolate;
  }

  public String getFaults() {
    return faults;
  }
}
