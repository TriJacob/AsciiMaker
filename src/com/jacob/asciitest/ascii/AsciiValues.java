package com.jacob.asciitest.ascii;

public enum AsciiValues {
  BLANK(' ', 230),
  PERIOD('.', 200),
  ASTRIK('*', 180),
  COLON(':', 160),
  CIRCLE('o', 130),
  AMPERSAND('&', 100),
  EIGHT('&', 70),
  POUND('#', 50),
  AT('@', 0);

  private char character;
  private double min;

  AsciiValues(char character, double min) {
    this.character = character;
    this.min = min;
  }

  public char getCharacter() {
    return character;
  }

  public double getMin() {
    return min;
  }
}
