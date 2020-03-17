package com.jacob.asciitest.io;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {

  private static final String FILE_EXTENSION = ".txt";

  /**
   * Writes to a file and then immediately opens it
   *
   * @param dir the file and directory chain
   * @param ascii the ascii to write to the file
   */
  public static void write(String dir, String ascii) {
    String directory = dir + FILE_EXTENSION;
    String replaced = ascii.replace("\n", System.getProperty("line.separator"));
    try(PrintWriter out = new PrintWriter(directory)) {
      out.print(replaced);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    File file = new File(directory);
    Desktop desktop = Desktop.getDesktop();
    try {
      desktop.open(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
