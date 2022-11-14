package com.example.cmcourse.data;

public class GraphicImageSettings {
  private int preferredWidth;
  private int preferredHeight;
  private int borderGap;
  private int xStrokeCount;
  private int yStrokeCount;
  private int xMax;
  private int yMax;

  public static class Builder {
    private int preferredWidth = 400;
    private int preferredHeight = 400;
    private int borderGap = 10;
    private int xStrokeCount = 20;
    private int yStrokeCount = 20;
    private int xMax = 20;
    private int yMax = 20;

    public Builder setPreferredWidth(int preferredWidth) {
      this.preferredWidth = preferredWidth;
      return this;
    }

    public Builder setPreferredHeight(int preferredHeight) {
      this.preferredHeight = preferredHeight;
      return this;
    }

    public Builder setBorderGap(int borderGap) {
      this.borderGap = borderGap;
      return this;
    }

    public Builder setxStrokeCount(int xStrokeCount) {
      this.xStrokeCount = xStrokeCount;
      return this;
    }

    public Builder setyStrokeCount(int yStrokeCount) {
      this.yStrokeCount = yStrokeCount;
      return this;
    }

    public Builder setxMax(int xMax) {
      this.xMax = xMax;
      return this;
    }

    public Builder setyMax(int yMax) {
      this.yMax = yMax;
      return this;
    }

    public GraphicImageSettings build() {
      return new GraphicImageSettings(this);
    }
  }

  private GraphicImageSettings(Builder builder) {
    this.preferredWidth = builder.preferredWidth;
    this.preferredHeight = builder.preferredHeight;
    this.borderGap = builder.borderGap;
    this.xStrokeCount = builder.xStrokeCount;
    this.yStrokeCount = builder.yStrokeCount;
    this.xMax = builder.xMax;
    this.yMax = builder.yMax;
  }

  public int getPreferredWidth() {
    return preferredWidth;
  }

  public int getPreferredHeight() {
    return preferredHeight;
  }

  public int getBorderGap() {
    return borderGap;
  }

  public int getxStrokeCount() {
    return xStrokeCount;
  }

  public int getyStrokeCount() {
    return yStrokeCount;
  }

  public int getxMax() {
    return xMax;
  }

  public int getyMax() {
    return yMax;
  }
}
