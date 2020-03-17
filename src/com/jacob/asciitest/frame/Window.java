package com.jacob.asciitest.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.jacob.asciitest.ascii.ImageRenderer;
import com.jacob.asciitest.io.FileExplorer;
import com.jacob.asciitest.io.FileWriter;

public class Window extends JFrame implements ActionListener {

  private static final String TITLE = "AsciiMaker";
  private static final String EMPTY = " ";
  private static final String LOAD = "Select an image!";
  private static final String SAVE = "Choose where to save the file!";

  private static final int WIDTH = 800;
  private static final int HEIGHT = 1200;

  private final transient ImageRenderer imageRenderer;
  private final JTextArea textArea;

  private JTextField red = new JTextField();
  private JTextField green = new JTextField();
  private JTextField blue = new JTextField();

  public Window(ImageRenderer imageRenderer) {
    this.imageRenderer = imageRenderer;
    textArea = new JTextArea(EMPTY, HEIGHT, WIDTH);

    setSize(HEIGHT, WIDTH);
    setTitle(TITLE);
    addContent();

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
    setVisible(true);
  }

  /**
   * Add all of the content to our frame so that
   * we can control the screen and how it works and
   * draw to the text area
   */
  private void addContent() {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    JMenuBar bar = new JMenuBar();
    addMenus(bar);

    textArea.setFont(new Font("Monospaced", Font.BOLD, 5));
    panel.add(bar, BorderLayout.NORTH);
    JScrollPane scroll = new JScrollPane(textArea);
    panel.add(scroll, BorderLayout.CENTER);
    add(panel);
  }

  /**
   * Adds the buttons to the menu bar so that we have
   * some actions to use
   *
   * @param bar the menu bar to add the buttons to
   */
  private void addMenus(JMenuBar bar) {
    addButton(bar, "Load", "load");
    addButton(bar, "Save", "save");
    addButton(bar, "Clear", "clear");
    addButton(bar, "Exit", "exit");
    addTextArea(bar, red, "red", String.valueOf(ImageRenderer.R_MULTIPLIER));
    addTextArea(bar, green, "green", String.valueOf(ImageRenderer.G_MULTIPLIER));
    addTextArea(bar, blue, "blue", String.valueOf(ImageRenderer.B_MULTIPLIER));
    addButton(bar, "Reset", "reset");
  }

  /**
   * Sets up the text area details and gives them default values
   */
  private void addTextArea(JMenuBar bar, JTextField field, String command, String initial) {
    field.setEditable(true);
    field.addActionListener(this);
    field.setActionCommand(command);
    field.setSize(10, 100);
    field.setText(initial);
    bar.add(field);
  }

  /**
   * A quick method for adding a button with an action
   *
   * @param menu the menu to add the button to
   * @param text the text on the button
   * @param action the action string name
   */
  private void addButton(JMenuBar menu, String text, String action) {
    JButton button = new JButton(text);
    button.addActionListener(this);
    button.setActionCommand(action);
    button.setHorizontalAlignment(SwingConstants.LEFT);
    menu.add(button);
  }

  /**
   * Invoke off of the swing thread to update the text
   * of the text area with our new ascii or with blank
   *
   * @param text the text to write
   * @param height the height of the image
   * @param width the width of the image
   */
  public void updateText(String text, int height, int width) {
    SwingUtilities.invokeLater(() ->  {
      textArea.setText(text);
      textArea.setSize(height, width);
    });
  }

  /**
   * Clears the ascii from the image renderrer and the text
   * area
   */
  private void updateText() {
    imageRenderer.clear();
    updateText("", HEIGHT, WIDTH);
  }

  /**
   * Parse the input of a multiplier field to a double with a default value
   */
  private double parseColourMultiplier(JTextField field, double def) {
    try {
      return (Double.parseDouble(field.getText()));
    } catch(NullPointerException e) {
      e.printStackTrace();
    }
    return def;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String command = actionEvent.getActionCommand();
    if("load".equals(command)) {
      FileExplorer load = new FileExplorer(true, LOAD);
      final String ascii = imageRenderer.draw(load.getSelectedImage());
      if(ascii != null && !ascii.isEmpty())
        updateText(ascii, imageRenderer.getHeight(), imageRenderer.getWidth());
    }
    if("save".equals(command) && (imageRenderer.getAscii() != null && !imageRenderer.getAscii().isEmpty())) {
      FileExplorer save = new FileExplorer(false, SAVE);
      FileWriter.write(save.getDirectory(), imageRenderer.getAscii());
    }
    if("clear".equals(command)) {
      updateText();
    }
    if("exit".equals(command)) {
      System.exit(0);
    }
    if("reset".equals(command)) {
      red.setText(String.valueOf(ImageRenderer.R_MULTIPLIER));
      green.setText(String.valueOf(ImageRenderer.G_MULTIPLIER));
      blue.setText(String.valueOf(ImageRenderer.B_MULTIPLIER));
      imageRenderer.setDefaultMultipliers();
      updateText(imageRenderer.draw(imageRenderer.getImage()), imageRenderer.getHeight(), imageRenderer.getWidth());
    }
    if("red".equals(command)) {
        imageRenderer.setR_multiplier(parseColourMultiplier(red, ImageRenderer.R_MULTIPLIER));
        updateText(imageRenderer.draw(imageRenderer.getImage()), imageRenderer.getHeight(), imageRenderer.getWidth());
    }
    if("green".equals(command)) {
      imageRenderer.setG_multiplier(parseColourMultiplier(green, ImageRenderer.G_MULTIPLIER));
      updateText(imageRenderer.draw(imageRenderer.getImage()), imageRenderer.getHeight(), imageRenderer.getWidth());
    }
    if("blue".equals(command)) {
      imageRenderer.setB_multiplier(parseColourMultiplier(blue, ImageRenderer.B_MULTIPLIER));
      updateText(imageRenderer.draw(imageRenderer.getImage()), imageRenderer.getHeight(), imageRenderer.getWidth());
    }
  }

}
