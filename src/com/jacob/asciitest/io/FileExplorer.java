package com.jacob.asciitest.io;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileExplorer
{

  private final FileDialog dialog = new FileDialog((java.awt.Frame) null);

  public FileExplorer(boolean filter, String title) {
    dialog.setTitle(title);
    if(filter)
      dialog.setFilenameFilter(getFilter());
    dialog.setVisible(true);
  }

  /**
   * Grabs the file and its directory chain
   */
  public String getDirectory() {
    return dialog.getDirectory() + File.separator + dialog.getFile();
  }

  /**
   * Gets the file selected, validates if it's an image file (if not it throws) and
   * if it is then it reads the file to a {@link BufferedImage} object.
   *
   * @return the buffered image object for the image file
   */
  public BufferedImage getSelectedImage() {
    File[] files = dialog.getFiles();
    for(File file : files) {
      if(dialog.getFile().equals(file.getName())) {
        BufferedImage image = validateFile(file);
        if(image == null) throw new IllegalArgumentException(dialog.getFile() + " is not a valid image file.");
        return image;
      }
    }
    return null;
  }

  /**
   * Builds the file filter for when we're opening a file
   * @return the built file filter
   */
  private FilenameFilter getFilter() {
    return (file, name) -> {
      String lower = name.toLowerCase();
      return lower.endsWith(".png") || lower.endsWith(".jpg");
    };
  }

  /**
   * Reads and validates a file as a buffered image
   *
   * @param file the file we're reading
   * @return the buffered image object that has been read
   */
  private BufferedImage validateFile(File file) {
    try {
      return ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}