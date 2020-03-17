package com.jacob.asciitest;

import com.jacob.asciitest.ascii.ImageRenderer;
import com.jacob.asciitest.frame.Window;

public class AsciiMaker
{
  public static void main(String[] args) {
    final ImageRenderer imageRenderer = new ImageRenderer();
    final Window window = new Window(imageRenderer);
  }
}