package com.jacob.asciitest.ascii;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer {

  private static final String NEW_LINE = "\n";
  /* DEFAULT VALUES */
  public static final double R_MULTIPLIER = 0.299;
  public static final double G_MULTIPLIER = 0.587;
  public static final double B_MULTIPLIER = 0.114;

  private BufferedImage image;
  private String ascii;
  private int width;
  private int height;

  private double r_multiplier = R_MULTIPLIER;
  private double g_multiplier = G_MULTIPLIER;
  private double b_multiplier = B_MULTIPLIER;

  /**
   * Draws the image as an ascii art picture
   *
   * @param imageInput the image selected to draw
   * @return the string equivalent of the image in ascii
   */
  public String draw(final BufferedImage imageInput) {
    if(imageInput == null) return "";
    this.image = imageInput;
    StringBuilder builder = new StringBuilder((image.getWidth() + 1) * image.getHeight());
    width = image.getWidth();
    height = image.getHeight();
    for(int i = 0; i < image.getHeight(); i++) {
      if(builder.length() != 0)
        builder.append(NEW_LINE);
      for(int j = 0; j < image.getWidth(); j++) {
        Color pixel = new Color(image.getRGB(j, i));
        double greyScaleValue = calcGreyScaleValue(pixel);
        final char character = getCharacter(greyScaleValue);
        builder.append(character).append(character);
      }
    }
    ascii = builder.toString();
    return builder.toString();
  }

  /**
   * Calculates the greyscale value of a pixel using multiplier values found online
   *
   * @param pixel the colour of th pixel
   * @return the double greyscale value
   */
  private double calcGreyScaleValue(Color pixel) {
    return pixel.getRed() * r_multiplier + pixel.getGreen() * g_multiplier + pixel.getBlue() *
      b_multiplier;
  }

  /**
   * Grabs the relevant character from the AsciiValues enum based on the greyscale
   * value of a given pixel
   *
   * @param greyscale the greyscale value of the pixel
   * @return the character to replace the pixel with
   */
  private char getCharacter(double greyscale) {
    for(AsciiValues value : AsciiValues.values()) {
      if(greyscale >= value.getMin()) {
        return value.getCharacter();
      }
    }
    return AsciiValues.BLANK.getCharacter();
  }

  public void setDefaultMultipliers() {
    r_multiplier = R_MULTIPLIER;
    g_multiplier = G_MULTIPLIER;
    b_multiplier = B_MULTIPLIER;
  }

  public String getAscii() {
    return ascii;
  }

  public void clear() {
    ascii = "";
    image = null;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BufferedImage getImage() {
    return image;
  }

  public double getR_multiplier() {
    return r_multiplier;
  }

  public void setR_multiplier(double r_multiplier) {
    this.r_multiplier = r_multiplier;
  }

  public double getG_multiplier() {
    return g_multiplier;
  }

  public void setG_multiplier(double g_multiplier) {
    this.g_multiplier = g_multiplier;
  }

  public double getB_multiplier() {
    return b_multiplier;
  }

  public void setB_multiplier(double b_multiplier) {
    this.b_multiplier = b_multiplier;
  }

}
